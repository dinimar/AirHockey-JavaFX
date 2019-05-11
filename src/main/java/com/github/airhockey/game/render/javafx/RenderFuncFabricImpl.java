/*
 * Developed by Boris Kozhuhovskiy (borisgk98@ya.ru) on 11.05.19 12:49.
 * airhockey
 */

package com.github.airhockey.game.render.javafx;

import com.github.airhockey.game.Circle;
import com.github.airhockey.game.render.RenderFunc;
import com.github.airhockey.game.render.RenderFuncFabric;
import com.github.airhockey.game.render.RenderProvider;

import java.util.HashMap;
import java.util.Map;

public class RenderFuncFabricImpl implements RenderFuncFabric<GroupRenderProvider> {

    private Map<Class, RenderFunc<GroupRenderProvider>> m;

    public RenderFuncFabricImpl() {
        m = new HashMap<>();
        m.put(Circle.class, new CircleRenderFunc());
    }

    @Override
    public RenderFunc<GroupRenderProvider> getRenderFunc(Class objectClass) {
        return m.get(objectClass);
    }
}
