/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 15:53.
 * airhockey
 */

package com.github.airhockey.game.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

@Setter
@Getter
@AllArgsConstructor
public class CursorMove extends GameEvent {
    protected Vector2D newCoord;
}
