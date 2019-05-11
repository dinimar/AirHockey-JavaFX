/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 12:47.
 * airhockey
 */

package com.github.airhockey.game.render;

public interface RenderFuncFabric<T extends RenderProvider> {
    RenderFunc<T> getRenderFunc(Class objectClass);
}
