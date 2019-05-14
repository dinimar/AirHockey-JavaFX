/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 15:42.
 * airhockey
 */

package com.github.airhockey.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Player {
    protected Integer id;
    protected Color color;
}
