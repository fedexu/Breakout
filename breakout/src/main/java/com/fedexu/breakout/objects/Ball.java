package com.fedexu.breakout.objects;


import android.graphics.Point;
import android.view.MotionEvent;

import com.fedexu.androidgameengine.EngineUtils;
import com.fedexu.androidgameengine.GameData;
import com.fedexu.androidgameengine.manager.ColliderManager;
import com.fedexu.androidgameengine.manager.SideCollision;
import com.fedexu.androidgameengine.object.BasicObject;
import com.fedexu.androidgameengine.object.GameObject;


public class Ball extends GameObject {

    private int score;

    private int lives;

    private Point startCenter;


    public Ball(int radius){
        super();

        this.getPolygon().addPoint(0, 0);

        this.getPolygon().addPoint(radius , 0) ;

        this.getPolygon().addPoint(radius, radius);

        this.getPolygon().addPoint(0, radius);

        this.setSpeed(100);

        this.setAcceleration(100);

        this.setImmovable(true);

        this.setDirectionAngle(45);

        this.score = 0;

        this.lives = 3;

    }

    public Ball(BasicObject b){
        super(b);

        this.setSpeed(100);

        this.setAcceleration(100);

        this.setDirectionAngle(45);

        this.setImmovable(true);

        this.score = 0;

        this.lives = 3;

        this.setSpinSpeed(10);

        this.startCenter = this.getCenter();
    }

    public void reset(){
        this.translate(startCenter.x, startCenter.y);
        this.setDirectionAngle(45);
    }


    @Override
    public void update(GameData gameData) {
        if(this.getSpeed() > 500)
            this.setAcceleration(0);
    }

    @Override
    public void onCollide(GameObject collideObject) {

        this.comeBack();

        SideCollision collisionOn = ColliderManager.detectSideCollision(this, collideObject);

        if(collisionOn == SideCollision.TOP_COLLISION || collisionOn == SideCollision.BOTTOM_COLLISION) {
            this.setDirectionAngle(EngineUtils.bounceTopBottom(this.getDirectionAngle()));
            this.setCurrentAnimation("TOP_BOTTOM");
            this.getCurrentAnimation().setAnimationDurationTime(400);
        }
        else {
            this.setDirectionAngle(EngineUtils.bounceLeftRight(this.getDirectionAngle()));
            this.setCurrentAnimation("LEFT_RIGHT");
            this.getCurrentAnimation().setAnimationDurationTime(400);
        }

        if (collideObject instanceof Brick) {
            score = score + 10;
        }

        if (collideObject instanceof TopBottomWall && collisionOn == SideCollision.BOTTOM_COLLISION ) {
            lives--;
        }


    }

    @Override
    public void onTouch(GameData gameData, MotionEvent motionEvent) {

    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

}