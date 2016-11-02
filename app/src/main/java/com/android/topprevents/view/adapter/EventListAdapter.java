package com.android.topprevents.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.topprevents.R;
import com.android.topprevents.api.Website;
import com.android.topprevents.databinding.EventListItemLayoutBinding;
import com.android.topprevents.view.activity.EventDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventListViewHolder> {

    private Context mContext;
    private List<Website> mEventList;
    private List<Website> backupList;

    public EventListAdapter(Context context, List<Website> list) {
        mContext = context;
        mEventList = new ArrayList<>(list);
        backupList = new ArrayList<>(list);
    }

    @Override
    public EventListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        EventListItemLayoutBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.event_list_item_layout,
                parent,
                false);
        return new EventListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(EventListViewHolder holder, int position) {
        final Website website = mEventList.get(position);
        holder.bindEventItemData(website);
        Picasso.with(mContext).load(website.getImage()).into(holder.binding.eventImg);
        holder.binding.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EventDetailActivity.class);
                intent.putExtra(EventDetailActivity.KEY_EVENT_DATA, website);
                mContext.startActivity(intent);
            }
        });

        website.setFavourite(checkIfExists(website.getId()));
        holder.binding.favIcon.setImageResource(website.isFavourite() ? R.drawable.ic_favorite_black_24dp : R.drawable.ic_favorite_border_black_24dp);

        holder.binding.favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(website.isFavourite()) {
                    website.setFavourite(false);
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    RealmResults<Website> realmResults = realm.where(Website.class).equalTo("id", website.getId()).findAll();
                    realmResults.deleteAllFromRealm();
                    realm.commitTransaction();
                    notifyDataSetChanged();
                } else {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    website.setFavourite(true);
                    realm.copyToRealm(website);
                    realm.commitTransaction();
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    public void flushFilter() {
        mEventList=new ArrayList<>();
        mEventList.addAll(backupList);
        notifyDataSetChanged();
    }

    public void setFilter(String queryText) {

        mEventList = new ArrayList<>();
        queryText = queryText.toLowerCase();
        for (Website item: backupList) {
            if (item.getName().toLowerCase().contains(queryText) || item.getCategory().toLowerCase().contains(queryText))
                mEventList.add(item);
        }

        notifyDataSetChanged();
    }

    public static class EventListViewHolder extends RecyclerView.ViewHolder {
        EventListItemLayoutBinding binding;
        public EventListViewHolder(EventListItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindEventItemData(Website website)
        {
            binding.setData(website);
        }
    }

    public boolean checkIfExists(String id) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Website> query = realm.where(Website.class)
                .equalTo("id", id).findAll();
        realm.commitTransaction();
        return query.size() != 0;
    }
}
