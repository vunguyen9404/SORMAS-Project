package de.symeda.sormas.app.vaccination.edit;

import java.util.List;

import de.symeda.sormas.api.caze.Vaccination;
import de.symeda.sormas.app.BaseEditFragment;
import de.symeda.sormas.app.R;
import de.symeda.sormas.app.backend.common.DatabaseHelper;
import de.symeda.sormas.app.backend.vaccination.VaccinationTallySheet;
import de.symeda.sormas.app.backend.vaccination.VaccinationTallySheetReport;
import de.symeda.sormas.app.backend.vaccination.VaccinationVisit;
import de.symeda.sormas.app.component.Item;
import de.symeda.sormas.app.databinding.FragmentVaccinationTallySheetEditLayoutBinding;
import de.symeda.sormas.app.databinding.FragmentVaccinationTallySheetReportLayoutBinding;
import de.symeda.sormas.app.util.DataUtils;
import de.symeda.sormas.app.vaccination.VaccinationSection;

public class VaccinationTallySheetReportFragment extends BaseEditFragment<FragmentVaccinationTallySheetReportLayoutBinding, VaccinationTallySheet, VaccinationTallySheet> {

    private VaccinationTallySheet record;

    private List<Item> teamNumberList;

    public static VaccinationTallySheetReportFragment newInstance(VaccinationTallySheet activityRootData) {
        return newInstance(VaccinationTallySheetReportFragment.class, null, activityRootData);
    }

    @Override
    protected String getSubHeadingTitle() {
        return getResources().getString(R.string.caption_vaccination_tally_sheet_report);
    }

    @Override
    public VaccinationTallySheet getPrimaryData() {
        return record;
    }

    @Override
    protected void prepareFragmentData() {
        record = getActivityRootData();

        teamNumberList = DataUtils.toItems(DatabaseHelper.getVaccinationTallySheetDao().getTeamNumbers());
    }

    @Override
    public void onLayoutBinding(FragmentVaccinationTallySheetReportLayoutBinding contentBinding) {
        contentBinding.setData(record);
    }

    @Override
    public void onAfterLayoutBinding(FragmentVaccinationTallySheetReportLayoutBinding contentBinding) {
        contentBinding.vaccinationTallySheetTeamNumber.addValueChangedListener(e -> {
            contentBinding.vaccinationTallySheetCampaignDay.setSpinnerData(
                    DataUtils.toItems(DatabaseHelper.getVaccinationTallySheetDao().getCampaignDaysForTeam((String) e.getValue())));
        });
        contentBinding.vaccinationTallySheetTeamNumber.initializeSpinner(teamNumberList);
        contentBinding.vaccinationTallySheetCampaignDay.addValueChangedListener(e -> {
            if (e.getValue() != null) {
                VaccinationTallySheet selectedSheet = DatabaseHelper.getVaccinationTallySheetDao().queryByTeamNumberAndDay(
                        (String) contentBinding.vaccinationTallySheetTeamNumber.getValue(),
                        (Integer) e.getValue());
                if (selectedSheet != null && !selectedSheet.getUuid().equals(record.getUuid())) {
                    getActivity().finish();
                    VaccinationTallySheetEditActivity.startActivity(getContext(), selectedSheet.getUuid(), VaccinationSection.TALLY_SHEET_REPORT);
                }
            }
        });

        VaccinationTallySheetReport report = DatabaseHelper.getVaccinationTallySheetDao().generateReport(record);
        contentBinding.housesVisited.setText(String.valueOf(report.getVisitCount()));
        contentBinding.residentChildren.setText(String.valueOf(report.getResidentChildrenCount()));
        contentBinding.guestChildren.setText(String.valueOf(report.getGuestChildrenCount()));
        contentBinding.vaccinatedChildren.setText(String.valueOf(report.getVaccinatedChildrenCount()));
        contentBinding.missedChildren.setText(String.valueOf(report.getMissedChildrenCount()));
        contentBinding.vaccinatedChildrenOutside.setText(String.valueOf(report.getVaccinatedOutsideChildrenCount()));
        contentBinding.vaccinatedNomadChildren.setText(String.valueOf(report.getVaccinatedNomadChildrenCount()));
        contentBinding.missedRecordedRdc.setText(
                String.valueOf(report.getRdcFoundVaccinatedCount() +
                        report.getRdcVaccinatedByTeamCount() +
                        report.getRdcRemainingCount())
        );
        contentBinding.missedRecordedRac.setText(
                String.valueOf(report.getRacFoundVaccinatedCount() +
                        report.getRacVaccinatedByTeamCount() +
                        report.getRacRemainingCount())
        );
        contentBinding.missedRecordedNss.setText(
                String.valueOf(report.getNssFoundVaccinatedCount() +
                        report.getNssVaccinatedByTeamCount() +
                        report.getNssRemainingCount())
        );
        contentBinding.missedRecordedRef.setText(
                String.valueOf(report.getRefFoundVaccinatedCount() +
                        report.getRefVaccinatedByTeamCount() +
                        report.getRefRemainingCount())
        );
        contentBinding.missedFoundVaccinatedRdc.setText(String.valueOf(report.getRdcFoundVaccinatedCount()));
        contentBinding.missedFoundVaccinatedRac.setText(String.valueOf(report.getRacFoundVaccinatedCount()));
        contentBinding.missedFoundVaccinatedNss.setText(String.valueOf(report.getNssFoundVaccinatedCount()));
        contentBinding.missedFoundVaccinatedRef.setText(String.valueOf(report.getRefFoundVaccinatedCount()));
        contentBinding.missedVaccinatedByTeamRdc.setText(String.valueOf(report.getRdcVaccinatedByTeamCount()));
        contentBinding.missedVaccinatedByTeamRac.setText(String.valueOf(report.getRacVaccinatedByTeamCount()));
        contentBinding.missedVaccinatedByTeamNss.setText(String.valueOf(report.getNssVaccinatedByTeamCount()));
        contentBinding.missedVaccinatedByTeamRef.setText(String.valueOf(report.getRefVaccinatedByTeamCount()));
        contentBinding.missedRecoveredRdc.setText(String.valueOf(report.getRdcFoundVaccinatedCount() + report.getRdcVaccinatedByTeamCount()));
        contentBinding.missedRecoveredRac.setText(String.valueOf(report.getRacFoundVaccinatedCount() + report.getRacVaccinatedByTeamCount()));
        contentBinding.missedRecoveredNss.setText(String.valueOf(report.getNssFoundVaccinatedCount() + report.getNssVaccinatedByTeamCount()));
        contentBinding.missedRecoveredRef.setText(String.valueOf(report.getRefFoundVaccinatedCount() + report.getRefVaccinatedByTeamCount()));
        contentBinding.missedRemainingRdc.setText(String.valueOf(report.getRdcRemainingCount()));
        contentBinding.missedRemainingRac.setText(String.valueOf(report.getRacRemainingCount()));
        contentBinding.missedRemainingNss.setText(String.valueOf(report.getNssRemainingCount()));
        contentBinding.missedRemainingRef.setText(String.valueOf(report.getRefRemainingCount()));
        contentBinding.vaccinationChildrenTotal.setText(
                String.valueOf(report.getVaccinatedChildrenCount() +
                        report.getVaccinatedOutsideChildrenCount() +
                        report.getVaccinatedNomadChildrenCount() +
                        report.getRdcVaccinatedByTeamCount() +
                        report.getRacVaccinatedByTeamCount() +
                        report.getNssVaccinatedByTeamCount() +
                        report.getRefVaccinatedByTeamCount())
        );
        contentBinding.vialsReceivedFull.setText(String.valueOf(report.getVialsReceivedFull()));
        contentBinding.vialsReturnedFull.setText(String.valueOf(report.getVialsReturnedFull()));
        contentBinding.vialsReceivedOpen.setText(String.valueOf(report.getVialsReceivedOpen()));
        contentBinding.vialsReturnedOpen.setText(String.valueOf(report.getVialsReturnedOpen()));
        contentBinding.vialsReturnedEmpty.setText(String.valueOf(report.getVialsReturnedEmpty()));
    }

    @Override
    public int getEditLayout() {
        return R.layout.fragment_vaccination_tally_sheet_report_layout;
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
