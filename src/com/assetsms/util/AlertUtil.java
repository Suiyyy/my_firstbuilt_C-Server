package com.assetsms.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

import static javafx.scene.control.Alert.AlertType.CONFIRMATION;
import static javafx.scene.control.Alert.AlertType.WARNING;

public class AlertUtil {
    public static Optional<ButtonType> showConfirmDialog(){
        Alert alert = new Alert(CONFIRMATION);
        alert.setTitle("确认对话框");
        alert.setHeaderText(" 删除数据确认");
        alert.setContentText("您确认要删除这条信息吗？");
       return alert.showAndWait();
    }

    public static void showWarningDialog(String title,String content){
        Alert alert = new Alert(WARNING);
        alert.setTitle("警告对话框");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public static void showWarningDialog(String content){
        showWarningDialog("程序运行出错",content);
    }
}
