package com.android.topprevents.view.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.topprevents.R;
import com.android.topprevents.api.Website;
import com.android.topprevents.databinding.FragmentFavouriteListBinding;
import com.android.topprevents.view.adapter.FavouriteEventListAdapter;
import com.android.topprevents.viewModel.FavouriteListViewModel;

import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteListFragment extends Fragment implements FavouriteListViewModel.DataListener {


    public static final String TAG = FavouriteListFragment.class.getName();
    private FragmentFavouriteListBinding mFragmentFavEventListBinding;
    private FavouriteListViewModel mFavEventListViewModel;
    private RealmResults<Website> mFavEventList;
    private FavouriteEventListAdapter mAdapter;

    public FavouriteListFragment() {
        // Required empty public constructor
    }


    public static FavouriteListFragment getInstance()
    {
        return new FavouriteListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentFavEventListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite_list, container, false);
        mFavEventListViewModel = new FavouriteListViewModel(getActivity(), this);
        mFragmentFavEventListBinding.setViewModel(mFavEventListViewModel);
        setHasOptionsMenu(true);
        return mFragmentFavEventListBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();
        mFavEventListViewModel.loadFavEventDataList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFavEventListViewModel.onDestroy();
    }

    /**
     * Set up recycler view
     */
    private void setUpRecyclerView()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentFavEventListBinding.fragEventListRv.setLayoutManager(linearLayoutManager);
    }


    @Override
    public void onFavEventListLoaded(RealmResults<Website> resultDbs) {
        if(null != resultDbs && resultDbs.size() != 0)
        {
            mFavEventList = resultDbs;
            mAdapter = new FavouriteEventListAdapter(getActivity(), mFavEventList);
            mFragmentFavEventListBinding.fragEventListRv.setAdapter(mAdapter);
            mFragmentFavEventListBinding.progressBar.setVisibility(View.GONE);
        }
        else
        {
            Toast.makeText(getActivity(), R.string.no_favourite_event, Toast.LENGTH_SHORT).show();
            mFragmentFavEventListBinding.progressBar.setVisibility(View.GONE);
        }
    }
}
