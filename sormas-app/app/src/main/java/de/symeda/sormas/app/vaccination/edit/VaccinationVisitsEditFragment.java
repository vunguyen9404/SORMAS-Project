package de.symeda.sormas.app.vaccination.edit;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.symeda.sormas.app.BaseEditFragment;
import de.symeda.sormas.app.R;
import de.symeda.sormas.app.backend.common.DaoException;
import de.symeda.sormas.app.backend.common.DatabaseHelper;
import de.symeda.sormas.app.backend.vaccination.VaccinationTallySheet;
import de.symeda.sormas.app.backend.vaccination.VaccinationVisit;
import de.symeda.sormas.app.component.dialog.ConfirmationDialog;
import de.symeda.sormas.app.core.NotificationContext;
import de.symeda.sormas.app.core.notification.NotificationHelper;
import de.symeda.sormas.app.databinding.FragmentVaccinationVisitsEditLayoutBinding;
import de.symeda.sormas.app.databinding.RowVaccinationVisitLayoutBinding;

import static de.symeda.sormas.app.BaseActivity.getActiveActivity;
import static de.symeda.sormas.app.core.notification.NotificationType.ERROR;
import static de.symeda.sormas.app.core.notification.NotificationType.SUCCESS;

public class VaccinationVisitsEditFragment extends BaseEditFragment<FragmentVaccinationVisitsEditLayoutBinding, VaccinationTallySheet, VaccinationTallySheet> {

    private VaccinationTallySheet parentTallySheet;
    private List<VaccinationVisit> visits;

    public static VaccinationVisitsEditFragment newInstance(VaccinationTallySheet parentTallySheet) {
        return newInstance(VaccinationVisitsEditFragment.class, null, parentTallySheet);
    }

    private void setupControls() {
        getContentBinding().addHouse.setOnClickListener(e -> {
            VaccinationVisit visit = DatabaseHelper.getVaccinationVisitDao().build(parentTallySheet);
            visits.add(visit);
            inflateVisit(visit);
        });

        getContentBinding().saveChildCounts.setOnClickListener(e -> {
            for (VaccinationVisit visit : visits) {
                try {
                    DatabaseHelper.getVaccinationVisitDao().saveAndSnapshot(visit);
                } catch (DaoException ex) {
                    NotificationHelper.showNotification((NotificationContext) getActivity(), ERROR, String.format(getResources().getString(R.string.message_save_error), getResources().getString(R.string.entity_child_counts)));
                    return;
                }
            }

            NotificationHelper.showNotification((NotificationContext) getActivity(), SUCCESS, String.format(getResources().getString(R.string.message_save_success), getResources().getString(R.string.entity_child_counts)));
            ((VaccinationTallySheetEditActivity) getActivity()).goToNextPage();
        });
    }

    private void loadVisits() {
        visits = DatabaseHelper.getVaccinationVisitDao().queryByVaccinationTallySheet(parentTallySheet);

        for (VaccinationVisit visit : visits) {
            inflateVisit(visit);
        }
    }

    private void inflateVisit(VaccinationVisit visit) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RowVaccinationVisitLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_vaccination_visit_layout, getContentBinding().visitsContent, true);
        binding.setData(visit);

        binding.buttonDelete.setOnClickListener(e -> {
            showDeleteConfirmationDialog(() -> {
                if (DatabaseHelper.getVaccinationVisitDao().queryUuid(visit.getUuid()) != null) {
                    try {
                        DatabaseHelper.getVaccinationVisitDao().delete(visit);
                    } catch (SQLException ex) {
                        NotificationHelper.showNotification((NotificationContext) getActivity(), ERROR, String.format(getResources().getString(R.string.message_delete_error), getResources().getString(R.string.entity_child_counts)));
                        return;
                    }
                }

                visits.remove(visit);
                getContentBinding().visitsContent.removeView(binding.getRoot());
                NotificationHelper.showNotification((NotificationContext) getActivity(), SUCCESS, String.format(getResources().getString(R.string.message_delete_success), getResources().getString(R.string.entity_child_counts)));
            });
        });
    }

    private void showDeleteConfirmationDialog(Runnable confirmCallback) {
        final ConfirmationDialog confirmationDialog = new ConfirmationDialog(getActiveActivity(),
                R.string.heading_delete_child_counts,
                R.string.message_delete_child_counts,
                R.string.yes,
                R.string.no);

        confirmationDialog.setPositiveCallback(() -> {
            confirmCallback.run();
        });
        confirmationDialog.setNegativeCallback(() -> {
        });

        confirmationDialog.show();
    }

    @Override
    protected String getSubHeadingTitle() {
        return getResources().getString(R.string.caption_vaccination_house_child_counts);
    }

    @Override
    public VaccinationTallySheet getPrimaryData() {
        return parentTallySheet;
    }

    @Override
    protected void prepareFragmentData() {
        parentTallySheet = getActivityRootData();
    }

    @Override
    public void onLayoutBinding(FragmentVaccinationVisitsEditLayoutBinding contentBinding) {
        visits = new ArrayList<>();

        setupControls();
        loadVisits();
    }

    @Override
    public void onAfterLayoutBinding(FragmentVaccinationVisitsEditLayoutBinding contentBinding) {

    }

    @Override
    public int getEditLayout() {
        return R.layout.fragment_vaccination_visits_edit_layout;
    }

    @Override
    public boolean isShowSaveAction() {
        return false;
    }

    @Override
    public boolean isShowDeleteAction() {
        return true;
    }

}
