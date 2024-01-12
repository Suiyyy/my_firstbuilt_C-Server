package com.assetsms.controller;

import com.assetsms.model.Assets;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.assetsms.util.AlertUtil.showConfirmDialog;
import static com.assetsms.util.AlertUtil.showWarningDialog;
import static com.assetsms.util.FXMLPage.UPDATE_ASSETS;
import static java.util.Objects.nonNull;
import static javafx.scene.control.ButtonType.OK;

public class ListAssets extends BaseController implements Initializable {
    @FXML
    private TableView<Assets> assetsListTable;
    @FXML
    private TextField searchText;
    @FXML
    private TableColumn assetsIDColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn typeIDColumn;
    @FXML
    private TableColumn modelColumn;
    @FXML
    private TableColumn priceColumn;
    @FXML
    private TableColumn buyDateColumn;
    @FXML
    private TableColumn statusColumn;
    @FXML
    private TableColumn otherColumn;
    
    // 删除按钮事件处理
    @FXML
    private void  doDelete(ActionEvent event){
        //显示确认对话框框
        Optional<ButtonType> result = showConfirmDialog();
        if (result.get().equals(OK)) {
            Assets assets=getItem();
            try {
                assetsDao.delete(assets.getAssetsID());
                assetsData.remove(assets);
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
        editAssets = null;
        editAssets = getItem();
        if (nonNull(editAssets)) {
            try {
                navigate(UPDATE_ASSETS.getTitle(),(Node)event.getSource(), UPDATE_ASSETS.getPage());
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

        assetsIDColumn.setCellValueFactory(new PropertyValueFactory<>("assetsID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeIDColumn.setCellValueFactory(new PropertyValueFactory<>("typeID"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        buyDateColumn.setCellValueFactory(new PropertyValueFactory<>("buyDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        otherColumn.setCellValueFactory(new PropertyValueFactory<>("other"));


        //把AssetsData中满足条件的数据添加到列表中
        FilteredList<Assets> filteredData = new FilteredList<>(assetsData, n -> true);
        /*
         * 将KeyReleased操作附加到searchText字段，
         * 以便在键入时过滤当前附加到表格视图的条目。
         */
        searchText.setOnKeyReleased(e -> {
            filteredData.setPredicate(n -> {

                if (searchText.getText() == null || searchText.getText().isEmpty()) {
                    return true;
                }

                return n.getAssetsID().contains(searchText.getText())
                        || n.getName().contains(searchText.getText())|| n.getAssetsType().getTypeID().contains(searchText.getText())
                        || n.getModel().contains(searchText.getText())|| n.getPrice().contains(searchText.getText())
                        || n.getBuyDate().toString().contains(searchText.getText())|| n.getStatus().contains(searchText.getText())
                        || n.getOther().contains(searchText.getText());
            });
        });

        assetsListTable.setItems(filteredData);
    }
    // 得到列表上选择的客户信息
    private Assets getItem() {
        return Assets.class.cast(assetsListTable.getSelectionModel().getSelectedItem());
    }
}
