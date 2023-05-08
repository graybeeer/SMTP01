package kr.co.dotsuvivor.dotsuvivor.game;

import kr.co.dotsuvivor.framework.util.Calculate;

import android.view.MotionEvent;

import kr.co.dotsuvivor.R;
import kr.co.dotsuvivor.framework.interfaces.ITouchable;
import kr.co.dotsuvivor.framework.objects.Sprite;
import kr.co.dotsuvivor.framework.view.Metrics;

public class Joystick extends Sprite implements ITouchable {
    private static final String TAG = Joystick.class.getSimpleName();
    private final Callback callback;


    public enum Action {
        pressed, released,
    }

    public interface Callback {
        public boolean onTouch_down(Action action);
        public boolean onTouch_move(Action action);
        public boolean onTouch_up(Action action);
    }

    public Joystick(Callback callback) {
        super(R.mipmap.ui,(float)4.5,12,(float)2.5,(float)2.5,112,79,148,115);
        this.callback = callback;
        this.isUI=true;
        this.paint.setAlpha(0);
    }

    public float touch_saved_x, touch_saved_y; //화면 기준
    public float moved_angle_x=0, moved_angle_y=0;
    private float maxDistance = 1; //화면기준
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float touch_x = Metrics.toGameX(e.getX());
        float touch_y = Metrics.toGameY(e.getY());

        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                touch_saved_x = touch_x; //처음 입력된 x위치
                touch_saved_y = touch_y; //처음 입력된 y위치
                this.x=touch_saved_x;
                this.y=touch_saved_y;
                callback.onTouch_down(Action.pressed); //현재 저장된 클릭 위치를 구한 다음에 외곽선 생성
                break;
            case MotionEvent.ACTION_MOVE:
                moved_angle_x=0;
                moved_angle_y=0;

                float distance = (float) Calculate.getDistance(touch_saved_x, touch_saved_y, touch_x, touch_y); //화면기준 거리
                float angle = (float) Calculate.getAngle(touch_saved_x, touch_saved_y, touch_x, touch_y);
                moved_angle_x=(float)Math.cos(angle);
                moved_angle_y=(float)Math.sin(angle);
                if (distance <= maxDistance) {

                    this.x=touch_x;
                    this.y=touch_y;
                }
                else { //조이스틱 범위를 넘어가면
                    float x = (float) (touch_saved_x + Math.cos(angle) * maxDistance);
                    float y = (float) (touch_saved_y + Math.sin(angle) * maxDistance);
                    this.x=x;
                    this.y=y;
                }
                callback.onTouch_move(Action.pressed);
                break;
            case MotionEvent.ACTION_UP:
                moved_angle_x=0;
                moved_angle_y=0;
                callback.onTouch_up(Action.pressed);
                break;
        }
        return true;
    }


}
