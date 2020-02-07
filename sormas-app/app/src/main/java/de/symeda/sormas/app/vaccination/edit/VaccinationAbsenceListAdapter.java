package de.symeda.sormas.app.vaccination.edit;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.symeda.sormas.api.sample.SpecimenCondition;
import de.symeda.sormas.app.R;
import de.symeda.sormas.app.backend.sample.Sample;
import de.symeda.sormas.app.backend.vaccination.VaccinationAbsence;
import de.symeda.sormas.app.core.adapter.databinding.BindingPagedListAdapter;
import de.symeda.sormas.app.core.adapter.databinding.BindingViewHolder;
import de.symeda.sormas.app.databinding.RowSampleListItemLayoutBinding;
import de.symeda.sormas.app.databinding.RowVaccinationAbsenceListItemLayoutBinding;

public class VaccinationAbsenceListAdapter extends BindingPagedListAdapter<VaccinationAbsence, RowVaccinationAbsenceListItemLayoutBinding> {

    public VaccinationAbsenceListAdapter() {
        super(R.layout.row_vaccination_absence_list_item_layout);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        if (getItemViewType(position) == TYPE_ITEM) {
            BindingViewHolder<Sample, RowSampleListItemLayoutBinding> pagedHolder = (BindingViewHolder) holder;

            pagedHolder.setOnListItemClickListener(this.mOnListItemClickListener);

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

//    public void updateUnreadIndicator(DataBoundViewHolder<RowSampleListItemLayoutBinding> holder, Sample item) {
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


