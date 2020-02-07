package de.symeda.sormas.app.vaccination.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.symeda.sormas.app.PagedBaseListFragment;
import de.symeda.sormas.app.R;
import de.symeda.sormas.app.backend.vaccination.VaccinationTallySheet;
import de.symeda.sormas.app.core.adapter.databinding.OnListItemClickListener;
import de.symeda.sormas.app.vaccination.VaccinationSection;
import de.symeda.sormas.app.vaccination.edit.VaccinationTallySheetEditActivity;

public class VaccinationTallySheetListFragment extends PagedBaseListFragment<VaccinationTallySheetListAdapter> implements OnListItemClickListener {

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerViewForList;

    public static VaccinationTallySheetListFragment newInstance() {
        return newInstance(VaccinationTallySheetListFragment.class, null, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerViewForList = view.findViewById(R.id.recyclerViewForList);

        return view;
    }

    @Override
    public VaccinationTallySheetListAdapter getNewListAdapter() {
        return (VaccinationTallySheetListAdapter) ((VaccinationTallySheetListActivity) getActivity()).getAdapter();
    }

    @Override
    public void onListItemClick(View view, int position, Object item) {
        VaccinationTallySheet sheet = (VaccinationTallySheet) item;
        VaccinationTallySheetEditActivity.startActivity(getContext(), sheet.getUuid(), VaccinationSection.TALLY_SHEET_REPORT);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerViewForList.setLayoutManager(linearLayoutManager);
        recyclerViewForList.setAdapter(getListAdapter());
    }

}
