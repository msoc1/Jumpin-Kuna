package com.fixed4fun.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    static int bestScore = 0;
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
    static Texture gameOver;
    static Texture pressToStart;
    static Texture scoreTexture;
    static Texture[] scores;
    static Texture bestScoreText;
    static private Texture background;
    static private Rectangle catArea;
    int activeTube;
    ShapeRenderer shapeRenderer;


    public static void setCatState(int catState) {
        MyGdxGame.catState = catState;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        blitzTexture = new Texture[6];
        blitzTexture[0] = new Texture("srodek.png");
        blitzTexture[1] = new Texture("one.png");
        blitzTexture[2] = new Texture("two.png");
        blitzTexture[3] = new Texture("three.png");
        blitzTexture[4] = new Texture("four.png");
        blitzTexture[5] = new Texture("five.png");

        background = new Texture("bg.jpg");
        catTexture = new Texture[3];
        rightTube = new Texture("left_ice.png");
        leftTube = new Texture("right_ice.png");

        catTexture[0] = new Texture("left.png");
        catTexture[1] = new Texture("right.png");
        catTexture[2] = new Texture("cat_down.png");
        shapeRenderer = new ShapeRenderer();
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

        catPositionY = Gdx.graphics.getHeight() / 2 - catTexture[catState].getHeight() / 2;
        catPositionX = Gdx.graphics.getWidth() / 2 - catTexture[catState].getWidth() / 2;
        catArea = new Rectangle();


        topTubeRectangles = new Rectangle[numberOfTubes];
        bottomTubeRectangles = new Rectangle[numberOfTubes];
        for (int i = 0; i < numberOfTubes; i++) {
            topTubeRectangles[i] = new Rectangle();
            bottomTubeRectangles[i] = new Rectangle();
        }

        randomGenerator = new Random();
        distanceBetweenTubes = Gdx.graphics.getHeight() * 0.66f;
        Tubes.createTubes();
    }

    @Override
    public void render() {
        batch.begin();
        batch.disableBlending();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
        batch.draw(catTexture[catState], catPositionX, catPositionY, 60, 240);
        batch.end();
    }

    public void onStartGame() {
        batch.draw(pressToStart, Gdx.graphics.getWidth() / 2 - 400,
                Gdx.graphics.getHeight() / 3 - pressToStart.getHeight(), 800, 580);
        printBestScore();

        if (Gdx.input.justTouched()) {
            gameState = 1;
        }
    }

    public void onGamePlaying() {
        Gdx.app.log("gam", "in game state 1");
        printScore();

        if (tubeX[activeTube] < catPositionY) {
            score++;
            if (activeTube < numberOfTubes - 1) {
                activeTube++;
            } else {
                activeTube = 0;
            }
        }

        Tubes.tubeDraw();
        Steering.hardSteering();
        BirdControls.drawBlitzCount();
        BirdControls.getFlapState();

        // shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //  shapeRenderer.setColor(Color.RED);
        // shapeRenderer.rect(catPositionX, catPositionY, 60, 200);


        for (int i = 0; i < numberOfTubes; i++) {

            // shapeRenderer.rect(Gdx.graphics.getWidth() / 2 + gap / 2 + tubeOffset[i], tubeX[i]+rightTube.getHeight()*0.2f, leftTube.getWidth(), rightTube.getHeight()*0.6f);

            // shapeRenderer.rect(Gdx.graphics.getWidth() / 2 - gap / 2 - rightTube.getWidth() + tubeOffset[i], tubeX[i]+rightTube.getHeight()*0.2f, leftTube.getWidth(), rightTube.getHeight()*0.6f);


            if (Intersector.overlaps(catArea, topTubeRectangles[i]) || Intersector.overlaps(catArea, bottomTubeRectangles[i])) {
                gameState = 2;
            }
        }
        catArea.set(catPositionX, catPositionY + 20, 60, 200);
        // shapeRenderer.end();


    }

    public void onGameReset() {
        printBestScore();
        Gdx.app.log("gam", "in game state 3");
        batch.draw(pressToStart, Gdx.graphics.getWidth() / 2 - 400,
                Gdx.graphics.getHeight() / 3 - pressToStart.getHeight(), 800, 580);
        if (Gdx.input.justTouched()) {
            Gdx.app.log("gam", "go to game state 1");
            gameState = 1;
            score = 0;
            activeTube = 0;
            velocity = 0;
            tiltPower = 0;
            Tubes.createTubes();

            for (int i = 0; i < numberOfTubes; i++) {
                topTubeRectangles[i].set(Gdx.graphics.getWidth() / 2 + gap / 2 + tubeOffset[i], tubeX[i] + leftTube.getHeight() * 0.2f+500,
                        leftTube.getWidth(), leftTube.getHeight() * 0.6f);
                bottomTubeRectangles[i].set(Gdx.graphics.getWidth() / 2 - gap / 2 - rightTube.getWidth() + tubeOffset[i],
                        tubeX[i] + leftTube.getHeight() * 0.2f+500, rightTube.getWidth(), rightTube.getHeight() * 0.6f);
            }
            catPositionY = Gdx.graphics.getHeight() / 2 - catTexture[catState].getHeight() / 2;
            catPositionX = Gdx.graphics.getWidth() / 2 - catTexture[catState].getWidth() / 2;
            Steering.timeFirstClick = System.currentTimeMillis() - 60000;
        }
    }


    public void onLostGame() {
        printScore();
        if (score > bestScore) {
            bestScore = score;
        }
        batch.draw(gameOver, Gdx.graphics.getWidth() / 2 - 400,
                Gdx.graphics.getHeight() / 2 + 180, 800, 180);
        catState = 2;
        Gdx.app.log("gam", "in game state 2");
        batch.draw(scoreTexture, Gdx.graphics.getWidth() / 2 - scoreTexture.getWidth() / 2, Gdx.graphics.getHeight() - scoreTexture.getHeight());
        if (Gdx.input.justTouched()) {
            Gdx.app.log("gam", "go to game state 3");
            gameState = 3;
            System.gc();
        }
    }

    public void printScore() {
        if (score < 10) {
            batch.draw(scores[score], Gdx.graphics.getWidth() / 2 - 75, Gdx.graphics.getHeight() - 400, 150, 210);
        } else {
            int firstDigit = score / 10;
            int secondDigit = score % 10;
            batch.draw(scores[firstDigit], Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() - 400, 150, 210);
            batch.draw(scores[secondDigit], Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 400, 150, 210);
        }
    }

    public void printBestScore() {
        batch.draw(bestScoreText, Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() - 70, 300, 70);

        if (bestScore < 10) {
            batch.draw(scores[bestScore], Gdx.graphics.getWidth() / 2 - 75, Gdx.graphics.getHeight() - 400, 150, 210);
        } else {
            int firstDigit = bestScore / 10;
            int secondDigit = bestScore % 10;
            batch.draw(scores[firstDigit], Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() - 400, 150, 210);
            batch.draw(scores[secondDigit], Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 400, 150, 210);
        }
    }


}