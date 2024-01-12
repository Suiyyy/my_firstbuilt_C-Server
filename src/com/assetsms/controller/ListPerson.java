package com.assetsms.controller;

import com.assetsms.model.Person;
import com.assetsms.util.AlertUtil;
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
import static com.assetsms.util.FXMLPage.ADD_PERSON;
import static com.assetsms.util.FXMLPage.UPDATE_PERSON;
import static java.util.Objects.nonNull;
import static javafx.scene.control.Alert.AlertType.CONFIRMATION;
import static javafx.scene.control.Alert.AlertType.WARNING;
import static javafx.scene.control.ButtonType.OK;

public class ListPerson extends BaseController implements Initializable {
    @FXML
    private TableView<Person> personListTable;
    @FXML
    private TextField searchText;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn sexColumn;
    @FXML
    private TableColumn deptColumn;
    @FXML
    private TableColumn jobColumn;
    @FXML
    private TableColumn otherColumn;
    // 删除按钮事件处理
    @FXML
    private void  doDelete(ActionEvent event){
        //显示确认对话框框
        Optional<ButtonType> result = showConfirmDialog();
        if (result.get().equals(OK)) {
            Person person=getItem();
            try {
                personDao.delete(person.getId());
                personData.remove(person);
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
        editPerson = null;
        editPerson = getItem();
        if (nonNull(editPerson)) {
            try {
                navigate(UPDATE_PERSON.getTitle(),(Node)event.getSource(), UPDATE_PERSON.getPage());
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

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        sexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));
        deptColumn.setCellValueFactory(new PropertyValueFactory<>("dept"));
        jobColumn.setCellValueFactory(new PropertyValueFactory<>("job"));
        otherColumn.setCellValueFactory(new PropertyValueFactory<>("other"));


        //把personData中满足条件的数据添加到列表中
        FilteredList<Person> filteredData = new FilteredList<>(personData, n -> true);
        /*
         * 将KeyReleased操作附加到searchText字段，
         * 以便在键入时过滤当前附加到表格视图的条目。
         */
        searchText.setOnKeyReleased(e -> {
            filteredData.setPredicate(n -> {

                if (searchText.getText() == null || searchText.getText().isEmpty()) {
                    return true;
                }

                return n.getId().contains(searchText.getText())|| n.getName().contains(searchText.getText())
                        || n.getSex().contains(searchText.getText())|| n.getDept().contains(searchText.getText())
                        || n.getJob().contains(searchText.getText())|| n.getOther().contains(searchText.getText());
            });
        });

        personListTable.setItems(filteredData);
    }
    // 得到列表上选择的客户信息
    private Person getItem() {
        return Person.class.cast(personListTable.getSelectionModel().getSelectedItem());
    }
}