package kr.co.dotsuvivor.dotsuvivor.game.UI;

import kr.co.dotsuvivor.R;
import kr.co.dotsuvivor.dotsuvivor.game.MainScene;
import kr.co.dotsuvivor.dotsuvivor.game.object.Player;
import kr.co.dotsuvivor.framework.objects.Sprite;


public class HPbar extends Sprite {
    private Player parentObj;
    private float max_Size;
    private float nowSize;
    private HPbar_out myHpbar_out;
    private HPbar_out myHpbar_out_left;
    private HPbar_out myHpbar_out_right;
    private float y_offset;

    public HPbar(int bitmapResId, float cx, float cy, float width, float height, int left, int top, int right, int bottom, Player obj) {
        super(bitmapResId, cx, cy, width, height, left, top, right, bottom);
        this.parentObj = obj;
        this.max_Size = width;
        this.y_offset= 1.6f;
        //HP바 테두리 추가
        myHpbar_out = new HPbar_out(R.mipmap.ui, 0, 0, this.width, (this.height/3)*4, 10, 76, 16, 84, parentObj, 0, y_offset);
        MainScene.add(MainScene.Layer.ui, myHpbar_out);
        myHpbar_out_left = new HPbar_out(R.mipmap.ui, 0, 0, 0.05f, this.height, 9, 77, 10, 83, parentObj, -this.width/2 - 0.025f, y_offset);
        MainScene.add(MainScene.Layer.ui, myHpbar_out_left);
        myHpbar_out_right = new HPbar_out(R.mipmap.ui, 0, 0, 0.05f, this.height, 9, 77, 10, 83, parentObj, this.width/2 + 0.025f, y_offset);
        MainScene.add(MainScene.Layer.ui, myHpbar_out_right);
    }

    public void HPbarSet(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void HPbarSet() {
        SetSize();
        this.x = parentObj.get_x() + nowSize / 2 - max_Size / 2;
        this.y = parentObj.get_y() + y_offset;
        myHpbar_out.HPbar_outSet();
        myHpbar_out_left.HPbar_outSet();
        myHpbar_out_right.HPbar_outSet();
    }

    private void SetSize() {
        if (parentObj.getMaxHP() <= 0) {
            nowSize = 0;
            width = nowSize;
            return;
        }
        if (parentObj.getNowHP() <= 0) {
            nowSize = 0;
            width = nowSize;
            return;
        }
        nowSize = max_Size * (parentObj.getNowHP() / parentObj.getMaxHP());
        width = nowSize;
    }
}
