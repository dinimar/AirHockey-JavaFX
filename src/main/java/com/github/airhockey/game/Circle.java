/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 11:59.
 * airhockey
 */

package com.github.airhockey.game;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Circle extends Point {
    protected Double radius;
    protected Color color;

    public Circle(double x, double y, double radius, Color color) {
        super(x, y);
        this.radius = radius;
        this.color = color;
    }
}
