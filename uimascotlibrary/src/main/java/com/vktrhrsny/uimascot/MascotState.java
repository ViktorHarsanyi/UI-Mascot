package com.vktrhrsny.uimascot;

import android.graphics.drawable.Animatable2;
import android.view.View;

import androidx.annotation.Nullable;

public interface MascotState {
    void move(@Nullable View view);
    void move(int x, int y);
    void animate(int animCode);
    void talk(String text);
    void dispose();
}
