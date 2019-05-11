/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 12:19.
 * airhockey
 */

package com.github.airhockey.game;

import lombok.Getter;

// TODO add logger
@Getter
public class GameProccess {
    protected GameField gameField;
    protected Circle puck, playerPuck1, playerPuck2;
    protected Boolean isPaused;
    /**
     * время в наносекундах, когда последний раз вызвался метод compute.
     * Нужен для расчета новых местоположений объектов на основе ускорения.
     */
    protected Long prefTime;

    public GameProccess() {
        // TODO вынести хардкод
        gameField = new GameField(new Color(100, 100, 100), 800d, 500d);
        puck = new Circle(gameField.getSizeX() / 2, gameField.getSizeY() / 2, 20, new Color(255, 255, 255));
        playerPuck1 = new Circle(gameField.getSizeX() - 20, gameField.getSizeY() - 20, 20, new Color(255, 255, 255));
        playerPuck2 = new Circle(20, 20, 20, new Color(255, 255, 255));
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
        Long time = System.nanoTime();

        prefTime = time;
    }
}
