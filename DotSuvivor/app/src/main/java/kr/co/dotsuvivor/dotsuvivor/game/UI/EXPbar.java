package kr.co.dotsuvivor.dotsuvivor.game.UI;

import kr.co.dotsuvivor.R;
import kr.co.dotsuvivor.dotsuvivor.game.object.Player;
import kr.co.dotsuvivor.dotsuvivor.game.scene.MainScene;
import kr.co.dotsuvivor.framework.objects.Sprite;
import kr.co.dotsuvivor.framework.scene.BaseScene;

public class EXPbar extends Sprite {
    private Player parentObj;

    private EXPbar_out myEXPbar_out;
    private EXPbar_out myEXPbar_out_up;
    private EXPbar_out myEXPbar_out_down;
    private EXPbar_out myEXPbar_out_left;
    private EXPbar_out myEXPbar_out_right;
    //HP 위치 조절
    private float up_height;
    private float down_height;
    private float left_width;
    private float right_width;
    private float max_x;
    private float max_y;

    private float maxWidthSize;
    private float nowWidthSize;
    private float max_height;


    public EXPbar(MainScene mainscene,Player obj) {
        super(R.mipmap.ui, 4.5f, 0.6f, 8.8f, 1, 39, 77, 54, 92);
        parentObj=obj;
        this.isUI = true;


        this.up_height = 0.1f;
        this.down_height = 0.1f;
        this.left_width = 0.1f;
        this.right_width = 0.1f;

        max_x = 4.5f;
        max_y = 0.6f;
        nowWidthSize=0;
        maxWidthSize = 8.8f;
        max_height = 1;

        //EXP바 테두리 추가
        myEXPbar_out = new EXPbar_out(R.mipmap.ui, max_x, max_y, this.width, this.height,
                10, 77, 16, 83, this);
        mainscene.add(MainScene.Layer.ui, myEXPbar_out);

        myEXPbar_out_up = new EXPbar_out(R.mipmap.ui, max_x, max_y + max_height / 2 + this.up_height / 2, maxWidthSize, up_height,
                10, 76, 16, 77, this);
        mainscene.add(MainScene.Layer.ui, myEXPbar_out_up);

        myEXPbar_out_down = new EXPbar_out(R.mipmap.ui, max_x, max_y - max_height / 2 - this.down_height / 2, maxWidthSize, down_height,
                10, 76, 16, 77, this);
        mainscene.add(MainScene.Layer.ui, myEXPbar_out_down);

        myEXPbar_out_left = new EXPbar_out(R.mipmap.ui, max_x - maxWidthSize / 2 - this.left_width/2, max_y, this.left_width, max_height,
                9, 77, 10, 83, this);
        mainscene.add(MainScene.Layer.ui, myEXPbar_out_left);
        myEXPbar_out_right = new EXPbar_out(R.mipmap.ui, max_x + maxWidthSize / 2 + this.right_width/2, max_y, this.right_width, max_height,
                9, 77, 10, 83, this);
        mainscene.add(MainScene.Layer.ui, myEXPbar_out_right);
    }
    public void EXPbarSet() {
        SetEXPSize();
        this.x = max_x + nowWidthSize / 2 - maxWidthSize / 2;
        this.y = max_y;
    }
    private void SetEXPSize() {
        if (parentObj.getMaxEXP() <= 0) {
            nowWidthSize = 0;
            width = nowWidthSize;
            return;
        }
        if (parentObj.getNowEXP() <= 0) {
            nowWidthSize = 0;
            width = nowWidthSize;
            return;
        }
        if (parentObj.getNowEXP() >= parentObj.getMaxEXP()) {
            nowWidthSize = maxWidthSize;
            width = nowWidthSize;
            return;
        }
        nowWidthSize = maxWidthSize * (parentObj.getNowEXP() / parentObj.getMaxEXP());
        width = nowWidthSize;
    }
}
