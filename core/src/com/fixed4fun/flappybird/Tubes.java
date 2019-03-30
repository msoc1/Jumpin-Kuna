package com.fixed4fun.flappybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class Tubes extends MyGdxGame {


    public static void createTubes(){
        for (int i = 0; i < numberOfTubes; i++) {

            tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getWidth() - gap - 200);

            tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + Gdx.graphics.getWidth() + i * distanceBetweenTubes;

            topTubeRectangles[i] = new Rectangle();
            bottomTubeRectangles[i] = new Rectangle();
        }
    }

    public static void tubeDraw(){
        for (int i = 0; i < numberOfTubes; i++) {

            if (tubeX[i] < - topTube.getWidth()) {

                tubeX[i] += numberOfTubes * distanceBetweenTubes;
                tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getWidth() - gap - 200);

            } else {

                tubeX[i] = tubeX[i] - tubeVelocity;
            }
            batch.draw(bottomTube, Gdx.graphics.getWidth() / 2 + gap / 2 + tubeOffset[i], tubeX[i]);
            batch.draw(topTube, Gdx.graphics.getWidth() / 2 - gap / 2 - bottomTube.getWidth() + tubeOffset[i], tubeX[i]);

            topTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
            bottomTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());
        }
    }

}
