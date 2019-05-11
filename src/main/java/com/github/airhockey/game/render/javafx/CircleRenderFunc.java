/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 12:51.
 * airhockey
 */

package com.github.airhockey.game.render.javafx;

import com.github.airhockey.game.Circle;
import com.github.airhockey.game.converters.ColorToJavaFXColorConverter;
import com.github.airhockey.game.render.RenderFunc;
import javafx.scene.paint.Color;

public class CircleRenderFunc implements RenderFunc<GroupRenderProvider> {
    @Override
    public void render(Object object, GroupRenderProvider provider) {
        Circle circle = null;
        try {
            circle = (Circle) object;
        }
        catch (ClassCastException e) {
            // TODO Обработка исключений
            return;
        }
        provider.getGroup().getChildren().add(new javafx.scene.shape.Circle(
                circle.getRadius(),
                circle.getX(),
                circle.getY(),
                (new ColorToJavaFXColorConverter()).convert(circle.getColor()))
        );
    }
}
