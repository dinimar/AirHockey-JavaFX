/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 12:19.
 * airhockey
 */

package com.github.airhockey.game;

import com.github.airhockey.game.events.CursorMove;
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
    protected List<Object> renderableObject;
    protected GameField gameField;
    protected Circle puck, playerPuck1, playerPuck2;
    protected Boolean isPaused;
    protected Player player1, player2;
    protected Vector2D cursor;
    protected List<GameEvent> events;
    /**
     * время в наносекундах, когда последний раз вызвался метод compute.
     * Нужен для расчета новых местоположений объектов на основе ускорения.
     */
    protected Long prefTime;

    public GameProccess() {
        // TODO вынести хардкод
        gameField = new GameField(new Color(100, 100, 100), 800d, 500d);
        puck = new Circle(gameField.getSizeX() / 2 - 20, gameField.getSizeY() / 2 - 20, 20, new Color(255, 255, 255));
        playerPuck1 = new Circle(gameField.getSizeX() - 40, gameField.getSizeY() / 2 - 20, 20, new Color(0, 255, 0));
        playerPuck2 = new Circle(0, gameField.getSizeY() / 2 - 20, 20, new Color(255, 0, 0));
        renderableObject = new ArrayList<>();
        renderableObject.add(puck);
        renderableObject.add(playerPuck1);
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
        prefTime = time;
    }
}