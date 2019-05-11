/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 12:33.
 * airhockey
 */

package com.github.airhockey.game.render;

import com.github.airhockey.game.GameProccess;

import java.util.List;

/**
 * То, что позволяет отображать объекты куда-то
 */
public interface RenderProvider {
    default void render(List<Object> objects){
        for (Object o : objects) {
            if (supports(o)) {
                render(o);
            }
        }
    }
    void render(Object o);
    boolean supports(Object o);
    void setUp(GameProccess proccess);
}
