package com.fixed4fun.tappymarten;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private static Texture gameOver;
    private static Texture pressToStart;
    private static Texture scoreTexture;
    private static Texture[] scores;
    private static Texture bestScoreText;
    private static Preferences preferences;
    private static int bestScore;
    static private Texture background;
    static private Rectangle catArea;
    private int activeTube;

    static void setCatState(int catState) {
        MyGdxGame.catState = catState;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("bg.jpg");
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
        catPositionY =(float)(Gdx.graphics.getHeight() / 2 - catTexture[catState].getHeight() / 2);
        catPositionX = (float)(Gdx.graphics.getWidth() / 2 - catTexture[catState].getWidth() / 2);
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


        rightTube = new Texture("left_ice.png");
        leftTube = new Texture("right_ice.png");
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

    private void onStartGame() {
        batch.draw(pressToStart, (float)Gdx.graphics.getWidth() / 2 - 400,
                (float)Gdx.graphics.getHeight() / 3 - pressToStart.getHeight(), 800, 580);
        printBestScore();

        if (Gdx.input.justTouched()) {
            gameState = 1;
        }
    }

    private void onGamePlaying() {
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
        for (int i = 0; i < numberOfTubes; i++) {
            if (Intersector.overlaps(catArea, topTubeRectangles[i]) || Intersector.overlaps(catArea, bottomTubeRectangles[i])) {
                gameState = 2;
            }
        }
        //+20 used in Y position as height is 40px less than texture, will make some free space at the top and the bottom
        catArea.set(catPositionX, catPositionY + 20, 60, 200);
    }

    private void onGameReset() {
        printBestScore();
        batch.draw(pressToStart, (float)Gdx.graphics.getWidth() / 2 - 400,
                (float)Gdx.graphics.getHeight() / 3 - pressToStart.getHeight(), 800, 580);
        if (Gdx.input.justTouched()) {
            gameState = 1;
            score = 0;
            activeTube = 0;
            velocity = 0;
            tiltPower = 0;
            Tubes.createTubes();

            for (int i = 0; i < numberOfTubes; i++) {
                topTubeRectangles[i].set((float)Gdx.graphics.getWidth() / 2 + gap / 2 + tubeOffset[i], tubeX[i] + leftTube.getHeight() * 0.2f + 500,
                        leftTube.getWidth(), leftTube.getHeight() * 0.6f);
                bottomTubeRectangles[i].set((float)Gdx.graphics.getWidth() / 2 - gap / 2 - rightTube.getWidth() + tubeOffset[i],
                        tubeX[i] + leftTube.getHeight() * 0.2f + 500, rightTube.getWidth(), rightTube.getHeight() * 0.6f);
            }
            catPositionY = (float) (Gdx.graphics.getHeight() / 2 - catTexture[catState].getHeight() / 2);
            catPositionX = (float)(Gdx.graphics.getWidth() / 2 - catTexture[catState].getWidth() / 2);
            Steering.timeFirstClick = System.currentTimeMillis() - 60000;
        }
    }


    private void onLostGame() {
        printScore();
        if (score > loadHighScore()) {
            bestScore = score;
            saveHighScore();
        }
        batch.draw(gameOver, (float)Gdx.graphics.getWidth() / 2 - 400,
                (float)Gdx.graphics.getHeight() / 2 + 180, 800, 180);
        catState = 2;
        batch.draw(scoreTexture, (float)Gdx.graphics.getWidth() / 2 - (float)scoreTexture.getWidth() / 2, Gdx.graphics.getHeight() - scoreTexture.getHeight());
        if (Gdx.input.justTouched()) {
            gameState = 3;
            System.gc();
        }
    }

    private void printScore() {
        if (score < 10) {
            batch.draw(scores[score], (float)Gdx.graphics.getWidth() / 2 - 75, Gdx.graphics.getHeight() - 400, 150, 210);
        } else {
            int firstDigit = score / 10;
            int secondDigit = score % 10;
            batch.draw(scores[firstDigit], (float)Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() - 400, 150, 210);
            batch.draw(scores[secondDigit], (float)Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 400, 150, 210);
        }
    }

    private void printBestScore() {
        batch.draw(bestScoreText, (float)Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() - 70, 300, 70);

        if (loadHighScore() < 10) {
            batch.draw(scores[loadHighScore()], (float)Gdx.graphics.getWidth() / 2 - 75, Gdx.graphics.getHeight() - 400, 150, 210);
        } else {
            int firstDigit = loadHighScore() / 10;
            int secondDigit = loadHighScore() % 10;
            batch.draw(scores[firstDigit], (float)Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() - 400, 150, 210);
            batch.draw(scores[secondDigit], (float)Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 400, 150, 210);
        }
    }


}