/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 12:21.
 * airhockey
 */

package com.github.airhockey.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GameField {
    protected Color color;
    protected Double sizeX, sizeY;
    protected Gate gate1, gate2;
    protected GameProcess process;
}
