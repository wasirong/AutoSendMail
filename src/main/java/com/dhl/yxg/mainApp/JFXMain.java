package com.dhl.yxg.mainApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JFXMain extends Application {

    public static Stage stage = null;
    private static double xOffset = 0;
    private static double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        primaryStage.setTitle("自动送信 Version.1.0.0.0");
        primaryStage.setScene(new Scene(root, 850, 350));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
