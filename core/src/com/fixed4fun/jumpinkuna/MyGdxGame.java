package com.fixed4fun.jumpinkuna;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    static float gravity ;
    static Rectangle[] topTubeRectangles;
    static Rectangle[] bottomTubeRectangles;
    static Circle[] leftCircles;
    static Circle[] rightCircles;
    static int score = 0;
    static Texture leftTube;
    static Texture rightTube;
    static float gap;
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
    private static Texture gameOver;
    private static Texture pressToStart;
    private static Texture scoreTexture;
    private static Texture[] scores;
    private static Texture bestScoreText;
    private static Preferences preferences;
    private static int bestScore;
    private static int gameWidth;
    private static int gameHeight;
    static private Texture background;
    static private Rectangle catArea;
    private int activeTube;
    static Texture jumpingKuna;
    private static float kunaHeight;
    private static float kunaWidth;
    private static float scoreWidth;
    private static float scoreHeight;
    private static float bestScoreWidth;
    private static float bestScoreHeight;
    public static float tubeHeight;
    public static float tubeWidth;

    static void setCatState(int catState) {
        MyGdxGame.catState = catState;
    }

    @Override
    public void create() {
        gravity = Gdx.graphics.getWidth() *0.001574f;

        gameWidth = Gdx.graphics.getWidth();
        gameHeight = Gdx.graphics.getHeight();
        kunaWidth = gameWidth * 0.055f;
        kunaHeight = gameHeight * 0.133f;
        scoreWidth = gameWidth * 0.121f;
        scoreHeight = gameHeight * 0.10f;
        gap = gameWidth*0.3f;
        bestScoreWidth = gameWidth * 0.277f;
        bestScoreHeight = gameWidth * 0.08f;
        tubeHeight = gameHeight * 0.1f ;
        tubeWidth = gameWidth;


        Gdx.app.log("tag1", " "+ Gdx.graphics.getWidth());
        Gdx.app.log("tag1", " "+ Gdx.graphics.getHeight());


        batch = new SpriteBatch();
        background = new Texture("bg.png");
        preferences = Gdx.app.getPreferences("game preferences");

        blitzTexture = new Texture[6];
        blitzTexture[0] = new Texture("srodek.png");
        blitzTexture[1] = new Texture("one.png");
        blitzTexture[2] = new Texture("two.png");
        blitzTexture[3] = new Texture("three.png");
        blitzTexture[4] = new Texture("four.png");
        blitzTexture[5] = new Texture("five.png");

        catTexture = new Texture[3];
        catTexture[0] = new Texture("left.png");
        catTexture[1] = new Texture("right.png");
        catTexture[2] = new Texture("cat_down.png");
        catPositionY =(float)(gameHeight / 2 - kunaHeight / 2);
        catPositionX = (float)(gameWidth / 2 - kunaWidth / 2);
        catArea = new Rectangle();

        scores = new Texture[10];
        scores[0] = new Texture("zero.png");
        scores[1] = new Texture("one.png");
        scores[2] = new Texture("two.png");
        scores[3] = new Texture("three.png");
        scores[4] = new Texture("four.png");
        scores[5] = new Texture("five.png");
        scores[6] = new Texture("six.png");
        scores[7] = new Texture("seven.png");
        scores[8] = new Texture("eight.png");
        scores[9] = new Texture("nine.png");
        gameOver = new Texture("game_over.png");
        pressToStart = new Texture("press_to_start.png");
        scoreTexture = new Texture("score.png");
        bestScoreText = new Texture("best_score.png");


        jumpingKuna = new Texture("jumping_kuna.png");
        rightTube = new Texture("left_ice.png");
        leftTube = new Texture("right_ice.png");
        topTubeRectangles = new Rectangle[numberOfTubes];
        bottomTubeRectangles = new Rectangle[numberOfTubes];
        leftCircles = new Circle[numberOfTubes];
        rightCircles = new Circle[numberOfTubes];
        for (int i = 0; i < numberOfTubes; i++) {
            topTubeRectangles[i] = new Rectangle();
            bottomTubeRectangles[i] = new Rectangle();
            leftCircles[i]= new Circle();
            rightCircles[i] = new Circle();
        }
        randomGenerator = new Random();
        distanceBetweenTubes = gameHeight * 0.66f;
        Tubes.createTubes();

    }

    private void saveHighScore() {
        preferences.putInteger("highScore", bestScore);
        preferences.flush();
    }

    private int loadHighScore() {
        return preferences.getInteger("highScore");
    }


    @Override
    public void render() {
        batch.begin();
        batch.disableBlending();
        batch.draw(background, 0, 0, gameWidth, gameHeight);
        batch.enableBlending();
        switch (gameState) {
            case 0:
                onStartGame();
                break;
            case 1:
                onGamePlaying();
                break;
            case 2:
                onLostGame();
                break;
            case 3:
                onGameReset();
                break;
        }
        batch.draw(catTexture[catState], catPositionX, catPositionY, kunaWidth, kunaHeight);
        batch.end();
    }

    private void onStartGame() {
        batch.draw(pressToStart, (float)gameWidth / 2 - (gameWidth*0.74f)/2,
                (float)gameHeight / 2 - (gameHeight*0.323f)/2, gameWidth*0.74f, gameHeight*0.323f);
        printBestScore();
        batch.draw(jumpingKuna,(gameWidth - gameWidth*0.9f)/2 , gameHeight*0.0278f, gameWidth*0.9f, gameHeight*0.064f );


        if (Gdx.input.justTouched()) {
            gameState = 1;
        }
    }

    private void onGamePlaying() {
        printScore();
        if (tubeX[activeTube] < catPositionY) {
            score++;
            if (activeTube < numberOfTubes - 1) {
                activeTube++;
            } else {
                activeTube = 0;
            }
        }
        batch.draw(jumpingKuna,(gameWidth - gameWidth*0.9f)/2 , gameHeight*0.0278f, gameWidth*0.9f, gameHeight*0.064f );

        Tubes.tubeDraw();
        Steering.hardSteering();
        BirdControls.drawBlitzCount();
        BirdControls.getFlapState();
        for (int i = 0; i < numberOfTubes; i++) {
            if (Intersector.overlaps(catArea, topTubeRectangles[i]) || Intersector.overlaps(catArea, bottomTubeRectangles[i]) ||
                    Intersector.overlaps(leftCircles[i], catArea) || Intersector.overlaps(rightCircles[i], catArea)) {
                gameState = 2;
            }
        }
        catArea.set(catPositionX, catPositionY + kunaHeight*0.0833f, kunaWidth, kunaHeight*0.833f);
    }

    private void onGameReset() {
        printBestScore();
        batch.draw(jumpingKuna,(gameWidth - gameWidth*0.9f)/2 , gameHeight*0.0278f, gameWidth*0.9f, gameHeight*0.064f );

        batch.draw(pressToStart, (float)gameWidth / 2 - (gameWidth*0.74f)/2,
                (float)gameHeight/ 2 - (gameHeight*0.323f)/2, gameWidth*0.74f, gameHeight*0.323f);
        if (Gdx.input.justTouched()) {
            gameState = 1;
            score = 0;
            activeTube = 0;
            velocity = 0;
            tiltPower = 0;
            Tubes.createTubes();
            for (int i = 0; i < numberOfTubes; i++) {
                topTubeRectangles[i].set((float)gameWidth / 2 + gap / 2 + tubeOffset[i], tubeX[i] + tubeHeight * 0.2f + gameHeight*0.28f,
                        leftTube.getWidth(), tubeHeight * 0.6f);
                bottomTubeRectangles[i].set((float)gameWidth / 2 - gap / 2 - tubeWidth + tubeOffset[i],
                        tubeX[i] + tubeHeight * 0.2f + gameHeight*0.28f, tubeWidth, tubeHeight * 0.6f);
                leftCircles[i].set(Gdx.graphics.getWidth() / 2 + gap / 2 + tubeOffset[i] + tubeWidth*0.02f, tubeX[i] + tubeHeight * 0.7f , Gdx.graphics.getWidth()*0.032f);
                rightCircles[i].set(Gdx.graphics.getWidth() / 2 - gap / 2 - tubeWidth*0.02f + tubeOffset[i], tubeX[i] + tubeHeight * 0.7f , Gdx.graphics.getWidth()*0.032f);
            }
            catPositionY = (float) (gameHeight / 2 - kunaHeight / 2);
            catPositionX = (float)(gameWidth / 2 - kunaWidth / 2);
            Steering.timeFirstClick = System.currentTimeMillis() - 60000;
        }

    }


    private void onLostGame() {
        printScore();
        batch.draw(jumpingKuna,(gameWidth - gameWidth*0.9f)/2 , gameHeight*0.0278f, gameWidth*0.9f, gameHeight*0.064f );

        if (score > loadHighScore()) {
            bestScore = score;
            saveHighScore();
        }
        batch.draw(gameOver, (float)gameWidth/ 2 - (gameWidth*0.74f)/2,
                (float)gameHeight / 2 + (gameHeight*0.10f)/2, gameWidth*0.74f, gameHeight*0.10f);
        catState = 2;
        batch.draw(scoreTexture, (float)gameWidth / 2 - gameWidth*0.139f, gameHeight - gameHeight * 0.0557f,
                bestScoreWidth, bestScoreHeight);
        if (Gdx.input.justTouched()) {
            gameState = 3;
            System.gc();
        }
    }

    private void printScore() {
        if (score < 10) {
            batch.draw(scores[score], (float)gameWidth / 2 - scoreWidth/2, gameHeight - gameHeight*0.222f, scoreWidth, scoreHeight);
        } else {
            int firstDigit = score / 10;
            int secondDigit = score % 10;
            batch.draw(scores[firstDigit], (float)gameWidth / 2 - gameWidth*0.134f, gameHeight - gameHeight*0.222f, scoreWidth, scoreHeight);
            batch.draw(scores[secondDigit], (float)gameWidth / 2, gameHeight - gameHeight*0.222f, scoreWidth, scoreHeight);
        }
    }

    private void printBestScore() {
        batch.draw(bestScoreText, (float)gameWidth / 2 - gameWidth*0.139f, gameHeight - gameHeight * 0.0557f,
                                    bestScoreWidth, bestScoreHeight);

        if (loadHighScore() < 10) {
            batch.draw(scores[loadHighScore()], (float)gameWidth / 2 - scoreWidth/2, gameHeight- gameHeight*0.222f, scoreWidth, scoreHeight);
        } else {
            int firstDigit = loadHighScore() / 10;
            int secondDigit = loadHighScore() % 10;
            batch.draw(scores[firstDigit], (float)gameWidth / 2 - gameWidth*0.134f, gameHeight - gameHeight*0.222f, scoreWidth, scoreHeight);
            batch.draw(scores[secondDigit], (float)gameWidth / 2, gameHeight - gameHeight*0.222f, scoreWidth, scoreHeight);
        }
    }


}