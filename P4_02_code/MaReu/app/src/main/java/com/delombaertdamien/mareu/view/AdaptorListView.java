package com.delombaertdamien.mareu.view;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.delombaertdamien.mareu.DI.DI;
import com.delombaertdamien.mareu.R;
import com.delombaertdamien.mareu.model.Meeting;
import com.delombaertdamien.mareu.service.MeetingApiService;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdaptorListView extends RecyclerView.Adapter<ViewHolder> {

    private final List<Meeting> mMeetings;
    private Context mContext;
    private MeetingApiService mApiService;

    public AdaptorListView(List<Meeting> items, Context context) {
        mMeetings = items;
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
        Meeting mMeeting = DI.getMettingApiService().getMeetings().get(position);

        holder.imgIcon.setColorFilter(mMeeting.getId());

        String hourDateFormat = DateFormat.getTimeInstance(DateFormat.SHORT).format(mMeeting.getHourOfMeeting());;
        Log.d("ItemAdaptor", "hour :" + hourDateFormat);

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setParseIntegerOnly(true);

        String stringHour =  String.format("%.0f", mMeeting.getHourOfMeeting());
        String stringMin = String.format("%.0f",(mMeeting.getHourOfMeeting() - (Float.parseFloat(stringHour))) * 60);

        String timeOfMeeting = stringHour + " H " + stringMin;

        holder.textNameMetting.setText("Local " + mMeeting.getPlace() + " - " + mMeeting.getHourOfMeeting() + " H - " + mMeeting.getSubject());
        holder.textNameParticipant.setText("");
        for (int i = 0; i < mMeeting.getContributors().size(); i++) {
            holder.textNameParticipant.setText(holder.textNameParticipant.getText() + " " + mMeeting.getContributors().get(i));
        }
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //notifyItemRemoved(position);
                /** Problem color changed --> re-init list*/
                notifyDataSetChanged();
                mApiService.removeMeeting(mMeeting);

            }
        });

    }


    @Override
    public int getItemCount() {
        return mMeetings.size();
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
