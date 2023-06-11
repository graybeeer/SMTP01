package kr.co.dotsuvivor.framework.objects;

import android.telecom.Call;
import android.util.Log;
import android.view.MotionEvent;

import kr.co.dotsuvivor.framework.interfaces.ITouchable;
import kr.co.dotsuvivor.framework.view.Metrics;

public class Button extends Sprite implements ITouchable {
    private static final String TAG = Button.class.getSimpleName();
    private final Callback callback;

    public enum Action {
        pressed, released,
    }
    public interface Callback {
        public boolean onTouch(Action action);
    }
    public Button(int bitmapResId, float cx, float cy, float width, float height, int left, int top, int right, int bottom,Callback callback) {
        super(bitmapResId, cx, cy, width, height, left, top, right, bottom);
        this.callback = callback;
        this.isUI = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = Metrics.toGameX(e.getX());
        float y = Metrics.toGameY(e.getY());
        //Log.d(TAG, "Button.onTouch(" + System.identityHashCode(this) + ", " + e.getAction() + ", " + e.getX() + ", " + e.getY());
        if (!dstRect.contains(x, y)) {
            return false;
        }

        int action = e.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            callback.onTouch(Action.pressed);
        } else if (action == MotionEvent.ACTION_UP) {
            callback.onTouch(Action.released);
        }
        return true;
    }
}
