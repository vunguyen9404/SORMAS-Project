package de.symeda.sormas.app.backend.vaccination;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import de.symeda.sormas.api.caze.Vaccination;
import de.symeda.sormas.app.backend.common.AbstractAdoDao;
import de.symeda.sormas.app.backend.common.AbstractDomainObject;
import de.symeda.sormas.app.backend.common.DatabaseHelper;
import de.symeda.sormas.app.backend.config.ConfigProvider;

public class VaccinationTallySheetDao extends AbstractAdoDao<VaccinationTallySheet> {

    public VaccinationTallySheetDao(Dao<VaccinationTallySheet, Long> innerDao) {
        super(innerDao);
    }

    @Override
    protected Class<VaccinationTallySheet> getAdoClass() {
        return VaccinationTallySheet.class;
    }

    @Override
    public String getTableName() {
        return VaccinationTallySheet.TABLE_NAME;
    }

    @Override
    public VaccinationTallySheet build() {
        VaccinationTallySheet sheet = super.build();
        sheet.setRegion(ConfigProvider.getUser().getRegion());
        sheet.setDistrict(ConfigProvider.getUser().getDistrict());
        return sheet;
    }

    @Override
    public void delete(VaccinationTallySheet tallySheet) throws SQLException {
        for (VaccinationVisit visit : DatabaseHelper.getVaccinationVisitDao().queryByVaccinationTallySheet(tallySheet)) {
            DatabaseHelper.getVaccinationVisitDao().delete(visit);
        }
        for (VaccinationAbsence absence : DatabaseHelper.getVaccinationAbsenceDao().queryByVaccinationTallySheet(tallySheet, null, null)) {
            DatabaseHelper.getVaccinationAbsenceDao().delete(absence);
        }
        super.delete(tallySheet);
    }

    public long count() {
        try {
            QueryBuilder<VaccinationTallySheet, Long> queryBuilder = queryBuilder();
            queryBuilder.where().eq(AbstractDomainObject.SNAPSHOT, false);
            return queryBuilder.countOf();
        } catch (SQLException e) {
            Log.e(getTableName(), "Could not perform count on VaccinationTallySheet");
            throw new RuntimeException(e);
        }
    }

    public List<VaccinationTallySheet> query(long offset, long limit) {
        try {
            QueryBuilder<VaccinationTallySheet, Long> queryBuilder = queryBuilder();
            queryBuilder.where().eq(AbstractDomainObject.SNAPSHOT, false);
            return queryBuilder.orderBy(VaccinationTallySheet.DATE, true)
                    .offset(offset).limit(limit).query();
        } catch (SQLException e) {
            Log.e(getTableName(), "Could not perform query on VaccinationTallySheet");
            throw new RuntimeException(e);
        }
    }

    public VaccinationTallySheet queryByTeamNumberAndDay(String teamNumber, int campaignDay) {
        try {
            QueryBuilder<VaccinationTallySheet, Long> queryBuilder = queryBuilder();
            queryBuilder.where().eq(AbstractDomainObject.SNAPSHOT, false)
                    .and().eq(VaccinationTallySheet.TEAM_NUMBER, teamNumber)
                    .and().eq(VaccinationTallySheet.CAMPAIGN_DAY, campaignDay);
            return queryBuilder.distinct().queryForFirst();
        } catch (SQLException e) {
            Log.e(getTableName(), "Could not perform queryByTeamNumberAndDay on VaccinationTallySheet");
            throw new RuntimeException(e);
        }
    }

    public List<String> getTeamNumbers() {
        try {
            QueryBuilder<VaccinationTallySheet, Long> queryBuilder = queryBuilder();
            queryBuilder.where().eq(AbstractDomainObject.SNAPSHOT, false);
            queryBuilder.selectColumns(VaccinationTallySheet.TEAM_NUMBER);
            queryBuilder.distinct();
            List<String[]> results = queryBuilder.queryRaw().getResults();
            List<String> teamNumbers = new ArrayList<>();
            for (String[] result : results) {
                teamNumbers.add(result[0]);
            }
            return teamNumbers;
        } catch (SQLException e) {
            Log.e(getTableName(), "Could not perform getTeamNumbers on VaccinationTallySheet");
            throw new RuntimeException(e);
        }
    }

    public List<Integer> getCampaignDaysForTeam(String teamNumber) {
        try {
            QueryBuilder<VaccinationTallySheet, Long> queryBuilder = queryBuilder();
            queryBuilder.where().eq(AbstractDomainObject.SNAPSHOT, false).and().eq(VaccinationTallySheet.TEAM_NUMBER, teamNumber);
            queryBuilder.selectColumns(VaccinationTallySheet.CAMPAIGN_DAY);
            queryBuilder.orderBy(VaccinationTallySheet.CAMPAIGN_DAY, true);
            queryBuilder.distinct();
            List<String[]> results = queryBuilder.queryRaw().getResults();
            List<Integer> campaignDays = new ArrayList<>();
            for (String[] result : results) {
                campaignDays.add(Integer.valueOf(result[0]));
            }
            return campaignDays;
        } catch (SQLException e) {
            Log.e(getTableName(), "Could not perform getCampaignDaysForTeam on VaccinationTallySheet");
            throw new RuntimeException(e);
        }
    }

    public VaccinationTallySheetReport generateReport(VaccinationTallySheet sheet) {
        try {
            GenericRawResults<Object[]> rawResults = queryRaw(
                    "SELECT " +
                            "COUNT(v.id), " +
                            "SUM(v.numberOfChildrenResident), " +
                            "SUM(v.numberOfChildrenGuest), " +
                            "SUM(v.numberOfChildrenVaccinated), " +
                            "COUNT(a.id), " +
                            "s.numberOfVaccinatedChildren - SUM(v.numberOfChildrenVaccinated), " +
                            "s.numberOfVaccinatedNomadChildren, " +
                            "COUNT(CASE WHEN a.absenceReason = 'RETURN_DURING_CAMPAIGN' AND a.absenceOutcome = 'FOUND_VACCINATED' THEN 1 ELSE null END), " +
                            "COUNT(CASE WHEN a.absenceReason = 'RETURN_DURING_CAMPAIGN' AND a.absenceOutcome = 'VACCINATED_DURING_REVISIT' THEN 1 ELSE null END), " +
                            "COUNT(CASE WHEN a.absenceReason = 'RETURN_DURING_CAMPAIGN' AND a.absenceOutcome IS NULL THEN 1 ELSE null END), " +
                            "COUNT(CASE WHEN a.absenceReason = 'RETURN_AFTER_CAMPAIGN' AND a.absenceOutcome = 'FOUND_VACCINATED' THEN 1 ELSE null END), " +
                            "COUNT(CASE WHEN a.absenceReason = 'RETURN_AFTER_CAMPAIGN' AND a.absenceOutcome = 'VACCINATED_DURING_REVISIT' THEN 1 ELSE null END), " +
                            "COUNT(CASE WHEN a.absenceReason = 'RETURN_AFTER_CAMPAIGN' AND a.absenceOutcome IS NULL THEN 1 ELSE null END), " +
                            "COUNT(CASE WHEN a.absenceReason = 'NEWBORN_SICK_SLEEPING' AND a.absenceOutcome = 'FOUND_VACCINATED' THEN 1 ELSE null END), " +
                            "COUNT(CASE WHEN a.absenceReason = 'NEWBORN_SICK_SLEEPING' AND a.absenceOutcome = 'VACCINATED_DURING_REVISIT' THEN 1 ELSE null END), " +
                            "COUNT(CASE WHEN a.absenceReason = 'NEWBORN_SICK_SLEEPING' AND a.absenceOutcome IS NULL THEN 1 ELSE null END), " +
                            "COUNT(CASE WHEN a.absenceReason = 'REFUSAL' AND a.absenceOutcome = 'FOUND_VACCINATED' THEN 1 ELSE null END), " +
                            "COUNT(CASE WHEN a.absenceReason = 'REFUSAL' AND a.absenceOutcome = 'VACCINATED_DURING_REVISIT' THEN 1 ELSE null END), " +
                            "COUNT(CASE WHEN a.absenceReason = 'REFUSAL' AND a.absenceOutcome IS NULL THEN 1 ELSE null END), " +
                            "s.numberOfVialsReceivedFull, " +
                            "s.numberOfVialsReceivedOpen, " +
                            "s.numberOfVialsReturnedFull, " +
                            "s.numberOfVialsReturnedOpen, " +
                            "s.numberOfVialsReturnedEmpty " +
                            "FROM vaccinationTallySheet s " +
                            "LEFT JOIN vaccinationVisit v ON v.vaccinationTallySheet_id = s.id AND v.snapshot = 0 " +
                            "LEFT JOIN vaccinationAbsence a ON a.vaccinationTallySheet_id = s.id AND a.snapshot = 0 " +
                            "WHERE s.id = " + sheet.getId() + " AND s.snapshot = 0;",
                    new DataType[]{DataType.STRING});

            List<Object[]> results = rawResults.getResults();
            Object[] resultArray = results.get(0);
            return new VaccinationTallySheetReport(
                    resultArray[0] != null ? Integer.parseInt((String) resultArray[0]) : 0,
                    resultArray[1] != null ? Integer.parseInt((String) resultArray[1]) : 0,
                    resultArray[2] != null ? Integer.parseInt((String) resultArray[2]) : 0,
                    resultArray[3] != null ? Integer.parseInt((String) resultArray[3]) : 0,
                    resultArray[4] != null ? Integer.parseInt((String) resultArray[4]) : 0,
                    resultArray[5] != null ? Integer.parseInt((String) resultArray[5]) : 0,
                    resultArray[6] != null ? Integer.parseInt((String) resultArray[6]) : 0,
                    resultArray[7] != null ? Integer.parseInt((String) resultArray[7]) : 0,
                    resultArray[8] != null ? Integer.parseInt((String) resultArray[8]) : 0,
                    resultArray[9] != null ? Integer.parseInt((String) resultArray[9]) : 0,
                    resultArray[10] != null ? Integer.parseInt((String) resultArray[10]) : 0,
                    resultArray[11] != null ? Integer.parseInt((String) resultArray[11]) : 0,
                    resultArray[12] != null ? Integer.parseInt((String) resultArray[12]) : 0,
                    resultArray[13] != null ? Integer.parseInt((String) resultArray[13]) : 0,
                    resultArray[14] != null ? Integer.parseInt((String) resultArray[14]) : 0,
                    resultArray[15] != null ? Integer.parseInt((String) resultArray[15]) : 0,
                    resultArray[16] != null ? Integer.parseInt((String) resultArray[16]) : 0,
                    resultArray[17] != null ? Integer.parseInt((String) resultArray[17]) : 0,
                    resultArray[18] != null ? Integer.parseInt((String) resultArray[18]) : 0,
                    resultArray[19] != null ? Integer.parseInt((String) resultArray[19]) : 0,
                    resultArray[20] != null ? Integer.parseInt((String) resultArray[20]) : 0,
                    resultArray[21] != null ? Integer.parseInt((String) resultArray[21]) : 0,
                    resultArray[22] != null ? Integer.parseInt((String) resultArray[22]) : 0,
                    resultArray[23] != null ? Integer.parseInt((String) resultArray[23]) : 0);
        } catch (SQLException e) {
            Log.e(getTableName(), "Could not perform generateReport on VaccinationTallySheet");
            throw new RuntimeException(e);
        }
    }

}
