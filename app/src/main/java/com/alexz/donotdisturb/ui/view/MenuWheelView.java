package com.alexz.donotdisturb.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import com.alexz.donotdisturb.R;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Scene;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

/**
 * Created by alex on 12.10.15.
 */
public class MenuWheelView extends LinearLayout{
    private ViewGroup sceneRoot;
    private Scene sceneCenter, sceneLeft, sceneRight;
    private float mStartDragX;
    private boolean move;
    private TransitionSet set;
    private GestureDetector mDetector;

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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MenuWheelView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context);
    }

    private void initViews(Context context){
        inflate(context, R.layout.menu_wheel, this);
        setClickable(true);
        sceneRoot = (ViewGroup) findViewById(R.id.scene_root);
        sceneCenter = Scene.getSceneForLayout(sceneRoot, R.layout.scene_center, context);
        sceneLeft = Scene.getSceneForLayout(sceneRoot, R.layout.scene_left, context);
        sceneRight = Scene.getSceneForLayout(sceneRoot, R.layout.scene_right, context);

        set = new TransitionSet();
        set.addTransition(new ChangeBounds());
        set.setOrdering(TransitionSet.ORDERING_TOGETHER);
        set.setDuration(500);
        set.setInterpolator(new AccelerateInterpolator());

        mDetector = new GestureDetector(context, new GestureListener());

        Button move = (Button) findViewById(R.id.move);
        move.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.go(sceneRight, set);
            }
        });
        move.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                TransitionManager.go(sceneCenter, set);
                return false;
            }
        });
    }


    class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            TransitionManager.go(sceneLeft, set);
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

}
