/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 14.05.19 14:04.
 * airhockey
 */

package com.github.airhockey.game;

public class GamePuck extends MoveableCircle implements Moveable, Circle, Collisionable {
    public GamePuck(Vector2 pos, Double radius, Color color, Double mass, Vector2 speed) {
        super(pos, radius, color, mass, speed);
    }

    @Override
    protected void coll(GameField field) {
        if (circle.center.getX() > field.getSizeX() - getRadius()
                && circle.getY() + getRadius() < field.getSizeY() / 2 + field.getGate2().getSize() / 2
                && circle.getY() - getRadius() > field.getSizeY() / 2 - field.getGate2().getSize() / 2
        ) {
            field.getProcess().goalTo(field.getGate2().getPlayer());
            return;
        }
        else if (circle.center.getX() - getRadius() < 0
                && circle.getY() + getRadius() < field.getSizeY() / 2 + field.getGate1().getSize() / 2
                && circle.getY() - getRadius() > field.getSizeY() / 2 - field.getGate1().getSize() / 2
        ) {
            field.getProcess().goalTo(field.getGate1().getPlayer());
            return;
        }
        super.coll(field);
    }
}
