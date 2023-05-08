package kr.co.dotsuvivor.framework.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import kr.co.dotsuvivor.framework.interfaces.IGameObject;
import kr.co.dotsuvivor.framework.res.BitmapPool;
import kr.co.dotsuvivor.framework.objects.Camera;

public class Sprite implements IGameObject {
    private static final String TAG = Sprite.class.getSimpleName();
    protected Bitmap bitmap;
    public RectF dstRect = new RectF();
    public float x;
    public float y;
    public float width;
    public float height;
    protected int top, left, bottom, right; //자르는 이미지의 왼쪽 위 오른쪽 아래 위치
    protected boolean isUI = false; //ui인지 확인하는 bool값. false라면 카메라 따라가고 아니면 화면에 고정
    protected Paint paint = new Paint(); //투명도 조절용 paint 변수
    protected float degree=0; //출력될 이미지 각도

    /**
     * @param bitmapResId 출력할 이미지의 경로
     * @param cx          이미지가 출력되는 위치 x좌표(화면 기준)
     * @param cy          이미지가 출력되는 위치 y좌표(화면 기준)
     * @param width       화면에 나올 이미지의 가로 크기(화면 기준)
     * @param height      화면에 나올 이미지의 세로 크기(화면 기준)
     * @param left        잘라서 이미지 출력할 부분의 왼쪽 위치(픽셀기준)
     * @param top         잘라서 이미지 출력할 부분의 위쪽 위치(픽셀기준)
     * @param right       잘라서 이미지 출력할 부분의 오른쪽 위치(픽셀기준)
     * @param bottom      잘라서 이미지 출력할 부분의 아래쪽 위치(픽셀기준)
     */
    public Sprite(int bitmapResId, float cx, float cy, float width, float height, int left, int top, int right, int bottom) {
        this.x = cx;
        this.y = cy;

        this.width = width;
        this.height = height;

        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;

        if (bitmapResId != 0) {
            setBitmapResource(bitmapResId);
        }
        fixDstRect();

        Log.v(TAG, "Created " + this.getClass().getSimpleName() + "@" + System.identityHashCode(this));
        paint.setAntiAlias(false);
        paint.setFilterBitmap(false);
        paint.setDither(false);
    }

    protected void setBitmapResource(int bitmapResId) {
        bitmap = BitmapPool.get(bitmapResId);
    }

    protected void fixDstRect() {
        setSize(width, height);
    }

    protected void setSize(float width, float height) {
        float half_width = width / 2;
        float half_height = height / 2;
        if (this.isUI) //ui면 카메라의 영향 안받음
        {
            //Log.d(TAG, String.valueOf(dstRect.centerX()));
            dstRect.set(x - half_width, y - half_height, x + half_width, y + half_height);
        } else { //아니면 카메라에 따라 움직임
            dstRect.set(x - width / 2 - Camera.camera_x + (float) 4.5, y - height / 2 - Camera.camera_y + (float) 8,
                    x + width / 2 - Camera.camera_x + (float) 4.5, y + height / 2 - Camera.camera_y + (float) 8);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        fixDstRect();
        Rect imageRect = new Rect(left, top, right, bottom);
        canvas.save(); // 현재 Canvas의 상태를 저장
        canvas.rotate(degree,dstRect.centerX(), dstRect.centerY()); //캔버스 회전
        canvas.drawBitmap(bitmap, imageRect, dstRect, paint);
        canvas.restore();
    }

    @Override
    public float get_x() {
        return x;
    }

    @Override
    public float get_y() {
        return y;
    }

    public void setAlpha(int alpha) {
        //0은 투명 255은 불투명
        paint.setAlpha(alpha);
    }
    public void setRotate(float angle){ //이미지 회전 함수
        degree=angle;
    }
}