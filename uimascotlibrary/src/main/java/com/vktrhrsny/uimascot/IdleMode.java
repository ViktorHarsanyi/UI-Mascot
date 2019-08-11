package com.vktrhrsny.uimascot;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class IdleMode implements MascotState {

    private static IdleMode instance = null;
    private MascotStateMachine mascotStateMachine;
    private Path path;

    static IdleMode getInstance(@Nullable MascotStateMachine mascotStateMachine){
        if(instance==null){
            synchronized (IdleMode.class){
                if(instance==null){
                    instance = new IdleMode(mascotStateMachine);
                }
            }
        }
        return instance;
    }

    private IdleMode(MascotStateMachine mascotStateMachine) {
        this.mascotStateMachine = mascotStateMachine;
        path = new Path();
    }

    @Override
    public void move(@Nullable View view) {
        if(mascotStateMachine!=null){
            talk("");
            int[] place = new int[2];
            int[] placeView = new int[2];
            mascotStateMachine.getView().getLocationOnScreen(place);

            if(view!=null)
            view.getLocationOnScreen(placeView);
            else
            mascotStateMachine.getIdleDestinationView().getLocationOnScreen(placeView);

            int x1 = place[0];


            int x0 = placeView[0];
            int y0 = placeView[1];


            path.moveTo(x1, y0);
            path.lineTo(x0,y0-mascotStateMachine.getView().getHeight());
            Animator animator = ObjectAnimator.ofFloat(mascotStateMachine.getView(), View.X, View.Y, path);
            animator.setDuration(mascotStateMachine.getDuration());
            animator.setInterpolator(mascotStateMachine.getInterpolator());
            animator.start();

            path.reset();
            if (mascotStateMachine.getView().getScaleX()==-1f)
                mascotStateMachine.getView().setScaleX(1f);
        }
    }

    @Override
    public void move(int x, int y) {
        if(mascotStateMachine!=null) {
            talk("");
            int[] place = new int[2];
            int[] placeDestination = new int[]{x,y};
            mascotStateMachine.getView().getLocationOnScreen(place);

            int x1 = place[0];
            int y1 = place[1];
            int x0 = placeDestination[0];
            int y0 = placeDestination[1];

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

        }
    }



    @Override
    public void talk(String text) {
        if(mascotStateMachine.getView() instanceof TextView)
            ((TextView) mascotStateMachine.getView()).setText(text);
    }

    @Override
    public void dispose() {
        instance=null;
    }
}
