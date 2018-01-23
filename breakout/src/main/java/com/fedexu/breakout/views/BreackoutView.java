package com.fedexu.breakout.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;
import android.view.MotionEvent;

import com.fedexu.breakout.R;
import com.fedexu.breakout.activities.BreakoutActivity;
import com.fedexu.breakout.objects.Ball;
import com.fedexu.breakout.objects.Brick;
import com.fedexu.breakout.objects.LeftRightWall;
import com.fedexu.breakout.objects.Paddle;
import com.fedexu.breakout.objects.TopBottomWall;
import com.fedexu.androidgameengine.Animation;
import com.fedexu.androidgameengine.EngineUtils;
import com.fedexu.androidgameengine.GameView;
import com.fedexu.androidgameengine.object.BasicObject;
import com.fedexu.androidgameengine.object.GameObject;

import java.util.ArrayList;


/**
 * Created by Federico Peruzzi on 10/01/2018.
 *
 */

public class BreackoutView extends GameView {

    Paddle paddle;
    Ball ball;

    public BreakoutActivity a;

    int numBricks = 0;

    int score = 0;
    int lives = 3;

    public BreackoutView(Context context, Display display) {
        super(context, display);
        gameData.setDebugEnable(true);
        createFromJson();
    }

    public void createFromJson(){

        gameData.setGameObjects(new ArrayList<GameObject>());

        String jsonString = EngineUtils.readJsonfile(getResources().openRawResource(R.raw.level_6_paddle_7_ball_16_38_brick));
        BasicObject[] levelElement = EngineUtils.parseJsonString(BasicObject[].class, jsonString);

        for(BasicObject b : levelElement) {
            EngineUtils.pointPercToPointPx(b, this.displeySize);

            if (b.getId() == 6) {
                paddle = new Paddle(b);
                Bitmap paddleImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.paddle);
                Animation paddleAnimation = new Animation(paddleImage, 1, 0, 130, 20);
                paddle.addAnimation("PADDLE", paddleAnimation);

                gameData.getGameObjects().add(paddle);
            }

            if (b.getId() == 7) {
                ball = new Ball(b);

                Bitmap ballImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.top_bottom_bouncing_ball_sheet);
                Animation ballAnimation = new Animation(ballImage, 2, 200, 200, 200);
                ball.addAnimation("TOP_BOTTOM", ballAnimation);

                ballImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.left_right_bouncing_ball_sheet);
                ballAnimation = new Animation(ballImage, 2, 200, 200, 200);
                ball.addAnimation("LEFT_RIGHT", ballAnimation);

                ballImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.ball);
                ballAnimation = new Animation(ballImage, 1, 0, 200, 200);
                ball.addAnimation("IDLE", ballAnimation);

                gameData.getGameObjects().add(ball);
            }

            if (b.getId() >= 16) {
                numBricks++;

                Brick brick = new Brick(b);

                Bitmap brickImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.brick_sprite_sheet);
                Animation brickAnimation = new Animation(brickImage, 6, 200, 390, 200);
                brick.addAnimation("FALLING", brickAnimation);

                brickImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.brick);
                brickAnimation = new Animation(brickImage, 1, 0, 390, 200);
                brick.addAnimation("IDLE", brickAnimation);

                gameData.getGameObjects().add(brick);
            }

            if (b.getId() == 13 || b.getId() == 12) {
                TopBottomWall btwall = new TopBottomWall(b);

                //sposto il wall al bordo dello schermo
                if(b.getId() == 12)
                    btwall.translateY((displeySize.y + btwall.getBoundingBox().height()/2 ) -1  );
                if(b.getId() == 13)
                    btwall.translateY((0 - btwall.getBoundingBox().height()/2 ) + 1  );

                gameData.getGameObjects().add(btwall);
            }

            if (b.getId() == 14 || b.getId() == 15) {
                LeftRightWall lrwall = new LeftRightWall(b);

                //sposto il wall al bordo dello schermo
                if(b.getId() == 14)
                    lrwall.translateX((displeySize.x + lrwall.getBoundingBox().width()/2 ) -1  );
                if(b.getId() == 15)
                    lrwall.translateX((0 - lrwall.getBoundingBox().width()/2 ) + 1  );

                gameData.getGameObjects().add(lrwall);
            }

        }

        score = 0;
        lives = 3;

    }

    public void restart(){
        ball.reset();
        paddle.reset();

        ArrayList<GameObject> objects = gameData.getGameObjects();
        for(GameObject g : objects){
            if(g instanceof Brick)
                ((Brick)g).reset();
        }

        score = 0;
        lives = 3;
        ball.setLives(3);
        ball.setScore(0);
        ball.setImmovable(true);
        ball.setSpeed(100);
        ball.setAcceleration(100);
    }
    @Override
    public void onTouch(MotionEvent motionEvent) {
        setGameLoopOn(true);
        ball.setImmovable(false);
    }

    @Override
    public void update() {

        score = ball.getScore();
        if (ball.getScore() == numBricks * 10) {
                setGameLoopOn(false);
            restart();
        }

        lives = ball.getLives();
        if (ball.getLives() == 0) {
            setGameLoopOn(false);
            restart();
        }

    }
}
