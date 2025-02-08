package com.example.test;

import com.example.test.Controller.AcceptBookReturnViewController;
import com.example.test.Controller.BookLendController;
import com.example.test.Controller.BookListController;
import com.example.test.Controller.BookReturnController;
import com.example.test.utils.OnBookReturnListener;
import com.example.test.utils.OnStatisticListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NewSceneController {
    private int width;
    private int height;
    private static final int d_width = 800;
    private static final int d_height = 600;

    public NewSceneController(String fxml) throws IOException {
        this.width = d_width;
        this.height = d_height;
    }

    public NewSceneController(String fxml, int width, int height) throws IOException {
        this.width = width;
        this.height = height;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

        public NewSceneController(String fxml, int width, int height, OnBookReturnListener listener) throws IOException {
        this.width = width;
        this.height = height;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);


        AcceptBookReturnViewController acceptController = fxmlLoader.getController();
        acceptController.setOnBookReturnListener(listener);
    }

    public NewSceneController(String fxml, int width, int height, OnStatisticListener listener) throws IOException {
        this.width = width;
        this.height = height;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        if (fxml.endsWith("BooklistView.fxml")) {
            BookListController booklistcontroll = fxmlLoader.getController();
            booklistcontroll.setOnStatisticListner(listener);
        } else if (fxml.endsWith("LendbookView.fxml")) {
            BookLendController booklendcontroll = fxmlLoader.getController();
            booklendcontroll.setOnStatisticListner(listener);
        } else if (fxml.endsWith("ReturnbookView.fxml")) {
            BookReturnController bookreturncontroll = fxmlLoader.getController();
            bookreturncontroll.setOnStatisticListner(listener);
        }
    }
}
