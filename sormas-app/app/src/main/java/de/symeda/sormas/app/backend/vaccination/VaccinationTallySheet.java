package de.symeda.sormas.app.backend.vaccination;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import de.symeda.sormas.app.backend.common.AbstractDomainObject;
import de.symeda.sormas.app.backend.region.District;
import de.symeda.sormas.app.backend.region.Region;

@Entity(name = VaccinationTallySheet.TABLE_NAME)
@DatabaseTable(tableName = VaccinationTallySheet.TABLE_NAME)
public class VaccinationTallySheet extends AbstractDomainObject {

    public static final String TABLE_NAME = "vaccinationTallySheet";
    public static final String I18N_PREFIX = "VaccinationTallySheet";

    public static final String CAMPAIGN_DAY = "campaignDay";
    public static final String DATE = "date";
    public static final String CLUSTER_NUMBER = "clusterNumber";
    public static final String TEAM_NUMBER = "teamNumber";

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Region region;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private District district;

    @Column(length = 512)
    private String village;

    @DatabaseField
    private Integer campaignDay;

    @DatabaseField(dataType = DataType.DATE_LONG)
    private Date date;

    @Column(length = 512)
    private String clusterNumber;

    @Column(length = 512)
    private String teamNumber;

    @Column(length = 512)
    private String clusterSupervisor;

    @Column(length = 512)
    private String communicationSupervisor;

    @Column(length = 512)
    private String volunteer1;

    @Column(length = 512)
    private String volunteer2;

    @Column(length = 512)
    private String socialMobilizer;

    @DatabaseField
    private Integer numberOfVialsReceivedFull;

    @DatabaseField
    private Integer numberOfVialsReceivedOpen;

    @Column(length = 512)
    private String firstHouseOwnerDetails;

    @Column(length = 512)
    private String lastHouseOwnerDetails;

    @DatabaseField
    private Integer numberOfVaccinatedChildren;

    @DatabaseField
    private Integer numberOfVaccinatedNomadChildren;

    @DatabaseField
    private Integer numberOfVialsReturnedFull;

    @DatabaseField
    private Integer numberOfVialsReturnedOpen;

    @DatabaseField
    private Integer numberOfVialsReturnedEmpty;

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public Integer getCampaignDay() {
        return campaignDay;
    }

    public void setCampaignDay(Integer campaignDay) {
        this.campaignDay = campaignDay;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getClusterNumber() {
        return clusterNumber;
    }

    public void setClusterNumber(String clusterNumber) {
        this.clusterNumber = clusterNumber;
    }

    public String getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(String teamNumber) {
        this.teamNumber = teamNumber;
    }

    public String getClusterSupervisor() {
        return clusterSupervisor;
    }

    public void setClusterSupervisor(String clusterSupervisor) {
        this.clusterSupervisor = clusterSupervisor;
    }

    public String getCommunicationSupervisor() {
        return communicationSupervisor;
    }

    public void setCommunicationSupervisor(String communicationSupervisor) {
        this.communicationSupervisor = communicationSupervisor;
    }

    public String getVolunteer1() {
        return volunteer1;
    }

    public void setVolunteer1(String volunteer1) {
        this.volunteer1 = volunteer1;
    }

    public String getVolunteer2() {
        return volunteer2;
    }

    public void setVolunteer2(String volunteer2) {
        this.volunteer2 = volunteer2;
    }

    public String getSocialMobilizer() {
        return socialMobilizer;
    }

    public void setSocialMobilizer(String socialMobilizer) {
        this.socialMobilizer = socialMobilizer;
    }

    public Integer getNumberOfVialsReceivedFull() {
        return numberOfVialsReceivedFull;
    }

    public void setNumberOfVialsReceivedFull(Integer numberOfVialsReceivedFull) {
        this.numberOfVialsReceivedFull = numberOfVialsReceivedFull;
    }

    public Integer getNumberOfVialsReceivedOpen() {
        return numberOfVialsReceivedOpen;
    }

    public void setNumberOfVialsReceivedOpen(Integer numberOfVialsReceivedOpen) {
        this.numberOfVialsReceivedOpen = numberOfVialsReceivedOpen;
    }

    public String getFirstHouseOwnerDetails() {
        return firstHouseOwnerDetails;
    }

    public void setFirstHouseOwnerDetails(String firstHouseOwnerDetails) {
        this.firstHouseOwnerDetails = firstHouseOwnerDetails;
    }

    public String getLastHouseOwnerDetails() {
        return lastHouseOwnerDetails;
    }

    public void setLastHouseOwnerDetails(String lastHouseOwnerDetails) {
        this.lastHouseOwnerDetails = lastHouseOwnerDetails;
    }

    public Integer getNumberOfVaccinatedChildren() {
        return numberOfVaccinatedChildren;
    }

    public void setNumberOfVaccinatedChildren(Integer numberOfVaccinatedChildren) {
        this.numberOfVaccinatedChildren = numberOfVaccinatedChildren;
    }

    public Integer getNumberOfVaccinatedNomadChildren() {
        return numberOfVaccinatedNomadChildren;
    }

    public void setNumberOfVaccinatedNomadChildren(Integer numberOfVaccinatedNomadChildren) {
        this.numberOfVaccinatedNomadChildren = numberOfVaccinatedNomadChildren;
    }

    public Integer getNumberOfVialsReturnedFull() {
        return numberOfVialsReturnedFull;
    }

    public void setNumberOfVialsReturnedFull(Integer numberOfVialsReturnedFull) {
        this.numberOfVialsReturnedFull = numberOfVialsReturnedFull;
    }

    public Integer getNumberOfVialsReturnedOpen() {
        return numberOfVialsReturnedOpen;
    }

    public void setNumberOfVialsReturnedOpen(Integer numberOfVialsReturnedOpen) {
        this.numberOfVialsReturnedOpen = numberOfVialsReturnedOpen;
    }

    public Integer getNumberOfVialsReturnedEmpty() {
        return numberOfVialsReturnedEmpty;
    }

    public void setNumberOfVialsReturnedEmpty(Integer numberOfVialsReturnedEmpty) {
        this.numberOfVialsReturnedEmpty = numberOfVialsReturnedEmpty;
    }

    @Override
    public String getI18nPrefix() {
        return I18N_PREFIX;
    }

}
