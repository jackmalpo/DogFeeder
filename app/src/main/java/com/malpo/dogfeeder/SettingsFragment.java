package com.malpo.dogfeeder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.metova.slim.SlimFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

/**
 * Created by Jack on 2/4/16.
 */
public class SettingsFragment extends SlimFragment {

    @Bind(R.id.ding_on_feed_switch)
    Switch mDingSwitch;

    @Bind(R.id.auto_refill_water_switch)
    Switch mAutorefillSwitch;

    @Bind(R.id.food_quantity)
    EditText mFoodQuantity;

    @Bind(R.id.post_to_facebook_switch)
    Switch mFacebookSwitch;


    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("PREFERENCES1", Context.MODE_PRIVATE);

        boolean dingSwitchEnabled = sharedPreferences.getBoolean(getString(R.string.ding_switch_enabled), false);
        boolean autoRefillSwitchEnabled = sharedPreferences.getBoolean(getString(R.string.auto_refill_switch_enabled), false);
        int foodQuantity = sharedPreferences.getInt(getString(R.string.food_quantity_amount), 200);
        boolean facebookPostEnabled = sharedPreferences.getBoolean(getString(R.string.facebook_switch_enabled), false);

        mDingSwitch.setChecked(dingSwitchEnabled);
        mAutorefillSwitch.setChecked(autoRefillSwitchEnabled);
        mFacebookSwitch.setChecked(facebookPostEnabled);
        mFoodQuantity.setText(String.valueOf(foodQuantity));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("PREFERENCES1", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getString(R.string.ding_switch_enabled), mDingSwitch.isChecked());
        editor.putBoolean(getString(R.string.auto_refill_switch_enabled), mAutorefillSwitch.isChecked());
        editor.putBoolean(getString(R.string.facebook_switch_enabled), mFacebookSwitch.isChecked());
        editor.putInt(getString(R.string.food_quantity_amount), Integer.valueOf(mFoodQuantity.getText().toString()));

        editor.apply();

        ButterKnife.unbind(this);
    }
}
