package com.fedexu.breakout.objects;

import android.graphics.Point;
import android.view.MotionEvent;

import com.fedexu.androidgameengine.GameData;
import com.fedexu.androidgameengine.manager.ColliderManager;
import com.fedexu.androidgameengine.object.BasicObject;
import com.fedexu.androidgameengine.object.GameObject;


public class Paddle extends GameObject {

    private Point startCenter;

    public Paddle(int length, int height){
        super();

        this.getPolygon().addPoint(0, 0);

        this.getPolygon().addPoint(length, 0);

        this.getPolygon().addPoint(length, height );

        this.getPolygon().addPoint(0, height);

        //this.setSpeed(1);

        this.setImmovable(true);

    }

    public Paddle(BasicObject b){
        super(b);
        this.setImmovable(true);
        this.startCenter = this.getCenter();
    }

    public void reset(){
        this.translate(startCenter.x, startCenter.y);
    }

    @Override
    public void update(GameData gameData) {

    }

    @Override
    public void onCollide(GameObject collideObject) {

        if (collideObject instanceof LeftRightWall) {
            this.comeBack();
        }

    }

    @Override
    public void onTouch(GameData gameData, MotionEvent motionEvent) {

        int index = motionEvent.getActionIndex();
        int action = motionEvent.getActionMasked();
        int pointerId = motionEvent.getPointerId(index);

        if(action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE ){
            gameData.setLockedTouched(this);
            Point pointTouched = new Point((int) motionEvent.getX(), (int) motionEvent.getY());
            //se non collido con niente
            if(ColliderManager.collisionCheck(gameData, this) == null){
                this.translateX(pointTouched.x);
            }else{
                this.comeBack();
            }
        }
    }

}