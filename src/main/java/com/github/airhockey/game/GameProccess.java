/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 12:19.
 * airhockey
 */

package com.github.airhockey.game;

import com.github.airhockey.game.events.GameEvent;
import lombok.Getter;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.List;

// TODO add logger

/**
 * Сама реализация игры.
 * Все координаты объектов считаются от нижнего левого угла.
 */
@Getter
public class GameProccess {
    protected List<DynamicObject> renderableObjects;
    protected GameField gameField;
    protected MoveableCircle puck, playerPuck1, playerPuck2;
    protected Boolean isPaused;
    protected Player player1, player2;
    protected Vector2D cursor;
    protected List<GameEvent> events;
    protected Double gameSpeed;
    /**
     * время в наносекундах, когда последний раз вызвался метод compute.
     * Нужен для расчета новых местоположений объектов на основе ускорения.
     */
    protected Long prefTime;

    public GameProccess() {
        // TODO вынести хардкод
        gameField = new GameField(new Color(100, 100, 100), 800d, 500d);
        puck = new MoveableCircle(
                gameField.getSizeX() / 2 - 20,
                gameField.getSizeY() / 2 - 20,
                20,
                new Color(255, 255, 255),
                20d,
                new Vector2(7d, 7d));
        playerPuck1 = new MoveableCircle(
                gameField.getSizeX() - 40,
                gameField.getSizeY() / 2 - 20,
                20, new Color(0, 255, 0),
                20d,
                new Vector2(0d, 0d));
        playerPuck2 = new MoveableCircle(
                0,
                gameField.getSizeY() / 2 - 20,
                20,
                new Color(255, 0, 0),
                20d,
                new Vector2(0d, 0d));
        renderableObjects = new ArrayList<>();
        renderableObjects.add(puck);
//        renderableObjects.add(playerPuck1);
        isPaused = false;
        prefTime = System.nanoTime();
        gameSpeed = 1d;
    }

    public void start() {
        isPaused = false;
    }

    public void pause() {
        isPaused = true;
    }

    /**
     * Основные игровые вычисления
     */
    public void compute() {
        if (isPaused) {
            return;
        }

        // обработка движения
        Long time = System.nanoTime();
        Long delta = time - prefTime;
        for (DynamicObject o : renderableObjects) {
            o.move(delta / PhysicContext.tick * gameSpeed);
        }

        // обработка столкновений
        for (Collisionable o : renderableObjects) {
            for (Collisionable o2 : renderableObjects) {
                o.collision(o2);
            }
        }
        for (Collisionable o : renderableObjects) {
            o.collision(gameField);
        }
        for (DynamicObject o : renderableObjects) {
            o.setSpeed(o.getNewSpeed());
        }

        prefTime = time;
    }
}
