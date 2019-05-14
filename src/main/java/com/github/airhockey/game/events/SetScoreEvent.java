/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 14.05.19 14:50.
 * airhockey
 */

package com.github.airhockey.game.events;

import com.github.airhockey.game.GameProcess;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class SetScoreEvent extends GameEvent {

    protected Long score1, score2;

    public SetScoreEvent(Long eventTime, Long score1, Long score2) {
        super(eventTime);
        this.score1 = score1;
        this.score2 = score2;
    }

    @Override
    public void process(GameProcess p) {
        p.getPlayer1().setScore(score1);
        p.getPlayer2().setScore(score2);
    }
}
