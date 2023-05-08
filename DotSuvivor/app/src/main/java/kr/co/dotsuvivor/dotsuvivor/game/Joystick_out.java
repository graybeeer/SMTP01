package kr.co.dotsuvivor.dotsuvivor.game;

import android.util.Log;
import android.view.MotionEvent;

import kr.co.dotsuvivor.R;
import kr.co.dotsuvivor.framework.interfaces.ITouchable;
import kr.co.dotsuvivor.framework.objects.Sprite;
import kr.co.dotsuvivor.framework.view.Metrics;

public class Joystick_out extends Sprite {
    private static final String TAG = Joystick_out.class.getSimpleName();
    public Joystick_out() {
        super(R.mipmap.ui, (float) 4.5, 12, 4, 4, 57, 73, 105, 121);
        this.isUI = true;
        this.paint.setAlpha(0);
    }
}
