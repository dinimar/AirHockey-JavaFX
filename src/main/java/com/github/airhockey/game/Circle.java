/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 22:09.
 * airhockey
 */

/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 22:09.
 * airhockey
 */

package com.github.airhockey.game;

public interface Circle {

    Double getRadius();
    Color getColor();
    Point getCenter();

    Double distance(Circle c);
}
