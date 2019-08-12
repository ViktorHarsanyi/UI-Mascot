package com.vktrhrsny.uimascotexample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.vktrhrsny.uimascot.MascotStateMachine;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    MascotStateMachine mascot;
    TextView mascotTextView;
    final String[] states = {"Tag","Random","Idle",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mascotTextView = findViewById(R.id.mascot);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        mascotTextView.setBackgroundResource(R.drawable.colibri);

        Spinner spin = findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, states);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        // Optionally include any animation on the image
        final AnimatedVectorDrawable hummingbird = (AnimatedVectorDrawable) mascotTextView.getBackground();
        hummingbird.start();
        // Example animation uses min API 23 callback
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hummingbird.registerAnimationCallback(new Animatable2.AnimationCallback(){
                @Override
                public void onAnimationEnd(Drawable drawable) {
                    mascotTextView.post(hummingbird::start);
                }
            });
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        mascot = new MascotStateMachine.Builder(mascotTextView,width)
                .setInterpolator(new OvershootInterpolator())
                .setDuration(2000L)
                .setAnimationType(MascotStateMachine.ROTATE_CW)
                .setIdleDestinationView(findViewById(R.id.textView))
                .setMirrored(true)
                .build();

         /*
          *call run() for:
            +Idle Mode to move around specified view or point
            +Random Mode to move around the screen
            *************************************************
            * if state set to other then Random Mode
            * callback removed
            * delay give in setDuration method of the builder
        */
        mascot.run();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //dispose to survive orientation change and activity||fragment navigation
        mascot.dispose();
    }

    public void move(View view) {
        mascot.move(findViewById(R.id.textView));
    }

    public void moveToXY(View view) {
        mascot.move(100,100);
    }

    /*
     *if the subject view is instance of TextView
     *otherwise call ignored
     *if setMirrored is true text is mirrored too
    */
    public void talk(View view){
        mascot.talk("UI-MASCOT");
    }

    public void animate(View view){
        mascot.animate();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mascot.setState(++i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        mascot.setState(MascotStateMachine.RANDOM_MODE);
    }
}
