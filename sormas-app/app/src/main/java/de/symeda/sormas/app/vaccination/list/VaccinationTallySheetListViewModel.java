package de.symeda.sormas.app.vaccination.list;

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
import de.symeda.sormas.app.backend.vaccination.VaccinationTallySheet;

public class VaccinationTallySheetListViewModel extends ViewModel {

    private LiveData<PagedList<VaccinationTallySheet>> vaccinationTallySheets;

    public VaccinationTallySheetListViewModel() {
        VaccinationTallySheetDataFactory factory = new VaccinationTallySheetDataFactory();

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(16)
                .setPageSize(8).build();

        LivePagedListBuilder builder = new LivePagedListBuilder(factory, config);
        vaccinationTallySheets = builder.build();
    }

    public LiveData<PagedList<VaccinationTallySheet>> getVaccinationTallySheets() {
        return vaccinationTallySheets;
    }

    void notifyCriteriaUpdated() {
        if (vaccinationTallySheets.getValue() != null) {
            vaccinationTallySheets.getValue().getDataSource().invalidate();
            if (!vaccinationTallySheets.getValue().isEmpty()) {
                vaccinationTallySheets.getValue().loadAround(0);
            }
        }
    }

    public static class VaccinationTallySheetDataSource extends PositionalDataSource<VaccinationTallySheet> {

        @Override
        public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<VaccinationTallySheet> callback) {
            long totalCount = DatabaseHelper.getVaccinationTallySheetDao().count();
            int offset = params.requestedStartPosition;
            int count = params.requestedLoadSize;
            if (offset + count > totalCount) {
                offset = (int) Math.max(0, totalCount - count);
            }
            List<VaccinationTallySheet> sheets = DatabaseHelper.getVaccinationTallySheetDao().query(offset, count);
            callback.onResult(sheets, offset, (int) totalCount);
        }

        @Override
        public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<VaccinationTallySheet> callback) {
            List<VaccinationTallySheet> sheets = DatabaseHelper.getVaccinationTallySheetDao().query(params.startPosition, params.loadSize);
            callback.onResult(sheets);
        }

    }

    public static class VaccinationTallySheetDataFactory extends DataSource.Factory {

        private MutableLiveData<VaccinationTallySheetDataSource> mutableDataSource;
        private VaccinationTallySheetDataSource dataSource;

        VaccinationTallySheetDataFactory() {
            this.mutableDataSource = new MutableLiveData<>();
        }

        @NonNull
        @Override
        public DataSource create() {
            dataSource = new VaccinationTallySheetDataSource();
            mutableDataSource.postValue(dataSource);
            return dataSource;
        }

    }

}
