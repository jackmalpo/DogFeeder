package com.malpo.dogfeeder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import com.metova.slim.SlimFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jack on 2/4/16.
 */
public class ScheduleFragment extends SlimFragment {

    @Bind(R.id.rv)
    RecyclerView mRecyclerView;

    @Bind(R.id.schedule_fab)
    FloatingActionButton scheduleFab;

    private ScheduleAdapter mScheduleAdapter;

    List<Schedule> scheduleList;

    public ScheduleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(llm);
        addStuffToRV();
    }

    private void addStuffToRV(){
        if(mScheduleAdapter == null && scheduleList == null) {
            scheduleList = new ArrayList<>();
            mScheduleAdapter = new ScheduleAdapter(getContext());
            mRecyclerView.setAdapter(mScheduleAdapter);
            mScheduleAdapter.addItem(new Schedule());

            ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                    //Remove swiped item from list and notify the RecyclerView
                    if(swipeDir == ItemTouchHelper.RIGHT){
                        mScheduleAdapter.removeItem(viewHolder.getAdapterPosition());
                    }
                }

                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }
            };

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
            itemTouchHelper.attachToRecyclerView(mRecyclerView);

        } else {
            mScheduleAdapter.addItem(new Schedule());
        }
    }

    @OnClick(R.id.schedule_fab)
    public void doClick(View view){
        mScheduleAdapter.addItem(new Schedule());
    }
}
