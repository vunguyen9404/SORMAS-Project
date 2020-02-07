package de.symeda.sormas.app.vaccination.edit;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.symeda.sormas.app.BaseActivity;
import de.symeda.sormas.app.BaseEditFragment;
import de.symeda.sormas.app.R;
import de.symeda.sormas.app.backend.vaccination.VaccinationAbsence;
import de.symeda.sormas.app.backend.vaccination.VaccinationTallySheet;
import de.symeda.sormas.app.core.adapter.databinding.OnListItemClickListener;
import de.symeda.sormas.app.databinding.FragmentFormListLayoutBinding;

public class VaccinationAbsenceListFragment extends BaseEditFragment<FragmentFormListLayoutBinding, List<VaccinationAbsence>, VaccinationTallySheet> implements OnListItemClickListener {

    private VaccinationAbsenceListAdapter adapter;
    private VaccinationAbsenceListViewModel model;

    public static VaccinationAbsenceListFragment newInstance(VaccinationTallySheet activityRootData) {
        return newInstance(VaccinationAbsenceListFragment.class, null, activityRootData);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((BaseActivity) getActivity()).showPreloader();
        adapter = new VaccinationAbsenceListAdapter();
        model = ViewModelProviders.of(this).get(VaccinationAbsenceListViewModel.class);
        model.initializeViewModel(getActivityRootData());
        model.getAbsences().observe(this, absences -> {
            ((VaccinationTallySheetEditActivity) getActivity()).hidePreloader();
            adapter.submitList(absences);
            updateEmptyListHint(absences);
        });
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        adapter.setOnListItemClickListener(this);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected String getSubHeadingTitle() {
        Resources r = getResources();
        return r.getString(R.string.caption_vaccination_missed_children);
    }

    @Override
    public List<VaccinationAbsence> getPrimaryData() {
        throw new UnsupportedOperationException("Sub list fragments don't hold their data");
    }

    @Override
    protected void prepareFragmentData() {

    }

    @Override
    public void onLayoutBinding(FragmentFormListLayoutBinding contentBinding) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        contentBinding.recyclerViewForList.setLayoutManager(linearLayoutManager);
        contentBinding.recyclerViewForList.setAdapter(adapter);
    }

    @Override
    public int getRootEditLayout() {
        return R.layout.fragment_root_list_form_layout;
    }

    @Override
    public int getEditLayout() {
        return R.layout.fragment_form_list_layout;
    }

    @Override
    public void onListItemClick(View view, int position, Object item) {
        VaccinationAbsence absence = (VaccinationAbsence) item;
        VaccinationAbsence absenceClone = (VaccinationAbsence) absence.clone();
        ((VaccinationTallySheetEditActivity) getActivity()).buildVaccinationAbsenceDialog(absenceClone, () -> {
            model.reloadList();
        });
    }

    @Override
    public boolean isShowSaveAction() {
        return false;
    }

    @Override
    public boolean isShowNewAction() {
        return true;
    }

    public VaccinationAbsenceListViewModel getModel() {
        return model;
    }

    @Override
    public boolean isShowDeleteAction() {
        return true;
    }

}
