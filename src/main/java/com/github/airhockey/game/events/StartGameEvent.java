/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 17.05.19 12:11.
 * airhockey
 */

package com.github.airhockey.game.events;

import com.github.airhockey.game.GameProcess;
import lombok.Getter;

public class StartGameEvent extends GameEvent {
    @Getter
    protected Long gameId;

    public StartGameEvent(Long eventTime, Long gameId) {
        super(eventTime);
        this.gameId = gameId;
    }

    @Override
    public void process(GameProcess p) {
        return;
    }
}
