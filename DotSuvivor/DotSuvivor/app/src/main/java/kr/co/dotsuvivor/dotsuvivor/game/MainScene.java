package kr.co.dotsuvivor.dotsuvivor.game;

import android.util.Log;

import java.util.ArrayList;

import kr.co.dotsuvivor.R;
import kr.co.dotsuvivor.framework.interfaces.IGameObject;
import kr.co.dotsuvivor.framework.scene.BaseScene;
import kr.co.dotsuvivor.framework.view.Metrics;

public class MainScene extends BaseScene {
    private static final String TAG = MainScene.class.getSimpleName();
    public static Player player;
    private final Joystick joystick;
    private final InfinityBackground infinityBackground1;
    private final InfinityBackground infinityBackground2;
    private final InfinityBackground infinityBackground3;
    private final InfinityBackground infinityBackground4;
    public enum Layer {
        bg, shadow, monster, player, weapon, effect, ui, touch, controller,COUNT
    }

    public MainScene() {
        Metrics.setGameSize(9.0f, 16.0f);
        initLayers(Layer.COUNT);
        //무한 배경 추가
        infinityBackground1 = new InfinityBackground(R.mipmap.background, -9, 9, 18, 18, 0, 0, 180, 180);
        infinityBackground2 = new InfinityBackground(R.mipmap.background, 9, 9, 18, 18, 0, 0, 180, 180);
        infinityBackground3 = new InfinityBackground(R.mipmap.background, -9, -9, 18, 18, 0, 0, 180, 180);
        infinityBackground4 = new InfinityBackground(R.mipmap.background, 9, -9, 18, 18, 0, 0, 180, 180);
        add(Layer.bg, infinityBackground1);
        add(Layer.bg, infinityBackground2);
        add(Layer.bg, infinityBackground3);
        add(Layer.bg, infinityBackground4);


        //몬스터 추가
        add(Layer.monster, new Monster(R.mipmap.enemy_1, -10, 10, 1.8f, 1.8f, 5, 1));
        add(Layer.monster, new Monster(R.mipmap.enemy_1, 10, 10, 1.8f, 1.8f, 5, 1));

        //플레이어 추가
        player = new Player();
        add(Layer.player, player);

        //조이스틱 추가
        joystick = new Joystick(new Joystick.Callback() { //콜백함수 사용안함.
            @Override
            public boolean onTouch_down(Joystick.Action action) {
                return true;
            }
            @Override
            public boolean onTouch_move(Joystick.Action action) {

                return false;
            }
            @Override
            public boolean onTouch_up(Joystick.Action action) {

                return false;
            }
        }
        ,player);
        add(Layer.touch, joystick);

        add(Layer.controller, new CollisionChecker()); //콜라이더 체크 추가
    }
/*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            player.move();
        }

        return super.onTouchEvent(event);
    }

 */
    @Override
    protected int getTouchLayerIndex() {

        return Layer.touch.ordinal();
    }


}