package com.vktrhrsny.uimascot;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.view.View;

import androidx.annotation.Nullable;

public class GuideMode implements MascotState {
    
    private static GuideMode instance = null;
    private MascotStateMachine mascotStateMachine;
    private Path path;

    public static GuideMode getInstance(@Nullable MascotStateMachine mascotStateMachine){
        if(instance==null){
            synchronized (GuideMode.class){
                if(instance==null){
                    instance = new GuideMode(mascotStateMachine);
                }
            }
        }
        return instance;
    }

    private GuideMode(MascotStateMachine mascotStateMachine) {
        this.mascotStateMachine = mascotStateMachine;
        path = new Path();
    }

    @Override
    public void moveTo(View view) {
        if(mascotStateMachine!=null) {

            int[] place = new int[2];
            int[] placeView = new int[2];
            mascotStateMachine.getView().getLocationOnScreen(place);
            view.getLocationOnScreen(placeView);
            int x1 = place[0];
            int y1 = place[1];


            int x0 = placeView[0];
            int y0 = placeView[1];

            float X = (float)(x0 + x1) / 3;
            float Y = (float)(y0 + y1) / 3;

            path.moveTo(x1, y1);
            path.quadTo(X, Y, x0, y0);
            Animator animator = ObjectAnimator.ofFloat(mascotStateMachine.getView(), View.X, View.Y, path);
            animator.setDuration(mascotStateMachine.getDuration());
            animator.setInterpolator(mascotStateMachine.getInterpolator());
            animator.start();

            path.reset();
            if (mascotStateMachine.getView().getScaleX()==-1f)
                mascotStateMachine.getView().setScaleX(1f);

            final View cv = view;

            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                   // talk(cv.getTag().toString());
                    talk("test");
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }
    }

    @Override
    public void move() {

    }

    @Override
    public void talk(String text) {
                mascotStateMachine.setText(text);
    }

    @Override
    public void dispose() {
        instance = null;
    }
}
