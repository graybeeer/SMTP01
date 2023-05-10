package kr.co.dotsuvivor.dotsuvivor.game;

import android.graphics.RectF;
import android.util.Log;

import kr.co.dotsuvivor.R;
import kr.co.dotsuvivor.framework.interfaces.IBoxCollidable;
import kr.co.dotsuvivor.framework.interfaces.IRecyclable;
import kr.co.dotsuvivor.framework.objects.Sprite;
import kr.co.dotsuvivor.framework.scene.BaseScene;
import kr.co.dotsuvivor.framework.util.Calculate;

public class Fireball extends Sprite implements IBoxCollidable, IRecyclable {
    private static final String TAG = Fireball.class.getSimpleName();
    private float speed; //파이어볼의 속도
    private float attackdegree; //파이어볼의 이동 각도
    private float damage; //파이어볼의 데미지

    public Fireball(float cx, float cy, float angle) {
        super(R.mipmap.props, cx, cy, 0.5f, 0.9f, 31, 20, 36, 29);
        attackdegree = angle; //실제 이동해야 하는 각도
        this.degree = angle + 90; //이미지를 회전시켜야 하는 각도. 꼭짓점이 위에 있음으로 꼭짓점이 적을 향하기 위해서는 오른쪽으로 90도 눕혀야 한다.
        this.speed = 5; //파이어볼의 스피드
        this.damage =50;
    }

    @Override
    public void update() {
        float dx = (float) (Math.cos(Calculate.DegreeToRadian(attackdegree)) * speed * BaseScene.frameTime);
        float dy = (float) (Math.sin(Calculate.DegreeToRadian(attackdegree)) * speed * BaseScene.frameTime);
        this.x += dx;
        this.y += dy;
        checkLifetime();
    }

    @Override
    public RectF getCollisionRect() {
        //나중에 콜라이드 전용 크기로 바꾸기
        return new RectF(x - width/2, y - width/2, x + width/2, y + width/2);
    }

    @Override
    public void onRecycle() {
    }
    private float lifetime=3; //파이어볼이 남아있는 총 시간
    private float accumulatedTime; //파이어볼 시간 재는 변수
    private void checkLifetime() {
        accumulatedTime += BaseScene.frameTime;
        if (accumulatedTime < lifetime) {
            return;
        }
        BaseScene.getTopScene().remove(MainScene.Layer.weapon, this);
        //Log.d(TAG, "화염구 삭제되었다");
    }
    public float getDamage()
    {
        return damage;
    }
}
