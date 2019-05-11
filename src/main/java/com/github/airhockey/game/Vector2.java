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

    public void add(Vector2 v) {
        x += v.x;
        y += v.y;
    }
}
