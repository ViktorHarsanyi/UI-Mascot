package com.vktrhrsny.uimascot;

import android.os.Looper;
import android.view.View;
import android.view.animation.Interpolator;

import androidx.annotation.Nullable;

import android.os.Handler;


public class MascotStateMachine implements MascotState,Runnable, View.OnClickListener {

    private MascotState state;
    private float screenWidth;
    private Handler handler;
    private View view;
    private Interpolator interpolator;
    private Long duration;
    private boolean isMirrored, isLooping;
    private int animationCode;

    public static final int GUIDE_MODE = 1;
    public static final int RANDOM_MODE = 2;
    public static final int IDLE_MODE = 3;

    public static final int ROTATE_CW = 1;
    public static final int ROTATE_CCW = -1;
    public static final int SCALE_UP = 2;
    public static final int SCALE_DOWN = -2;

    private MascotStateMachine(View view,float screenWidth,Interpolator interpolator,Long duration,boolean isMirrored,int animationCode,boolean isLooping){

        this.view = view;
        this.screenWidth = screenWidth;
        this.interpolator = interpolator;
        this.duration = duration;
        this.isMirrored = isMirrored;
        this.animationCode = animationCode;
        this.isLooping = isLooping;
        handler = new Handler(Looper.myLooper());

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void move(View view) {
        state.move(view);
    }

    @Override
    public void move(int x,int y) {
        state.move(x,y);
    }

    @Override
    public void animate(int animationCode) {
        state.animate(animationCode);
    }

    @Override
    public void talk(String text) {
        state.talk(text);
    }

    @Override
    public void dispose() {
        if(state!=null)
        state.dispose();
        handler.removeCallbacks(this);
    }

    @Override
    public void run() {
        if(state instanceof RandomMoveMode)
            state.move(null);
        if(isLooping)
            state.animate(animationCode);

        handler.postDelayed(this, duration);
    }

    public void setState(int stateCode){
        switch (stateCode){
            case GUIDE_MODE:
                state = GuideMode.getInstance(this);
                break;
            case RANDOM_MODE:
                state = RandomMoveMode.getInstance(this);
                break;
            case IDLE_MODE:
                state = IdleMode.getInstance(this);
                break;

                default:state = RandomMoveMode.getInstance(this);

        }
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

    float getScreenWidth(){return screenWidth;}

    boolean getMirrored(){return isMirrored;}

    public static class Builder{

        private Long duration;
        private Interpolator interpolator;
        private View view;
        private float screenWidth;
        private boolean isMirrored=true, isLooping=false;
        private int animationCode=0;

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

        public Builder setMirrored(boolean isMirrored){
            this.isMirrored=isMirrored;
            return this;
        }

        public Builder setAnimationType(int animationCode, boolean isLooping){
            this.animationCode = animationCode;
            this.isLooping = isLooping;
            return this;
        }

        public MascotStateMachine build(){
            return new MascotStateMachine(view,screenWidth,interpolator,duration,isMirrored,animationCode,isLooping);
        }

    }
}
