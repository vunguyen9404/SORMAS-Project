package de.symeda.sormas.api.therapy;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface PrescriptionFacade {
	
	List<PrescriptionIndexDto> getIndexList(PrescriptionCriteria criteria);
	
	PrescriptionDto getPrescriptionByUuid(String uuid);
	
	PrescriptionDto savePrescription(PrescriptionDto prescription);
	
	void deletePrescription(String prescriptionUuid, String userUuid);
	
	List<PrescriptionDto> getAllActivePrescriptionsAfter(Date date, String userUuid);
	
	List<PrescriptionDto> getByUuids(List<String> uuids);
	
	List<String> getAllActiveUuids(String userUuid);

}
