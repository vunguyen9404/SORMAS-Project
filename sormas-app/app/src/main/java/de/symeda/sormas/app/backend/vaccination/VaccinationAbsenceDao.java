package de.symeda.sormas.app.backend.vaccination;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;

import de.symeda.sormas.app.backend.common.AbstractAdoDao;
import de.symeda.sormas.app.backend.common.AbstractDomainObject;

public class VaccinationAbsenceDao extends AbstractAdoDao<VaccinationAbsence> {

    public VaccinationAbsenceDao(Dao<VaccinationAbsence, Long> innerDao) {
        super(innerDao);
    }

    @Override
    protected Class<VaccinationAbsence> getAdoClass() {
        return VaccinationAbsence.class;
    }

    @Override
    public String getTableName() {
        return VaccinationAbsence.TABLE_NAME;
    }

    public VaccinationAbsence build(VaccinationTallySheet sheet) {
        VaccinationAbsence absence = super.build();
        absence.setVaccinationTallySheet(sheet);
        return absence;
    }

    public long countByVaccinationTallySheet(VaccinationTallySheet tallySheet) {
        try {
            QueryBuilder<VaccinationAbsence, Long> queryBuilder = queryBuilder();
            Where<VaccinationAbsence, Long> where = queryBuilder.where().eq(AbstractDomainObject.SNAPSHOT, false);
            where.and().eq(VaccinationAbsence.VACCINATION_TALLY_SHEET + "_id", tallySheet.getId());
            return queryBuilder.countOf();
        } catch (SQLException e) {
            Log.e(getTableName(), "Could not perform countByCriteria on Sample");
            throw new RuntimeException(e);
        }
    }

    public List<VaccinationAbsence> queryByVaccinationTallySheet(VaccinationTallySheet tallySheet, Long offset, Long limit) {
        try {
            QueryBuilder<VaccinationAbsence, Long> queryBuilder = queryBuilder();
            Where<VaccinationAbsence, Long> where = queryBuilder.where().eq(AbstractDomainObject.SNAPSHOT, false);
            where.and().eq(VaccinationAbsence.VACCINATION_TALLY_SHEET + "_id", tallySheet.getId());
            return queryBuilder.orderBy(VaccinationAbsence.CREATION_DATE, true).offset(offset).limit(limit).query();
        } catch (SQLException e) {
            Log.e(getTableName(), "Could not perform queryByCriteria on Sample");
            throw new RuntimeException(e);
        }
    }

}
