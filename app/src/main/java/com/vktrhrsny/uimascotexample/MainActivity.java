package com.vktrhrsny.uimascotexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.vktrhrsny.uimascot.MascotStateMachine;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    MascotStateMachine mascot;
    final String[] states = {"Tag","Random","Idle",};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spin = findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, states);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        mascot = new MascotStateMachine.Builder(findViewById(R.id.mascot),width)
                .setInterpolator(new OvershootInterpolator())
                .setDuration(2000L)
                .setAnimationType(MascotStateMachine.ROTATE_CW,true)
                .setMirrored(true)
                .build();

         /*call run() for:
            +looping animation if isLoop true
            +Idle Mode to move around specified view or point
            +Random Mode to move around the screen
        */
        //mascot.setState(MascotStateMachine.RANDOM_MODE);
        mascot.run();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //dispose to survive orientation change and activity/fragment navigation
        mascot.dispose();
    }

    public void move(View view) {
        mascot.move(findViewById(R.id.textView));
    }

    public void moveTo100(View view) {
        mascot.move(100,100);
    }

    //if the subject view is instance of TextView
    //otherwise call ignored
    //if setMirrored is true text is mirrored too
    public void talk(View view){
        mascot.talk("Something to say");
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
