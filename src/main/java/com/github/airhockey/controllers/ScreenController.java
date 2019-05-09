package com.github.airhockey.controllers;

import com.github.airhockey.config.ViewConfig;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ScreenController {
    private ApplicationContext context = new AnnotationConfigApplicationContext(ViewConfig.class);
    private HashMap<String, Pane> screenMap = new HashMap<>();
    private Scene main;

    public ScreenController() {
    }

    public ScreenController(Scene main) {
        this.main = main;
    }

    protected void addScreen(String name, Pane pane) {
        screenMap.put(name, pane);
    }

    protected void removeScreen(String name) {
        screenMap.remove(name);
    }

    protected void activate(String name) {
        main.setRoot(screenMap.get(name));
    }

    protected void init() {
        List<URL> layouts = (List<URL>) context.getBean("fxmlLayoutsURLs");

        for (URL layoutUrl : layouts) {
            try {
                addScreen(new File(layoutUrl.toURI()).getName(), FXMLLoader.load(layoutUrl));
            } catch (Exception ex) {
                System.err.println("FXML: Layout loading exception");
            }
        }
    }


    public HashMap<String, Pane> getScreenMap() {
        return screenMap;
    }

    public void setScreenMap(HashMap<String, Pane> screenMap) {
        this.screenMap = screenMap;
    }

    public Scene getMain() {
        return main;
    }

    public void setMain(Scene main) {
        this.main = main;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScreenController that = (ScreenController) o;
        return Objects.equals(screenMap, that.screenMap) &&
                Objects.equals(main, that.main);
    }

    @Override
    public int hashCode() {
        return Objects.hash(screenMap, main);
    }
}
