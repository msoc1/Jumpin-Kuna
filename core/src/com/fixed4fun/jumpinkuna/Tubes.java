package com.fixed4fun.jumpinkuna;

import com.badlogic.gdx.Gdx;

import static java.lang.Math.sqrt;

public class Tubes extends MyGdxGame {


    public static void createTubes() {
        for (int i = 0; i < numberOfTubes; i++) {
            tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getWidth() - gap - 200);
            tubeX[i] = Gdx.graphics.getHeight() - 400 + i * distanceBetweenTubes;

        }
    }

    protected static void tubeDraw() {
        float tubeVelocity = (float) (7 + (sqrt(score) * 2.45f));

        for (int i = 0; i < numberOfTubes; i++) {

            if (tubeX[i] < -leftTube.getWidth()) {

                tubeX[i] += numberOfTubes * distanceBetweenTubes;
                tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getWidth() - gap - 200);

            } else {
                tubeX[i] = tubeX[i] - tubeVelocity;
            }

            if (tubeX[i] + leftTube.getHeight() < 0 || tubeX[i] > Gdx.graphics.getHeight()) {

            } else {
                batch.draw(rightTube, Gdx.graphics.getWidth() / 2 + gap / 2 + tubeOffset[i], tubeX[i]);
                batch.draw(leftTube, Gdx.graphics.getWidth() / 2 - gap / 2 - rightTube.getWidth() + tubeOffset[i], tubeX[i]);

                topTubeRectangles[i].set(Gdx.graphics.getWidth() / 2 + gap / 2 + tubeOffset[i], tubeX[i] + leftTube.getHeight() * 0.2f,
                        leftTube.getWidth(), leftTube.getHeight() * 0.6f);
                bottomTubeRectangles[i].set(Gdx.graphics.getWidth() / 2 - gap / 2 - rightTube.getWidth() + tubeOffset[i],
                        tubeX[i] + leftTube.getHeight() * 0.2f, rightTube.getWidth(), rightTube.getHeight() * 0.6f);
            }
        }
    }

}
