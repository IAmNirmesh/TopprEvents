package com.android.topprevents.view.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.topprevents.R;
import com.android.topprevents.api.EventDataModel;
import com.android.topprevents.api.Website;
import com.android.topprevents.databinding.FragmentEventListBinding;
import com.android.topprevents.view.activity.FavouriteEventListActivity;
import com.android.topprevents.view.adapter.EventListAdapter;
import com.android.topprevents.viewModel.EventListViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventListFragment extends Fragment implements EventListViewModel.DataListener {

    private FragmentEventListBinding mFragmentEventListBinding;
    public static String TAG = EventListFragment.class.getName();
    private EventListViewModel mEventListViewModel;
    private EventListAdapter mAdapter;
    private List<Website> mEventList;

    public EventListFragment() {
        // Required empty public constructor
    }

    private Comparator<Website> categoryComparator = new Comparator<Website>() {
        @Override
        public int compare(Website lhs, Website rhs) {
            return lhs.getCategory().compareTo(rhs.getCategory());
        }
    };

    public static EventListFragment getInstance()
    {
        return new EventListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentEventListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_list, container, false);
        mEventListViewModel = new EventListViewModel(getActivity(), this);
        mFragmentEventListBinding.setViewModel(mEventListViewModel);
        setHasOptionsMenu(true);
        return mFragmentEventListBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();
        mEventListViewModel.loadEventDataList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mEventListViewModel.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(null != mAdapter)
        {
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Set up recycler view
     */
    private void setUpRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentEventListBinding.fragEventListRv.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onEventListLoaded(EventDataModel data) {
        if(null != data && null != data.getWebsites() && data.getWebsites().size() != 0) {
            mEventList = new ArrayList<>(data.getWebsites());
            mAdapter = new EventListAdapter(getActivity(), mEventList);
            mFragmentEventListBinding.fragEventListRv.setAdapter(mAdapter);
            mFragmentEventListBinding.progressBar.setVisibility(View.GONE);
        } else {
            mFragmentEventListBinding.progressBar.setVisibility(View.GONE);
        }
    }

    private void sortList() {
        if(null != mEventList && mEventList.size() != 0) {
            Collections.sort(mEventList, categoryComparator);
            mAdapter = new EventListAdapter(getActivity(), mEventList);
            mFragmentEventListBinding.fragEventListRv.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onEventListFailedToLoad() {
        Toast.makeText(getActivity(), R.string.no_event_msg, Toast.LENGTH_SHORT).show();
        mFragmentEventListBinding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getResources().getString(R.string.query_hint));

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                if(mAdapter != null && !TextUtils.isEmpty(newText))
                    mAdapter.setFilter(newText);
                else if(mAdapter != null && TextUtils.isEmpty(newText))
                    mAdapter.flushFilter();
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                if(mAdapter != null && !TextUtils.isEmpty(query))
                    mAdapter.setFilter(query);
                else if(mAdapter != null && TextUtils.isEmpty(query))
                    mAdapter.flushFilter();
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_filter) {
            sortList();
            Toast.makeText(getActivity(), R.string.list_sorted_msg,Toast.LENGTH_LONG).show();
        } else if(item.getItemId() == R.id.action_favourite) {
            Intent intent = new Intent(getActivity(), FavouriteEventListActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
