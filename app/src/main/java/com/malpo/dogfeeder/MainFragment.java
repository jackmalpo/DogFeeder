package com.malpo.dogfeeder;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.metova.slim.SlimFragment;
import com.metova.slim.annotation.Callback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jack on 2/2/16.
 */
public class MainFragment extends SlimFragment {

    ProgressBar foodLevelProgress;

    ProgressBar waterLevelProgress;

    @Callback
    OnFoodGoneListener onFoodGoneListener;

    @Bind(R.id.last_fed_date)
    TextView lastFedTextView;

    TextView mFoodLevelPercentage;

    TextView mWaterLevelPercentage;

    private static Integer BASE_FOOD_AMOUNT = 45;
    private static Integer BASE_WATER_AMOUNT = 80;


    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        foodLevelProgress = (ProgressBar) view.findViewById(R.id.food_level_progress);
        waterLevelProgress = (ProgressBar) view.findViewById(R.id.water_level_progress);
        mFoodLevelPercentage = (TextView) view.findViewById(R.id.food_level_percentage);
        mWaterLevelPercentage = (TextView) view.findViewById(R.id.water_level_percentage);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new FirstProgressUpdate().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, BASE_FOOD_AMOUNT);
                new SecondProgressUpdate().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, BASE_WATER_AMOUNT);
            }
        }, 500);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void feedDog(){
        new UpdateFoodLevel().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 0);
    }


    class FirstProgressUpdate extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            for (int count = 0; count <= params[0]; count++) {
                try {
                    Thread.sleep(5);
                    publishProgress(count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Task Completed.";
        }
        @Override
        protected void onPostExecute(String result) {
        }
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            foodLevelProgress.setProgress(values[0]);
            String percentage = values[0] + "%";
            mFoodLevelPercentage.setText(percentage);
        }
    }


    class SecondProgressUpdate extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            for (int count = 0; count <= params[0]; count++) {
                try {
                    Thread.sleep(10);
                    publishProgress(count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Task Completed.";
        }
        @Override
        protected void onPostExecute(String result) {
        }
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            waterLevelProgress.setProgress(values[0]);
            String percentage = values[0] + "%";
            mWaterLevelPercentage.setText(percentage);
        }
    }

    class UpdateFoodLevel extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            Integer newLevel = BASE_FOOD_AMOUNT - 20;
            if (newLevel < 0)
                newLevel = 0;
            for (int count = BASE_FOOD_AMOUNT; count >= newLevel; count--) {
                try {
                    Thread.sleep(10);
                    publishProgress(count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            BASE_FOOD_AMOUNT = newLevel;
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
            lastFedTextView.setText(dateTimeInstance.format(Calendar.getInstance().getTime()));
        }
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            Integer value = values[0];
            foodLevelProgress.setProgress(value);
            String percentage = values[0] + "%";
            mFoodLevelPercentage.setText(percentage);
            if(value <= 0){
                onFoodGoneListener.onFoodGone();
            }
        }
    }

    public interface OnFoodGoneListener{
        void onFoodGone();
    }
}
