/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 12:37.
 * airhockey
 */

/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 12:33.
 * airhockey
 */

package com.github.airhockey.game.render.javafx;

import com.github.airhockey.game.*;
import com.github.airhockey.game.converters.ColorToJavaFXColorConverter;
import com.github.airhockey.game.render.RenderFunc;
import com.github.airhockey.game.render.RenderFuncFabric;
import com.github.airhockey.game.render.RenderProvider;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class GroupRenderProvider implements RenderProvider {

    protected Group group;
    private RenderFuncFabric<GroupRenderProvider> funcFabric = new RenderFuncFabricImpl();
    protected Double offsetX, offsetY;
    protected GameProcess proccess;
    protected Map<Object, Node> objects = new HashMap<>();
    protected final Double gateSizeX = 10d;

    public GroupRenderProvider(Group group, GameProcess gameProcess) {
        this.group = group;
        this.proccess = gameProcess;
        group.setLayoutX(proccess.getGameField().getSizeX() + gateSizeX * 2);
        group.setLayoutY(proccess.getGameField().getSizeY());
        setUp();
        offsetX = group.getChildren().get(0).getLayoutX();
        offsetY = group.getChildren().get(0).getLayoutY();
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
        return funcFabric.getRenderFunc(o.getClass()) != null;
    }

    public void setUp() {
        GameField gf = proccess.getGameField();
        group.getChildren().add(new Rectangle(
                getGateSizeX(),
                0,
                gf.getSizeX(),
                gf.getSizeY()
        ){{ setFill((new ColorToJavaFXColorConverter()).convert(gf.getColor())); }});
        group.getChildren().add(new Rectangle(
                0,
                gf.getSizeY() / 2 - gf.getGate1().getSize() / 2,
                gateSizeX,
                gf.getGate1().getSize()
        ){{ setFill((new ColorToJavaFXColorConverter()).convert(gf.getGate1().getPlayer().getColor())); }});
        group.getChildren().add(new Rectangle(
                gf.getSizeX() + getGateSizeX(),
                gf.getSizeY() / 2 - gf.getGate2().getSize() / 2,
                gateSizeX,
                gf.getGate2().getSize()
        ){{ setFill((new ColorToJavaFXColorConverter()).convert(gf.getGate2().getPlayer().getColor())); }});
    }
}
