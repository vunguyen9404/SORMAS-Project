package de.symeda.sormas.app.vaccination.edit;

import android.content.Context;
import android.os.AsyncTask;
import android.view.Menu;

import java.sql.SQLException;
import java.util.List;

import de.symeda.sormas.api.utils.DataHelper;
import de.symeda.sormas.api.utils.ValidationException;
import de.symeda.sormas.app.BaseEditActivity;
import de.symeda.sormas.app.BaseEditFragment;
import de.symeda.sormas.app.R;
import de.symeda.sormas.app.backend.common.DaoException;
import de.symeda.sormas.app.backend.common.DatabaseHelper;
import de.symeda.sormas.app.backend.vaccination.VaccinationAbsence;
import de.symeda.sormas.app.backend.vaccination.VaccinationTallySheet;
import de.symeda.sormas.app.component.dialog.ConfirmationDialog;
import de.symeda.sormas.app.component.menu.PageMenuItem;
import de.symeda.sormas.app.component.validation.FragmentValidator;
import de.symeda.sormas.app.core.NotImplementedException;
import de.symeda.sormas.app.core.NotificationContext;
import de.symeda.sormas.app.core.async.AsyncTaskResult;
import de.symeda.sormas.app.core.async.SavingAsyncTask;
import de.symeda.sormas.app.core.async.TaskResultHolder;
import de.symeda.sormas.app.core.notification.NotificationHelper;
import de.symeda.sormas.app.vaccination.VaccinationSection;
import de.symeda.sormas.app.vaccination.list.VaccinationTallySheetListActivity;

import static de.symeda.sormas.app.core.notification.NotificationType.ERROR;
import static de.symeda.sormas.app.core.notification.NotificationType.SUCCESS;
import static de.symeda.sormas.app.core.notification.NotificationType.WARNING;

public class VaccinationTallySheetEditActivity extends BaseEditActivity<VaccinationTallySheet> {

    public static final String TAG = VaccinationTallySheetEditActivity.class.getSimpleName();

    private AsyncTask saveTask;

    public static void startActivity(Context context, String rootUuid, VaccinationSection section) {
        BaseEditActivity.startActivity(context, VaccinationTallySheetEditActivity.class, buildBundle(rootUuid, section));
    }

    @Override
    protected VaccinationTallySheet queryRootEntity(String recordUuid) {
        return DatabaseHelper.getVaccinationTallySheetDao().queryUuidWithEmbedded(recordUuid);
    }

    @Override
    protected VaccinationTallySheet buildRootEntity() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<PageMenuItem> getPageMenuData() {
        return PageMenuItem.fromEnum(VaccinationSection.values(), getContext());
    }

    @Override
    public Enum getPageStatus() {
        return null;
    }

    @Override
    protected BaseEditFragment buildEditFragment(PageMenuItem menuItem, VaccinationTallySheet activityRootData) {
        VaccinationSection section = VaccinationSection.fromOrdinal(menuItem.getPosition());
        BaseEditFragment fragment;
        switch (section) {
            case TALLY_SHEET_REPORT:
                fragment = VaccinationTallySheetReportFragment.newInstance(activityRootData);
                break;
            case TALLY_SHEET_HEADER:
                fragment = VaccinationTallySheetEditFragment.newInstance(activityRootData);
                break;
            case HOUSE_CHILD_COUNTS:
                fragment = VaccinationVisitsEditFragment.newInstance(activityRootData);
                break;
            case MISSED_CHILDREN:
                fragment = VaccinationAbsenceListFragment.newInstance(activityRootData);
                break;
            case TALLY_SHEET_FOOTER:
                fragment = VaccinationTallySheetFooterEditFragment.newInstance(activityRootData);
                break;
            default:
                throw new IndexOutOfBoundsException(DataHelper.toStringNullable(section));
        }
        return fragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        getSaveMenu().setTitle(R.string.action_save_vaccination_tally_sheet);
        return result;
    }

    @Override
    protected int getActivityTitle() {
        return R.string.heading_vaccination_tally_sheet_edit;
    }

    @Override
    public void saveData() {
        if (saveTask != null) {
            NotificationHelper.showNotification(this, WARNING, getString(R.string.message_already_saving));
            return; // don't save multiple times
        }

        final VaccinationTallySheet sheetToSave = getStoredRootEntity();

        try {
            FragmentValidator.validate(getContext(), getActiveFragment().getContentBinding());
        } catch (ValidationException e) {
            NotificationHelper.showNotification(this, ERROR, e.getMessage());
            return;
        }

        saveTask = new SavingAsyncTask(getRootView(), sheetToSave) {

            @Override
            protected void onPreExecute() {
                showPreloader();
            }

            @Override
            public void doInBackground(TaskResultHolder resultHolder) throws DaoException {
                DatabaseHelper.getVaccinationTallySheetDao().saveAndSnapshot(sheetToSave);
            }

            @Override
            protected void onPostExecute(AsyncTaskResult<TaskResultHolder> taskResult) {
                hidePreloader();
                super.onPostExecute(taskResult);
                if (taskResult.getResultStatus().isSuccess()) {
                    goToNextPage();
                } else {
                    onResume(); // reload data
                }
                saveTask = null;
            }
        }.executeOnThreadPool();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (saveTask != null && !saveTask.isCancelled())
            saveTask.cancel(true);
    }

    @Override
    public void goToNewView() {
        VaccinationSection activeSection = VaccinationSection.fromOrdinal(getActivePage().getPosition());

        if (activeSection == VaccinationSection.MISSED_CHILDREN) {
            VaccinationAbsence newAbsence = DatabaseHelper.getVaccinationAbsenceDao().build(getStoredRootEntity());
            buildVaccinationAbsenceDialog(newAbsence, () -> {
                ((VaccinationAbsenceListFragment) getActiveFragment()).getModel().reloadList();
            });
        }
    }

    @Override
    public void delete() {
        final ConfirmationDialog confirmationDialog = new ConfirmationDialog(getActiveActivity(),
            R.string.heading_delete_tally_sheet,
            R.string.message_delete_tally_sheet,
            R.string.yes,
            R.string.no);

        confirmationDialog.setPositiveCallback(() -> {
            try {
                DatabaseHelper.getVaccinationTallySheetDao().delete(getStoredRootEntity());
                NotificationHelper.showNotification(this, SUCCESS, String.format(getResources().getString(R.string.message_delete_success), getResources().getString(R.string.entity_vaccination_tally_sheet)));
                VaccinationTallySheetListActivity.startActivity(this);
                this.finish();
            } catch (SQLException ex) {
                NotificationHelper.showNotification(this, ERROR, String.format(getResources().getString(R.string.message_delete_error), getResources().getString(R.string.entity_vaccination_tally_sheet)));
            }
        });

        confirmationDialog.setNegativeCallback(() -> {
        });

        confirmationDialog.show();
    }

    public void buildVaccinationAbsenceDialog(VaccinationAbsence absence, Runnable saveCallback) {
        VaccinationAbsenceDialog dialog = new VaccinationAbsenceDialog(this, absence);
        dialog.setPositiveCallback(() -> {
            try {
                DatabaseHelper.getVaccinationAbsenceDao().saveAndSnapshot(absence);
            } catch (DaoException ex) {
                NotificationHelper.showNotification(this, ERROR, String.format(getResources().getString(R.string.message_save_error), getResources().getString(R.string.entity_missed_child)));
                return;
            }

            NotificationHelper.showNotification(this, SUCCESS, String.format(getResources().getString(R.string.message_save_success), getResources().getString(R.string.entity_missed_child)));
            if (saveCallback != null) {
                saveCallback.run();
            }
        });
        dialog.show();
    }

}
