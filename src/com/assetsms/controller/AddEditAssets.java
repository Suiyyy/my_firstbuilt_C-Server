package com.assetsms.controller;

import com.assetsms.model.Assets;
import com.assetsms.model.AssetsType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.assetsms.util.AlertUtil.showWarningDialog;
import static com.assetsms.util.FXMLPage.LIST_ASSETS;
import static java.util.Objects.nonNull;

public class AddEditAssets extends BaseController implements Initializable {
    @FXML
    private TextField assetsIDText;
    @FXML
    private TextField nameText;
    @FXML
    private ChoiceBox<String> typeIDText1;
    @FXML
    private ChoiceBox<String> typeIDText2;
    @FXML
    private TextField modelText;
    @FXML
    private TextField priceText;
    @FXML
    private TextField buyDateText;
    @FXML
    private ChoiceBox<String> statusText;
    @FXML
    private TextField otherText;

    @FXML
    private Button submitButton;

    AssetsType selectAssetsType = null;

    /**
     * 提交按钮的事件处理方法，
     * 方法的名称要和对应的布局文件中提交按钮的onAction属性的值保持一致，
     * 即onAction属性应该这样赋值：onAction="#submit"
     * @param event Action事件对象
     *
     */
    @FXML
    private void submit(ActionEvent event)  {
        /**********日期类型的变量**************************/
        java.sql.Date date = null;
        try {
            date = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(buyDateText.getText()).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /****************************************/

        //如果任意字段为空，显示警告对话框
        if ( nameText.getText().trim().equals("") || modelText.getText().trim().equals("")
                || priceText.getText().trim().equals("")|| buyDateText.getText().trim().equals("")) {
            //显示对话框
            showWarningDialog("要添加或更新的数据是错误的！","字段的内容不能为空！");
            return;
        }

        /**************************/
        if (date == null) {
            showWarningDialog("要添加或更新的数据是错误的！", "日期格式错误！");
            return;
        }

        if (selectAssetsType == null){
            showWarningDialog("要添加或更新的数据是错误的！", "字段的内容不能为空！");
            return;
        }
        /***************************************/

        Assets assets=new Assets(nameText.getText(),selectAssetsType.getTypeID(),modelText.getText(),
                priceText.getText(),date,statusText.getValue(),otherText.getText());
        try {
            String oldId;
            if (nonNull(editAssets)){
                assetsData.remove(editAssets);
                oldId=editAssets.getAssetsID();
                assets.setAssetsID(oldId);
                assetsDao.update(assets);
            }else{

                oldId=assetsDao.add(assets);

                assets.setAssetsID(oldId);
            }


            assetsData.add(assets);

            clearForm();
            navigate(LIST_ASSETS.getTitle(),(Node)event.getSource(), LIST_ASSETS.getPage());
        } catch (Exception e) {
            //发生异常后通过警告对话框显示异常信息
            showWarningDialog(e.toString());
            e.printStackTrace();
        }
    }

    //清空文本框中的内容
    private void clearForm(){
        nameText.clear();
        modelText.clear();
        priceText.clear();
        buyDateText.clear();
        otherText.clear();
    }
    //复位按钮的事件处理，清空文本框中的内容
    @FXML
    private void reset(ActionEvent event){
        clearForm();
    }
    //控制器的初始化方法，控制器对象创建后会自动执行此方法
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showChoiceBox();
        statusText.getItems().addAll("可用","领用","报废");
        statusText.getSelectionModel().selectFirst();//默认第一个选项
        statusText.setDisable(true);//禁能

        buyDateText.setText(new java.sql.Date(new java.util.Date().getTime()).toString());

        // 如果editAssetsType对象不为null，就在文本框中显示资产类别信息，并把提交按钮上显示的文字改为“更新”
        if (nonNull(editAssets)) {
            assetsIDText.setText(editAssets.getAssetsID());
            nameText.setText(editAssets.getName());
            modelText.setText(editAssets.getModel());
            priceText.setText(editAssets.getPrice());
            buyDateText.setText(editAssets.getBuyDate().toString());
            statusText.setValue(editAssets.getStatus());
            otherText.setText(editAssets.getOther());
            submitButton.setText("更新");
        }
    }
    /**********AddAssets大小类别的下拉框与ListAssetsType表联系起来****************/
    public void showChoiceBox() {
        List<AssetsType> assetsTypeList = new ArrayList<>();

        Set<String> bigType = new HashSet<>();
        for (AssetsType assetsType : assetsTypeData) {
            bigType.add(assetsType.getBigType());
        }

        typeIDText1.getItems().addAll(bigType);

        typeIDText1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                typeIDText2.getItems().clear();

                for (AssetsType assetsType : assetsTypeData) {
                    if (newValue.equals(assetsType.getBigType())) {
                        assetsTypeList.add(assetsType);
                        typeIDText2.getItems().add(assetsType.getSmallType());
                    }
                }
            }
        });

        typeIDText2.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                selectAssetsType = assetsTypeList.get(newValue.intValue());
            }
        });
    }
    /****************************************************/
}
