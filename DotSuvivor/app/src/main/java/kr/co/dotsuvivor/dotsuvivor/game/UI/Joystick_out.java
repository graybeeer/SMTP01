package kr.co.dotsuvivor.dotsuvivor.game.UI;

import kr.co.dotsuvivor.R;
import kr.co.dotsuvivor.framework.objects.Sprite;

public class Joystick_out extends Sprite {
    private static final String TAG = Joystick_out.class.getSimpleName();
    private final Sprite parentJoystick;

    public Joystick_out(Sprite obj) {
        super(R.mipmap.ui, (float) 4.5, 12, 4, 4, 57, 73, 105, 121);
        this.isUI = true;
        this.paint.setAlpha(0);
        parentJoystick = obj;
    }
}
