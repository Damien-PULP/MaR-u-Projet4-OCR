package com.delombaertdamien.mareu.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.delombaertdamien.mareu.R;
import com.delombaertdamien.mareu.controller.Activity.ConfigureMeetingActivity;

import java.util.ArrayList;
import java.util.List;

public class AdaptorNonScrollListView extends BaseAdapter {

    private List<String> mEmailContributors;
    private List<Integer> nbItem;

    private final LayoutInflater mInflate;

    private final ConfigureMeetingActivity mActivity;

    public AdaptorNonScrollListView(Context context, List<String> list, ConfigureMeetingActivity activity) {

        this.mEmailContributors = list;
        this.mInflate = LayoutInflater.from(context);
        nbItem = new ArrayList<>();
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return mEmailContributors.size();
    }

    @Override
    public Object getItem(int i) {
        return mEmailContributors.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mEmailContributors.get(i).hashCode();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        nbItem.add(1);
        view = mInflate.inflate(R.layout.item_contributor, null);

        String name = mEmailContributors.get(i);

        TextView nameText = view.findViewById(R.id.item_contributor_name);
        ImageButton deleteButton = view.findViewById(R.id.item_contributor_delete);

        nameText.setText(name);

        deleteButton.setOnClickListener(view1 -> {
            mActivity.removeAnContributor(name);
        });

        return view;
    }
}
