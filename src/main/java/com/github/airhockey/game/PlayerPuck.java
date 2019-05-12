/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 12.05.19 11:09.
 * airhockey
 */

package com.github.airhockey.game;

public class PlayerPuck extends MoveableCircle {

    @Override
    public void move(Double deltaT) {
        return;
    }

    public PlayerPuck(double x, double y, double radius, Color color, Double mass, Vector2 f) {
        super(x, y, radius, color, mass, f);
    }

    public void computeSpeedAndCoord(Double newX, Double newY, Double deltaT) {
        speed = (new Vector2(newX - getX(), newY - getY())).multiply(1 / deltaT);
        setX(newX);
        setY(newY);
    }
}
