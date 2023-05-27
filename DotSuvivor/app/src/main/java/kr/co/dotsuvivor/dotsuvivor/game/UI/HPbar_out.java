package kr.co.dotsuvivor.dotsuvivor.game.UI;

import kr.co.dotsuvivor.framework.objects.Sprite;



public class HPbar_out extends Sprite {
    private Sprite parentObj;
    private float offset_x;
    private float offset_y;

    public HPbar_out(int bitmapResId, float cx, float cy, float width, float height, int left, int top, int right, int bottom, Sprite obj, float offset_x,float offset_y) {
        super(bitmapResId, cx, cy, width, height, left, top, right, bottom);
        this.parentObj = obj;
        this.offset_x = offset_x;
        this.offset_y = offset_y;
    }
    public void HPbar_outSet() {
        this.x = parentObj.get_x() + offset_x;
        this.y = parentObj.get_y() + offset_y;
    }
}
