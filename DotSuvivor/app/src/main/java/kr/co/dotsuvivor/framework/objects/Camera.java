package kr.co.dotsuvivor.framework.objects;

public class Camera {
    public static float camera_x;
    public static float camera_y;
    private void set(float x, float y) {
        camera_x = x;
        camera_y = y;
    }
    private float get_x() {
        return camera_x;
    }
    private float get_y() {
        return camera_y;
    }
}
