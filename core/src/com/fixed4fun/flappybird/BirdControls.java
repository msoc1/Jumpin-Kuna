package com.fixed4fun.flappybird;

import com.badlogic.gdx.graphics.Texture;

public class BirdControls extends MyGdxGame {


    public static void drawBlitzCount() {
        blitzTexture = new Texture[6];
        blitzTexture[0] = new Texture("srodek.png");
        blitzTexture[1] = new Texture("blitz1.png");
        blitzTexture[2] = new Texture("blitz2.png");
        blitzTexture[3] = new Texture("blitz3.png");
        blitzTexture[4] = new Texture("blitz4.png");
        blitzTexture[5] = new Texture("blitz5.png");

        long diff = System.currentTimeMillis() - Steering.timeFirstClick;
        if (diff >= 5000) {
            batch.draw(blitzTexture[0], 100, 100, 100, 100);
        } else if (diff >= 4000) {
            batch.draw(blitzTexture[1], 100, 100, 100, 100);
        } else if (diff >= 3000) {
            batch.draw(blitzTexture[2], 100, 100, 100, 100);
        } else if (diff >= 2000) {
            batch.draw(blitzTexture[3], 100, 100, 100, 100);
        } else if (diff >= 1000) {
            batch.draw(blitzTexture[4], 100, 100, 100, 100);
        } else {
            batch.draw(blitzTexture[5], 100, 100, 100, 100);
        }

    }

    public static void getFlapState() {


        if (velocity >= 10 && leftOrRight <= -0.4) {
            setCatState(0);
        } else if (velocity >= -5 && velocity <= 5 && leftOrRight <= -0.4) {
            setCatState(1);
        } else if (velocity <= -5 && leftOrRight <= -0.4) {
            setCatState(2);
        } else if (velocity <= -5 && leftOrRight == 0) {
            setCatState(3);
        } else if (velocity <= -5 && leftOrRight >= 0.4) {
            setCatState(4);
        } else if (velocity >= -5 && velocity <= 5 && leftOrRight >= 0.4) {
            setCatState(5);
        } else if (velocity >= 5 && leftOrRight >= 0.4) {
            setCatState(6);
        } else if (velocity >= 5 && leftOrRight == 0.0) {
            setCatState(7);
        } else
            setCatState(8);


        // return 1;
    }

}
