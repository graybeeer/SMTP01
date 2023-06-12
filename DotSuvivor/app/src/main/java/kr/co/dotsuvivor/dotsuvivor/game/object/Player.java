package kr.co.dotsuvivor.dotsuvivor.game.object;

import android.graphics.RectF;

import kr.co.dotsuvivor.R;
import kr.co.dotsuvivor.dotsuvivor.game.UI.EXPbar;
import kr.co.dotsuvivor.dotsuvivor.game.object.monster.Monster;
import kr.co.dotsuvivor.dotsuvivor.game.object.weapon.Fireball;
import kr.co.dotsuvivor.dotsuvivor.game.object.weapon.FireballBullet;
import kr.co.dotsuvivor.dotsuvivor.game.object.weapon.Weapon;
import kr.co.dotsuvivor.dotsuvivor.game.scene.MainScene;
import kr.co.dotsuvivor.dotsuvivor.game.UI.GameOverUI;
import kr.co.dotsuvivor.dotsuvivor.game.UI.HPbar;
import kr.co.dotsuvivor.framework.interfaces.IBoxCollidable;
import kr.co.dotsuvivor.framework.interfaces.IGameObject;
import kr.co.dotsuvivor.framework.objects.AnimSprite;
import kr.co.dotsuvivor.framework.scene.BaseScene;
import kr.co.dotsuvivor.framework.objects.Camera;
import kr.co.dotsuvivor.framework.util.Calculate;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

public class Player extends AnimSprite implements IBoxCollidable {
    private static final String TAG = Player.class.getSimpleName();
    private static int playersize_x = 18;
    private static int playersize_y = 20;
    private final Shadow myShadow; //내 그림자
    private final HPbar myHpbar; //내 체력바
    private final EXPbar myEXPbar; //내 경험치바
    private float maxHP; //내 최대 체력
    private float nowHP; //내 지금 체력

    private float maxEXP; //최대 경험치
    private float nowEXP; //내 경험치
    private boolean hurt = false; //공격받은 상태
    protected final float HURT_INTERVAL = 1f; //플레이어 피격 무적 시간
    private ArrayList<Weapon> playerWeaponList; //플레이어가 소지하고 있는 무기들 리스트



    //도트 서바이벌 리소스 이미지 시트는 18x18, 여백 1 크기다.
    public Player(MainScene mainscene) {
        super(R.mipmap.farmer_0, 5, 5, (float) 1.8, 2, 5, 1);
        //그림자 추가
        myShadow = new Shadow(R.mipmap.props, 0, 0, 1, 0.6f, 37, 20, 47, 26, this);
        mainscene.add(MainScene.Layer.shadow, myShadow);
        this.isUI = false;
        this.maxHP = 30;
        this.nowHP = this.maxHP;
        this.maxEXP = 1000;
        this.nowEXP = 0;

        //HP바 추가
        myHpbar = new HPbar(mainscene, R.mipmap.ui, 0, 0, 1.6f, 0.3f, 20, 77, 26, 83, this);
        mainscene.add(MainScene.Layer.ui, myHpbar);

        //EXP바 추가
        myEXPbar = new EXPbar(mainscene,this);
        mainscene.add(MainScene.Layer.ui, myEXPbar);

        //무기배열 추가
        playerWeaponList = new ArrayList<Weapon>();
        playerWeaponList.add(new Fireball()); //파이어볼 추가

    }

    @Override
    public RectF getCollisionRect() {
        //나중에 콜라이드 전용 크기로 바꾸기
        return new RectF(x - width / 4, y - height / 3, x + width / 4, y + height / 3);
    }

    //플레이어의 상태
    protected enum PlayerState {
        idle, move, dead
    }

    //    protected Rect[] srcRects
    protected static Rect[][] srcRects = {
            new Rect[]{ //idle 애니메이션
                    new Rect(0 + 0 * playersize_x, 2 + 2 * playersize_y, 0 + 1 * playersize_x, 2 + 3 * playersize_y),
                    new Rect(1 + 1 * playersize_x, 2 + 2 * playersize_y, 1 + 2 * playersize_x, 2 + 3 * playersize_y),
                    new Rect(2 + 2 * playersize_x, 2 + 2 * playersize_y, 2 + 3 * playersize_x, 2 + 3 * playersize_y),
                    new Rect(0 + 0 * playersize_x, 3 + 3 * playersize_y, 0 + 1 * playersize_x, 3 + 4 * playersize_y),
            },
            new Rect[]{ //move 애니메이션
                    new Rect(0 + 0 * playersize_x, 0 + 0 * playersize_y, 0 + 1 * playersize_x, 0 + 1 * playersize_y),
                    new Rect(1 + 1 * playersize_x, 0 + 0 * playersize_y, 1 + 2 * playersize_x, 0 + 1 * playersize_y),
                    new Rect(2 + 2 * playersize_x, 0 + 0 * playersize_y, 2 + 3 * playersize_x, 0 + 1 * playersize_y),
                    new Rect(0 + 0 * playersize_x, 1 + 1 * playersize_y, 0 + 1 * playersize_x, 1 + 2 * playersize_y),
                    new Rect(1 + 1 * playersize_x, 1 + 1 * playersize_y, 1 + 2 * playersize_x, 1 + 2 * playersize_y),
                    new Rect(2 + 2 * playersize_x, 1 + 1 * playersize_y, 2 + 3 * playersize_x, 1 + 2 * playersize_y)
            },
            new Rect[]{ //dead 애니메이션
                    new Rect(1 + 1 * playersize_x, 3 + 3 * playersize_y, 1 + 2 * playersize_x, 3 + 4 * playersize_y),
                    new Rect(2 + 2 * playersize_x, 3 + 3 * playersize_y, 2 + 3 * playersize_x, 3 + 4 * playersize_y),
            }
    };
    protected PlayerState nowPlayerState = PlayerState.idle;

    @Override
    public void draw(Canvas canvas) {
        fixDstRect();
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        Rect[] rects = srcRects[nowPlayerState.ordinal()];
        int frameIndex = Math.round(time * fps) % rects.length;
        canvas.save(); // 현재 Canvas의 상태를 저장

        canvas.rotate(degree, dstRect.centerX(), dstRect.centerY()); //캔버스 회전

        if (this.speed_x >= 0) //캐릭터의 이동 방향에 따라 좌우 바라보는 방향을 정함
        {
            canvas.scale(1, 1, dstRect.centerX(), dstRect.centerY());
        } else {
            canvas.scale(-1, 1, dstRect.centerX(), dstRect.centerY()); // x 축으로 반전
        }

        canvas.drawBitmap(bitmap, rects[frameIndex], dstRect, paint);
        canvas.restore();
    }

    private float speed_x = 0; //플레이어의 x좌표 스피드(기준 1)
    private float speed_y = 0;
    private float maxSpeed = 3; //플레이어의 총 스피드.

    @Override
    public void update() {
        switch (nowPlayerState) {
            case idle:
                Camera.camera_x = this.x; //카메라 플레이어와 동기화
                Camera.camera_y = this.y;
                checkAttack(); //플레이어 공격 체크

                if (this.hurt) //피격무적 상태일경우 반투명 상태로 만들기
                {
                    this.setAlpha(180);
                } else {
                    this.setAlpha(255);
                }
                checkHurtTime(); //플레이어 피격 무적 시간 체크
                break;
            case move:
                float dx = speed_x * maxSpeed * BaseScene.frameTime;
                float dy = speed_y * maxSpeed * BaseScene.frameTime;
                this.x += dx;
                this.y += dy;
                Camera.camera_x = this.x;
                Camera.camera_y = this.y;
                checkAttack();

                if (this.hurt) //피격무적 상태일경우 반투명 상태로 만들기
                {
                    this.setAlpha(180);
                } else {
                    this.setAlpha(255);
                }
                checkHurtTime();
                break;
            case dead:
                Camera.camera_x = this.x;
                Camera.camera_y = this.y;
                break;
        }
        this.myShadow.ShadowSet();
        this.myHpbar.HPbarSet();
        this.myEXPbar.EXPbarSet();
    }

    public void move(float dx, float dy) {
        if (nowPlayerState == PlayerState.idle) {
            nowPlayerState = PlayerState.move;
        }
        speed_x = dx;
        speed_y = dy;

    }

    public void idle() {
        if (nowPlayerState == PlayerState.move) {
            nowPlayerState = PlayerState.idle;
        }
    }

    private IGameObject nearest_monster = null; //가장 가까운 적 변수
    private float nearest_monster_distance = 0; //가장 가까운 적 까지의 거리
    private float attack_range_distance = 6; //공격 인식 범위

    private void attack() { //주변 적 공격 함수
        //메인 씬에서 몬스터 레이어에 있는 목록 가져오기
        ArrayList<IGameObject> MonsterArrayList = BaseScene.getTopScene().getObjectsAt(MainScene.Layer.monster);
        int i = 0;
        for (int ei = MonsterArrayList.size() - 1; ei >= 0; ei--) {
            Monster monsterObj = (Monster) MonsterArrayList.get(ei);

            float monster_player_distance; //플레이어-몬스터 거리 기록용 변수
            monster_player_distance = (float) Calculate.getDistance(this.x, this.y, monsterObj.get_x(), monsterObj.get_y());
            if (attack_range_distance > monster_player_distance && monsterObj.checkMonsterAlive())//몬스터가 인식 범위보다 안쪽에 있으면
            {
                if (i == 0) {
                    nearest_monster = monsterObj;
                    nearest_monster_distance = monster_player_distance;
                } else {
                    if (nearest_monster_distance > monster_player_distance) {
                        nearest_monster_distance = monster_player_distance;
                        nearest_monster = monsterObj;
                    }
                }
                i++;
            }
        }

        if (i != 0) {
            shotFireball();
            //Log.d(TAG, String.valueOf(attackDegree));
        }
        //Log.d(TAG, String.valueOf(nearest_monster_distance));
    }

    private float accumulatedTime;//플레이어 공격 주기 계산 변수
    private static final float ATTACK_INTERVAL = 1f; //플레이어 공격 속도

    private void checkAttack() {
        accumulatedTime += BaseScene.frameTime;
        if (accumulatedTime < ATTACK_INTERVAL) {
            return;
        }
        accumulatedTime = 0;
        attack();
    }

    private void shotFireball() { //가장 가까운 적에게 파이어볼 발사 함수
        if (nearest_monster == null)
            return;
        float attackDegree = (float) Calculate.getAngle(this.x, this.y, nearest_monster.get_x(), nearest_monster.get_y());
        attackDegree = (float) Calculate.RadianToDegree(attackDegree);
        BaseScene.getTopScene().add(MainScene.Layer.weapon, new FireballBullet(this.x, this.y, attackDegree));
    }

    public void get_EXP(float coin) {
        this.nowEXP += coin;
    }
    //플레이어가 살아있는지 체크
    public boolean checkPlayerAlive() {
        if (nowPlayerState == PlayerState.dead) {
            return false;
        }
        return true;
    }

    protected float hurtAccumulatedTime = 0;//플레이어 피격무적 계산 변수


    protected void checkHurtTime() {
        if (this.hurt == false) { //다친 상태가 아니라면 계산 종료
            return;
        }
        hurtAccumulatedTime += BaseScene.frameTime;
        if (hurtAccumulatedTime < HURT_INTERVAL) {
            return;
        }
        hurtAccumulatedTime = 0;
        hurt = false; //시간 다 되면 피격 무적 종료
    }

    public boolean attacked(float damage) {
        if (this.hurt == true) {
            //Log.d(TAG, String.valueOf(HP));
            return false;
        }
        //Log.d(TAG, "attacked!!!");
        this.nowHP -= damage;
        this.hurt = true;

        if (nowHP <= 0) {
            dead();
            return true;
        }
        return false;
    }


    private void dead() {
        this.setAlpha(255);
        this.nowPlayerState = PlayerState.dead;
        BaseScene.getTopScene().add(MainScene.Layer.ui, new GameOverUI());
    }
    public void gainEXP(float coinEXP){
        nowEXP+=coinEXP;
        //만약 최대 exp를 넘기면 레벨업
        if(nowEXP>maxEXP)
        {
            //levelup();
        }
    }


    //체력
    public float getMaxHP(){
        return this.maxHP;
    }
    public float getNowHP(){
        return this.nowHP;
    }
    public float getMaxEXP(){
        return this.maxEXP;
    }
    public float getNowEXP(){
        return this.nowEXP;
    }
}