/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 12:19.
 * airhockey
 */

package com.github.airhockey.game;

import com.github.airhockey.game.events.GameEvent;
import lombok.Getter;
import lombok.Setter;
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
    protected MoveableCircle puck;
    protected PlayerPuck playerPuck1, playerPuck2;
    protected Boolean isPaused;
    protected Player player1, player2;
    protected Vector2D cursor;
    protected List<GameEvent> events;
    @Setter
    protected Vector2 mouseLocation;
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
                new Vector2(0d, 0d));
        playerPuck1 = new PlayerPuck(
                gameField.getSizeX() - 40,
                gameField.getSizeY() / 2 - 20,
                20, new Color(0, 255, 0),
                40d,
                new Vector2(0d, 0d));
        playerPuck2 = new PlayerPuck(
                0,
                gameField.getSizeY() / 2 - 20,
                40,
                new Color(255, 0, 0),
                20d,
                new Vector2(0d, 0d));
        renderableObjects = new ArrayList<>();
        renderableObjects.add(puck);
        renderableObjects.add(playerPuck1);
        renderableObjects.add(playerPuck2);

        // For test
//        renderableObjects.add(new MoveableCircle(
//                        gameField.getSizeX() / 2 - 20 - 140,
//                        gameField.getSizeY() / 2 - 20,
//                        20,
//                        new Color(255, 255, 255),
//                        20d,
//                        new Vector2(4d, 4d)));
//        renderableObjects.add(new MoveableCircle(
//                gameField.getSizeX() / 2 - 20 + 140,
//                gameField.getSizeY() / 2 - 20,
//                30,
//                new Color(255, 255, 125),
//                60d,
//                new Vector2(-4d, 4d)));

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

        Long time = System.nanoTime();
        Long delta = time - prefTime;
        // Обработка движения шайбы игрока
        playerPuck1.computeSpeedAndCoord(mouseLocation, delta / PhysicContext.tick * gameSpeed);
        // обработка движения
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
