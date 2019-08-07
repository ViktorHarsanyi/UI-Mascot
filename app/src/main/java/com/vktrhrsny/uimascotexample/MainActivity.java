package com.vktrhrsny.uimascotexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.vktrhrsny.uimascot.MascotStateMachine;


public class MainActivity extends AppCompatActivity {
    MascotStateMachine mascot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        mascot = new MascotStateMachine.Builder(findViewById(R.id.mascot),width)
                .setInterpolator(new OvershootInterpolator())
                .setDuration(2000L)
                .setAnimationType(MascotStateMachine.SCALE_UP,true)
                .setMirrored(false)
                .build();
        mascot.setState(MascotStateMachine.TAG_MODE);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //dispose to survive orientation change and activity/fragment navigation
        mascot.dispose();
    }

    @Override
    protected void onResume(){
        super.onResume();
        /*call run() for:
            +looping animation if isLoop true
            +Idle Mode to move around specified view or point
            +Random Mode to move around the screen
        */
        mascot.run();
    }

    public void move(View view) {
        mascot.move(findViewById(R.id.textView));
    }


    public void moveTo100(View view) {
        mascot.move(100,100);
    }

    //if the subject view is instance of TextView
    //otherwise call ignored
    public void talk(View view){
        mascot.talk("Something to say");
    }


    public void animate(View view){
        mascot.animate();
    }
}
