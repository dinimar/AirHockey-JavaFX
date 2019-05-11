/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 18:54.
 * airhockey
 */

package com.github.airhockey.game;

/**
 * Предназначен для интерфейсов игровых объектов, которые могут сталкиваться
 */
public interface Collisionable {
    void collision(Object o);
}
