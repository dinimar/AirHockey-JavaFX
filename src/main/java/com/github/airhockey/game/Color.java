/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 12:15.
 * airhockey
 */

package com.github.airhockey.game;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Color {

    protected Integer red, green, blue;
    /**
     * 100 - максимальная непрозрачность
     * 0 - максимальная прозрачность
     */
    protected Integer transparency;

    public Color(Integer red, Integer green, Integer blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        transparency = 100;
    }

    public Color(Integer red, Integer green, Integer blue, Integer transparency) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.transparency = transparency;
    }
}
