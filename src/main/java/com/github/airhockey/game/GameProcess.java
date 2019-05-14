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
public class GameProcess {
    protected List<DynamicObject> renderableObjects;
    protected GameField gameField;
    protected MoveableCircle puck;
    protected PlayerPuck playerPuck1, playerPuck2;
    protected Boolean isPaused;
    @Setter
    @Getter
    protected Player player1, player2, currentPlayer;
    protected Vector2D cursor;
    /**
     * игровые события, долджны быть обработыны в методе compute
     */
    protected List<GameEvent> events;
    protected Vector2 mouseLocation;
    protected Double gameSpeed;
    /**
     * время в наносекундах, когда последний раз вызвался метод compute.
     * Нужен для расчета новых местоположений объектов на основе ускорения.
     */
    protected Long prefTime;

    public GameProcess() {
        // TODO вынести хардкод
        gameField = new GameField(new Color(100, 100, 100), 800d, 500d);
        player1 = new Player(1, new Color(255, 0, 0));
        player2 = new Player(2, new Color(0, 0, 255));
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
                player1,
                40d);
        playerPuck2 = new PlayerPuck(
                0,
                gameField.getSizeY() / 2 - 20,
                player2,
                20d);
        currentPlayer = player1;
        renderableObjects = new ArrayList<>();
        renderableObjects.add(puck);
        renderableObjects.add(playerPuck1);
        renderableObjects.add(playerPuck2);
        mouseLocation = new Vector2(0d, 0d);
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
        events = new ArrayList<>();
    }

    public void start() {
        isPaused = false;
    }

    public void pause() {
        isPaused = true;
    }

    public void addEvent(GameEvent event) {
        events.add(event);
    }

    /**
     * Основные игровые вычисления
     */
    public void compute() {
        if (isPaused) {
            return;
        }

        // Обработка событий
        events.sort((x, y) -> x.getEventTime().compareTo(y.getEventTime()));
        for (GameEvent e : events) {
            e.process(this);
        }
        events.clear();

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

    public void movePlayerPuck(Vector2 newCoord, Player player) {
        if (player == currentPlayer) {
            mouseLocation = newCoord;
            return;
        }
        if (playerPuck1.getPlayer() == player) {
            playerPuck1.setY(newCoord.getX());
            playerPuck1.setX(newCoord.getX());
        }
        if (playerPuck2.getPlayer() == player) {
            playerPuck2.setY(newCoord.getX());
            playerPuck2.setX(newCoord.getX());
        }
    }
}
