package com.vktrhrsny.uimascot;

import android.os.Looper;
import android.view.View;
import android.view.animation.Interpolator;

import androidx.annotation.Nullable;

import java.util.logging.Handler;
import java.util.logging.SocketHandler;

public class MascotStateMachine implements MascotState,Runnable, View.OnClickListener {

    private MascotState state;
    private float screenWidth;
    private Handler handler;
    private View view;
    private Interpolator interpolator;
    private Long duration;

    public static final int GUIDE_MODE = 1;

    private MascotStateMachine(View view,float screenWidth,Interpolator interpolator,Long duration){

        this.view = view;
        this.screenWidth = screenWidth;
        this.interpolator = interpolator;
        this.duration = duration;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void moveTo(View view) {
        state.moveTo(view);
    }

    @Override
    public void move() {
        state.move();
    }

    @Override
    public void talk(String text) {
        state.talk(text);
    }

    @Override
    public void dispose() {
        state.dispose();
    }

    @Override
    public void run() {

    }

    public void setState(int stateCode){
        switch (stateCode){
            case GUIDE_MODE:
                state = GuideMode.getInstance(this);
                break;


        }
    }

    void setText(String text){

    }

    View getView(){
        return view;
    }

    Interpolator getInterpolator(){
        return interpolator;
    }

    Long getDuration(){
        return duration;
    }

    public static class Builder{

        private Long duration;
        private Interpolator interpolator;
        private View view;
        private float screenWidth;

        public Builder(@Nullable final View view,final float screenWidth){
            this.view = view;
            this.screenWidth = screenWidth;
        }

        public Builder setInterpolator(Interpolator interpolator){
            this.interpolator = interpolator;
            return this;
        }

        public Builder setDuration(Long duration){
            this.duration = duration;
            return this;
        }

        public MascotStateMachine createStateMachine(){
            return new MascotStateMachine(view,screenWidth,interpolator,duration);
        }

    }
}
