package com.vktrhrsny.uimascotexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.vktrhrsny.uimascot.MascotStateMachine;
import com.vktrhrsny.uimascot.MascotView;

public class MainActivity extends AppCompatActivity {
    MascotStateMachine mascot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        mascot = new MascotStateMachine.Builder(findViewById(R.id.msct),width)
                .setInterpolator(new OvershootInterpolator())
                .setDuration(2000L)
                .build();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mascot.dispose();
    }

    public void move(View view) {

        mascot.setState(MascotStateMachine.GUIDE_MODE);
        mascot.move(findViewById(R.id.textView));

    }


}
