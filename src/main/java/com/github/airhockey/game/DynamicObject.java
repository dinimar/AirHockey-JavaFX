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
     * Вектор скорости
     */
    protected Vector2 speed;
    /**
     * Новый вектор скорости, нужен для вычисления отталкивания
     */
    protected Vector2 newSpeed;

    abstract Double getX();
    abstract Double getY();
    abstract void setX(Double x);
    abstract void setY(Double y);

    public DynamicObject(Double mass, Vector2 speed) {
        this.mass = mass;
        this.speed = speed;
        this.newSpeed = speed;
    }

    @Override
    public void move(Double deltaT) {
        speed = speed
                .multiply(Math.pow(PhysicContext.slowdownPerTick, deltaT));
        Vector2 s = (new Vector2(getX(), getY()))
                .add(speed.multiply(deltaT));
        setX(s.getX());
        setY(s.getY());
    }
}
