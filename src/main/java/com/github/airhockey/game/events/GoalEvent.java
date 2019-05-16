/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 16.05.19 23:33.
 * airhockey
 */

package com.github.airhockey.game.events;

import com.github.airhockey.game.GameProcess;
import com.github.airhockey.game.Player;
import lombok.AllArgsConstructor;

public class GoalEvent extends GameEvent {
    protected Player from, to;

    public GoalEvent(Long eventTime, Player from, Player to) {
        super(eventTime);
        this.from = from;
        this.to = to;
    }

    @Override
    public void process(GameProcess p) {
        p.goal(from, to);
    }
}
