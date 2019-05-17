/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 12:19.
 * airhockey
 */

package com.github.airhockey.game;

import com.github.airhockey.game.events.DynamicObjectMove;
import com.github.airhockey.game.events.GameEvent;
import com.github.airhockey.game.events.StartGameEvent;
import lombok.Getter;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// TODO add logger

/**
 * Сама реализация игры.
 * Все координаты объектов считаются от нижнего левого угла.
 */
@Component
@Getter
public class GameProcess {
    protected List<DynamicObject> dynamicObjects;
    protected MoveableCircle puck;
    protected PlayerPuck playerPuck1, playerPuck2;
    protected Boolean isPaused;
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
    protected Long gameId;

    protected Vector2 player1StartPos,
            player2StartPos,
            gamePuckStartPos;
    protected GameField gameField;

    public GameProcess() {
        // TODO вынести хардкод
        player1 = new Player(1, new Color(255, 0, 0), "RED", 0l);
        player2 = new Player(2, new Color(0, 0, 255), "GREEN", 0l);
        gameField = new GameField(
                new Color(100, 100, 100), 800d, 500d,
                new Gate(player2, 200d),
                new Gate(player1, 200d),
                this);
        player1StartPos = new Vector2(
                gameField.getSizeX() - 40,
                gameField.getSizeY() / 2 - 20
        );
        player2StartPos = new Vector2(
                0d,
                gameField.getSizeY() / 2 - 20
        );
        gamePuckStartPos = new Vector2(
                gameField.getSizeX() / 2 - 20,
                gameField.getSizeY() / 2 - 20);
        puck = new GamePuck(
                gamePuckStartPos,
                this,
                20d,
                new Color(255, 255, 255),
                20d,
                new Vector2(0d, 0d));
        playerPuck1 = new PlayerPuck(
                player1StartPos,
                this,
                player1,
                40d);
        playerPuck2 = new PlayerPuck(
                player2StartPos,
                this,
                player2,
                40d);
        dynamicObjects = new ArrayList<>();
        dynamicObjects.add(puck);
        dynamicObjects.add(playerPuck1);
        dynamicObjects.add(playerPuck2);
        mouseLocation = new Vector2(0d, 0d);
        // For test
//        dynamicObjects.add(new MoveableCircle(
//                        gameField.getSizeX() / 2 - 20 - 140,
//                        gameField.getSizeY() / 2 - 20,
//                        20,
//                        new Color(255, 255, 255),
//                        20d,
//                        new Vector2(4d, 4d)));
//        dynamicObjects.add(new MoveableCircle(
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
        gameId = System.nanoTime();
    }

    public void start() {
        isPaused = false;
        events.add(new StartGameEvent(System.nanoTime(), gameId));
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
        StartGameEvent myStart = null, anotherStart = null;
        for (GameEvent e : events) {
            e.process(this);
            if (e.getClass().equals(StartGameEvent.class)) {
                StartGameEvent startGameEvent = (StartGameEvent) e;
                if (myStart == null && startGameEvent.getGameId().equals(gameId)) {
                    myStart = startGameEvent;
                }
                if (anotherStart == null && !startGameEvent.getGameId().equals(gameId)) {
                    anotherStart = startGameEvent;
                }
            }
        }
        if (myStart.getEventTime() < anotherStart.getEventTime()) {
            currentPlayer = player1;
        }
        else {
            currentPlayer = player2;
        }
        events.clear();

        Long time = System.nanoTime();
        Long delta = time - prefTime;
        // Обработка движения шайбы игрока
        playerPuck1.computeSpeedAndCoord(mouseLocation, delta / PhysicContext.tick * gameSpeed);
        // обработка движения
        for (DynamicObject o : dynamicObjects) {
            o.move(delta / PhysicContext.tick * gameSpeed);
        }

        // обработка столкновений
        for (Collisionable o : dynamicObjects) {
            for (Collisionable o2 : dynamicObjects) {
                o.collision(o2);
            }
        }
        for (Collisionable o : dynamicObjects) {
            o.collision(gameField);
        }
        for (DynamicObject o : dynamicObjects) {
            o.setSpeed(o.getNewSpeed());
        }

        for (int i = 0; i < dynamicObjects.size(); i++) {
            DynamicObject o = dynamicObjects.get(i);
            events.add(new DynamicObjectMove(
                    System.nanoTime(),
                    i,
                    new Vector2(o.getX(), o.getY()),
                    o.getSpeed()
            ));
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

    public void goal(Player from, Player to) {
        if (from != to) {
            if (from == null) {
                from = (to == player1 ? player2 : player1);
            }
            from.setScore(from.getScore() + 1);
        }
        System.out.println("GOAL!!");
        refresh();
    }

    public void refresh() {
        events.clear();
        playerPuck1.setX(player1StartPos.getX());
        playerPuck1.setY(player1StartPos.getY());
        playerPuck2.setX(player2StartPos.getX());
        playerPuck2.setY(player2StartPos.getY());
        puck.setX(gamePuckStartPos.getX());
        puck.setY(gamePuckStartPos.getY());
    }

    public List<Object> getRenderableObjects() {
        Object game = this;
        return new ArrayList<Object>(dynamicObjects){{ add(game); }};
    }
}
