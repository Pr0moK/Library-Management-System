package com.example.test;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NewScene {
    private int width;
    private int height;
    private static final int d_width = 800;
    private static final int d_height = 600;

    public NewScene(String fxml, ActionEvent event) throws IOException {
        this.width = d_width;
        this.height = d_height;
    }

    public NewScene(String fxml, ActionEvent event, int width, int height) throws IOException {
        this.width = width;
        this.height = height;


        FXMLLoader fxmlLoader;

        fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
