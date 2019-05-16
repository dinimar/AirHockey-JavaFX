/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 12.05.19 11:09.
 * airhockey
 */

package com.github.airhockey.game;

import lombok.Getter;

public class PlayerPuck extends MoveableCircle implements Circle, Collisionable, Moveable {
    @Getter
    protected Player player;

    @Override
    public void move(Double deltaT) {
        return;
    }

    public PlayerPuck(Vector2 pos, GameProcess p, Player player, Double mass) {
        // TODO вынести хардкод
        super(pos, p, 30d, player.getColor(), mass, new Vector2(0d, 0d));
    }

    public void computeSpeedAndCoord(Vector2 mouseLocation, Double deltaT) {
        speed = (new Vector2(mouseLocation.getX() - getX(), mouseLocation.getY() - getY())).multiply(1 / deltaT);
        setX(mouseLocation.getX());
        setY(mouseLocation.getY());
    }
}
