package com.fixed4fun.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;


import java.util.Random;


public class MyGdxGame extends ApplicationAdapter {
    static SpriteBatch batch;
    private Texture background;
    private ShapeRenderer shapeRenderer;
    static Texture[] blitzTexture;

    static Texture[] birds;
    static int flapState = 0;
    static float birdY = 0;
    static float birdX = 0;
    static float velocity = 0;
    int gameState = 0;
    static float gravity = 1.7f;

    private Circle birdCircle;
    static Rectangle[] topTubeRectangles;
    static Rectangle[] bottomTubeRectangles;

    static int score = 0;
    int activeTube;
    BitmapFont font;

    static Texture topTube;
    static Texture bottomTube;
    static float gap = 300;
    static float screenX = 0;
    static float screenY =0;
    static float lewoPrawo = 0;
    static float leftOrRight = 0;
    static float maxTubeOffSet;
    static Random randomGenerator;


    static int numberOfTubes =6;
    static float[] tubeX = new float[numberOfTubes];
    static float[] tubeOffset =new float[numberOfTubes];
    static float distanceBetweenTubes;




    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("bg.png");
        birds = new Texture[2];
        birds[0] = new Texture("bird.png");
        birds[1] = new Texture("bird2.png");
        birdY = Gdx.graphics.getHeight() / 2 - birds[flapState].getHeight() / 2;
        birdX = Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2;
        shapeRenderer = new ShapeRenderer();
        birdCircle = new Circle();

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().scale(10);


        blitzTexture = new Texture[6];
        blitzTexture[0] = new Texture("bird.png");
        blitzTexture[1] = new Texture("blitz1.png");
        blitzTexture[2] = new Texture("blitz2.png");
        blitzTexture[3] = new Texture("blitz3.png");
        blitzTexture[4] = new Texture("blitz4.png");
        blitzTexture[5] = new Texture("blitz5.png");


        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        maxTubeOffSet = Gdx.graphics.getHeight() /2 - gap/2 - 100;
        randomGenerator = new Random();
        distanceBetweenTubes = Gdx.graphics.getWidth() * 3 / 4;
        topTubeRectangles = new Rectangle[numberOfTubes];
        bottomTubeRectangles = new Rectangle[numberOfTubes];


        Tubes.createTubes();
    }



    @Override
    public void render() {


        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());




        if (gameState != 0) {

            if(tubeX[activeTube] < birdY){
                score++;
                if(activeTube < numberOfTubes-1){
                    activeTube++;
                } else {
                    activeTube =0;
                }
            }

            Tubes tubes = new Tubes();

            Steering.easySteering();
            tubes.tubeDraw();
            //Tubes.tubeDraw();
            BirdControls.drawBlitzCount();

        } else {
            if (Gdx.input.justTouched()) {
                gameState = 1;

            }

        }
        if (flapState == 0) {
            flapState = 1;
        } else {
            flapState = 0;
        }

        batch.draw(birds[flapState], birdX, birdY);

        font.draw(batch, String.valueOf(score), Gdx.graphics.getWidth()/2 - 50, Gdx.graphics.getHeight()-300);


        batch.end();
        birdCircle.set(birdX+birds[flapState].getWidth()/2, birdY+birds[flapState].getHeight()/2, birds[flapState].getWidth()/2 );


       // shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
      //  shapeRenderer.setColor(Color.RED);
      //  shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);
        for(int i =0; i < numberOfTubes ; i++) {

          //  shapeRenderer.rect(Gdx.graphics.getWidth() / 2 + gap / 2 + tubeOffset[i], tubeX[i], topTube.getWidth(), topTube.getHeight());
         //   shapeRenderer.rect(Gdx.graphics.getWidth() / 2 - gap / 2 - bottomTube.getWidth() + tubeOffset[i], tubeX[i], bottomTube.getWidth(), bottomTube.getHeight());

            if(Intersector.overlaps(birdCircle, topTubeRectangles[i]) ||Intersector.overlaps(birdCircle, bottomTubeRectangles[i])){
             //   Gdx.app.log("collision", "yes");
              //  gameState=0;
            }
        }

      //  shapeRenderer.end();
    }



}