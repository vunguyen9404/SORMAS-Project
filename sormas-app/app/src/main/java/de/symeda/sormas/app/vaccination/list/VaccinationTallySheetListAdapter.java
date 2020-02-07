package de.symeda.sormas.app.vaccination.list;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.lang3.math.NumberUtils;

import de.symeda.sormas.app.R;
import de.symeda.sormas.app.backend.common.DatabaseHelper;
import de.symeda.sormas.app.backend.vaccination.VaccinationTallySheet;
import de.symeda.sormas.app.core.adapter.databinding.BindingPagedListAdapter;
import de.symeda.sormas.app.core.adapter.databinding.BindingViewHolder;
import de.symeda.sormas.app.databinding.RowVaccinationTallySheetListItemLayoutBinding;

public class VaccinationTallySheetListAdapter extends BindingPagedListAdapter<VaccinationTallySheet, RowVaccinationTallySheetListItemLayoutBinding> {

    VaccinationTallySheetListAdapter() {
        super(R.layout.row_vaccination_tally_sheet_list_item_layout);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        if (getItemViewType(position) == TYPE_ITEM) {
            BindingViewHolder<VaccinationTallySheet, RowVaccinationTallySheetListItemLayoutBinding> pagedHolder = (BindingViewHolder) holder;
            VaccinationTallySheet item = getItem(position);

            int absences = (int) DatabaseHelper.getVaccinationAbsenceDao().countByVaccinationTallySheet(item);
            int numberOfVaccinatedChildren = 0;
            if (item.getNumberOfVaccinatedChildren() != null) {
                numberOfVaccinatedChildren = item.getNumberOfVaccinatedChildren();
            }
            if (item.getNumberOfVaccinatedNomadChildren() != null) {
                numberOfVaccinatedChildren += item.getNumberOfVaccinatedNomadChildren();
            }

            pagedHolder.setOnListItemClickListener(this.mOnListItemClickListener);

            pagedHolder.binding.vaccinatedChildren.setText(String.format(
                    pagedHolder.binding.getRoot().getResources().getString(R.string.title_detail_format),
                    pagedHolder.binding.getRoot().getResources().getString(R.string.caption_number_of_vaccinated_children),
                    String.valueOf(numberOfVaccinatedChildren)));

            pagedHolder.binding.missedChildren.setText(String.format(
                    pagedHolder.binding.getRoot().getResources().getString(R.string.title_detail_format),
                    pagedHolder.binding.getRoot().getResources().getString(R.string.caption_number_of_missed_children),
                    String.valueOf(absences)));

//            if (item.isModifiedOrChildModified()) {
//                pagedHolder.binding.imgSyncIcon.setVisibility(View.VISIBLE);
//                pagedHolder.binding.imgSyncIcon.setImageResource(R.drawable.ic_sync_blue_24dp);
//            } else {
//                pagedHolder.binding.imgSyncIcon.setVisibility(View.GONE);
//            }
        }

        // TODO #704
//        updateUnreadIndicator(holder, record);
    }

//    public void updateUnreadIndicator(DataBoundViewHolder<RowEventListItemLayoutBinding> holder, Event item) {
//        backgroundRowItem = (LayerDrawable) ContextCompat.getDrawable(holder.context, R.drawable.background_list_activity_row);
//        unreadListItemIndicator = backgroundRowItem.findDrawableByLayerId(R.id.unreadListItemIndicator);
//
//        if (item != null) {
//            if (item.isUnreadOrChildUnread()) {
//                unreadListItemIndicator.setTint(holder.context.getResources().getColor(R.color.unreadIcon));
//            } else {
//                unreadListItemIndicator.setTint(holder.context.getResources().getColor(android.R.color.transparent));
//            }
//        }
//    }

}
