package de.symeda.sormas.app.backend.vaccination;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.persistence.Column;
import javax.persistence.Entity;

import de.symeda.sormas.app.backend.common.AbstractDomainObject;

@Entity(name = VaccinationVisit.TABLE_NAME)
@DatabaseTable(tableName = VaccinationVisit.TABLE_NAME)
public class VaccinationVisit extends AbstractDomainObject {

    public static final String TABLE_NAME = "vaccinationVisit";
    public static final String I18N_PREFIX = "VaccinationVisit";

    public static final String VACCINATION_TALLY_SHEET = "vaccinationTallySheet";
    public static final String HOUSE_NUMBER = "houseNumber";
    public static final String NUMBER_OF_CHILDREN_RESIDENT = "numberOfChildrenResident";
    public static final String NUMBER_OF_CHILDREN_GUEST = "numberOfChildrenGuest";
    public static final String NUMBER_OF_CHILDREN_VACCINATED = "numberOfChildrenVaccinated";

    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false, maxForeignAutoRefreshLevel = 3)
    private VaccinationTallySheet vaccinationTallySheet;

    @Column(length = 512)
    private String houseNumber;

    @DatabaseField
    private Integer numberOfChildrenResident;

    @DatabaseField
    private Integer numberOfChildrenGuest;

    @DatabaseField
    private Integer numberOfChildrenVaccinated;

    public VaccinationTallySheet getVaccinationTallySheet() {
        return vaccinationTallySheet;
    }

    public void setVaccinationTallySheet(VaccinationTallySheet vaccinationTallySheet) {
        this.vaccinationTallySheet = vaccinationTallySheet;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Integer getNumberOfChildrenResident() {
        return numberOfChildrenResident;
    }

    public void setNumberOfChildrenResident(Integer numberOfChildrenResident) {
        this.numberOfChildrenResident = numberOfChildrenResident;
    }

    public Integer getNumberOfChildrenGuest() {
        return numberOfChildrenGuest;
    }

    public void setNumberOfChildrenGuest(Integer numberOfChildrenGuest) {
        this.numberOfChildrenGuest = numberOfChildrenGuest;
    }

    public Integer getNumberOfChildrenVaccinated() {
        return numberOfChildrenVaccinated;
    }

    public void setNumberOfChildrenVaccinated(Integer numberOfChildrenVaccinated) {
        this.numberOfChildrenVaccinated = numberOfChildrenVaccinated;
    }

    @Override
    public String getI18nPrefix() {
        return I18N_PREFIX;
    }

}
