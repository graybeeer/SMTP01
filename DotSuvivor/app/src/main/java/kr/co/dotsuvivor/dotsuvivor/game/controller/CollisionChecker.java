package kr.co.dotsuvivor.dotsuvivor.game.controller;

import android.graphics.Canvas;

import java.util.ArrayList;

import kr.co.dotsuvivor.dotsuvivor.game.scene.MainScene;
import kr.co.dotsuvivor.dotsuvivor.game.object.Fireball;
import kr.co.dotsuvivor.dotsuvivor.game.object.Monster;
import kr.co.dotsuvivor.framework.scene.BaseScene;
import kr.co.dotsuvivor.framework.util.CollisionHelper;
import kr.co.dotsuvivor.framework.interfaces.IGameObject;

public class CollisionChecker implements IGameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();

    @Override
    public void update() {
        MainScene scene = (MainScene) BaseScene.getTopScene();
        ArrayList<IGameObject> monster = scene.getObjectsAt(MainScene.Layer.monster);
        ArrayList<IGameObject> fireball = scene.getObjectsAt(MainScene.Layer.weapon);
        for (int ei = monster.size() - 1; ei >= 0; ei--) {
            Monster monsterobj = (Monster) monster.get(ei);
            if(monsterobj.checkMonsterAlive()) {
                //몬스터와 파이어볼 충돌 체크
                for (int bi = fireball.size() - 1; bi >= 0; bi--) {
                    Fireball fireballobj = (Fireball) fireball.get(bi);
                    if (CollisionHelper.collides(monsterobj, fireballobj)) {
                        //Log.d(TAG, "ATTACK!!");
                        //충돌된 파이어볼 삭제처리. 나중엔 관통하거나 폭발하는 효과 추가.
                        BaseScene.getTopScene().remove(MainScene.Layer.weapon, fireballobj);
                        boolean dead = monsterobj.attacked(fireballobj.getDamage());
                        if (dead) {
                            //scene.addScore(enemy.getScore());
                        }
                    }
                }
                //몬스터와 플레이어 충돌 체크
                if (CollisionHelper.collides(monsterobj, MainScene.player)) {
                    MainScene.player.attacked(monsterobj.getDamage());
                    //Log.d(TAG, "DAMAGED!!");
                }
            }
        }
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
}
