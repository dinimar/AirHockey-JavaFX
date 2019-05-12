/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 18:03.
 * airhockey
 */

package com.github.airhockey.game;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Vector2 {
    protected Double x, y;

    public Vector2(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 add(Vector2 v) {
        return new Vector2(x + v.x, y + v.y);
    }

    public Double distance(Vector2 v) {
        return Math.sqrt((x - v.getX()) * (x - v.getX()) + (y - v.getY()) * (y - v.getY()));
    }

    public void moveTo(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
    }

    public Vector2 multiply(Double a) {
        return new Vector2(x * a, y * a);
    }

    public Double angle() {
        return Math.atan2(y, x);
    }

    public Vector2 negate() {
        return new Vector2(-x, -y);
    }

    public Double mod() {
        return Math.sqrt(x*x + y * y);
    }
}
