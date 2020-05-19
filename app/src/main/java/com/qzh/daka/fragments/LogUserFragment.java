package com.qzh.daka.fragments;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.qzh.daka.R;
import com.qzh.daka.common.constant.SystemConstant;
import com.qzh.daka.common.context.LogUserContextHolder;
import com.qzh.daka.common.receiver.InternetReceiver;
import com.qzh.daka.entity.LogUser;
import com.qzh.daka.entity.LogUserOperatedStatus;
import com.qzh.daka.entity.vo.LogUserDetails;
import com.qzh.daka.service.DailyHealthDetailsService;
import com.qzh.daka.service.LogUserService;
import com.qzh.daka.task.LogUserTask;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class LogUserFragment extends Fragment {

    private EditText stuIdInput;
    private EditText pwdInput;
    private Button submit;
    private InternetReceiver receiver = new InternetReceiver();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.log_user_view, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        stuIdInput = view.findViewById(R.id.log_user_number);
        pwdInput = view.findViewById(R.id.log_user_password);
        submit = view.findViewById(R.id.btn_log_user_save);
        boolean isEmpty = LogUserService.isEmpty(getContext());
        initData(!isEmpty);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        Objects.requireNonNull(getActivity()).registerReceiver(receiver, filter);
        submit.setOnClickListener((view) -> {
            String studentNum = stuIdInput.getText().toString();
            String password = pwdInput.getText().toString();
            //判空
            if (StringUtils.isEmpty(studentNum)||StringUtils.isEmpty(password)) {
                Toast.makeText(getContext(), "用户名或密码不能为空~", Toast.LENGTH_LONG).show();
                return;
            }
            LogUser user = LogUser.builder().studentNum(studentNum).password(password).build();
            try {
                AsyncTask<LogUser, Void, LogUserDetails> execute = new LogUserTask().execute(user);
                LogUserDetails logUserDetails = execute.get();
                Toast.makeText(getContext(),logUserDetails.getStatus().getMessage(),Toast.LENGTH_SHORT).show();
                if (logUserDetails.getStatus().getMessage().equals(LogUserOperatedStatus.VERIFY_SUCCESS.getMessage())) {
                    LogUserContextHolder.setContext(logUserDetails);
                    LogUser logUser = logUserDetails.getLogUser();
                    logUser.setId(SystemConstant.ID_IN_DB);
                    //插入到数据库
                    LogUserService.saveLogUser(getContext(),logUser);
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
    }

    private void initData(Boolean isNeed) {
        if (isNeed) {
            LogUser logUser = LogUserService.getLogUser(getContext(), SystemConstant.ID_IN_DB);
            stuIdInput.setText(logUser.getStudentNum());
            pwdInput.setText(logUser.getPassword());
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.toolbar, menu);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle("验证用户~");
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Toast.makeText(getContext(),"肥肥牛逼！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete_log_user:
                LogUserService.deleteLogUser(getContext());
                LogUserContextHolder.clearContext();
                Toast.makeText(getContext(),"登出成功！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete_health_detail:
                DailyHealthDetailsService.deleteHealthDetail(getContext());
                Toast.makeText(getContext(),"健康信息已清除！",Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}