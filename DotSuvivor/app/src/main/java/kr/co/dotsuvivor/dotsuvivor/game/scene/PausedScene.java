package kr.co.dotsuvivor.dotsuvivor.game.scene;

import android.util.Log;

import kr.co.dotsuvivor.R;
import kr.co.dotsuvivor.framework.objects.Button;
import kr.co.dotsuvivor.framework.objects.Sprite;
import kr.co.dotsuvivor.framework.scene.BaseScene;

public class PausedScene extends BaseScene {
    private static final String TAG = MainScene.class.getSimpleName();

    private Sprite black_background;
    public enum Layer {
        bg, title, touch, COUNT
    }
    public PausedScene() {
        initLayers(Layer.COUNT);

        black_background = new Sprite(R.mipmap.trans_50b, 4.5f, 8.0f, 9, 16, 0, 0, 1, 1);
        black_background.setUI(true);
        add(Layer.bg, black_background);

        //일시정지 되돌아가기 버튼
        add(Layer.touch, new Button(R.mipmap.ui, 8.4f, 1.7f, 1.2f, 1.2f, 0, 85, 18, 103, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                if (action == Button.Action.pressed) {
                    popScene();
                }
                return true;
            }
        }));
        //나가기 버튼
        add(Layer.touch, new Button(R.mipmap.ui, 4.5f, 12f, 6f, 3f, 0, 104, 36, 122, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                if (action == Button.Action.pressed) {
                    finishActivity();
                }
                return true;
            }
        }));
    }
    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }
}
