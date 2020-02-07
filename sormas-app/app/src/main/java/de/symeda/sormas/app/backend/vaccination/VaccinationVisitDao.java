package de.symeda.sormas.app.backend.vaccination;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;

import de.symeda.sormas.app.backend.common.AbstractAdoDao;
import de.symeda.sormas.app.backend.common.AbstractDomainObject;
import de.symeda.sormas.app.backend.config.ConfigProvider;

public class VaccinationVisitDao extends AbstractAdoDao<VaccinationVisit> {

    public VaccinationVisitDao(Dao<VaccinationVisit, Long> innerDao) {
        super(innerDao);
    }

    @Override
    protected Class<VaccinationVisit> getAdoClass() {
        return VaccinationVisit.class;
    }

    @Override
    public String getTableName() {
        return VaccinationVisit.TABLE_NAME;
    }

    public VaccinationVisit build(VaccinationTallySheet sheet) {
        VaccinationVisit visit = super.build();
        visit.setVaccinationTallySheet(sheet);
        return visit;
    }

    public List<VaccinationVisit> queryByVaccinationTallySheet(VaccinationTallySheet tallySheet) {
        try {
            QueryBuilder<VaccinationVisit, Long> queryBuilder = queryBuilder();
            Where where = queryBuilder.where().eq(AbstractDomainObject.SNAPSHOT, false);
            where.and().eq(VaccinationVisit.VACCINATION_TALLY_SHEET + "_id", tallySheet.getId());
            return queryBuilder.orderBy(VaccinationVisit.CREATION_DATE, true).query();
        } catch (SQLException e) {
            Log.e(getTableName(), "Could not perform queryByVaccinationTallySheet on VaccinationVisit");
            throw new RuntimeException(e);
        }
    }

}