/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 13:12.
 * airhockey
 */

package com.github.airhockey.game.converters;

import javafx.scene.paint.Color;

public class ColorToJavaFXColorConverter implements Converter<Color, com.github.airhockey.game.Color> {
    @Override
    public Color convert(com.github.airhockey.game.Color o) {
        return new Color(o.getRed() / 255.0, o.getGreen() / 255.0, o.getBlue() / 255.0, o.getTransparency() / 100.0);
    }
}
