package de.symeda.sormas.app.vaccination.edit;

import android.content.Context;

import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.FragmentActivity;

import java.util.List;

import de.symeda.sormas.api.utils.ValidationException;
import de.symeda.sormas.api.vaccination.AbsenceOutcome;
import de.symeda.sormas.api.vaccination.AbsenceReason;
import de.symeda.sormas.app.R;
import de.symeda.sormas.app.backend.vaccination.VaccinationAbsence;
import de.symeda.sormas.app.component.controls.ControlButtonType;
import de.symeda.sormas.app.component.dialog.AbstractDialog;
import de.symeda.sormas.app.component.validation.FragmentValidator;
import de.symeda.sormas.app.core.notification.NotificationHelper;
import de.symeda.sormas.app.databinding.DialogVaccinationAbsenceEditLayoutBinding;
import de.symeda.sormas.app.util.DataUtils;

import static de.symeda.sormas.app.core.notification.NotificationType.ERROR;

public class VaccinationAbsenceDialog extends AbstractDialog {

    public static final String TAG = VaccinationAbsenceDialog.class.getSimpleName();

    private VaccinationAbsence data;
    private DialogVaccinationAbsenceEditLayoutBinding contentBinding;

    VaccinationAbsenceDialog(final FragmentActivity activity, VaccinationAbsence absence) {
        super(activity, R.layout.dialog_root_layout, R.layout.dialog_vaccination_absence_edit_layout,
                R.layout.dialog_root_two_button_panel_layout,
                R.string.heading_missed_child, -1);

        this.data = absence;
    }

    @Override
    protected void setContentBinding(Context context, ViewDataBinding binding, String layoutName) {
        this.contentBinding = (DialogVaccinationAbsenceEditLayoutBinding) binding;
        contentBinding.setVariable(BR.data, data);
    }

    @Override
    protected void initializeContentView(ViewDataBinding rootBinding, ViewDataBinding buttonPanelBinding) {
        this.contentBinding.vaccinationAbsenceAbsenceReason.initializeSpinner(DataUtils.getEnumItems(AbsenceReason.class, true));
        this.contentBinding.vaccinationAbsenceAbsenceOutcome.initializeSpinner(DataUtils.getEnumItems(AbsenceOutcome.class, true));

        if (data.getId() == null) {
            setLiveValidationDisabled(true);
        }
    }

    @Override
    public void onPositiveClick() {
        setLiveValidationDisabled(false);
        try {
            FragmentValidator.validate(getContext(), contentBinding);
        } catch (ValidationException e) {
            NotificationHelper.showDialogNotification(VaccinationAbsenceDialog.this, ERROR, e.getMessage());
            return;
        }

        super.onPositiveClick();
    }

    @Override
    public boolean isRounded() {
        return true;
    }

    @Override
    public int getPositiveButtonText() {
        return R.string.action_save;
    }

    @Override
    public int getNegativeButtonText() {
        return R.string.action_discard;
    }

}
