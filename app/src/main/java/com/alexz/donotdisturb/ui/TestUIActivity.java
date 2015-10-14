package com.alexz.donotdisturb.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.alexz.donotdisturb.R;
import com.alexz.donotdisturb.TestData;
import com.alexz.donotdisturb.ui.view.MenuWheelView;

/**
 * Created by alex on 12.10.15.
 */
public class TestUIActivity extends AppCompatActivity implements MenuWheelView.WheelItemListener {

    private TextSwitcher aboutOptionsText;
    private MenuWheelView wheelMenu;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        initViews();
    }

    private void initViews() {
        initTextSwitcher();
        wheelMenu = (MenuWheelView) findViewById(R.id.wheel_menu);
        wheelMenu.setOnItemSelectListener(this);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setAdapter();
    }

    private void setAdapter() {
        recyclerView.setAdapter(
                new RecyclerViewAdapter(TestData.getTestCollection(4))
        );
    }

    private void initTextSwitcher() {
        aboutOptionsText = (TextSwitcher) findViewById(R.id.about_option);
        aboutOptionsText.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(TestUIActivity.this);
                textView.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL);
                textView.setTextSize(18);
                textView.setLineSpacing(1.2f,1.2f);
                textView.setTextColor(Color.WHITE);
                return textView;
            }
        });

        Animation inAnimation = AnimationUtils.loadAnimation(this, R.anim.slow_fade_in);
        Animation outAnimation = AnimationUtils.loadAnimation(this, R.anim.fast_fade_out);
        aboutOptionsText.setInAnimation(inAnimation);
        aboutOptionsText.setOutAnimation(outAnimation);

        aboutOptionsText.setText(getResources().getString(R.string.about_clock_text));
    }


    @Override
    public void onItemSelected(int item) {
        switch (item) {
            case MenuWheelView.WIFI:
                aboutOptionsText.setText(getResources().getString(R.string.about_wifi_text));
                break;

            case MenuWheelView.CLOCK:
                aboutOptionsText.setText(getResources().getString(R.string.about_clock_text));
                break;

            case MenuWheelView.GPS:
                aboutOptionsText.setText(getResources().getString(R.string.about_gps_text));
                break;
        }
    }
}
