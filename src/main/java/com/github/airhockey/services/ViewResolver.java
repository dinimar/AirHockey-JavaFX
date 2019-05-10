package com.github.airhockey.services;

import com.github.airhockey.config.ViewConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class ViewResolver {
    private ApplicationContext context = new AnnotationConfigApplicationContext(ViewConfig.class);
    private HashMap<String, URL> viewMap = new HashMap<>();

    private void addView(String name, URL loader) {
        viewMap.put(name, loader);
    }

    private void removeView(String name) {
        viewMap.remove(name);
    }

    public URL getView(String name) {
        return viewMap.get(name);
    }

    public void init() {
        List<URL> layouts = (List<URL>) context.getBean("fxmlLayoutsURLs");

        for (URL layoutUrl : layouts) {
            try {
                addView(new File(layoutUrl.toURI()).getName(), layoutUrl);
            } catch (Exception ex) {
                System.err.println("FXML: Layout loading exception");
            }
        }
    }
}

