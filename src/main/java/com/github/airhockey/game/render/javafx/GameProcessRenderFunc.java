/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 14.05.19 14:58.
 * airhockey
 */

package com.github.airhockey.game.render.javafx;

import com.github.airhockey.game.Circle;
import com.github.airhockey.game.GameProcess;
import com.github.airhockey.game.render.RenderFunc;

public class GameProcessRenderFunc implements RenderFunc<GroupRenderProvider> {
    @Override
    public void render(Object object, GroupRenderProvider provider) {
        GameProcess p = null;
        try {
            p = (GameProcess) object;
        }
        catch (ClassCastException e) {
            // TODO Обработка исключений
            return;
        }
        provider.setScore(p.getPlayer1().getScore(), p.getPlayer2().getScore());
    }
}
