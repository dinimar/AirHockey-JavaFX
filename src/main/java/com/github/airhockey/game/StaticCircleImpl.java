/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 11:59.
 * airhockey
 */

package com.github.airhockey.game;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StaticCircleImpl implements Circle {
    protected Double radius;
    protected Color color;
    protected Vector2 center;

    public StaticCircleImpl(Vector2 pos, Double radius, Color color) {
        center = pos;
        this.radius = radius;
        this.color = color;
    }

    public Double distance(Circle c) {
        return c.getCenter().distance(center) - radius - c.getRadius();
    }

    public Double getRadius() {
        return radius;
    }

    public Color getColor() {
        return color;
    }

    public Vector2 getCenter() {
        return center;
    }

    @Override
    public Double getX() {
        return center.getX();
    }

    @Override
    public Double getY() {
        return center.getY();
    }

    @Override
    public void setX(Double x) {
        center.setX(x);
    }

    @Override
    public void setY(Double y) {
        center.setY(y);
    }
}
