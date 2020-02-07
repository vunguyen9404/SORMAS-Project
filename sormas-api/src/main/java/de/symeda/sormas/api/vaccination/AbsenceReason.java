package de.symeda.sormas.api.vaccination;

import de.symeda.sormas.api.i18n.I18nProperties;

public enum AbsenceReason {

	RETURN_DURING_CAMPAIGN,
	RETURN_AFTER_CAMPAIGN,
	NEWBORN_SICK_SLEEPING,
	REFUSAL;
	
	public String toString() {
		return I18nProperties.getEnumCaption(this);
	}
	
}
