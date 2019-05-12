/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 12.05.19 11:09.
 * airhockey
 */

package com.github.airhockey.game;

public class PlayerPuck extends MoveableCircle implements Circle, Collisionable, Moveable {

    @Override
    public void move(Double deltaT) {
        return;
    }

    public PlayerPuck(double x, double y, double radius, Color color, Double mass, Vector2 f) {
        super(x, y, radius, color, mass, f);
    }

    public void computeSpeedAndCoord(Vector2 mouseLocation, Double deltaT) {
        speed = (new Vector2(mouseLocation.getX() - getX(), mouseLocation.getY() - getY())).multiply(1 / deltaT);
        setX(mouseLocation.getX());
        setY(mouseLocation.getY());
    }
}
