package com.vktrhrsny.uimascot;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.graphics.drawable.Animatable2;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.concurrent.ThreadLocalRandom;

public class RandomMoveMode implements MascotState {

    private static RandomMoveMode instance = null;
    private MascotStateMachine mascotStateMachine;
    private Path path;


    static RandomMoveMode getInstance(@Nullable MascotStateMachine mascotStateMachine){
        if(instance==null){
            synchronized (RandomMoveMode.class){
                if(instance==null){
                    instance = new RandomMoveMode(mascotStateMachine);
                }
            }
        }
        return instance;
    }

    private RandomMoveMode(MascotStateMachine mascotStateMachine) {
        this.mascotStateMachine = mascotStateMachine;
        path = new Path();
    }

    @Override
    public void move(@Nullable View view) {

        if(mascotStateMachine!=null) {

            int[] hely = new int[2];
            mascotStateMachine.getView().getLocationOnScreen(hely);

            int x1 = hely[0];
            int y1 = hely[1];

            int x0 = ThreadLocalRandom.current().nextInt((int) mascotStateMachine.getScreenWidth() / 4 - mascotStateMachine.getView().getWidth(),
                    (int) mascotStateMachine.getScreenWidth() / 2 + mascotStateMachine.getView().getWidth());
            int y0 = ThreadLocalRandom.current().nextInt((int) mascotStateMachine.getScreenWidth() / 4 - mascotStateMachine.getView().getWidth(),
                    (int) mascotStateMachine.getScreenWidth() + mascotStateMachine.getView().getWidth());

            float X = (float) (x0 + x1) / 3;
            float Y = (float) (y0 + y1) / 3;

            path.moveTo(x1, y1);
            path.quadTo(X, Y, x0, y0);
            ObjectAnimator animator = ObjectAnimator.ofFloat(mascotStateMachine.getView(), View.X, View.Y, path);
            animator.setDuration(2000);
            animator.setInterpolator(mascotStateMachine.getInterpolator());
            animator.start();
            path.reset();

            if (mascotStateMachine.getMirrored()) {
                    if (x0 < mascotStateMachine.getScreenWidth() / 2)
                        mascotStateMachine.getView().setScaleX(-1f);
                    else mascotStateMachine.getView().setScaleX(1f);
                }
            }

        }


    @Override
    public void move(int x, int y) {

    }

    @Override
    public void animate(Animatable2 anim) {
        if(!anim.isRunning()){
            anim.start();
        }else{
            anim.stop();
        }
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

