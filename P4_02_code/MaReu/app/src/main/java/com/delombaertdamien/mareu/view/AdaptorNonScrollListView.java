package com.delombaertdamien.mareu.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.delombaertdamien.mareu.R;
import com.delombaertdamien.mareu.controller.ConfigureMeetingActivity;

import java.util.ArrayList;
import java.util.List;

public class AdaptorNonScrollListView extends BaseAdapter {

    private Context mContext;
    private List<String> mEmailContributors;
    private LayoutInflater mInflate;
    private List<Integer> nbItem;
    private ConfigureMeetingActivity mActivtity;

    public AdaptorNonScrollListView(Context context, List<String> list, ConfigureMeetingActivity activity) {

        this.mContext = context;
        this.mEmailContributors = list;
        this.mInflate = LayoutInflater.from(context);
        nbItem = new ArrayList<>();
        mActivtity = activity;
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
        //value
        String name = mEmailContributors.get(i);
        //layout
        TextView nameText = view.findViewById(R.id.item_contributor_name);
        ImageButton deleteButton = view.findViewById(R.id.item_contributor_delete);
        //set
        nameText.setText(name);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivtity.removeAnContributor(name);
                System.out.println("delete");
            }
        });

        return view;
    }
}
