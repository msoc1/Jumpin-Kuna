package com.fixed4fun.jumpinkuna;

import com.badlogic.gdx.Gdx;


public class Steering extends MyGdxGame {
    static long timeFirstClick;
    private static boolean canBlitz = true;


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

        float changeDir = Gdx.graphics.getWidth() * 0.00037037037f;

        if (Gdx.input.justTouched()) {
            velocity = -Gdx.graphics.getHeight()*0.014f;
            leftOrRight = 0;
            tiltPower = 0;
            screenX = Gdx.input.getX();
            screenY = Gdx.input.getY();
            canHeBlitz();

            if (screenX < Gdx.graphics.getWidth() / 2) {
                leftOrRight -= changeDir;
            } else if (screenX > Gdx.graphics.getWidth() / 2) {
                leftOrRight += changeDir;
            }
        }

        if (catPositionY > 0) {
            if (catPositionX <= 2) {
                bounceLeft();
            }
            if (catPositionX >= Gdx.graphics.getWidth() - Gdx.graphics.getWidth()* 0.055f) {
                bounceRight();
            }
            moveBird();
        } else {
            gameState = 2;
        }
    }


    private static void canHeBlitz() {
        if (screenX >= BirdControls.blitzPositionX && screenX <= blitzTexture[0].getWidth() + BirdControls.blitzPositionX  && screenY <= Gdx.graphics.getHeight() - BirdControls.blitzPositionY &&
                screenY >= Gdx.graphics.getHeight() - BirdControls.blitzPositionY - blitzTexture[0].getHeight()) {

            if (System.currentTimeMillis() - timeFirstClick >= 5000)
                canBlitz = true;

            if (canBlitz) {
                timeFirstClick = System.currentTimeMillis();
                catPositionY += Gdx.graphics.getHeight() / 3;
                canBlitz = false;
            }
        }
    }


    private static void bounceLeft() {
        leftOrRight = (float) Gdx.graphics.getWidth()*0.00055555555f;
        tiltPower = Gdx.graphics.getWidth()*0.00157407407f;
        tiltPower = tiltPower + leftOrRight;
        catPositionX += tiltPower;
    }

    private static void bounceRight() {
        leftOrRight = (float) -Gdx.graphics.getWidth()*0.00055555555f;
        tiltPower = -Gdx.graphics.getWidth()*0.00157407407f;
        tiltPower = tiltPower + leftOrRight;
        catPositionX -= tiltPower;
    }

    private static void moveBird() {
        tiltPower = tiltPower + leftOrRight;
        catPositionX += tiltPower;
        straigUp();
    }

    private static void straigUp() {
        if (catPositionY + Gdx.graphics.getHeight()*0.133f >= Gdx.graphics.getHeight()) {
            velocity = Gdx.graphics.getWidth()*0.01111f;
        } else {
            velocity = velocity + gravity;
        }
        catPositionY -= velocity;

    }


}
