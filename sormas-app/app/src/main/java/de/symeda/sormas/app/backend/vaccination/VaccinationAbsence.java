package de.symeda.sormas.app.backend.vaccination;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import de.symeda.sormas.api.vaccination.AbsenceOutcome;
import de.symeda.sormas.api.vaccination.AbsenceReason;
import de.symeda.sormas.app.backend.common.AbstractDomainObject;

@Entity(name = VaccinationAbsence.TABLE_NAME)
@DatabaseTable(tableName = VaccinationAbsence.TABLE_NAME)
public class VaccinationAbsence extends AbstractDomainObject {

    public static final String TABLE_NAME = "vaccinationAbsence";
    public static final String I18N_PREFIX = "VaccinationAbsence";

    public static final String VACCINATION_TALLY_SHEET = "vaccinationTallySheet";

    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false, maxForeignAutoRefreshLevel = 3)
    private VaccinationTallySheet vaccinationTallySheet;

    @Column(length = 512)
    private String houseNumber;

    @Column(length = 512)
    private String childName;

    @Column(length = 512)
    private String parentName;

    @Enumerated(EnumType.STRING)
    private AbsenceReason absenceReason;

    @Enumerated(EnumType.STRING)
    private AbsenceOutcome absenceOutcome;

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

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public AbsenceReason getAbsenceReason() {
        return absenceReason;
    }

    public void setAbsenceReason(AbsenceReason absenceReason) {
        this.absenceReason = absenceReason;
    }

    public AbsenceOutcome getAbsenceOutcome() {
        return absenceOutcome;
    }

    public void setAbsenceOutcome(AbsenceOutcome absenceOutcome) {
        this.absenceOutcome = absenceOutcome;
    }

    @Override
    public String getI18nPrefix() {
        return I18N_PREFIX;
    }

}
