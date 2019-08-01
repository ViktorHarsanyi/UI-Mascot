package com.vktrhrsny.uimascotexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.OvershootInterpolator;

import com.vktrhrsny.uimascot.MascotStateMachine;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        MascotStateMachine mascot = new MascotStateMachine.Builder(findViewById(R.id.mascot),width)
                .setInterpolator(new OvershootInterpolator())
                .setDuration(2000L)
                .createStateMachine();

        mascot.setState(MascotStateMachine.GUIDE_MODE);
        mascot.moveTo(findViewById(R.id.textView));

    }
}
