package com.alexz.donotdisturb;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.alexz.donotdisturb.utils.RippleDrawable;

import java.util.List;

public class SettingsActivity extends Activity implements View.OnClickListener {


    private WifiManager mainWifi;
    private List<WifiConfiguration> wifiList;
    private String[] networks;
    private Button tvChoosedSpots, timeCase, tvGps;
    private BroadcastReceiver updateReceiver;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiList = mainWifi.getConfiguredNetworks();
        networks = new String[wifiList.size()];
        for (int i = 0; i < wifiList.size(); i++) {
            networks[i] = (wifiList.get(i)).SSID.replaceAll("\"", "");
        }

        tvChoosedSpots = (Button) findViewById(R.id.choosed);
        tvChoosedSpots.setOnClickListener(this);
        RippleDrawable.createRipple(tvChoosedSpots, getResources().getColor(R.color.material_blue));
        timeCase = (Button) findViewById(R.id.time);
        timeCase.setOnClickListener(this);
        RippleDrawable.createRipple(timeCase, getResources().getColor(R.color.material_blue));
        tvGps = (Button) findViewById(R.id.gps);
        RippleDrawable.createRipple(tvGps, getResources().getColor(R.color.material_blue));
        initUserSettings();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return super.onMenuItemSelected(featureId, item);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onStart() {
        super.onStart();
        initReceiver();
    }


    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(updateReceiver);
    }


    private void initReceiver(){
        IntentFilter intentFilter = new IntentFilter(QuietApp.event_update);
        updateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                initUserSettings();
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(updateReceiver, intentFilter);
    }



    private void initUserSettings(){
        tvChoosedSpots.setText(
                String.format(getResources().getString(R.string.trigger_wifi_text),
                        QuietApp.getInstans().getTrigersWiFi())
                        .replaceAll("\\[", "")
                        .replaceAll("\\]", ""));
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choosed:
                DialogUtil.choiseWiFiSpotsDialog(this, networks);
                break;
            case R.id.time:
                DialogUtil.choiseTimeDialog(this);
                break;
        }
    }

}
