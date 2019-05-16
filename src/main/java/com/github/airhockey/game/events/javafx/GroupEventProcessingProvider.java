/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 13.05.19 23:42.
 * airhockey
 */

/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 13.05.19 23:37.
 * airhockey
 */

package com.github.airhockey.game.events.javafx;

import com.github.airhockey.game.GameProcess;
import com.github.airhockey.game.Vector2;
import com.github.airhockey.game.events.CursorMove;
import com.github.airhockey.game.events.EventProcessingProvider;
import com.github.airhockey.game.events.GameEvent;
import javafx.scene.Group;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GroupEventProcessingProvider implements EventProcessingProvider {
    protected Group group;
    protected GameProcess process;

    @Override
    public void startEventProcessing() {
        group.setOnMouseMoved(e -> {
            process.addEvent(new CursorMove(System.nanoTime(), new Vector2(e.getX(), e.getY())));
        });
    }
}
