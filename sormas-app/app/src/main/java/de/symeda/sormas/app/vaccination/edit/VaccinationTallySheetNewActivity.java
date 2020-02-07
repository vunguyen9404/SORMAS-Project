package de.symeda.sormas.app.vaccination.edit;

import android.content.Context;
import android.os.AsyncTask;
import android.view.Menu;

import de.symeda.sormas.api.utils.ValidationException;
import de.symeda.sormas.app.BaseEditActivity;
import de.symeda.sormas.app.BaseEditFragment;
import de.symeda.sormas.app.R;
import de.symeda.sormas.app.backend.common.DaoException;
import de.symeda.sormas.app.backend.common.DatabaseHelper;
import de.symeda.sormas.app.backend.vaccination.VaccinationTallySheet;
import de.symeda.sormas.app.component.menu.PageMenuItem;
import de.symeda.sormas.app.component.validation.FragmentValidator;
import de.symeda.sormas.app.core.async.AsyncTaskResult;
import de.symeda.sormas.app.core.async.SavingAsyncTask;
import de.symeda.sormas.app.core.async.TaskResultHolder;
import de.symeda.sormas.app.core.notification.NotificationHelper;
import de.symeda.sormas.app.vaccination.VaccinationSection;

import static de.symeda.sormas.app.core.notification.NotificationType.ERROR;
import static de.symeda.sormas.app.core.notification.NotificationType.WARNING;

public class VaccinationTallySheetNewActivity extends BaseEditActivity<VaccinationTallySheet> {

    public static final String TAG = VaccinationTallySheetNewActivity.class.getSimpleName();

    private AsyncTask saveTask;

    public static void startActivity(Context fromActivity) {
        BaseEditActivity.startActivity(fromActivity, VaccinationTallySheetNewActivity.class, null);
    }

    @Override
    protected VaccinationTallySheet queryRootEntity(String recordUuid) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected VaccinationTallySheet buildRootEntity() {
        VaccinationTallySheet tallySheet = DatabaseHelper.getVaccinationTallySheetDao().build();
        return tallySheet;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        getSaveMenu().setTitle(R.string.action_save_vaccination_tally_sheet);
        return result;
    }

    @Override
    public Enum getPageStatus() {
        return null;
    }

    @Override
    protected BaseEditFragment buildEditFragment(PageMenuItem menuItem, VaccinationTallySheet activityRootData) {
        BaseEditFragment fragment = VaccinationTallySheetEditFragment.newInstance(activityRootData);
        fragment.setLiveValidationDisabled(true);
        return fragment;
    }

    @Override
    public void replaceFragment(BaseEditFragment f, boolean allowBackNavigation) {
        super.replaceFragment(f, allowBackNavigation);
        getActiveFragment().setLiveValidationDisabled(true);
    }

    @Override
    protected int getActivityTitle() {
        return R.string.heading_vaccination_tally_sheet_new;
    }

    @Override
    public void saveData() {

        if (saveTask != null) {
            NotificationHelper.showNotification(this, WARNING, getString(R.string.message_already_saving));
            return; // don't save multiple times
        }

        final VaccinationTallySheet sheetToSave = (VaccinationTallySheet) getActiveFragment().getPrimaryData();
        VaccinationTallySheetEditFragment fragment = (VaccinationTallySheetEditFragment) getActiveFragment();

        fragment.setLiveValidationDisabled(false);

        try {
            FragmentValidator.validate(getContext(), fragment.getContentBinding());
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
                    finish();
                    VaccinationTallySheetEditActivity.startActivity(getContext(), sheetToSave.getUuid(), VaccinationSection.HOUSE_CHILD_COUNTS);
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

}
