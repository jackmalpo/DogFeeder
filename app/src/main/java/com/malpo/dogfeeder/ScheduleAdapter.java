package com.malpo.dogfeeder;

import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jack on 2/8/16.
 */
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private List<Schedule> schedules;
    private Context context;

    public ScheduleAdapter(Context context){
        schedules = new ArrayList<>();
        this.context = context;
    }

    public void addItem(Schedule schedule){
        schedules.add(schedule);
        notifyItemInserted(schedules.size() - 1);
    }

    public void removeItem(int pos){
        schedules.remove(pos);
        notifyItemRemoved(pos);
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.schedule_item, viewGroup, false);
        return new ScheduleViewHolder(v);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder scheduleViewHolder, int i) {
        Schedule schedule = schedules.get(i);
        scheduleViewHolder.bindSchedule(schedule, context);
    }


    public static class ScheduleViewHolder extends RecyclerView.ViewHolder{
        private Schedule schedule;
        private Context context;

        @Bind(R.id.mon)
        TextView mon;
        boolean monClicked;

        @Bind(R.id.tues)
        TextView tues;
        boolean tuesClicked;

        @Bind(R.id.wed)
        TextView wed;
        boolean wedClicked;

        @Bind(R.id.thurs)
        TextView thurs;
        boolean thursClicked;

        @Bind(R.id.fri)
        TextView fri;
        boolean friClicked;

        @Bind(R.id.sat)
        TextView sat;
        boolean satClicked;

        @Bind(R.id.sun)
        TextView sun;
        boolean sunClicked;

        @Bind(R.id.time_input)
        TextView timeInput;

        ScheduleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindSchedule(Schedule schedule, Context context){
            this.schedule = schedule;
            this.context = context;
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            int amPm = mcurrentTime.get(Calendar.AM_PM);
            String end = amPm == Calendar.AM ? "AM" : "PM";
            String min = String.valueOf(minute);
            if(minute < 10){
                min = "0" + minute;
            }
            String time = hour + ":" + min + " " + end;
            timeInput.setText(time);
        }

        private void setClicked(TextView v){
            v.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.circle_filled));
            v.setTextColor(ContextCompat.getColor(v.getContext(), android.R.color.white));
        }

        private void setUnclicked(TextView v){
            v.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.circle));
            v.setTextColor(ContextCompat.getColor(v.getContext(), android.R.color.black));
        }

        @OnClick(R.id.mon)
        public void doMonClick(View v){
            TextView view = (TextView) v;
            if(!monClicked) {
                setClicked(view);
                monClicked = true;
            } else {
                setUnclicked(view);
                monClicked = false;
            }
        }

        @OnClick(R.id.tues)
        public void doTuesClick(View v){
            TextView view = (TextView) v;
            if(!tuesClicked) {
                setClicked(view);
                tuesClicked = true;
            } else {
                setUnclicked(view);
                tuesClicked = false;
            }
        }

        @OnClick(R.id.wed)
        public void doWedClick(View v){
            TextView view = (TextView) v;
            if(!wedClicked) {
                setClicked(view);
                wedClicked = true;
            } else {
                setUnclicked(view);
                wedClicked = false;
            }
        }

        @OnClick(R.id.thurs)
        public void doThursClick(View v){
            TextView view = (TextView) v;
            if(!thursClicked) {
                setClicked(view);
                thursClicked = true;
            } else {
                setUnclicked(view);
                thursClicked = false;
            }
        }


        @OnClick(R.id.fri)
        public void doFriClick(View v){
            TextView view = (TextView) v;
            if(!friClicked) {
                setClicked(view);
                friClicked = true;
            } else {
                setUnclicked(view);
                friClicked = false;
            }
        }

        @OnClick(R.id.sat)
        public void doSatClick(View v){
            TextView view = (TextView) v;
            if(!satClicked) {
                setClicked(view);
                satClicked = true;
            } else {
                setUnclicked(view);
                satClicked = false;
            }
        }

        @OnClick(R.id.sun)
        public void doSunClick(View v){
            TextView view = (TextView) v;
            if(!sunClicked) {
                setClicked(view);
                sunClicked = true;
            } else {
                setUnclicked(view);
                sunClicked = false;
            }
        }

        @OnClick(R.id.time_input)
        public void onClick(View v){
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            final int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String amPm = "AM";

                    if(selectedHour >= 12){
                        amPm = "PM";
                    }

                    selectedHour = selectedHour % 12;

                    if(selectedHour == 0)
                        selectedHour = 12;

                    String minute = String.valueOf(selectedMinute);
                    if(selectedMinute < 10){
                        minute = "0" + selectedMinute;
                    }

                    String text = selectedHour + ":" + minute + " " + amPm;

                    timeInput.setText(text);
                }
            }, hour, minute, false);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        }
    }
}
