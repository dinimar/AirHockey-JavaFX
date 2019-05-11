/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 12:51.
 * airhockey
 */

package com.github.airhockey.game.render.javafx;

import com.github.airhockey.game.Circle;
import com.github.airhockey.game.converters.ColorToJavaFXColorConverter;
import com.github.airhockey.game.render.RenderFunc;
import javafx.collections.ObservableList;
import javafx.scene.Node;
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
        ObservableList<Node> nodes = provider.group.getChildren();
        if (provider.objects.containsKey(circle)) {
            Node node = provider.objects.get(circle);
            javafx.scene.shape.Circle c = (javafx.scene.shape.Circle) node;
            c.setRadius(circle.getRadius());
            c.setCenterX(circle.getCenter().getX() + provider.getOffsetX());
            c.setCenterY(circle.getCenter().getY() + provider.getOffsetY());
        }
        else {
            synchronized (nodes) {
                nodes.add(new javafx.scene.shape.Circle(
                        circle.getCenter().getX() + provider.getOffsetX(),
                        circle.getCenter().getY() + provider.getOffsetY(),
                        circle.getRadius(),
                        (new ColorToJavaFXColorConverter()).convert(circle.getColor())));
                Node node = nodes.get(nodes.size() - 1);
                provider.objects.put(circle, node);
            }
        }
    }
}
