/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 16.05.19 23:51.
 * airhockey
 */

package com.github.airhockey.game.events;

import com.github.airhockey.game.DynamicObject;
import com.github.airhockey.game.GameMapObject;
import com.github.airhockey.game.GameProcess;
import com.github.airhockey.game.Vector2;

public class DynamicObjectMove extends GameEvent {

    protected int objectId;
    protected Vector2 newCoord;
    protected Vector2 newSpeed;

    public DynamicObjectMove(Long eventTime, int objectId, Vector2 newCoord, Vector2 newSpeed) {
        super(eventTime);
        this.objectId = objectId;
        this.newCoord = newCoord;
        this.newSpeed = newSpeed;
    }

    @Override
    public void process(GameProcess p) {
        DynamicObject object = p.getDynamicObjects().get(objectId);
        object.setX(newCoord.getX());
        object.setY(newCoord.getY());
        object.setSpeed(newSpeed);
    }
}
