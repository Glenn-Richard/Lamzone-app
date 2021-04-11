package com.example.lamzone;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import services.ApiSerivces;
import services.ApiMeetingServices;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder> {
    //ApiServices
    private ApiSerivces mApiservices;
    List<Meeting> mMeetings;

    public MeetingAdapter(List<Meeting> items){
        mMeetings = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mApiservices = new ApiMeetingServices();
        Meeting meeting = mApiservices.getMeetings().get(position);
        holder.display(meeting);

    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView image;
        private final TextView ttop;
        private final TextView tbottom;
        private final ImageButton button;

        private Meeting currentMeeting;
        private Room room;

//        private Timestamp time;
//        Calendar cal = Calendar.getInstance();
//        @SuppressLint("SimpleDateFormat")
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd  HH:mm");
//        Date strDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_list_user_avatar);
            ttop = itemView.findViewById(R.id.item_list_user_top);
            tbottom = itemView.findViewById(R.id.item_list_user_bottom);
            button = itemView.findViewById(R.id.item_list_user_delete_button);

        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        @SuppressLint("SetTextI18n")
        public void display(Meeting meeting){
//            time.setTime(meeting.getTimestamp());
            currentMeeting = meeting;

            if(currentMeeting.getLocation().toString()=="Reunion A"){
            Glide.with(itemView.getContext())
                    .load(R.mipmap.blue)
                    .apply(RequestOptions.circleCropTransform())
                    .into(image);}
            if(currentMeeting.getLocation().toString()=="Reunion B"){
                Glide.with(itemView.getContext())
                        .load(R.mipmap.orange)
                        .apply(RequestOptions.circleCropTransform())
                        .into(image);}
            if(currentMeeting.getLocation().toString()=="Reunion C"){
                Glide.with(itemView.getContext())
                        .load(R.mipmap.magenta)
                        .apply(RequestOptions.circleCropTransform())
                        .into(image);}
//            try {
//                strDate = sdf.parse(time.toString());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            ttop.setText(currentMeeting.getLocation().getName()+"-14h00-"+ currentMeeting.getSubject());
            List<String> emails = currentMeeting.getEmails();
            String email = emails.stream()
                    .map(n -> String.valueOf(n))
                    .collect(Collectors.joining(","));

                tbottom.setText(email);

            button.setOnClickListener(v ->
                    mApiservices.deleteMeeting(meeting)
            );
        }
    }
}
