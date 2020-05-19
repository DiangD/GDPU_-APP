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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bin.david.form.core.SmartTable;
import com.qzh.daka.R;
import com.qzh.daka.common.context.LogUserContextHolder;
import com.qzh.daka.common.receiver.InternetReceiver;
import com.qzh.daka.entity.ClockInItem;
import com.qzh.daka.entity.LogUserOperatedStatus;
import com.qzh.daka.entity.vo.ClockInItemsVo;
import com.qzh.daka.entity.vo.LogUserDetails;
import com.qzh.daka.service.DailyHealthDetailsService;
import com.qzh.daka.service.LogUserService;
import com.qzh.daka.task.ClockInHistoryTask;
import com.qzh.daka.task.LogUserTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class ClockInHistoryFragment extends Fragment {

    private SmartTable table;
    private List<ClockInItem> clockInItems = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private InternetReceiver receiver = new InternetReceiver();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.clock_in_history_view, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        table = view.findViewById(R.id.latest_clock_in_table);
        swipeRefreshLayout = view.findViewById(R.id.sw_history);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        Objects.requireNonNull(getActivity()).registerReceiver(receiver, filter);
        refresh();
        LogUserDetails LogUser = LogUserContextHolder.getContext(getContext());
        if (LogUser == null) {
            Toast.makeText(getContext(),"未验证登录",Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            ClockInItemsVo clockInItemsVo = new ClockInHistoryTask().execute(LogUser).get();
            if (clockInItemsVo.getClockInItems() != null && clockInItemsVo.getClockInItems().size() > 0) {
                clockInItems = clockInItemsVo.getClockInItems();
                table.setData(clockInItems);
            }
            if (clockInItemsVo.getStatus().equals(LogUserOperatedStatus.USER_EXPIRE)) {
                LogUserDetails details = new LogUserTask().execute(LogUser.getLogUser()).get();
                LogUserContextHolder.setContext(details);
            }
            Toast.makeText(getContext(),clockInItemsVo.getStatus().getMessage(),Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void refresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            clockInItems.clear();
            LogUserDetails LogUser = LogUserContextHolder.getContext(getContext());
            ClockInItemsVo clockInItemsVo;
            if (LogUser != null) {
                try {
                    clockInItemsVo = new ClockInHistoryTask().execute(LogUser).get();
                    if (clockInItemsVo.getClockInItems() != null && clockInItemsVo.getClockInItems().size() > 0) {
                        clockInItems = clockInItemsVo.getClockInItems();
                        table.setData(clockInItems);
                    }
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(), clockInItemsVo.getStatus().getMessage(), Toast.LENGTH_LONG).show();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.toolbar, menu);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle("最近打卡记录~");
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
