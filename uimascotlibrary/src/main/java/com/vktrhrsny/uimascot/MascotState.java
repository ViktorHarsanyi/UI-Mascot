package com.vktrhrsny.uimascot;

import android.view.View;

public interface MascotState {
    void moveTo(View view);
    void move();
    void talk(String text);
    void dispose();
}
