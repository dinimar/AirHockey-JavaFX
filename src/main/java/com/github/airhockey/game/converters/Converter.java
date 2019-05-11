/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 13:11.
 * airhockey
 */

package com.github.airhockey.game.converters;

public interface Converter<F, T> {
    F convert(T o);
}
