package com.fedexu.breakout.objects;

import android.view.MotionEvent;

import com.fedexu.androidgameengine.GameData;
import com.fedexu.androidgameengine.object.BasicObject;
import com.fedexu.androidgameengine.object.GameObject;


/**
 * Created by Federico Peruzzi on 12/01/2018.
 *
 */

public class TopBottomWall extends GameObject {


    public TopBottomWall(int x, int y, int width, int height){
        super();
        this.getPolygon().addPoint( x, y);

        this.getPolygon().addPoint( x + width , y);

        this.getPolygon().addPoint( x + width , y + height);

        this.getPolygon().addPoint( x, y + height);

        this.updateBoundingBox();

    }

    public TopBottomWall(BasicObject b){
        super(b);
    }


    @Override
    public void update(GameData gameData) {

    }

    @Override
    public void onCollide(GameObject collideObject) {

    }

    @Override
    public void onTouch(GameData gameData, MotionEvent motionEvent) {

    }
}
