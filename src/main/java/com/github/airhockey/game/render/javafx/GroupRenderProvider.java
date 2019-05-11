/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 12:37.
 * airhockey
 */

/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 12:33.
 * airhockey
 */

package com.github.airhockey.game.render.javafx;

import com.github.airhockey.game.Circle;
import com.github.airhockey.game.GameProccess;
import com.github.airhockey.game.Vector2;
import com.github.airhockey.game.converters.ColorToJavaFXColorConverter;
import com.github.airhockey.game.render.RenderFuncFabric;
import com.github.airhockey.game.render.RenderProvider;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class GroupRenderProvider implements RenderProvider {

    protected Group group;
    private RenderFuncFabric<GroupRenderProvider> funcFabric = new RenderFuncFabricImpl();
    protected Double offsetX, offsetY;
    protected GameProccess proccess;
    protected Map<Object, Node> objects = new HashMap<>();

    public GroupRenderProvider(Group group, GameProccess gameProccess) {
        this.group = group;
        this.proccess = gameProccess;
        group.setLayoutX(proccess.getGameField().getSizeX());
        group.setLayoutY(proccess.getGameField().getSizeY());
        setUp();
        offsetX = group.getChildren().get(0).getLayoutX();
        offsetY = group.getChildren().get(0).getLayoutY();

        // TODO вынести работу с событиями в EventProvider
        group.setOnMouseMoved(e -> {
            Circle c = proccess.getPlayerPuck1();
            Vector2 newPos = new Vector2(e.getX() - c.getRadius(), e.getY() - c.getRadius());
            if (newPos.getX() > gameProccess.getGameField().getSizeX() - c.getRadius() * 2) {
                newPos.setX(gameProccess.getGameField().getSizeX() - c.getRadius() * 2);
            }
            if (newPos.getX() < 0) {
                newPos.setX(0d);
            }
            if (newPos.getY() > gameProccess.getGameField().getSizeY() - c.getRadius() * 2) {
                newPos.setY(gameProccess.getGameField().getSizeY() - c.getRadius() * 2);
            }
            if (newPos.getY() < 0) {
                newPos.setY(0d);
            }
            c.moveTo(newPos);
        });
    }

    @Override
    public void render(List<Object> objects) {
        for (Object o : objects) {
            if (supports(o)) {
                render(o);
            }
        }
    }

    public void render(Object o) {
        funcFabric.getRenderFunc(o.getClass()).render(o, this);
    }

    public boolean supports(Object o) {
        return o.getClass().equals(Circle.class);
    }

    public void setUp() {
        group.getChildren().add(new Rectangle(
                proccess.getGameField().getSizeX(),
                proccess.getGameField().getSizeY(),
                (new ColorToJavaFXColorConverter()).convert(proccess.getGameField().getColor())
        ));
    }
}
