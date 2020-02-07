package de.symeda.sormas.app.vaccination.edit;

import java.util.List;

import de.symeda.sormas.app.BaseEditFragment;
import de.symeda.sormas.app.R;
import de.symeda.sormas.app.backend.vaccination.VaccinationTallySheet;
import de.symeda.sormas.app.component.Item;
import de.symeda.sormas.app.databinding.FragmentVaccinationTallySheetEditLayoutBinding;
import de.symeda.sormas.app.databinding.FragmentVaccinationTallySheetFooterEditLayoutBinding;
import de.symeda.sormas.app.util.DataUtils;

public class VaccinationTallySheetFooterEditFragment extends BaseEditFragment<FragmentVaccinationTallySheetFooterEditLayoutBinding, VaccinationTallySheet, VaccinationTallySheet> {

    private VaccinationTallySheet record;

    public static VaccinationTallySheetFooterEditFragment newInstance(VaccinationTallySheet activityRootData) {
        return newInstance(VaccinationTallySheetFooterEditFragment.class, null, activityRootData);
    }

    @Override
    protected String getSubHeadingTitle() {
        return getResources().getString(R.string.caption_vaccination_tally_sheet_footer);
    }

    @Override
    public VaccinationTallySheet getPrimaryData() {
        return record;
    }

    @Override
    protected void prepareFragmentData() {
        record = getActivityRootData();
    }

    @Override
    public void onLayoutBinding(FragmentVaccinationTallySheetFooterEditLayoutBinding contentBinding) {
        contentBinding.setData(record);
    }

    @Override
    public void onAfterLayoutBinding(FragmentVaccinationTallySheetFooterEditLayoutBinding contentBinding) {
    }

    @Override
    public int getEditLayout() {
        return R.layout.fragment_vaccination_tally_sheet_footer_edit_layout;
    }

    @Override
    public boolean isShowDeleteAction() {
        return true;
    }

}
