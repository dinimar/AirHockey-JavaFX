/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 22:00.
 * airhockey
 */

package com.github.airhockey.game;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class DynamicObject implements Collisionable, Moveable {
    /**
     * Масса
     */
    protected Double mass;
    /**
     * Вектор силы
     */
    protected Vector2 F;
    /**
     * Новый вектор силы, нужен для вычисления отталкивания
     */
    protected Vector2 newF;
}
