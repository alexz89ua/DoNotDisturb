package com.alexz.donotdisturb.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alexz.donotdisturb.R;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Scene;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import java.util.ArrayList;

/**
 * Created by alex on 12.10.15.
 */
public class MenuWheelView extends RelativeLayout implements View.OnClickListener {

    public static final int WIFI = 0;
    public static final int CLOCK = 1;
    public static final int GPS = 2;
    private int CURRENT_POSITION = CLOCK;

    private ViewGroup sceneRoot;
    private Scene sceneCenter, sceneLeft, sceneRight;
    private TransitionSet set;
    private GestureDetector mDetector;
    private LinearLayout linearLayoutTouch;
    private ArrayList<Scene> allScene = new ArrayList<Scene>();
    private static final int SWIPE_THRESHOLD = 30;
    private static final int SWIPE_VELOCITY_THRESHOLD = 30;
    private WheelItemListener callback;

    public MenuWheelView(Context context) {
        super(context);
        initViews(context);
    }

    public MenuWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public MenuWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }


    private void initViews(Context context) {
        inflate(context, R.layout.menu_wheel, this);

        sceneRoot = (ViewGroup) findViewById(R.id.scene_root);
        sceneCenter = Scene.getSceneForLayout(sceneRoot, R.layout.scene_center, context);
        sceneLeft = Scene.getSceneForLayout(sceneRoot, R.layout.scene_left, context);
        sceneRight = Scene.getSceneForLayout(sceneRoot, R.layout.scene_right, context);

        initClickListeners(sceneRoot);

        allScene.add(WIFI, sceneRight);
        allScene.add(CLOCK, sceneCenter);
        allScene.add(GPS, sceneLeft);

        linearLayoutTouch = (LinearLayout) findViewById(R.id.touch);
        linearLayoutTouch.setClickable(true);
        linearLayoutTouch.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mDetector.onTouchEvent(motionEvent);
                return true;
            }
        });

        set = new TransitionSet();
        set.addTransition(new ChangeBounds());
        set.setOrdering(TransitionSet.ORDERING_TOGETHER);
        set.setDuration(500);
        set.setInterpolator(new AccelerateInterpolator());

        mDetector = new GestureDetector(context, new GestureListener());
    }

    public void setOnItemSelectListener(WheelItemListener callback) {
        this.callback = callback;
    }

    private void initClickListeners(ViewGroup sceneRoot) {
        sceneRoot.findViewById(R.id.item1).setOnClickListener(this);
        sceneRoot.findViewById(R.id.item2).setOnClickListener(this);
        sceneRoot.findViewById(R.id.item3).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.item1:
                TransitionManager.go(sceneLeft, set);
                onItemSelected(WIFI);
                break;

            case R.id.item2:
                TransitionManager.go(sceneCenter, set);
                onItemSelected(CLOCK);
                break;

            case R.id.item3:
                TransitionManager.go(sceneRight, set);
                onItemSelected(GPS);
                break;
        }
    }

    private void onItemSelected(int item){
        if (callback != null){
            callback.onItemSelected(item);
        }
    }


    class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(distanceX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                    result = true;
                }
                result = true;

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }


    public void onSwipeRight() {
        if (CURRENT_POSITION > 0) {
            CURRENT_POSITION--;
            TransitionManager.go(allScene.get(CURRENT_POSITION), set);
            onItemSelected(CURRENT_POSITION);
        }
    }

    public void onSwipeLeft() {
        if (CURRENT_POSITION < 2) {
            CURRENT_POSITION++;
            TransitionManager.go(allScene.get(CURRENT_POSITION), set);
            onItemSelected(CURRENT_POSITION);
        }
    }


    public interface WheelItemListener {

        public void onItemSelected(int item);
    }

}
