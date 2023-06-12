package kr.co.dotsuvivor.dotsuvivor.game.object;

import kr.co.dotsuvivor.dotsuvivor.game.scene.MainScene;
import kr.co.dotsuvivor.framework.objects.Sprite;
import kr.co.dotsuvivor.framework.interfaces.IRecyclable;

public class InfinityBackground extends Sprite implements IRecyclable{
    private Player game_player;

    public InfinityBackground(Player player, int bitmapResId, float cx, float cy, float width, float height, int left, int top, int right, int bottom) {
        super(bitmapResId, cx, cy, width, height, left, top, right, bottom);
        this.width=width;
        this.height=height;
        this.game_player=player;
    }
    @Override
    public void update() {
        //무한맵
        if (game_player.get_x() > this.x + width) {
            this.x += width * 2;
        }
        if (game_player.get_x() < this.x - width) {
            this.x -= width * 2;
        }
        if (game_player.get_y() > this.y + height) {
            this.y += height * 2;
        }
        if (game_player.get_y() < this.y - height) {
            this.y -= height * 2;
        }
    }
    @Override
    public void onRecycle() {

    }
}
