/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 18:31.
 * airhockey
 */

package com.github.airhockey.game;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MoveableCircle extends DynamicObject implements Collisionable, Circle{
    protected StaticCircleImpl circle;

    public MoveableCircle(double x, double y, double radius, Color color, Double mass, Vector2 f) {
        circle = new StaticCircleImpl(x, y, radius, color);
        this.mass = mass;
        F = f;
        newF = new Vector2(0d, 0d);
    }

    @Override
    public void collision(Object o) {
        if (o.getClass().equals(GameField.class)) {
            coll((GameField) o);
        }
        else if (MoveableCircle.class.isAssignableFrom(o.getClass())) {
            coll((MoveableCircle) o);
        }
    }

    protected void coll(MoveableCircle c) {
        if (c == this) {
            return;
        }
        if (c.distance(this) < 0) {
            // Расчет отталкивания от обычного шара
            System.out.println("kek");
        }
    }

    protected void coll(GameField field) {
        if (circle.center.getX() > field.getSizeX() - getRadius()) {
            circle.center.setX(field.getSizeX() - getRadius());
            newF.setX(-F.getX());
            newF.setY(F.getY());
        }
        if (circle.center.getX() - getRadius() < 0) {
            circle.center.setX(getRadius());
            newF.setX(-F.getX());
            newF.setY(F.getY());
        }
        if (circle.center.getY() > field.getSizeY() - getRadius()) {
            circle.center.setY(field.getSizeY() - getRadius());
            newF.setX(F.getX());
            newF.setY(-F.getY());
        }
        if (circle.center.getY() - getRadius() < 0) {
            circle.center.setY(getRadius());
            newF.setX(F.getX());
            newF.setY(-F.getY());
        }
        return;
    }

    @Override
    public Double getRadius() {
        return circle.getRadius();
    }

    @Override
    public Color getColor() {
        return circle.getColor();
    }

    @Override
    public Point getCenter() {
        return circle.getCenter();
    }

    @Override
    public Double distance(Circle c) {
        return circle.distance(c);
    }

    @Override
    public void move() {

    }
}
