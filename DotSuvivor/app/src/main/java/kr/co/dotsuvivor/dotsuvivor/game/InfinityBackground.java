package kr.co.dotsuvivor.dotsuvivor.game;

import kr.co.dotsuvivor.framework.objects.Sprite;
import kr.co.dotsuvivor.framework.interfaces.IRecyclable;

public class InfinityBackground extends Sprite implements IRecyclable{

    public InfinityBackground(int bitmapResId, float cx, float cy, float width, float height, int left, int top, int right, int bottom) {
        super(bitmapResId, cx, cy, width, height, left, top, right, bottom);
        this.width=width;
        this.height=height;
    }
    @Override
    public void update() {
        //무한맵
        if (MainScene.player.x > this.x + width) {
            this.x += width * 2;
        }
        if (MainScene.player.x < this.x - width) {
            this.x -= width * 2;
        }
        if (MainScene.player.y > this.y + height) {
            this.y += height * 2;
        }
        if (MainScene.player.y < this.y - height) {
            this.y -= height * 2;
        }
    }
    @Override
    public void onRecycle() {

    }
}
