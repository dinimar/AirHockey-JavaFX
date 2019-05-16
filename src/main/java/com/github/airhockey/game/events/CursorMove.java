/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 15:53.
 * airhockey
 */

package com.github.airhockey.game.events;

import com.github.airhockey.game.GameProcess;
import com.github.airhockey.game.Player;
import com.github.airhockey.game.Vector2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CursorMove extends GameEvent {
    protected Vector2 newCoord;
    protected Player player;

    public CursorMove(Long eventTime, Vector2 newCoord, Player player) {
        super(eventTime);
        this.newCoord = newCoord;
        this.player = player;
    }

    public CursorMove(Long eventTime, Vector2 newCoord) {
        super(eventTime);
        this.newCoord = newCoord;
    }

    @Override
    public void process(GameProcess p) {
        p.movePlayerPuck(newCoord, player == null ? p.getCurrentPlayer() : player);
    }
}
