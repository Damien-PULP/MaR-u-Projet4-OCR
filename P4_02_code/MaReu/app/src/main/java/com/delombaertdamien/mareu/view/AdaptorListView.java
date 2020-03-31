package com.delombaertdamien.mareu.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.delombaertdamien.mareu.DI.DI;
import com.delombaertdamien.mareu.R;
import com.delombaertdamien.mareu.events.StartShowDetailEvent;
import com.delombaertdamien.mareu.model.Meeting;
import com.delombaertdamien.mareu.service.MeetingApiService;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdaptorListView extends RecyclerView.Adapter<ViewHolder> {

    private List<Meeting> mMeetings;
    private MeetingApiService mApiService;

    private final SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());

    public AdaptorListView(List<Meeting> items) {

        mMeetings = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mApiService = DI.getMeetingApiService();

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.meeting_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Meeting mMeeting = DI.getMeetingApiService().getMeetings().get(position);

        holder.imgIcon.setColorFilter(mMeeting.getId());

        holder.item.setOnClickListener(view -> {
            EventBus.getDefault().post(new StartShowDetailEvent(mMeeting.hashCode()));
        });

        String startHourTxt = format.format(mMeeting.getStartHourOfMeeting().getTime());
        String endHourTxt = format.format(mMeeting.getEndHourOfMeeting().getTime());

        holder.textNameMeeting.setText("Local " + mMeeting.getPlace() + " - " + startHourTxt + " - " + endHourTxt + " , " + mMeeting.getSubject());

        holder.textNameParticipant.setText("");

        for (int i = 0; i < mMeeting.getContributors().size(); i++) {
            holder.textNameParticipant.setText(holder.textNameParticipant.getText() + " " + mMeeting.getContributors().get(i));
        }

        holder.buttonDelete.setOnClickListener(view -> {
            notifyDataSetChanged();
            mApiService.removeMeeting(mMeeting);

        });

    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

}

class ViewHolder extends RecyclerView.ViewHolder{

        //UI Components
        @BindView(R.id.item_meeting_list_name)
        public TextView textNameMeeting;
        @BindView(R.id.item_meeting_list_delete_button)
        public ImageButton buttonDelete;
        @BindView(R.id.item_meeting_list_icon)
        public CircularImageView imgIcon;
        @BindView(R.id.item_meeting_list_name_participant)
        public TextView textNameParticipant;
        @BindView(R.id.item_meeting)
        public ConstraintLayout item;


    public ViewHolder(View itemView) {

        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
