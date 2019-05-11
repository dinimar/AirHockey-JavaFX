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
    protected Point center;

    public StaticCircleImpl(double x, double y, double radius, Color color) {
        center = new Point(x, y);
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

    public Point getCenter() {
        return center;
    }
}
