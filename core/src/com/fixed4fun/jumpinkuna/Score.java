package com.fixed4fun.jumpinkuna;

public class Score {
    float time;
    int score;

    public Score() {
    }


    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Score(String username, float time, int score) {
        this.time = time;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Score{" +
                ", time=" + time +
                ", score=" + score +
                '}';
    }
}
