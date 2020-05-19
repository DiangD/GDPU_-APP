package com.qzh.daka.fragments;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.qzh.daka.R;
import com.qzh.daka.common.context.LogUserContextHolder;
import com.qzh.daka.common.receiver.InternetReceiver;
import com.qzh.daka.entity.LogUserOperatedStatus;
import com.qzh.daka.entity.vo.LogUserDetails;
import com.qzh.daka.service.DailyHealthDetailsService;
import com.qzh.daka.service.LogUserService;
import com.qzh.daka.task.ClockInTask;
import com.qzh.daka.task.LogUserTask;

import java.util.Objects;
import java.util.concurrent.ExecutionException;


public class ClockInFragment extends Fragment {
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
        View view = inflater.inflate(R.layout.clock_in_view, container, false);
        initView(view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        Objects.requireNonNull(getActivity()).registerReceiver(receiver, filter);
        submit.setOnClickListener(view -> {
            LogUserDetails LogUser = LogUserContextHolder.getContext(getContext());
            if (LogUser == null) {
                Toast.makeText(getContext(),"未验证登录",Toast.LENGTH_SHORT).show();
                return;
            }
            boolean isEmpty = DailyHealthDetailsService.isEmpty(getContext());
            if (isEmpty) {
                Toast.makeText(getContext(),"未填写健康信息",Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                LogUserDetails logUserDetails = new ClockInTask(getContext()).execute(LogUser).get();
                if (logUserDetails.getStatus().equals(LogUserOperatedStatus.USER_EXPIRE)) {
                    LogUserDetails details = new LogUserTask().execute(logUserDetails.getLogUser()).get();
                    LogUserContextHolder.setContext(details);
                }
                Toast.makeText(getContext(),logUserDetails.getStatus().getMessage(),Toast.LENGTH_LONG).show();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.toolbar, menu);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle("记得打卡~");
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

    private void initView(View view) {
        submit = view.findViewById(R.id.btn_clock_in);
    }
}
