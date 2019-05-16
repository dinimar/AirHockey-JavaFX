/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 22:00.
 * airhockey
 */

package com.github.airhockey.game;

import com.github.airhockey.game.events.DynamicObjectMove;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class DynamicObject implements Collisionable, Moveable, GameMapObject {
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
    protected GameProcess gameProcess;

    public DynamicObject(Double mass, Vector2 speed, GameProcess p) {
        this.mass = mass;
        this.speed = speed;
        this.newSpeed = speed;
        this.gameProcess = p;
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
