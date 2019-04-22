package com.fixed4fun.tappymarten;

public class BirdControls extends MyGdxGame {


    static int width = 90;
    static int height = 130;

    public static void drawBlitzCount() {
        long diff = System.currentTimeMillis() - Steering.timeFirstClick;
        if (diff >= 5000) {
            batch.draw(blitzTexture[0], 100, 100, 130, 130);
        } else if (diff >= 4000) {
            batch.draw(blitzTexture[1], 100, 100, width, height);
        } else if (diff >= 3000) {
            batch.draw(blitzTexture[2], 100, 100, width, height);
        } else if (diff >= 2000) {
            batch.draw(blitzTexture[3], 100, 100, width, height);
        } else if (diff >= 1000) {
            batch.draw(blitzTexture[4], 100, 100, width, height);
        } else {
            batch.draw(blitzTexture[5], 100, 100, width, height);
        }
    }

    public static void getFlapState() {
        if (leftOrRight < 0) {
            setCatState(0);
        } else
            setCatState(1);
    }

}
