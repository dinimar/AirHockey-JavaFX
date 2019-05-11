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
import com.github.airhockey.game.converters.ColorToJavaFXColorConverter;
import com.github.airhockey.game.render.RenderFuncFabric;
import com.github.airhockey.game.render.RenderProvider;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

@Getter
public class GroupRenderProvider implements RenderProvider {

    protected Group group;
    private RenderFuncFabric<GroupRenderProvider> funcFabric;

    public GroupRenderProvider(Group group) {
        this.group = group;
    }

    @Override
    public void render(Object o) {
        funcFabric.getRenderFunc(o.getClass()).render(o, this);
    }

    @Override
    public boolean supports(Object o) {
        return o.getClass().equals(Circle.class);
    }

    @Override
    public void setUp(GameProccess proccess) {
        group.setLayoutX(proccess.getGameField().getSizeX());
        group.setLayoutY(proccess.getGameField().getSizeY());
        group.getChildren().add(new Rectangle(
                proccess.getGameField().getSizeX(),
                proccess.getGameField().getSizeY(),
                (new ColorToJavaFXColorConverter()).convert(proccess.getGameField().getColor())
                ));
    }
}
