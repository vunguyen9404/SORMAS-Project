package de.symeda.sormas.app.backend.vaccination;

public class VaccinationTallySheetReport {

    private int visitCount;
    private int residentChildrenCount;
    private int guestChildrenCount;
    private int vaccinatedChildrenCount;
    private int missedChildrenCount;
    private int vaccinatedOutsideChildrenCount;
    private int vaccinatedNomadChildrenCount;
    private int rdcFoundVaccinatedCount;
    private int rdcVaccinatedByTeamCount;
    private int rdcRemainingCount;
    private int racFoundVaccinatedCount;
    private int racVaccinatedByTeamCount;
    private int racRemainingCount;
    private int nssFoundVaccinatedCount;
    private int nssVaccinatedByTeamCount;
    private int nssRemainingCount;
    private int refFoundVaccinatedCount;
    private int refVaccinatedByTeamCount;
    private int refRemainingCount;
    private int vialsReceivedFull;
    private int vialsReceivedOpen;
    private int vialsReturnedFull;
    private int vialsReturnedOpen;
    private int vialsReturnedEmpty;

    public VaccinationTallySheetReport(int visitCount, int residentChildrenCount, int guestChildrenCount,
                                       int vaccinatedChildrenCount, int missedChildrenCount, int vaccinatedOutsideChildrenCount,
                                       int vaccinatedNomadChildrenCount, int rdcFoundVaccinatedCount, int rdcVaccinatedByTeamCount,
                                       int rdcRemainingCount, int racFoundVaccinatedCount, int racVaccinatedByTeamCount, int racRemainingCount,
                                       int nssFoundVaccinatedCount, int nssVaccinatedByTeamCount, int nssRemainingCount,
                                       int refFoundVaccinatedCount, int refVaccinatedByTeamCount, int refRemainingCount,
                                       int vialsReceivedFull, int vialsReceivedOpen, int vialsReturnedFull,
                                       int vialsReturnedOpen, int vialsReturnedEmpty) {
        this.visitCount = visitCount;
        this.residentChildrenCount = residentChildrenCount;
        this.guestChildrenCount = guestChildrenCount;
        this.vaccinatedChildrenCount = vaccinatedChildrenCount;
        this.missedChildrenCount = missedChildrenCount;
        this.vaccinatedOutsideChildrenCount = vaccinatedOutsideChildrenCount;
        this.vaccinatedNomadChildrenCount = vaccinatedNomadChildrenCount;
        this.rdcFoundVaccinatedCount = rdcFoundVaccinatedCount;
        this.rdcVaccinatedByTeamCount = rdcVaccinatedByTeamCount;
        this.rdcRemainingCount = rdcRemainingCount;
        this.racFoundVaccinatedCount = racFoundVaccinatedCount;
        this.racVaccinatedByTeamCount = racVaccinatedByTeamCount;
        this.racRemainingCount = racRemainingCount;
        this.nssFoundVaccinatedCount = nssFoundVaccinatedCount;
        this.nssVaccinatedByTeamCount = nssVaccinatedByTeamCount;
        this.nssRemainingCount = nssRemainingCount;
        this.refFoundVaccinatedCount = refFoundVaccinatedCount;
        this.refVaccinatedByTeamCount = refVaccinatedByTeamCount;
        this.refRemainingCount = refRemainingCount;
        this.vialsReceivedFull = vialsReceivedFull;
        this.vialsReceivedOpen = vialsReceivedOpen;
        this.vialsReturnedFull = vialsReceivedFull;
        this.vialsReturnedOpen = vialsReturnedOpen;
        this.vialsReturnedEmpty = vialsReturnedEmpty;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public int getResidentChildrenCount() {
        return residentChildrenCount;
    }

    public void setResidentChildrenCount(int residentChildrenCount) {
        this.residentChildrenCount = residentChildrenCount;
    }

    public int getGuestChildrenCount() {
        return guestChildrenCount;
    }

    public void setGuestChildrenCount(int guestChildrenCount) {
        this.guestChildrenCount = guestChildrenCount;
    }

    public int getVaccinatedChildrenCount() {
        return vaccinatedChildrenCount;
    }

    public void setVaccinatedChildrenCount(int vaccinatedChildrenCount) {
        this.vaccinatedChildrenCount = vaccinatedChildrenCount;
    }

    public int getMissedChildrenCount() {
        return missedChildrenCount;
    }

    public void setMissedChildrenCount(int missedChildrenCount) {
        this.missedChildrenCount = missedChildrenCount;
    }

    public int getVaccinatedOutsideChildrenCount() {
        return vaccinatedOutsideChildrenCount;
    }

    public void setVaccinatedOutsideChildrenCount(int vaccinatedOutsideChildrenCount) {
        this.vaccinatedOutsideChildrenCount = vaccinatedOutsideChildrenCount;
    }

    public int getVaccinatedNomadChildrenCount() {
        return vaccinatedNomadChildrenCount;
    }

    public void setVaccinatedNomadChildrenCount(int vaccinatedNomadChildrenCount) {
        this.vaccinatedNomadChildrenCount = vaccinatedNomadChildrenCount;
    }

    public int getRdcFoundVaccinatedCount() {
        return rdcFoundVaccinatedCount;
    }

    public void setRdcFoundVaccinatedCount(int rdcFoundVaccinatedCount) {
        this.rdcFoundVaccinatedCount = rdcFoundVaccinatedCount;
    }

    public int getRdcVaccinatedByTeamCount() {
        return rdcVaccinatedByTeamCount;
    }

    public void setRdcVaccinatedByTeamCount(int rdcVaccinatedByTeamCount) {
        this.rdcVaccinatedByTeamCount = rdcVaccinatedByTeamCount;
    }

    public int getRdcRemainingCount() {
        return rdcRemainingCount;
    }

    public void setRdcRemainingCount(int rdcRemainingCount) {
        this.rdcRemainingCount = rdcRemainingCount;
    }

    public int getRacFoundVaccinatedCount() {
        return racFoundVaccinatedCount;
    }

    public void setRacFoundVaccinatedCount(int racFoundVaccinatedCount) {
        this.racFoundVaccinatedCount = racFoundVaccinatedCount;
    }

    public int getRacVaccinatedByTeamCount() {
        return racVaccinatedByTeamCount;
    }

    public void setRacVaccinatedByTeamCount(int racVaccinatedByTeamCount) {
        this.racVaccinatedByTeamCount = racVaccinatedByTeamCount;
    }

    public int getRacRemainingCount() {
        return racRemainingCount;
    }

    public void setRacRemainingCount(int racRemainingCount) {
        this.racRemainingCount = racRemainingCount;
    }

    public int getNssFoundVaccinatedCount() {
        return nssFoundVaccinatedCount;
    }

    public void setNssFoundVaccinatedCount(int nssFoundVaccinatedCount) {
        this.nssFoundVaccinatedCount = nssFoundVaccinatedCount;
    }

    public int getNssVaccinatedByTeamCount() {
        return nssVaccinatedByTeamCount;
    }

    public void setNssVaccinatedByTeamCount(int nssVaccinatedByTeamCount) {
        this.nssVaccinatedByTeamCount = nssVaccinatedByTeamCount;
    }

    public int getNssRemainingCount() {
        return nssRemainingCount;
    }

    public void setNssRemainingCount(int nssRemainingCount) {
        this.nssRemainingCount = nssRemainingCount;
    }

    public int getRefFoundVaccinatedCount() {
        return refFoundVaccinatedCount;
    }

    public void setRefFoundVaccinatedCount(int refFoundVaccinatedCount) {
        this.refFoundVaccinatedCount = refFoundVaccinatedCount;
    }

    public int getRefVaccinatedByTeamCount() {
        return refVaccinatedByTeamCount;
    }

    public void setRefVaccinatedByTeamCount(int refVaccinatedByTeamCount) {
        this.refVaccinatedByTeamCount = refVaccinatedByTeamCount;
    }

    public int getRefRemainingCount() {
        return refRemainingCount;
    }

    public void setRefRemainingCount(int refRemainingCount) {
        this.refRemainingCount = refRemainingCount;
    }

    public int getVialsReceivedFull() {
        return vialsReceivedFull;
    }

    public void setVialsReceivedFull(int vialsReceivedFull) {
        this.vialsReceivedFull = vialsReceivedFull;
    }

    public int getVialsReceivedOpen() {
        return vialsReceivedOpen;
    }

    public void setVialsReceivedOpen(int vialsReceivedOpen) {
        this.vialsReceivedOpen = vialsReceivedOpen;
    }

    public int getVialsReturnedFull() {
        return vialsReturnedFull;
    }

    public void setVialsReturnedFull(int vialsReturnedFull) {
        this.vialsReturnedFull = vialsReturnedFull;
    }

    public int getVialsReturnedOpen() {
        return vialsReturnedOpen;
    }

    public void setVialsReturnedOpen(int vialsReturnedOpen) {
        this.vialsReturnedOpen = vialsReturnedOpen;
    }

    public int getVialsReturnedEmpty() {
        return vialsReturnedEmpty;
    }

    public void setVialsReturnedEmpty(int vialsReturnedEmpty) {
        this.vialsReturnedEmpty = vialsReturnedEmpty;
    }
}
