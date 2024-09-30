package com.example.photoshopfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PhotoShopFX extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PhotoShopFX.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle(("PhotoShopFX"));
        stage.setMaximized(true);
        MainController controller = fxmlLoader.getController();
        controller.onFechar(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}