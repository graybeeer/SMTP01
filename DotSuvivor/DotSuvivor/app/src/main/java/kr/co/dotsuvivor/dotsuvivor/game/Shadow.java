package kr.co.dotsuvivor.dotsuvivor.game;

import android.util.Log;

import kr.co.dotsuvivor.framework.objects.Sprite;

public class Shadow extends Sprite {
    private static final String TAG = Shadow.class.getSimpleName();
    private Sprite parentObj;
    public Shadow(int bitmapResId, float cx, float cy, float width, float height, int left, int top, int right, int bottom, Sprite obj) {
        super(bitmapResId, cx, cy, width, height, left, top, right, bottom);
        parentObj = obj;
    }
    @Override
    public void update() {
    }
    public void ShadowSet(float x, float y){
        this.x = x;
        this.y = y;
    }
}
