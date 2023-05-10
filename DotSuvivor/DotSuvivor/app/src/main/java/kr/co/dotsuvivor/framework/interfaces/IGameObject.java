package kr.co.dotsuvivor.framework.interfaces;

import android.graphics.Canvas;

public interface IGameObject {
    public void update();
    public void draw(Canvas canvas);
    public float get_x();
    public float get_y();
}
