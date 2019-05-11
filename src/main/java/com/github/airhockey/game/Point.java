/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 12:05.
 * airhockey
 */

/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 12:03.
 * airhockey
 */

package com.github.airhockey.game;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

@Setter
@Getter
public class Point extends Vector2 {
    public Point(double x, double y) {
        super(x, y);
    }

    public void moveTo(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
    }
}
