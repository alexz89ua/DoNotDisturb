package com.alexz.donotdisturb;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class SettingsActivity extends Activity implements View.OnClickListener {


    private WifiManager mainWifi;
    private QuietReceiver receiverWifi;
    private List<WifiConfiguration> wifiList;
    private String[] networks;
    private TextView tvChoosedSpots, timeCase;
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

        tvChoosedSpots = (TextView) findViewById(R.id.choosed);
        tvChoosedSpots.setOnClickListener(this);
        timeCase = (TextView) findViewById(R.id.time);
        timeCase.setOnClickListener(this);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "Info");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return super.onMenuItemSelected(featureId, item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        tvChoosedSpots.setText(
                String.format(getResources().getString(R.string.trigers_wifi_text),
                        QuietApp.getInstans().getTrigersWiFi())
                        .replaceAll("\\[", "")
                        .replaceAll("\\]", ""));
    }

    private void initReceiver(){

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
