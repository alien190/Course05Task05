package com.example.alien.course05task05;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout mSceneRoot;
    private Button mButton;
    private int mRingNumber;
    private int mSceneNumber;
    private int[] mScenesListId = {R.layout.scene00, R.layout.scene01, R.layout.scene02, R.layout.scene03};
    private final int mSceneCount = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSceneRoot = findViewById(R.id.sceneRoot);
        mButton = findViewById(R.id.button);
        mRingNumber = 0;
        mSceneNumber = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mButton.setOnClickListener(this::onClickButton);
    }

    @Override
    protected void onPause() {
        mButton.setOnClickListener(null);
        super.onPause();
    }

    private void onClickButton(View view) {
        Scene scene = Scene.getSceneForLayout(mSceneRoot, getNextSceneId(), getBaseContext());
        TransitionSet set = new TransitionSet();
        set.addTransition(new ChangeBounds());
        set.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
        set.setDuration(500);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                mButton = mSceneRoot.findViewById(R.id.button);
                mButton.setOnClickListener(MainActivity.this::onClickButton);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
        TransitionManager.go(scene, set);
    }

    private int getNextSceneId() {
        mSceneNumber++;
        if (mSceneNumber >= mSceneCount) {
            mSceneNumber = 0;
            mRingNumber++;
            if (mRingNumber % 2 == 0) {
                Toast.makeText(this, R.string.ring_complete, Toast.LENGTH_SHORT).show();
            }
        }
        return mScenesListId[mSceneNumber];
    }
}
