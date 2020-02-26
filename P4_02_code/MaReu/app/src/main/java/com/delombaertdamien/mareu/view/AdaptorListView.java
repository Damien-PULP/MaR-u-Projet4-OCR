package com.delombaertdamien.mareu.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.delombaertdamien.mareu.DI.DI;
import com.delombaertdamien.mareu.R;
import com.delombaertdamien.mareu.model.Metting;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdaptorListView extends RecyclerView.Adapter<ViewHolder>{

        private final List<Metting> mMettings;
        private Context mContext;

        public AdaptorListView (List<Metting> items, Context context){
            mMettings = items;
            mContext =context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.metting_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            Metting metting = DI.getMettingApiService().getMettings().get(position);

            holder.imgIcon.setBackgroundResource(R.color.colorAccent);
            holder.textNameMetting.setText(metting.getSubject() + " / Local " + metting.getPlace() + ", " + metting.getHourOfMetting() + "H" );
            holder.textNameParticipant.setText("");
            for(int i = 0; i < metting.getContributors().size(); i++){
                holder.textNameParticipant.setText(holder.textNameParticipant.getText() + ", " + metting.getContributors().get(i));
            }

        }

        @Override
        public int getItemCount() {
            return mMettings.size();
        }
    }

class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_metting_list_name)
        public TextView textNameMetting;
        @BindView(R.id.item_metting_list_delete_button)
        public Button buttonDelete;
        @BindView(R.id.item_metting_list_icon)
        public ImageView imgIcon;
        @BindView(R.id.item_metting_list_name_participant)
        public TextView textNameParticipant;


    public ViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        /** Replace ButterKnife */
        textNameMetting = itemView.findViewById(R.id.item_metting_list_name);
        imgIcon = itemView.findViewById(R.id.item_metting_list_icon);
        textNameParticipant = itemView.findViewById(R.id.item_metting_list_name_participant);
    }
}
