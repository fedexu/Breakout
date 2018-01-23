package com.fedexu.breakout.objects;

import android.graphics.Point;
import android.view.MotionEvent;

import com.fedexu.androidgameengine.GameData;
import com.fedexu.androidgameengine.object.BasicObject;
import com.fedexu.androidgameengine.object.GameObject;


public class Brick extends GameObject {

    private long timedDeath;

    private Point startCenter;

    public Brick(int x, int y, int width, int height){
        super();
        this.getPolygon().addPoint( x, y);

        this.getPolygon().addPoint( x + width , y);

        this.getPolygon().addPoint( x + width , y + height);

        this.getPolygon().addPoint( x, y + height);

        this.updateBoundingBox();

        this.setImmovable(true);

        timedDeath = 0;
    }

    public Brick(BasicObject b){
        super(b);
        timedDeath = 0;
        this.startCenter = this.getCenter();
        this.setImmovable(true);
    }

    public void reset(){
        this.translate(startCenter.x, startCenter.y);
        this.setVisible(true);
        this.setUntouchable(false);
        this.timedDeath = 0;
        this.setCurrentAnimation("IDLE");
        this.setImmovable(true);
    }

    @Override
    public void update(GameData gameData) {
        if(this.isUntouchable() && this.isVisible() && timedDeath == 0){
            timedDeath = 1000;
            this.setCurrentAnimation("FALLING");
            this.getCurrentAnimation().setAnimationDurationTime(timedDeath);
            this.getCurrentAnimation().setFrameCount(6);
            this.setDirectionAngle(270);
            this.setSpeed(80);
            this.setImmovable(false);
        }

        if(timedDeath != 0 && timedDeath < 0){
            this.setVisible(false);
            setSpeed(0);
        }
        if(timedDeath != 0 && timedDeath > 0){
            timedDeath -= gameData.getDeltaFrameTime();
        }

    }

    @Override
    public void onCollide(GameObject collideObject) {
        this.setUntouchable(true);
    }

    @Override
    public void onTouch(GameData gameData, MotionEvent motionEvent) {

    }
}