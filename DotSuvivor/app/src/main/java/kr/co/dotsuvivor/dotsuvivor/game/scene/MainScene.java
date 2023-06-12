package kr.co.dotsuvivor.dotsuvivor.game.scene;

import android.util.Log;

import kr.co.dotsuvivor.R;
import kr.co.dotsuvivor.dotsuvivor.game.UI.Joystick;
import kr.co.dotsuvivor.dotsuvivor.game.controller.CollisionChecker;
import kr.co.dotsuvivor.dotsuvivor.game.controller.MonsterSpawner;
import kr.co.dotsuvivor.dotsuvivor.game.object.InfinityBackground;
import kr.co.dotsuvivor.dotsuvivor.game.object.Player;
import kr.co.dotsuvivor.framework.objects.Button;
import kr.co.dotsuvivor.framework.scene.BaseScene;
import kr.co.dotsuvivor.framework.view.Metrics;

public class MainScene extends BaseScene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Player player;
    private final Joystick joystick;
    private final InfinityBackground infinityBackground1;
    private final InfinityBackground infinityBackground2;
    private final InfinityBackground infinityBackground3;
    private final InfinityBackground infinityBackground4;
    public enum Layer {
        bg, shadow, coin, weapon, monster, player, effect, ui, touch, controller, COUNT
    }

    public MainScene() {
        Metrics.setGameSize(9.0f, 16.0f);
        initLayers(Layer.COUNT);

        //플레이어 추가
        player = new Player();
        add(Layer.player, player);

        //무한 배경 추가
        infinityBackground1 = new InfinityBackground(player, R.mipmap.background, -9, 9, 18, 18, 0, 0, 180, 180);
        infinityBackground2 = new InfinityBackground(player, R.mipmap.background, 9, 9, 18, 18, 0, 0, 180, 180);
        infinityBackground3 = new InfinityBackground(player, R.mipmap.background, -9, -9, 18, 18, 0, 0, 180, 180);
        infinityBackground4 = new InfinityBackground(player, R.mipmap.background, 9, -9, 18, 18, 0, 0, 180, 180);
        add(Layer.bg, infinityBackground1);
        add(Layer.bg, infinityBackground2);
        add(Layer.bg, infinityBackground3);
        add(Layer.bg, infinityBackground4);

        //정지 버튼 추가
        add(Layer.touch, new Button(R.mipmap.ui, 8.4f, 1.7f, 1.2f, 1.2f, 0, 85, 18, 103, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                if (action == Button.Action.pressed) {
                    new PausedScene().pushScene();
                    //Log.d(TAG, "pause touch");
                }
                return true;
            }
        }));

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



        add(Layer.controller, new CollisionChecker(player)); //콜라이더 체크 추가
        add(Layer.controller, new MonsterSpawner(player));


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