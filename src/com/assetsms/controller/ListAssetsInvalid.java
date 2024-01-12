package com.assetsms.controller;

import com.assetsms.controller.BaseController;
import com.assetsms.model.AssetsTrjn;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.assetsms.util.AlertUtil.showWarningDialog;
import static com.assetsms.util.FXMLPage.UPDATE_ASSETSINVALID;
import static java.util.Objects.nonNull;

public class ListAssetsInvalid extends BaseController implements Initializable {
    @FXML
    private TableView<AssetsTrjn> assetsInvalidListTable;
    @FXML
    private TextField searchText;
    @FXML
    private TableColumn jourNoColumn;
    @FXML
    private TableColumn fromAccColumn;
    @FXML
    private TableColumn assetsIDColumn;
    @FXML
    private TableColumn regDateColumn;
    @FXML
    private TableColumn personIDColumn;
    @FXML
    private TableColumn purposeColumn;
    @FXML
    private TableColumn otherColumn;

    //更新按钮事件处理
    @FXML
    private void  doUpdate(ActionEvent event){
        editAssetsTrjn  = null;
        editAssetsTrjn  = getItem();
        if (nonNull(editAssetsTrjn )) {
            try {
                navigate(UPDATE_ASSETSINVALID.getTitle(),(Node)event.getSource(), UPDATE_ASSETSINVALID.getPage());
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

        jourNoColumn.setCellValueFactory(new PropertyValueFactory<>("jourNo"));
        fromAccColumn.setCellValueFactory(new PropertyValueFactory<>("fromAcc"));
        assetsIDColumn.setCellValueFactory(new PropertyValueFactory<>("assetsID"));
        regDateColumn.setCellValueFactory(new PropertyValueFactory<>("regDate"));
        personIDColumn.setCellValueFactory(new PropertyValueFactory<>("personID"));
        purposeColumn.setCellValueFactory(new PropertyValueFactory<>("purpose"));
        otherColumn.setCellValueFactory(new PropertyValueFactory<>("other"));


        //把assetsTrjnData中满足条件操作为归还的数据记录添加到列表中
        FilteredList<AssetsTrjn> filteredData = new FilteredList<>(assetsTrjnData, n -> n.getFromAcc().contains("废弃"));
        //FilteredList<AssetsTrjn> filteredData = new FilteredList<>(assetsTrjnData, n -> true);//显示的是全部数据记录

        /*
         * 将KeyReleased操作附加到searchText字段，
         * 以便在键入时过滤当前附加到表格视图的条目。
         */
        searchText.setOnKeyReleased(e -> {
            filteredData.setPredicate(n -> {

                if (searchText.getText() == null || searchText.getText().isEmpty()) {
                    return true;
                }

                return  n.getJourNo().contains(searchText.getText())|| n.getFromAcc().contains(searchText.getText())
                        || n.getAssets().getAssetsID().contains(searchText.getText())|| n.getRegDate().toString().contains(searchText.getText())
                        || n.getPerson().getId().contains(searchText.getText())|| n.getPurpose().contains(searchText.getText())
                        || n.getOther().contains(searchText.getText());
            });
        });

        assetsInvalidListTable.setItems(filteredData);
    }
    // 得到列表上选择的客户信息
    private AssetsTrjn getItem() {
        return AssetsTrjn.class.cast(assetsInvalidListTable.getSelectionModel().getSelectedItem());
    }
}
