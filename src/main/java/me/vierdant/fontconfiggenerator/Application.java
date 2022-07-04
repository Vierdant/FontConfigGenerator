package me.vierdant.fontconfiggenerator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    public static Stage instStage;
    // stores the directory path for the style files

    @Override
    public void start(Stage stage) throws IOException {
        // set up the scene
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 620, 540);
        //load the dark theme
        scene.getStylesheets().add("dark-theme.css");
        // set up the stage
        stage.setTitle("Font Config Generator - v1.0");
        stage.setScene(scene);
        stage.show();
        instStage = stage;
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Get the active stage for the GUI
     * @return active GUI stage
     */
    public static Stage getStage() {
        return instStage;
    }
}