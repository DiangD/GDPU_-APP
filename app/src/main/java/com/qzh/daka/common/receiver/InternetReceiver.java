package com.qzh.daka.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AlertDialog;

import java.util.Objects;

public class InternetReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = Objects.requireNonNull(manager).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            // TODO: 2019/12/27
        } else {
            Dialog(context);
        }
    }

    private void Dialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Warning");
        builder.setMessage("接受不到脑电波~");
        builder.setCancelable(false);
        builder.setPositiveButton("设置", (dialog, which) -> {
            context.startActivity(new Intent("android.net.wifi.PICK_WIFI_NETWORK"));
        });
        builder.setNegativeButton("好的", (dialog, which) -> {
            // TODO: 2019/12/27
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
