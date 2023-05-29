package kr.co.dotsuvivor.dotsuvivor.game.controller;

import android.graphics.Canvas;

import kr.co.dotsuvivor.dotsuvivor.game.scene.MainScene;
import kr.co.dotsuvivor.dotsuvivor.game.object.Monster;
import kr.co.dotsuvivor.dotsuvivor.game.object.Player;
import kr.co.dotsuvivor.framework.interfaces.IGameObject;
import kr.co.dotsuvivor.framework.scene.BaseScene;

public class MonsterSpawner implements IGameObject {
    private Player game_player;
    private int level; //현재 레벨
    private float game_time;
    private float timer;
    private float spawnCycle; //소환 주기

    public MonsterSpawner(Player player) {
        game_player = player;
        game_time = 0; //게임 시간 초기화
        timer = 0;
        spawnCycle = 1f;
    }

    @Override
    public void update() {
        spawnTimer();
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public float get_x() {
        return 0;
    }

    @Override
    public float get_y() {
        return 0;
    }

    private void spawn(float spawn_x, float spawn_y) {
        BaseScene.getTopScene().add(MainScene.Layer.monster, new Monster(spawn_x, spawn_y));
    }

    private float randomPoint[][] = {
            {5, 9}, {5, 4}, {5, 0}, {5, -4}, {5, -9},
            {-5, 9}, {-5, 4}, {-5, 0}, {-5, -4}, {-5, -9},
            {0, 9}, {0, -9}, {3, 9}, {3, -9}, {-3, 9}, {-3, -9}
    };

    private void spawnTimer() {
        game_time += BaseScene.frameTime;
        timer += BaseScene.frameTime;
        if (timer > spawnCycle) {
            timer -= spawnCycle;
            int randomNum = (int) (Math.random() * 15);
            spawn(

                    game_player.get_x() + randomPoint[randomNum][0],
                    game_player.get_y() + randomPoint[randomNum][1]);
        }
    }
}
