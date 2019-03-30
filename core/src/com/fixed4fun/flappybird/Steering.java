package com.fixed4fun.flappybird;

import com.badlogic.gdx.Gdx;


public class Steering extends MyGdxGame {
    static long timeFirstClick;
    private static boolean canBlitz= true;


    public static void easySteering() {

        if (Gdx.input.justTouched()) {


            velocity = -25;
            lewoPrawo = 0;
            leftOrRight = 0;
            screenX = Gdx.input.getX();
            screenY = Gdx.input.getY();

           canHeBlitz();
                if (screenX <= Gdx.graphics.getWidth() / 3) {
                    lewoPrawo -= 0.4;
                } else if (screenX >= Gdx.graphics.getWidth() * 2 / 3) {
                    lewoPrawo += 0.4;
                } else if (screenX >= Gdx.graphics.getWidth() / 3 && screenX <= Gdx.graphics.getWidth() * 2 / 3) {
                    lewoPrawo = 0;
                }

        }

        if (birdY > 0 || velocity < 0) {
            if (birdX <= 2) {
                bounceLeft();
            }
            if (birdX >= Gdx.graphics.getWidth() - birds[flapState].getWidth()) {
                bounceRight();
            }
            moveBird();
        }
    }


    public static void hardSteering() {

        if (Gdx.input.justTouched()) {
            velocity = -25;
            lewoPrawo = 0;
            leftOrRight = 0;
            screenX = Gdx.input.getX();
            screenY = Gdx.input.getY();
            canHeBlitz();

            if (screenX < Gdx.graphics.getWidth()/2) {
                lewoPrawo += 0.4;
            } else if (screenX > Gdx.graphics.getWidth()/2) {
                lewoPrawo -= 0.4;
            }
        }

        if (birdY > 0 || velocity < 0 ) {
            if (birdX <= 2) {
                bounceLeft();
            }
            if (birdX >= Gdx.graphics.getWidth() - birds[flapState].getWidth()) {
                bounceRight();
            }
            moveBird();
        }
    }


    private static void canHeBlitz(){
        if (screenX >= 100 && screenX <= blitzTexture[0].getWidth() + 100 && screenY <= Gdx.graphics.getHeight() - 100 &&
                screenY >= Gdx.graphics.getHeight() - 100 - blitzTexture[0].getHeight()) {

            if (System.currentTimeMillis() - timeFirstClick >= 5000)
                canBlitz = true;

            if (canBlitz) {
                timeFirstClick = System.currentTimeMillis();
                birdY += Gdx.graphics.getHeight()/3;
                canBlitz = false;
            }


        }
    }


    private static void bounceLeft() {
        lewoPrawo = (float) 0.6;
        leftOrRight = 1.7f;
        leftOrRight = leftOrRight + lewoPrawo;
        birdX += leftOrRight;
    }

    private static void bounceRight() {
        lewoPrawo = (float) -0.6;
        leftOrRight = -1.7f;
        leftOrRight = leftOrRight + lewoPrawo;
        birdX -= leftOrRight;
    }

    private static void moveBird() {
        leftOrRight = leftOrRight + lewoPrawo;
        birdX += leftOrRight;
        straigUp();
    }

    private static void straigUp() {
        velocity = velocity + gravity;
        birdY -= velocity;
    }


}
