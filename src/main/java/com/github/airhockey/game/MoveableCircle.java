/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 18:31.
 * airhockey
 */

package com.github.airhockey.game;

public class MoveableCircle extends Circle implements Collisionable{
    /**
     * Масса
     */
    protected Double mass;
    /**
     * Вектор силы
     */
    protected Vector2 F;
    /**
     * Новый вектор силы, нужен для вычисления отталкивания
     */
    protected Vector2 newF;


    public MoveableCircle(double x, double y, double radius, Color color, Double mass, Vector2 f) {
        super(x, y, radius, color);
        this.mass = mass;
        F = f;
    }

    @Override
    public void collision(Object o) {
        if (o.getClass().equals(GameField.class)) {
            coll((GameField) o);
        }
        else if (Circle.class.isAssignableFrom(o.getClass())) {
            coll((Circle) o);
        }
    }

    protected void coll(Circle c) {
        if (c == this) {
            return;
        }
        if (c.distance(this) < 0) {
            // Расчет отталкивания от обычного шара
            System.out.println("kek");
        }
    }

    protected void coll(GameField field) {
        if (center.getX() > field.getSizeX() - getRadius()) {
            center.setX(field.getSizeX() - getRadius());
        }
        if (center.getX() - radius < 0) {
            center.setX(radius);
        }
        if (center.getY() > field.getSizeY() - getRadius()) {
            center.setY(field.getSizeY() - getRadius());
        }
        if (center.getY() - radius < 0) {
            center.setY(radius);
        }
        return;
    }
}
