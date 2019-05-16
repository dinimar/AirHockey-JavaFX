/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 12:46.
 * airhockey
 */

package com.github.airhockey.game.render;

public interface RenderFunc<T extends RenderProvider> {
    void render(Object object, T provider);
}
