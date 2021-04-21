package com.example.lamzone;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.format.DateFormat;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.text.format.DateFormat.format;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder> {

    List<Meeting> mMeetings;
    ListItemListener mListener;

    public MeetingAdapter(ListItemListener listener){
        mListener=listener;
        mMeetings=new ArrayList<Meeting>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list,parent,false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.display(mMeetings.get(position));

    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public  void updateList(ArrayList<Meeting> meetings){
        mMeetings.clear();
        mMeetings.addAll(meetings);
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView image;
        private final TextView ttop;
        private final TextView tbottom;
        private final ImageButton button;

        private Meeting currentMeeting;

        private String time ;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_list_user_avatar);
            ttop = itemView.findViewById(R.id.item_list_user_top);
            tbottom = itemView.findViewById(R.id.item_list_user_bottom);
            button = itemView.findViewById(R.id.item_list_user_delete_button);

        }
        @SuppressLint("SetTextI18n")
        public void display(Meeting meeting){
            currentMeeting = meeting;

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(currentMeeting.getTimestamp()*1000);
            time = DateFormat.format("dd/MM HH:mm",cal).toString();

            if(currentMeeting.getLocation().getName().equals("Reunion A")){
                Glide.with(itemView.getContext())
                        .load(R.mipmap.blue)
                        .apply(RequestOptions.circleCropTransform())
                        .into(image);}
            if(currentMeeting.getLocation().getName().equals("Reunion B")){
                Glide.with(itemView.getContext())
                        .load(R.mipmap.orange)
                        .apply(RequestOptions.circleCropTransform())
                        .into(image);}
            if(currentMeeting.getLocation().getName().equals("Reunion C")){
                Glide.with(itemView.getContext())
                        .load(R.mipmap.magenta)
                        .apply(RequestOptions.circleCropTransform())
                        .into(image);}

            ttop.setText(currentMeeting.getLocation().getName()+"-"+time+"-"+ currentMeeting.getSubject());
            List<String> emails = currentMeeting.getEmails();
            String email = emails.get(0);

                    for(int i=1;i<emails.size();i++){
                            email = email +","+ emails.get(i);
                    }

                tbottom.setText(email);

            button.setOnClickListener(v ->
            mListener.onDelete(currentMeeting)
            );
        }
    }
}
