package com.fedexu.breakout.activities;

import android.view.Display;

import com.fedexu.breakout.views.BreackoutView;
import com.fedexu.androidgameengine.GameActivity;
import com.fedexu.androidgameengine.GameView;


public class BreakoutActivity extends GameActivity {

    @Override
    public GameView loadGameView() {
        Display display = getWindowManager().getDefaultDisplay();

        BreackoutView breackoutView = new BreackoutView(this, display);
        breackoutView.a = this;
        return breackoutView;
    }



}
