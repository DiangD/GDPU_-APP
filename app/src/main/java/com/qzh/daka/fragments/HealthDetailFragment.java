package com.qzh.daka.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.qzh.daka.R;
import com.qzh.daka.common.constant.SystemConstant;
import com.qzh.daka.common.context.LogUserContextHolder;
import com.qzh.daka.entity.DailyHealthDetails;
import com.qzh.daka.entity.LogUserOperatedStatus;
import com.qzh.daka.service.DailyHealthDetailsService;
import com.qzh.daka.service.LogUserService;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class HealthDetailFragment extends Fragment {
    private Button submit;
    private Spinner atSchoolSpinner;
    private EditText locationInput;
    private Spinner observationSpinner;
    private Spinner healthSpinner;
    private EditText tempInput;
    private EditText describeInput;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.health_detail_view, container, false);
        initView(view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        submit.setOnClickListener(view -> {
            String location = locationInput.getText().toString();
            String temp = tempInput.getText().toString();
            String describe = describeInput.getText().toString();
            if (StringUtils.isEmpty(location) || StringUtils.isEmpty(temp)) {
                Toast.makeText(getContext(), LogUserOperatedStatus.PARAMS_NOT_NULL.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
            Double temperature = Double.valueOf(temp);
            String atSchool = getSpinnerValue(atSchoolSpinner);
            String observation = getSpinnerValue(observationSpinner);
            String healthDetails = getSpinnerValue(healthSpinner);
            DailyHealthDetails dailyHealthDetails = new DailyHealthDetails(SystemConstant.ID_IN_DB, atSchool, location, observation, healthDetails, temperature, describe);
            DailyHealthDetailsService.saveDailyHealthDetails(getContext(), dailyHealthDetails);
            Toast.makeText(getContext(), LogUserOperatedStatus.SUBMIT_SUCCESS.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }


    private void initView(View view) {
        submit = view.findViewById(R.id.health_detail_save);
        atSchoolSpinner = view.findViewById(R.id.at_school);
        locationInput = view.findViewById(R.id.log_user_location);
        observationSpinner = view.findViewById(R.id.sp_medical_observation);
        healthSpinner = view.findViewById(R.id.sp_daily_health);
        tempInput = view.findViewById(R.id.daily_temperature);
        describeInput = view.findViewById(R.id.describe);
        // TODO: 2020/5/17 回显数据
        boolean isEmpty = DailyHealthDetailsService.isEmpty(getContext());
        initData(!isEmpty);
    }

    private String getSpinnerValue(Spinner spinner) {
        return spinner.getSelectedItem().toString();
    }

    private void initData(boolean isNeed) {
        if (isNeed) {
            DailyHealthDetails healthDetails = DailyHealthDetailsService.getDailyHealthDetails(getContext(), SystemConstant.ID_IN_DB);
            locationInput.setText(healthDetails.getLocation());
            tempInput.setText(String.valueOf(healthDetails.getTemperature()));
            if (!StringUtils.isEmpty(healthDetails.getDescribe())) {
                describeInput.setText(healthDetails.getDescribe());
            }
            setSpinnerDefaultValue(atSchoolSpinner, healthDetails.getIsAtSchool());
            setSpinnerDefaultValue(observationSpinner, healthDetails.getObservation());
            setSpinnerDefaultValue(healthSpinner, healthDetails.getHealth());
        }
    }

    /**
     * spinner 接收默认值的Spinner
     * value 需要设置的默认值
     */
    private void setSpinnerDefaultValue(Spinner spinner, String value) {
        SpinnerAdapter apsAdapter = spinner.getAdapter();
        int size = apsAdapter.getCount();
        for (int i = 0; i < size; i++) {
            if (TextUtils.equals(value, apsAdapter.getItem(i).toString())) {
                spinner.setSelection(i, true);
                break;
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.toolbar, menu);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle("健康信息哦~");
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Toast.makeText(getContext(), "肥肥牛逼！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete_log_user:
                LogUserService.deleteLogUser(getContext());
                LogUserContextHolder.clearContext();
                Toast.makeText(getContext(), "登出成功！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete_health_detail:
                DailyHealthDetailsService.deleteHealthDetail(getContext());
                Toast.makeText(getContext(), "健康信息已清除！", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

}
