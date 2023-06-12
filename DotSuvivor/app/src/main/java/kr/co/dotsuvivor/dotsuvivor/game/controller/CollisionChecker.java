package kr.co.dotsuvivor.dotsuvivor.game.controller;

import android.graphics.Canvas;

import java.util.ArrayList;

import kr.co.dotsuvivor.dotsuvivor.game.object.Coin;
import kr.co.dotsuvivor.dotsuvivor.game.object.Player;
import kr.co.dotsuvivor.dotsuvivor.game.scene.MainScene;
import kr.co.dotsuvivor.dotsuvivor.game.object.weapon.FireballBullet;
import kr.co.dotsuvivor.dotsuvivor.game.object.monster.Monster;
import kr.co.dotsuvivor.framework.scene.BaseScene;
import kr.co.dotsuvivor.framework.util.CollisionHelper;
import kr.co.dotsuvivor.framework.interfaces.IGameObject;

public class CollisionChecker implements IGameObject {

    private Player game_player;

    public CollisionChecker(Player player) {
        this.game_player = player;
    }

    @Override
    public void update() {
        MainScene scene = (MainScene) BaseScene.getTopScene();
        ArrayList<IGameObject> monster = scene.getObjectsAt(MainScene.Layer.monster);
        ArrayList<IGameObject> fireball = scene.getObjectsAt(MainScene.Layer.weapon);
        ArrayList<IGameObject> coin = scene.getObjectsAt(MainScene.Layer.coin);

        for (int ei = monster.size() - 1; ei >= 0; ei--) { //몬스터 순회
            Monster monsterobj = (Monster) monster.get(ei);
            if(monsterobj.checkMonsterAlive()) {
                //몬스터와 파이어볼 충돌 체크
                for (int bi = fireball.size() - 1; bi >= 0; bi--) {
                    FireballBullet fireballobj = (FireballBullet) fireball.get(bi);
                    if (CollisionHelper.collides(monsterobj, fireballobj)) {
                        //Log.d(TAG, "ATTACK!!");
                        //충돌된 파이어볼 삭제처리. 나중엔 관통하거나 폭발하는 효과 추가.
                        BaseScene.getTopScene().remove(MainScene.Layer.weapon, fireballobj);
                        monsterobj.attacked(fireballobj.getDamage());
                    }
                }
                //몬스터와 플레이어 충돌 체크
                if (CollisionHelper.collides(monsterobj, game_player)) {
                    game_player.attacked(monsterobj.getDamage());
                    //Log.d(TAG, "DAMAGED!!");
                }
            }
        }
        for (int ei = coin.size() - 1; ei >= 0; ei--) { //코인 순회
            Coin coinobj = (Coin) coin.get(ei);
            //코인과 플레이어 충돌 체크
            if (CollisionHelper.collides(coinobj, game_player)) {
                game_player.gainEXP(coinobj.getEXP());
                coinobj.deleteCoin();
                //Log.d(TAG, "DAMAGED!!");
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
