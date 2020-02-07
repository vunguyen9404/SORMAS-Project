package de.symeda.sormas.api.vaccination;

import de.symeda.sormas.api.i18n.I18nProperties;

public enum AbsenceOutcome {

	FOUND_VACCINATED,
	VACCINATED_DURING_REVISIT;
	
	public String toString() {
		return I18nProperties.getEnumCaption(this);
	}
	
}
