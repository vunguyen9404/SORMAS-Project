package de.symeda.sormas.app.vaccination;

import android.content.Context;

import de.symeda.sormas.app.R;
import de.symeda.sormas.app.core.enumeration.StatusElaborator;
import de.symeda.sormas.app.event.EventSection;

public enum VaccinationSection implements StatusElaborator {

    TALLY_SHEET_REPORT(R.string.caption_vaccination_tally_sheet_report, R.drawable.ic_insert_chart_black_24dp),
    TALLY_SHEET_HEADER(R.string.caption_vaccination_tally_sheet_header, R.drawable.ic_featured_play_list_black_24dp),
    HOUSE_CHILD_COUNTS(R.string.caption_vaccination_house_child_counts, R.drawable.ic_group_black_24dp),
    MISSED_CHILDREN(R.string.caption_vaccination_missed_children, R.drawable.ic_location_off_black_24dp),
    TALLY_SHEET_FOOTER(R.string.caption_vaccination_tally_sheet_footer, R.drawable.ic_call_to_action_black_24dp);

    private int friendlyNameResourceId;
    private int iconResourceId;

    VaccinationSection(int friendlyNameResourceId, int iconResourceId) {
        this.friendlyNameResourceId = friendlyNameResourceId;
        this.iconResourceId = iconResourceId;
    }

    public static VaccinationSection fromOrdinal(int ordinal) {
        return VaccinationSection.values()[ordinal];
    }

    @Override
    public String getFriendlyName(Context context) {
        return context.getResources().getString(friendlyNameResourceId);
    }

    @Override
    public int getColorIndicatorResource() {
        return 0;
    }

    @Override
    public Enum getValue() {
        return this;
    }

    @Override
    public int getIconResourceId() {
        return iconResourceId;
    }

}
