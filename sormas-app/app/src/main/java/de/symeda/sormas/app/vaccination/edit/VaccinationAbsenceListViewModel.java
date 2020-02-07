package de.symeda.sormas.app.vaccination.edit;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PositionalDataSource;

import java.util.List;

import de.symeda.sormas.app.backend.common.DatabaseHelper;
import de.symeda.sormas.app.backend.vaccination.VaccinationAbsence;
import de.symeda.sormas.app.backend.vaccination.VaccinationTallySheet;

public class VaccinationAbsenceListViewModel extends ViewModel {

    private LiveData<PagedList<VaccinationAbsence>> absences;
    private VaccinationAbsenceDataFactory factory;

    public void initializeViewModel(VaccinationTallySheet tallySheet) {
        factory = new VaccinationAbsenceDataFactory();
        factory.setVaccinationTallySheet(tallySheet);

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(16)
                .setPageSize(8).build();

        LivePagedListBuilder builder = new LivePagedListBuilder(factory, config);
        absences = builder.build();
    }

    public LiveData<PagedList<VaccinationAbsence>> getAbsences() {
        return absences;
    }


    void reloadList() {
        if (absences.getValue() != null) {
            absences.getValue().getDataSource().invalidate();
            if (!absences.getValue().isEmpty()) {
                absences.getValue().loadAround(0);
            }
        }
    }

    public static class VaccinationAbsenceDataSource extends PositionalDataSource<VaccinationAbsence> {

        VaccinationTallySheet tallySheet;

        VaccinationAbsenceDataSource(VaccinationTallySheet tallySheet) {
            this.tallySheet = tallySheet;
        }

        @Override
        public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<VaccinationAbsence> callback) {
            long totalCount = DatabaseHelper.getVaccinationAbsenceDao().countByVaccinationTallySheet(tallySheet);
            int offset = params.requestedStartPosition;
            int count = params.requestedLoadSize;
            if (offset + count > totalCount) {
                offset = (int) Math.max(0, totalCount - count);
            }
            List<VaccinationAbsence> absences = DatabaseHelper.getVaccinationAbsenceDao().queryByVaccinationTallySheet(tallySheet, (long) offset, (long) count);
            callback.onResult(absences, offset, (int) totalCount);
        }

        @Override
        public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<VaccinationAbsence> callback) {
            List<VaccinationAbsence> absences = DatabaseHelper.getVaccinationAbsenceDao().queryByVaccinationTallySheet(tallySheet, (long) params.startPosition, (long) params.loadSize);
            callback.onResult(absences);
        }

    }

    public static class VaccinationAbsenceDataFactory extends DataSource.Factory {

        private MutableLiveData<VaccinationAbsenceDataSource> mutableDataSource;
        private VaccinationAbsenceDataSource dataSource;
        private VaccinationTallySheet tallySheet;

        VaccinationAbsenceDataFactory() {
            this.mutableDataSource = new MutableLiveData<>();
        }

        @NonNull
        @Override
        public DataSource create() {
            dataSource = new VaccinationAbsenceDataSource(tallySheet);
            mutableDataSource.postValue(dataSource);
            return dataSource;
        }

        void setVaccinationTallySheet(VaccinationTallySheet tallySheet) {
            this.tallySheet = tallySheet;
        }

    }

}
