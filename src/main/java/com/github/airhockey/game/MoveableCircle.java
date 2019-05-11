/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 18:31.
 * airhockey
 */

package com.github.airhockey.game;

public class MoveableCircle extends Circle {
    protected Double mass;
    /**
     * Вектор силы
     */
    protected Vector2 F;

    public MoveableCircle(double x, double y, double radius, Color color, Double mass, Vector2 f) {
        super(x, y, radius, color);
        this.mass = mass;
        F = f;
    }
}
