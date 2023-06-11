package kr.co.dotsuvivor.dotsuvivor.game.object;

import android.graphics.RectF;

import kr.co.dotsuvivor.R;
import kr.co.dotsuvivor.dotsuvivor.game.scene.MainScene;
import kr.co.dotsuvivor.framework.interfaces.IBoxCollidable;
import kr.co.dotsuvivor.framework.interfaces.IRecyclable;
import kr.co.dotsuvivor.framework.objects.Sprite;
import kr.co.dotsuvivor.framework.scene.BaseScene;
import kr.co.dotsuvivor.framework.util.Calculate;

public class Coin extends Sprite implements IBoxCollidable, IRecyclable {
    private float EXP;
    private final float lifetime = 25; //코인이 남아있는 총 시간

    public Coin(float cx, float cy) {
        super(R.mipmap.props, cx, cy, 0.8f, 0.9f, 23, 52, 31, 61);
        EXP=100;
    }

    @Override
    public void update() {
        checkLifetime();
    }

    @Override
    public RectF getCollisionRect() {
        return new RectF(x - width / 2, y - width / 2, x + width / 2, y + width / 2);
    }

    @Override
    public void onRecycle() {

    }

    private float accumulatedTime; //코인 잔류 시간 재는 변수

    private void checkLifetime() {
        accumulatedTime += BaseScene.frameTime;
        if (accumulatedTime < lifetime) {
            return;
        }
        deleteCoin();
        //Log.d(TAG, "코인 삭제되었다");
    }

    public void deleteCoin() {
        BaseScene.getTopScene().remove(MainScene.Layer.coin, this);
    }
    public float getEXP(){
        return EXP;
    }
}
