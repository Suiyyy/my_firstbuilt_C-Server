package com.assetsms.controller;

import com.assetsms.model.AssetsType;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.assetsms.util.AlertUtil.showConfirmDialog;
import static com.assetsms.util.AlertUtil.showWarningDialog;
import static com.assetsms.util.FXMLPage.*;
import static java.util.Objects.nonNull;
import static javafx.scene.control.ButtonType.OK;

public class ListAssetsType extends com.assetsms.controller.BaseController implements Initializable {
    @FXML
    private TableView<AssetsType> assetsTypeListTable;
    @FXML
    private TextField searchText;
    @FXML
    private TableColumn typeIDColumn;
    @FXML
    private TableColumn bigTypeColumn;
    @FXML
    private TableColumn smallTypeColumn;

    // 删除按钮事件处理
    @FXML
    private void  doDelete(ActionEvent event){
        //显示确认对话框
        Optional<ButtonType> result =  showConfirmDialog();
        if (result.get().equals(OK)) {
            AssetsType AssetsType=getItem();
            assetsTypeData.remove(AssetsType);
            try {
                assetsTypeDao.delete(AssetsType.getTypeID());
            } catch (Exception e) {
                //发生异常后通过警告对话框显示异常信息
                showWarningDialog(e.toString());
                e.printStackTrace();
            }
        }
    }
    //更新按钮事件处理
    @FXML
    private void  doUpdate(ActionEvent event){
        editAssetsType = null;
        editAssetsType = getItem();
        if (nonNull(editAssetsType)) {
            try {
                navigate(UPDATE_ASSETS_TYPE.getTitle(),(Node)event.getSource(), UPDATE_ASSETS_TYPE.getPage());
            } catch (IOException e) {
                //发生异常后通过警告对话框显示异常信息
                showWarningDialog(e.toString());
                e.printStackTrace();
            }
        }
    }
    //控制器的初始化方法，控制器对象创建后会自动执行此方法
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // 设置和列表每一列想关联的AssetsType对象的属性
        typeIDColumn.setCellValueFactory(new PropertyValueFactory<>("typeID"));
        bigTypeColumn.setCellValueFactory(new PropertyValueFactory<>("bigType"));
        smallTypeColumn.setCellValueFactory(new PropertyValueFactory<>("smallType"));

        //把AssetsTypeData中满足条件的数据添加到列表中
        FilteredList<AssetsType> filteredData = new FilteredList<>(assetsTypeData, n -> true);
        /*
         * 将KeyReleased操作附加到searchText字段，
         * 以便在键入时过滤当前附加到表格视图的条目。
         */
        searchText.setOnKeyReleased(e -> {
            filteredData.setPredicate(n -> {

                if (searchText.getText() == null || searchText.getText().isEmpty()) {
                    return true;
                }

                return n.getTypeID().toString().contains(searchText.getText())
                        || n.getSmallType().contains(searchText.getText())|| n.getBigType().contains(searchText.getText());
            });
        });

        assetsTypeListTable.setItems(filteredData);
    }
    // 得到列表上选择的资产类别信息
    private AssetsType getItem() {
        return AssetsType.class.cast(assetsTypeListTable.getSelectionModel().getSelectedItem());
    }
}
