package de.symeda.sormas.app.vaccination.list;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.AdapterView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import org.joda.time.DateTime;

import java.util.Random;

import de.symeda.sormas.app.BaseListActivity;
import de.symeda.sormas.app.PagedBaseListActivity;
import de.symeda.sormas.app.PagedBaseListFragment;
import de.symeda.sormas.app.R;
import de.symeda.sormas.app.component.menu.PageMenuItem;
import de.symeda.sormas.app.util.Callback;
import de.symeda.sormas.app.vaccination.edit.VaccinationTallySheetNewActivity;

public class VaccinationTallySheetListActivity extends PagedBaseListActivity {

    private VaccinationTallySheetListViewModel model;

    public static void startActivity(Context context) {
        BaseListActivity.startActivity(context, VaccinationTallySheetListActivity.class, null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showPreloader();
        adapter = new VaccinationTallySheetListAdapter();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                // Scroll to the topmost position after cases have been inserted
                if (positionStart == 0) {
                    RecyclerView recyclerView = findViewById(R.id.recyclerViewForList);
                    if (recyclerView != null) {
                        recyclerView.scrollToPosition(0);
                    }
                }
            }
            @Override
            public void onItemRangeMoved(int positionStart, int toPosition, int itemCount) {
                RecyclerView recyclerView = findViewById(R.id.recyclerViewForList);
                if (recyclerView != null) {
                    recyclerView.scrollToPosition(0);
                }
            }
        });
        model = ViewModelProviders.of(this).get(VaccinationTallySheetListViewModel.class);
        model.getVaccinationTallySheets().observe(this, sheets -> {
            adapter.submitList(sheets);
            hidePreloader();
        });
    }

    @Override
    protected Callback getSynchronizeResultCallback() {
        // Reload the list after a synchronization has been done
        return () -> {
            showPreloader();
            model.getVaccinationTallySheets().getValue().getDataSource().invalidate();
        };
    }

    @Override
    public int onNotificationCountChangingAsync(AdapterView parent, PageMenuItem menuItem, int position) {
        //TODO: Call database and retrieve notification count
        return (int) (new Random(DateTime.now().getMillis() * 1000).nextInt() / 10000000);
    }

    @Override
    protected PagedBaseListFragment buildListFragment(PageMenuItem menuItem) {
        return VaccinationTallySheetListFragment.newInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getNewMenu().setTitle(R.string.action_new_vaccination_tally_sheet);
        return true;
    }

    @Override
    protected int getActivityTitle() {
        return R.string.heading_vaccination_tally_sheets_list;
    }

    @Override
    public void goToNewView() {
        VaccinationTallySheetNewActivity.startActivity(this);
        finish();
    }

    @Override
    protected boolean isEntryCreateAllowed() {
        return true;
    }

    @Override
    public void addFiltersToPageMenu() {
        // Not supported yet
    }

}
