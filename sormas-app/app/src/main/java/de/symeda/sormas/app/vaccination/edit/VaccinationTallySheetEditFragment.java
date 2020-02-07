package de.symeda.sormas.app.vaccination.edit;

import java.util.List;

import de.symeda.sormas.app.BaseEditFragment;
import de.symeda.sormas.app.R;
import de.symeda.sormas.app.backend.vaccination.VaccinationTallySheet;
import de.symeda.sormas.app.component.Item;
import de.symeda.sormas.app.databinding.FragmentVaccinationTallySheetEditLayoutBinding;
import de.symeda.sormas.app.util.DataUtils;

public class VaccinationTallySheetEditFragment extends BaseEditFragment<FragmentVaccinationTallySheetEditLayoutBinding, VaccinationTallySheet, VaccinationTallySheet> {

    private VaccinationTallySheet record;

    private List<Item> campaignDayList;

    public static VaccinationTallySheetEditFragment newInstance(VaccinationTallySheet activityRootData) {
        return newInstance(VaccinationTallySheetEditFragment.class, null, activityRootData);
    }

    @Override
    protected String getSubHeadingTitle() {
        return getResources().getString(R.string.caption_vaccination_tally_sheet_header);
    }

    @Override
    public VaccinationTallySheet getPrimaryData() {
        return record;
    }

    @Override
    protected void prepareFragmentData() {
        record = getActivityRootData();

        campaignDayList = DataUtils.getNumberItems(1, 5, true);
    }

    @Override
    public void onLayoutBinding(FragmentVaccinationTallySheetEditLayoutBinding contentBinding) {
        contentBinding.setData(record);
    }

    @Override
    public void onAfterLayoutBinding(FragmentVaccinationTallySheetEditLayoutBinding contentBinding) {
        contentBinding.vaccinationTallySheetCampaignDay.initializeSpinner(campaignDayList);
        contentBinding.vaccinationTallySheetDate.initializeDateField(getFragmentManager());
    }

    @Override
    public int getEditLayout() {
        return R.layout.fragment_vaccination_tally_sheet_edit_layout;
    }

    @Override
    public boolean isShowDeleteAction() {
        return true;
    }

}
