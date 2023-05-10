package kr.co.dotsuvivor.framework.util;

public class Calculate {
    public static double getDistance(float x1, float y1, float x2, float y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
    public static double getAngle(float startX, float startY, float endX, float endY) {
        double deltaX = endX - startX;
        double deltaY = endY - startY;
        return Math.atan2(deltaY, deltaX);
    }
    public static double RadianToDegree(float radian)
    {
        return (radian * 180) / Math.PI;
    }
    public static double DegreeToRadian(float degree)
    {
        return (degree * Math.PI) / 180;
    }
}
