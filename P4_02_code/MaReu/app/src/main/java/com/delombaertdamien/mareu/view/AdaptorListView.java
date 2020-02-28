package com.delombaertdamien.mareu.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.delombaertdamien.mareu.DI.DI;
import com.delombaertdamien.mareu.R;
import com.delombaertdamien.mareu.model.Metting;
import com.delombaertdamien.mareu.service.MettingApiService;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdaptorListView extends RecyclerView.Adapter<ViewHolder> {

    private final List<Metting> mMettings;
    private Context mContext;
    private MettingApiService mApiService;

    public AdaptorListView(List<Metting> items, Context context) {
        mMettings = items;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mApiService = DI.getMettingApiService();
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.metting_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Metting metting = DI.getMettingApiService().getMettings().get(position);

        holder.imgIcon.setColorFilter(getRandomColor());
        String stringHour = String.format("%.0f", metting.getHourOfMetting());
        String stringMin = String.format("%.0f",(metting.getHourOfMetting() - (Float.parseFloat(stringHour))) * 60);

        holder.textNameMetting.setText(metting.getSubject() + " - Local " + metting.getPlace() + " - " + stringHour + "H" + stringMin);
        holder.textNameParticipant.setText("");
        for (int i = 0; i < metting.getContributors().size(); i++) {
            holder.textNameParticipant.setText(holder.textNameParticipant.getText() + " " + metting.getContributors().get(i));
        }
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //notifyItemRemoved(position);
                /** Problem color changed --> re-init list*/
                notifyDataSetChanged();
                mApiService.removeMetting(metting);

            }
        });

    }


    @Override
    public int getItemCount() {
        return mMettings.size();
    }

    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}

class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_metting_list_name)
        public TextView textNameMetting;
        @BindView(R.id.item_metting_list_delete_button)
        public ImageButton buttonDelete;
        @BindView(R.id.item_metting_list_icon)
        public CircularImageView imgIcon;
        @BindView(R.id.item_metting_list_name_participant)
        public TextView textNameParticipant;


    public ViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        /** Replace ButterKnife */
        textNameMetting = itemView.findViewById(R.id.item_metting_list_name);
        imgIcon = itemView.findViewById(R.id.item_metting_list_icon);
        textNameParticipant = itemView.findViewById(R.id.item_metting_list_name_participant);
        buttonDelete = itemView.findViewById(R.id.item_metting_list_delete_button);
    }
}
