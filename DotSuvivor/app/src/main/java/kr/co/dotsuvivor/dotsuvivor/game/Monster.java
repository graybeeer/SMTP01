package kr.co.dotsuvivor.dotsuvivor.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.co.dotsuvivor.R;
import kr.co.dotsuvivor.framework.interfaces.IBoxCollidable;
import kr.co.dotsuvivor.framework.interfaces.IRecyclable;
import kr.co.dotsuvivor.framework.objects.AnimSprite;
import kr.co.dotsuvivor.framework.scene.BaseScene;
import kr.co.dotsuvivor.framework.util.Calculate;

public class Monster extends AnimSprite implements IBoxCollidable, IRecyclable {
    private static final String TAG = Monster.class.getSimpleName();
    protected float speed;
    protected Shadow myShadow;
    protected float HP; //몬스터의 체력
    protected RectF collisionRect = new RectF(); //몬스터 피격 범위
    private float damage; //몬스터의 데미지

    public Monster(int bitmapResId, float cx, float cy, float width, float height, float fps, int frameCount) {
        super(bitmapResId, cx, cy, width, height, fps, frameCount);
        this.isUI = false;
        speed = 2.5f;
        myShadow = new Shadow(R.mipmap.props, 0, 0, 1, 0.6f, 37, 20, 47, 26, this);
        MainScene.add(MainScene.Layer.shadow, myShadow);
        this.HP = 200;
        this.damage = 10;
    }

    protected static int monstersize_x = 18; //몬스터 이미지 픽셀 사이즈 x크기
    protected static int monstersize_y = 18; //몬스터 이미지 픽셀 사이즈 y크기

    protected boolean lookinRight;
    protected static Rect[][] srcRects = {
            new Rect[]{ //move 애니메이션
                    new Rect(0 + 0 * monstersize_x, 0 + 0 * monstersize_y, 0 + 1 * monstersize_x, 0 + 1 * monstersize_y),
                    new Rect(1 + 1 * monstersize_x, 0 + 0 * monstersize_y, 1 + 2 * monstersize_x, 0 + 1 * monstersize_y),
                    new Rect(2 + 2 * monstersize_x, 0 + 0 * monstersize_y, 2 + 3 * monstersize_x, 0 + 1 * monstersize_y),
                    new Rect(0 + 0 * monstersize_x, 1 + 1 * monstersize_y, 0 + 1 * monstersize_x, 1 + 2 * monstersize_y),
                    new Rect(1 + 1 * monstersize_x, 1 + 1 * monstersize_y, 1 + 2 * monstersize_x, 1 + 2 * monstersize_y),
                    new Rect(2 + 2 * monstersize_x, 1 + 1 * monstersize_y, 2 + 3 * monstersize_x, 1 + 2 * monstersize_y)
            },
            new Rect[]{ //hurt 애니메이션
                    new Rect(0 + 0 * monstersize_x, 2 + 2 * monstersize_y, 0 + 1 * monstersize_x, 2 + 3 * monstersize_y)
            },
            new Rect[]{
                    new Rect(1 + 1 * monstersize_x, 2 + 2 * monstersize_y, 1 + 2 * monstersize_x, 2 + 3 * monstersize_y),
            }
    };

    @Override
    public void draw(Canvas canvas) {
        fixDstRect();
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        Rect[] rects = srcRects[nowMonsterState.ordinal()];
        int frameIndex = Math.round(time * fps) % rects.length;

        canvas.save(); // 현재 Canvas의 상태를 저장
        if (this.lookinRight) //캐릭터의 이동 방향에 따라 좌우 바라보는 방향을 정함
        {
            canvas.scale(1, 1, dstRect.centerX(), dstRect.centerY());
        } else {
            canvas.scale(-1, 1, dstRect.centerX(), dstRect.centerY()); // x 축으로 반전
        }
        canvas.drawBitmap(bitmap, rects[frameIndex], dstRect, null);
        canvas.restore(); // 이전 상태로 되돌림
    }

    public float getDamage() {
        return this.damage;
    }

    protected enum MonsterState {
        move, hurt, dead
    }

    protected MonsterState nowMonsterState = MonsterState.move;

    @Override
    public void update() {
        switch (nowMonsterState) {
            case move:
                if (MainScene.player.checkPlayerAlive()) {
                    doMove();
                }
                break;
            case hurt:
                checkHurtTime();
                break;
            case dead:
                checkDeadTime();
                break;
        }
        this.myShadow.ShadowSet(this.x, this.y + 1);
    }

    @Override
    public RectF getCollisionRect() {
        //나중에 콜라이드 전용 크기로 바꾸기
        return new RectF(x - width / 3, y - height / 3, x + width / 3, y + height / 3);
    }

    @Override
    public void onRecycle() {
    }

    public boolean attacked(float damage) {
        if (this.nowMonsterState == MonsterState.hurt)
            return false;

        HP -= damage;
        hurt();
        if (HP <= 0) {
            dead();
            return true;
        }
        return false;
    }

    protected void doMove() {
        //몬스터가 플레이어를 향해서 움직임
        float pl_ms_angle = (float) Calculate.getAngle(this.x, this.y, MainScene.player.x, MainScene.player.y);
        float move_distance_x = (float) (Math.cos(pl_ms_angle) * speed * BaseScene.frameTime);
        float move_distance_y = (float) (Math.sin(pl_ms_angle) * speed * BaseScene.frameTime);
        if (move_distance_x > 0) {
            lookinRight = true;
        } else {
            lookinRight = false;
        }

        //플레이어-몬스터 사이의 거리가 이동할 거리보다 가까우면
        if (Calculate.getDistance(this.x, this.y, MainScene.player.x, MainScene.player.y) <
                Calculate.getDistance(0, 0, move_distance_x, move_distance_y)) {
            this.x = MainScene.player.x;
            this.y = MainScene.player.y;
        } else {
            this.x += move_distance_x;
            this.y += move_distance_y;
        }
    }

    public void hurtRecovery() {
        //다친 이동상태가 됨(피격무적 해제)
        if (nowMonsterState == MonsterState.hurt) {

            nowMonsterState = MonsterState.move;
        }
    }

    public void hurt() {
        //다친 상태가 됨(피격무적)
        if (nowMonsterState == MonsterState.move) {
            nowMonsterState = MonsterState.hurt;
        }
    }

    protected float hurtAccumulatedTime = 0;//몬스터 피격무적 계산 변수
    protected final float HURT_INTERVAL = 0.1f; //몬스터 피격 무적 시간

    protected void checkHurtTime() {
        hurtAccumulatedTime += BaseScene.frameTime;
        if (hurtAccumulatedTime < HURT_INTERVAL) {
            return;
        }
        hurtAccumulatedTime = 0;
        hurtRecovery();
    }

    protected void dead() { //몬스터가 쓰러진 상태. 곧 죽을 상태
        this.nowMonsterState = MonsterState.dead;

    }

    protected float deadAccumulatedTime = 0;//몬스터 죽은 상태 시간 계산 변수
    protected float DEAD_INTERVAL = 1.5f; //몬스터 죽어있는 시간

    protected void checkDeadTime() {
        deadAccumulatedTime += BaseScene.frameTime;
        if (deadAccumulatedTime < DEAD_INTERVAL) {
            return;
        }
        deadAccumulatedTime = 0;
        die();
    }

    protected void die() { //죽은 몬스터의 삭제
        //몬스터 죽는 처리
        BaseScene.getTopScene().remove(MainScene.Layer.monster, this);
        BaseScene.getTopScene().remove(MainScene.Layer.shadow, this.myShadow);
    }

    public Shadow getMonsterShadow() {
        return myShadow;
    }

    //몬스터가 살아있는지 체크하는 함수
    public boolean checkMonsterAlive() {
        if (this.nowMonsterState == MonsterState.dead) {
            return false;
        }
        return true;
    }
    //***변수와 함수 정리하기
}
