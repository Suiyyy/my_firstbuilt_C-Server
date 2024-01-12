package com.assetsms.controller;

import com.assetsms.model.AssetsType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static com.assetsms.util.AlertUtil.showWarningDialog;
import static com.assetsms.util.FXMLPage.*;
import static java.util.Objects.nonNull;
import static javafx.scene.control.Alert.AlertType.WARNING;

/**
 * 添加和编辑资产类别信息窗口的控制器类，
 * 把要操作的组件对象声明成该类的属性，
 * 属性的名称要和对应的窗口布局文件中组件标记的fx:id属性的值保持一致，
 * 并且在属性声明前添加 @FXML注解
 * @author Liu Jinyu
 */
public class AddEditAssetsType extends BaseController implements Initializable {


    @FXML
    private TextField bigTypeText;
    @FXML
    private TextField smallTypeText;

    @FXML
    private Button submitButton;

    /**
     * 提交按钮的事件处理方法，
     * 方法的名称要和对应的布局文件中提交按钮的onAction属性的值保持一致，
     * 即onAction属性应该这样赋值：onAction="#submit"
     * @param event Action事件对象
     *
     */
    @FXML
    private void submit(ActionEvent event)  {

        //如果任意字段为空，显示警告对话框
        if (bigTypeText.getText().trim().equals("")|| smallTypeText.getText().trim().equals("")) {
            //显示对话框
            showWarningDialog("要添加或更新的数据是错误的！","字段的内容不能为空！");
            return;
        }
        AssetsType assetsType=new AssetsType( bigTypeText.getText(),smallTypeText.getText());
        try {

            //如果editAssetsType不为null，提交的数据是要更新的数据，在更新之前，先删除旧数据
            //并获取要更新数据的id，用该id作为用户输入的要更新数据的id
            // nonNull是java.util.Objects类中的静态方法，已静态导入，可以直接调用
            String oldId;
            if (nonNull(editAssetsType)){
                assetsTypeData.remove(editAssetsType);
                oldId=editAssetsType.getTypeID();
                assetsType.setTypeID(oldId);
                assetsTypeDao.update(assetsType);
            }else{
                //把新的资产类别信息添加到数据源,添加成功后返回自动生成的id
                oldId=assetsTypeDao.add(assetsType);
                //把新记录的id赋给表格中新添加的对象
                assetsType.setTypeID(oldId);
            }

            //把新的资产类别信息添加到列表
            assetsTypeData.add(assetsType);

            //通过LIST_AssetsType枚举对象得到列表窗口的标题和代表布局文件路径的URL对象，
            //通过(Node)event.getSource()得到发生事件的组件对象
            //把3个对象传递给navigate方法，从而实现窗口的切换
            clearForm();
            navigate(LIST_ASSETS_TYPE.getTitle(),(Node)event.getSource(), LIST_ASSETS_TYPE.getPage());
        } catch (Exception e) {
            //发生异常后通过警告对话框显示异常信息
            showWarningDialog(e.toString());
            e.printStackTrace();
        }
    }
    //清空文本框中的内容
    private void clearForm(){
        bigTypeText.clear();
        smallTypeText.clear();
        editAssetsType=null;
    }
    //复位按钮的事件处理，清空文本框中的内容
    @FXML
    private void reset(ActionEvent event){
        clearForm();
    }
    //控制器的初始化方法，控制器对象创建后会自动执行此方法
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // 如果editAssetsType对象不为null，就在文本框中显示资产类别信息，并把提交按钮上显示的文字改为“更新”
        if (nonNull(editAssetsType)) {
            bigTypeText.setText(editAssetsType.getBigType());
            smallTypeText.setText(editAssetsType.getSmallType());
            submitButton.setText("更新");
        }
    }
}
