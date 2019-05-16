/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 22:02.
 * airhockey
 */

package com.github.airhockey.game;

public interface Moveable {
    /**
     * Высчитывает новые координаты объекта
     * @param deltaT временя, прошедшее с последнего изменения
     */
    void move(Double deltaT);
}
