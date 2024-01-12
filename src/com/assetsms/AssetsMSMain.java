package com.assetsms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static com.assetsms.util.FXMLPage.ASSETS_MAIN;

public class AssetsMSMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(ASSETS_MAIN.getPage());
        primaryStage.setTitle("资产管理系统");
        Scene scene=new Scene(root, 1500, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
}
