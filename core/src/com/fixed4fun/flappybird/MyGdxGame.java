package com.fixed4fun.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;


public class MyGdxGame extends ApplicationAdapter {
    static SpriteBatch batch;
    static Texture[] blitzTexture;
    static Texture[] catTexture;
    static int catState = 0;
    static float catPositionY = 0;
    static float catPositionX = 0;
    static float velocity = 0;
    static float gravity = 1.7f;
    static Rectangle[] topTubeRectangles;
    static Rectangle[] bottomTubeRectangles;
    static int score = 0;
    static Texture leftTube;
    static Texture rightTube;
    static float gap = 300;
    static float screenX = 0;
    static float screenY = 0;
    static float leftOrRight = 0;
    static float tiltPower = 0;
    static Random randomGenerator;
    static int numberOfTubes = 6;
    static float[] tubeX = new float[numberOfTubes];
    static float[] tubeOffset = new float[numberOfTubes];
    static float distanceBetweenTubes;
    static int gameState = 0;
    int activeTube;
    BitmapFont font;
    private Texture background;
    private Circle catAreaCircle;

    Texture gameOver;


    public static void setCatState(int catState) {
        MyGdxGame.catState = catState;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("bg.png");
        catTexture = new Texture[9];
        catTexture[0] = new Texture("dol-lewo.png");
        catTexture[1] = new Texture("lewo.png");
        catTexture[2] = new Texture("gora-lewo.png");
        catTexture[3] = new Texture("gora.png");
        catTexture[4] = new Texture("gora-prawo.png");
        catTexture[5] = new Texture("prawo.png");
        catTexture[6] = new Texture("dol-prawo.png");
        catTexture[7] = new Texture("dol.png");
        catTexture[8] = new Texture("srodek.png");
        gameOver = new Texture("game_over.jpg");


        catPositionY = Gdx.graphics.getHeight() / 2 - catTexture[catState].getHeight() / 2;
        catPositionX = Gdx.graphics.getWidth() / 2 - catTexture[catState].getWidth() / 2;
        catAreaCircle = new Circle();

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().scale(10);


        leftTube = new Texture("toptube.png");
        rightTube = new Texture("bottomtube.png");
        randomGenerator = new Random();
        distanceBetweenTubes = Gdx.graphics.getHeight() *0.5f;
        topTubeRectangles = new Rectangle[numberOfTubes];
        bottomTubeRectangles = new Rectangle[numberOfTubes];


        Tubes.createTubes();
    }

    @Override
    public void render() {


        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        if (gameState ==1) {

            if (tubeX[activeTube] < catPositionY) {
                score++;
                if (activeTube < numberOfTubes - 1) {
                    activeTube++;
                } else {
                    activeTube = 0;
                }
            }

            Tubes tubes = new Tubes();
            tubes.tubeDraw();

            Steering.hardSteering();
            BirdControls.drawBlitzCount();
            BirdControls.getFlapState();

        } else if (gameState ==0){
            if (Gdx.input.justTouched()) {
                gameState = 1;

            }

        } else if(gameState ==2){
            batch.draw(gameOver, Gdx.graphics.getWidth()/2 - gameOver.getWidth()/2 ,
                    Gdx.graphics.getHeight()/2 - gameOver.getHeight()/2 );

            if (Gdx.input.justTouched()) {
                gameState = 1;
                score =0;
                activeTube =0;
                velocity=0;
                tiltPower =0;
                catPositionY = Gdx.graphics.getHeight() / 2 - catTexture[catState].getHeight() / 2;
                catPositionX = Gdx.graphics.getWidth() / 2 - catTexture[catState].getWidth() / 2;
                Tubes.createTubes();
                Steering.timeFirstClick = System.currentTimeMillis()-60000;

            }
        }

        batch.draw(catTexture[catState], catPositionX, catPositionY, 100, 100);

        font.draw(batch, String.valueOf(score), Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() - 300);

        batch.end();
        catAreaCircle.set(catPositionX + catTexture[catState].getWidth() / 2,
                catPositionY + catTexture[catState].getHeight() / 2, catTexture[catState].getWidth() / 2);

        for (int i = 0; i < numberOfTubes; i++) {


            if (Intersector.overlaps(catAreaCircle, topTubeRectangles[i]) || Intersector.overlaps(catAreaCircle, bottomTubeRectangles[i])) {
                gameState = 2;
            }
        }

    }
}