package com.fixed4fun.flappybird;

import com.badlogic.gdx.Gdx;


public class Steering extends MyGdxGame {
    static long timeFirstClick;
    private static boolean canBlitz= true;


    public static void easySteering() {

        if (Gdx.input.justTouched()) {


            velocity = -25;
            leftOrRight = 0;
            tiltPower = 0;
            screenX = Gdx.input.getX();
            screenY = Gdx.input.getY();

           canHeBlitz();
                if (screenX <= Gdx.graphics.getWidth() / 3) {
                    leftOrRight -= 0.4;
                } else if (screenX >= Gdx.graphics.getWidth() * 2 / 3) {
                    leftOrRight += 0.4;
                } else if (screenX >= Gdx.graphics.getWidth() / 3 && screenX <= Gdx.graphics.getWidth() * 2 / 3) {
                    leftOrRight = 0;
                }

        }

        if (catPositionY > 0 || velocity < 0) {
            if (catPositionX <= 2) {
                bounceLeft();
            }
            if (catPositionX >= Gdx.graphics.getWidth() - catTexture[catState].getWidth()) {
                bounceRight();
            }
            moveBird();
        }
    }


    public static void hardSteering() {

        if (Gdx.input.justTouched()) {
            velocity = -25;
            leftOrRight = 0;
            tiltPower = 0;
            screenX = Gdx.input.getX();
            screenY = Gdx.input.getY();
            canHeBlitz();

            if (screenX < Gdx.graphics.getWidth()/2) {
                leftOrRight -= 0.4;
            } else if (screenX > Gdx.graphics.getWidth()/2) {
                leftOrRight += 0.4;
            }
        }

        if (catPositionY > 0 ) {
            if (catPositionX <= 2) {
                bounceLeft();
            }
            if (catPositionX >= Gdx.graphics.getWidth() - 60) {
                bounceRight();
            }
            moveBird();
        } else {
            gameState = 2;
        }
    }


    private static void canHeBlitz(){
        if (screenX >= 100 && screenX <= blitzTexture[0].getWidth() + 100 && screenY <= Gdx.graphics.getHeight() - 100 &&
                screenY >= Gdx.graphics.getHeight() - 100 - blitzTexture[0].getHeight()) {

            if (System.currentTimeMillis() - timeFirstClick >= 5000)
                canBlitz = true;

            if (canBlitz) {
                timeFirstClick = System.currentTimeMillis();
                catPositionY += Gdx.graphics.getHeight()/3;
                canBlitz = false;
            }
        }
    }


    private static void bounceLeft() {
        leftOrRight = (float) 0.6;
        tiltPower = 1.7f;
        tiltPower = tiltPower + leftOrRight;
        catPositionX += tiltPower;
    }

    private static void bounceRight() {
        leftOrRight = (float) -0.6;
        tiltPower = -1.7f;
        tiltPower = tiltPower + leftOrRight;
        catPositionX -= tiltPower;
    }

    private static void moveBird() {
        tiltPower = tiltPower + leftOrRight;
        catPositionX += tiltPower;
        straigUp();
    }

    private static void straigUp() {
        if(catPositionY+200 >=Gdx.graphics.getHeight() ) {
            velocity = 12;
        } else {
            velocity = velocity + gravity;
        }
        catPositionY -= velocity;

    }


}
