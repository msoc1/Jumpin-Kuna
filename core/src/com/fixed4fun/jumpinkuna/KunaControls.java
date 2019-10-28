package com.fixed4fun.jumpinkuna;

import com.badlogic.gdx.Gdx;

public class KunaControls extends MyGdxGame {


    static float width = Gdx.graphics.getWidth()*0.0833f;
    static float height = Gdx.graphics.getHeight()*0.072f;
    public static float blitzPositionX =Gdx.graphics.getWidth() * 0.0925f;
    public static float blitzPositionY = Gdx.graphics.getHeight() * 0.111f;

    public static void drawBlitzCount() {
        long diff = System.currentTimeMillis() - Steering.timeFirstClick;
        if (diff >= 5000) {
            batch.draw(blitzTexture[0], blitzPositionX, blitzPositionY, height, height);
        } else if (diff >= 4000) {
            batch.draw(blitzTexture[1], blitzPositionX, blitzPositionY, width, height);
        } else if (diff >= 3000) {
            batch.draw(blitzTexture[2], blitzPositionX, blitzPositionY, width, height);
        } else if (diff >= 2000) {
            batch.draw(blitzTexture[3], blitzPositionX, blitzPositionY, width, height);
        } else if (diff >= 1000) {
            batch.draw(blitzTexture[4], blitzPositionX, blitzPositionY, width, height);
        } else {
            batch.draw(blitzTexture[5], blitzPositionX, blitzPositionY, width, height);
        }
    }

    public static void getFlapState() {
        if (leftOrRight < 0) {
            setCatState(0);
        } else
            setCatState(1);
    }

}
