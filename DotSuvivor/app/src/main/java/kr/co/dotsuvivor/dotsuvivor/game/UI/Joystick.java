package kr.co.dotsuvivor.dotsuvivor.game.UI;

import kr.co.dotsuvivor.dotsuvivor.game.scene.MainScene;
import kr.co.dotsuvivor.dotsuvivor.game.object.Player;
import kr.co.dotsuvivor.framework.scene.BaseScene;
import kr.co.dotsuvivor.framework.util.Calculate;

import android.view.MotionEvent;

import kr.co.dotsuvivor.R;
import kr.co.dotsuvivor.framework.interfaces.ITouchable;
import kr.co.dotsuvivor.framework.objects.Sprite;
import kr.co.dotsuvivor.framework.view.Metrics;

public class Joystick extends Sprite implements ITouchable {
    private static final String TAG = Joystick.class.getSimpleName();
    private final Callback callback;
    private final Player control_player; //이 조이스틱이 조종하는 플레이어
    private final Joystick_out my_Joystick_out; //이 조이스틱의 바깥 ui

    public enum Action {
        pressed, released,
    }

    public interface Callback {
        public boolean onTouch_down(Action action);
        public boolean onTouch_move(Action action);
        public boolean onTouch_up(Action action);
    }

    public Joystick(MainScene mainscene, Callback callback, Player player) {
        super(R.mipmap.ui, (float) 4.5, 12, (float) 2.5, (float) 2.5, 112, 79, 148, 115);
        this.control_player = player;
        this.callback = callback;
        this.isUI = true;
        this.paint.setAlpha(0);
        my_Joystick_out = new Joystick_out(this);
        mainscene.add(MainScene.Layer.ui, my_Joystick_out);
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
            case MotionEvent.ACTION_DOWN: //화면을 누르면
                touch_saved_x = touch_x; //처음 입력된 x위치
                touch_saved_y = touch_y; //처음 입력된 y위치
                this.x=touch_saved_x;
                this.y=touch_saved_y;

                my_Joystick_out.x = this.touch_saved_x; //외곽 조이스틱 위치 결정
                my_Joystick_out.y = this.touch_saved_y;
                this.setAlpha(255); //조이스틱 불투명 만들기
                my_Joystick_out.setAlpha(255);
                //callback.onTouch_down(Action.pressed); //현재 저장된 클릭 위치를 구한 다음에 외곽선 생성
                break;
            case MotionEvent.ACTION_MOVE: //조이스틱을 움직이면
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
                this.setAlpha(255); //조이스틱 불투명 만들기
                my_Joystick_out.setAlpha(255);
                control_player.move(this.moved_angle_x, this.moved_angle_y);
                //callback.onTouch_move(Action.pressed);
                break;
            case MotionEvent.ACTION_UP: //화면에서 손가락을 떼면
                moved_angle_x=0;
                moved_angle_y=0;

                this.setAlpha(0); //조이스틱 투명 만들기
                my_Joystick_out.setAlpha(0);
                control_player.idle(); //조종하는 플레이어 정지
                //callback.onTouch_up(Action.pressed);
                break;
        }
        return true;
    }


}
