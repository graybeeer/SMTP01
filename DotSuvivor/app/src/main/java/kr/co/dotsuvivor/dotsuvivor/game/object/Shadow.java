package kr.co.dotsuvivor.dotsuvivor.game.object;

import kr.co.dotsuvivor.framework.objects.Sprite;

public class Shadow extends Sprite {
    private static final String TAG = Shadow.class.getSimpleName();
    private Sprite parentObj;
    public Shadow(int bitmapResId, float cx, float cy, float width, float height, int left, int top, int right, int bottom, Sprite obj) {
        super(bitmapResId, cx, cy, width, height, left, top, right, bottom);
        parentObj = obj;
        ShadowSet(); //재활용시 제대로된 위치로 옮겨야함
    }
    @Override
    public void update() {
    }
    public void ShadowSet(float x, float y){
        this.x = x;
        this.y = y;
    }
    public void ShadowSet() {
        this.x = parentObj.get_x();
        this.y = parentObj.get_y() + 1;
    }
}
