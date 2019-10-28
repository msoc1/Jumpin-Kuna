package com.fixed4fun.jumpinkuna;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static java.lang.Math.sqrt;

public class Tubes extends MyGdxGame {


    public static void createTubes() {
        for (int i = 0; i < numberOfTubes; i++) {
            tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getWidth() - gap - (Gdx.graphics.getWidth()*0.185f));
            tubeX[i] = Gdx.graphics.getHeight() - (Gdx.graphics.getHeight()*0.222f) + i * distanceBetweenTubes;

        }


    }

    protected static void tubeDraw() {
        float tubeVelocity = (float) ((7 + (sqrt(score) * 2.45f))/1794) * Gdx.graphics.getHeight();


        for (int i = 0; i < numberOfTubes; i++) {

            if (tubeX[i] < -tubeWidth) {

                tubeX[i] += numberOfTubes * distanceBetweenTubes;
                tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getWidth() - gap - (Gdx.graphics.getWidth()*0.185f));

            } else {
                tubeX[i] = tubeX[i] - tubeVelocity;
            }

            if (tubeX[i] + tubeHeight < 0 || tubeX[i] > Gdx.graphics.getHeight()) {

            } else {




                batch.draw(rightTube, Gdx.graphics.getWidth() / 2 + gap / 2 + tubeOffset[i], tubeX[i], tubeWidth, tubeHeight);
                batch.draw(leftTube, Gdx.graphics.getWidth() / 2 - gap / 2 - tubeWidth + tubeOffset[i], tubeX[i], tubeWidth, tubeHeight);


                topTubeRectangles[i].set(Gdx.graphics.getWidth() / 2 + gap / 2 + tubeOffset[i] + tubeWidth*0.05f, tubeX[i] + tubeHeight * 0.2f,
                        tubeWidth, tubeHeight * 0.65f );

                bottomTubeRectangles[i].set(Gdx.graphics.getWidth() / 2 - gap / 2 - tubeWidth*1.05f + tubeOffset[i],tubeX[i] + tubeHeight * 0.2f ,
                        tubeWidth, tubeHeight * 0.65f);

                leftCircles[i].set(Gdx.graphics.getWidth() / 2 + gap / 2 + tubeOffset[i] + tubeWidth*0.02f, tubeX[i] + tubeHeight * 0.7f , Gdx.graphics.getWidth()*0.03f);

                rightCircles[i].set(Gdx.graphics.getWidth() / 2 - gap / 2 - tubeWidth*0.02f + tubeOffset[i], tubeX[i] + tubeHeight * 0.7f , Gdx.graphics.getWidth()*0.03f);



            }
        }

    }

}
