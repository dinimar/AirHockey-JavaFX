package com.github.airhockey.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ViewConfig {
    @Bean
    public List<URL> fxmlLayoutsURLs() {
        List<URL> layouts = new ArrayList<>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL layoutDir = loader.getResource("views");

        File[] layoutsFiles;
        layoutsFiles = new File(layoutDir.getPath()).listFiles();

        for (File file : layoutsFiles) {
            try {
                layouts.add(file.toURI().toURL());
            } catch (MalformedURLException ex) {
                System.err.println("FXML: Invalid layout URI");
            }
        }

        return layouts;
    }
}
