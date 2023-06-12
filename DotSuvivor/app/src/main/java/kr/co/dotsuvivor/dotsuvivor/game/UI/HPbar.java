package kr.co.dotsuvivor.dotsuvivor.game.UI;

import kr.co.dotsuvivor.R;
import kr.co.dotsuvivor.dotsuvivor.game.scene.MainScene;
import kr.co.dotsuvivor.dotsuvivor.game.object.Player;
import kr.co.dotsuvivor.framework.objects.Sprite;
import kr.co.dotsuvivor.framework.scene.BaseScene;


public class HPbar extends Sprite {
    private Player parentObj;
    private float max_Size;
    private float nowSize;
    private HPbar_out myHpbar_out;
    private HPbar_out myHpbar_out_up;
    private HPbar_out myHpbar_out_down;
    private HPbar_out myHpbar_out_left;
    private HPbar_out myHpbar_out_right;
    //HP 위치 조절
    private float x_offset;
    private float y_offset;
    private float up_height;
    private float down_height;
    private float left_width;
    private float right_width;

    public HPbar(MainScene mainscene, int bitmapResId, float cx, float cy, float width, float height, int left, int top, int right, int bottom, Player obj) {
        super(bitmapResId, cx, cy, width, height, left, top, right, bottom);
        this.parentObj = obj;
        this.max_Size = width;
        this.x_offset = 0;
        this.y_offset = 1.6f;

        this.up_height = 0.1f;
        this.down_height = 0.1f;
        this.left_width = 0.1f;
        this.right_width = 0.1f;

        //HP바 테두리 추가
        myHpbar_out = new HPbar_out(R.mipmap.ui, 0, 0, this.width, this.height,
                10, 77, 16, 83, parentObj, x_offset, y_offset);
        mainscene.add(MainScene.Layer.ui, myHpbar_out);

        myHpbar_out_up = new HPbar_out(R.mipmap.ui, 0, 0, this.width, up_height,
                10, 76, 16, 77, parentObj, x_offset, y_offset + this.height / 2 + up_height / 2);
        mainscene.add(MainScene.Layer.ui, myHpbar_out_up);

        myHpbar_out_down = new HPbar_out(R.mipmap.ui, 0, 0, this.width, down_height,
                10, 76, 16, 77, parentObj, x_offset, y_offset - this.height / 2 - up_height / 2);
        mainscene.add(MainScene.Layer.ui, myHpbar_out_down);

        myHpbar_out_left = new HPbar_out(R.mipmap.ui, 0, 0, left_width, this.height,
                9, 77, 10, 83, parentObj, x_offset - this.width / 2 - left_width / 2, y_offset);
        mainscene.add(MainScene.Layer.ui, myHpbar_out_left);

        myHpbar_out_right = new HPbar_out(R.mipmap.ui, 0, 0, right_width, this.height,
                9, 77, 10, 83, parentObj, x_offset + this.width / 2 + right_width / 2, y_offset);
        mainscene.add(MainScene.Layer.ui, myHpbar_out_right);
    }

    public void HPbarSet(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void HPbarSet() {
        SetHPSize();
        this.x = parentObj.get_x() + nowSize / 2 - max_Size / 2;
        this.y = parentObj.get_y() + y_offset;
        myHpbar_out.HPbar_outSet();
        myHpbar_out_up.HPbar_outSet();
        myHpbar_out_down.HPbar_outSet();
        myHpbar_out_left.HPbar_outSet();
        myHpbar_out_right.HPbar_outSet();
    }

    private void SetHPSize() {
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
