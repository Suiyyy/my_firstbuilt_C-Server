package com.assetsms.controller;

import com.assetsms.model.Assets;
import com.assetsms.model.AssetsTrjn;
import com.assetsms.model.AssetsType;
import com.assetsms.model.Person;
import com.assetsms.util.DBUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.assetsms.util.AlertUtil.showWarningDialog;
import static com.assetsms.util.FXMLPage.LIST_ASSETSBACK;
import static com.assetsms.util.FXMLPage.LIST_ASSETSUSING;
import static java.util.Objects.nonNull;
/*为了使用数据库*/
import com.assetsms.dao.AssetsDaoMySQLImpl;
import javafx.scene.control.cell.PropertyValueFactory;

public class AssetsUsing extends BaseController implements Initializable {

    /******需要显示可用资产信息用于选择****************/
    public TableColumn nameColumn;
    public TableColumn typeIDColumn;
    public TableColumn modelColumn;
    public TableColumn priceColumn;
    public TableColumn buyDateColumn;
    public TableColumn statusColumn;
    public TableColumn assetsIDColumn;
    public TableColumn otherColumn;
    @FXML
    private TableView<Assets> assetsListTable;

    Assets selectAssets = null;//外键用findID作为主键，但这里需要找到名字显示在框里
    Person selectPerson = null;
    /************************/

    @FXML
    private TextField jourNoText;
    @FXML
    private TextField fromAccText;
    @FXML
    private TextField assetsText;
    @FXML
    private TextField regDateText;
    @FXML
    private ChoiceBox<String> personText;
    @FXML
    private TextField purposeText;
    @FXML
    private TextField otherText;
    @FXML
    private Button submitButton;

    @FXML
    private void submit(ActionEvent event) {
        java.sql.Date date = null;
        try {
            date = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(regDateText.getText()).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //如果任意字段为空，显示警告对话框
        if (purposeText.getText().trim().equals("") || regDateText.getText().trim().equals("")) {
            //显示对话框
            showWarningDialog("要添加或更新的数据是错误的！", "字段的内容不能为空！");
            return;
        }

        /**************************/
        if (date == null) {
            showWarningDialog("要添加或更新的数据是错误的！", "日期格式错误！");
            return;
        }
        /***************************************/

        //得到窗口中输入的客户信息
        AssetsTrjn assetsTrjn = new AssetsTrjn("领用",selectAssets.getAssetsID(), date,
                selectPerson.getId(), purposeText.getText(), otherText.getText());

        /******为外键对应assets和person对象进行初始化************/
        try {
            assetsTrjn.setAssets(assetsDao.findById(assetsTrjn.getAssets().getAssetsID()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            assetsTrjn.setPerson(personDao.findById(assetsTrjn.getPerson().getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        /******************/

        try {
            String jourNo;
            if (nonNull(editAssetsTrjn)) {
                assetsTrjnData.remove(editAssetsTrjn);
                jourNo = editAssetsTrjn.getJourNo();
                assetsTrjn.setJourNo(jourNo);
                assetsTrjnDao.update(assetsTrjn);
            } else {
                jourNo = assetsTrjnDao.add(assetsTrjn);

                assetsTrjn.setJourNo(jourNo);

                /*****操作设为领用,并把assests表里对应项的状态改为领用******/
                Assets assets = getItem();
                assetsData.remove(assets);
                assets.setStatus("领用");
                //更新状态到数据库
                assetsDao.update(assets);
                assetsData.add(assets);

                //通过LIST_AssetsType枚举对象得到列表窗口的标题和代表布局文件路径的URL对象，
                //通过(Node)event.getSource()得到发生事件的组件对象
                //把3个对象传递给navigate方法，从而实现窗口的切换
                clearForm();
                /*******************************************/


            }


            assetsTrjnData.add(assetsTrjn);

            clearForm();
            navigate(LIST_ASSETSBACK.getTitle(), (Node) event.getSource(), LIST_ASSETSBACK.getPage());
        } catch (Exception e) {
            showWarningDialog(e.toString());
            e.printStackTrace();
        }
    }

    //清空文本框中的内容
    private void clearForm() {
        regDateText.clear();
        purposeText.clear();
        otherText.clear();
    }

    //复位按钮的事件处理，清空文本框中的内容
    @FXML
    private void reset(ActionEvent event) {
        clearForm();
    }

    //控制器的初始化方法，控制器对象创建后会自动执行此方法
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showComboData();

        /***************************设置和列表每一列想关联的AssetsType对象的属性******************************************/
        assetsIDColumn.setCellValueFactory(new PropertyValueFactory<>("AssetsID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        typeIDColumn.setCellValueFactory(new PropertyValueFactory<>("TypeID"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("Model"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        buyDateColumn.setCellValueFactory(new PropertyValueFactory<>("BuyDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("Status"));
        /***************************************************************************************/

        // 如果editPerson对象不为null，就在文本框中显示客户信息，并把提交按钮上显示的文字改为“更新”
        if (nonNull(editAssetsTrjn)) {
            assetsText.setText(editAssetsTrjn.getAssets().getName());
            regDateText.setText(editAssetsTrjn.getRegDate().toString());
            personText.setValue(editAssetsTrjn.getPerson().getName());
            purposeText.setText(editAssetsTrjn.getPurpose());
            otherText.setText(editAssetsTrjn.getOther());
            submitButton.setText("更新");
        }
    }

    /*************************从ListAssets粘贴过来的方法****************************/
    // 得到列表上选择的资产类别信息
    private Assets getItem() {
        return Assets.class.cast(assetsListTable.getSelectionModel().getSelectedItem());
    }
    /***************************************************/

    /**********Assets和Person的下拉框与ListAssets表及ListPerson联系起来****************/

    private void showComboData() {
        regDateText.setText(new java.sql.Date(new java.util.Date().getTime()).toString());
        regDateText.setStyle("-fx-font-size: 16;");
        //把AssetsData中满足条件的数据添加到列表中,初始化Assets表项和表中的内容，状态项必须为’可用‘
        FilteredList<Assets> filteredData = new FilteredList<>(assetsData, n -> true);

        filteredData.setPredicate(n -> n.getStatus().contains("可用"));

        assetsListTable.setItems(filteredData);

        assetsListTable.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Assets>() {
                    @Override
                    public void changed(ObservableValue<? extends Assets> observable, Assets oldValue, Assets newValue) {
                        assetsText.setText(newValue.getAssetsID() + "-" + newValue.getName());
                        selectAssets = newValue;
                    }
                });

        for (Person person : personData) {
            personText.getItems().add(person.getId() + "-" + person.getName());
        }

        personText.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov,
                                Number value, Number new_value) {
                selectPerson = personData.get(new_value.intValue());

            }
        });
        /*******************************************************************************/
    }
}
