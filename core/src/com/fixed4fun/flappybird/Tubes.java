package com.fixed4fun.flappybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import static java.lang.Math.sqrt;

public class Tubes extends MyGdxGame {



    public static void createTubes(){
        for (int i = 0; i < numberOfTubes; i++) {

            tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getWidth() - gap - 200);
            tubeX[i] = Gdx.graphics.getHeight() - 400 + i * distanceBetweenTubes;
            topTubeRectangles[i] = new Rectangle();
            bottomTubeRectangles[i] = new Rectangle();
        }
    }

    protected static void tubeDraw(){
        float tubeVelocity = (float) (7+(sqrt(score) * 2.7f));

        for (int i = 0; i < numberOfTubes; i++) {

            if (tubeX[i] < - leftTube.getWidth()) {

                tubeX[i] += numberOfTubes * distanceBetweenTubes;
                tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getWidth() - gap - 200);

            } else {
                tubeX[i] = tubeX[i] - tubeVelocity;
                Gdx.app.log("tube","tubevelovity: " + tubeVelocity );
            }


            batch.draw(rightTube, Gdx.graphics.getWidth() / 2 + gap / 2 + tubeOffset[i], tubeX[i]);
            batch.draw(leftTube, Gdx.graphics.getWidth() / 2 - gap / 2 - rightTube.getWidth() + tubeOffset[i], tubeX[i]);

            topTubeRectangles[i] = new Rectangle(Gdx.graphics.getWidth() / 2 + gap / 2 + tubeOffset[i], tubeX[i]+leftTube.getHeight()*0.2f,
                    leftTube.getWidth(), leftTube.getHeight()*0.6f);
            bottomTubeRectangles[i] = new Rectangle(Gdx.graphics.getWidth() / 2 - gap / 2 - rightTube.getWidth() + tubeOffset[i],
                    tubeX[i]+leftTube.getHeight()*0.2f, rightTube.getWidth(), rightTube.getHeight()*0.6f);
        }
    }

}
