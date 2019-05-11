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
import com.github.airhockey.game.MoveableCircle;
import com.github.airhockey.game.Vector2;
import com.github.airhockey.game.converters.ColorToJavaFXColorConverter;
import com.github.airhockey.game.render.RenderFunc;
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
            MoveableCircle c = proccess.getPlayerPuck1();
            Vector2 newPos = new Vector2(e.getX(), e.getY());
            c.getCenter().moveTo(newPos);
            c.collision(proccess.getGameField());
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
        if (List.class.isAssignableFrom(o.getClass())) {
            render((List) o);
            return;
        }
        RenderFunc f = funcFabric.getRenderFunc(o.getClass());
        if (f == null) {
            return;
        }
        f.render(o, this);
    }

    public boolean supports(Object o) {
        return Circle.class.isAssignableFrom(o.getClass());
    }

    public void setUp() {
        group.getChildren().add(new Rectangle(
                proccess.getGameField().getSizeX(),
                proccess.getGameField().getSizeY(),
                (new ColorToJavaFXColorConverter()).convert(proccess.getGameField().getColor())
        ));
    }
}
