package com.assetsms.controller;

import com.assetsms.model.Person;
import com.assetsms.util.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.assetsms.util.AlertUtil.showWarningDialog;
import static com.assetsms.util.FXMLPage.LIST_PERSON;
import static java.util.Objects.nonNull;
import static javafx.scene.control.Alert.AlertType.WARNING;


/**
 * 添加和编辑客户信息窗口的控制器类，
 * 把要操作的组件对象声明成该类的属性，
 * 属性的名称要和对应的窗口布局文件中组件标记的fx:id属性的值保持一致，
 * 并且在属性声明前添加 @FXML注解
 * @author Liu Jinyu
 */
public class AddEditPerson extends BaseController implements Initializable {

    @FXML
    private TextField idText;
    @FXML
    private TextField nameText;
    @FXML
    private ChoiceBox<String> sexText;
//    @FXML
//    private TextField sexText;
    @FXML
    private TextField deptText;
    @FXML
    private TextField jobText;
    @FXML
    private TextField otherText;
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
        if (idText.getText().trim().equals("")|| nameText.getText().trim().equals("")
                || deptText.getText().trim().equals("") )/*性别是选项框，职位和备注可以空*/ {
            //显示对话框
            showWarningDialog("要添加或更新的数据是错误的！","字段的内容不能为空！");
            return;
        }
        //得到窗口中输入的客户信息
        Person person=new Person(idText.getText(), nameText.getText(),sexText.getValue(),deptText.getText(),jobText.getText(),otherText.getText());

        try {
            //如果editPerson不为null，提交的数据是要更新的数据，在更新之前，先删除旧数据
            // nonNull是java.util.Objects类中的静态方法，已静态导入，可以直接调用
            if (nonNull(editPerson)) {
                //更新数据源中的数据
                personDao.update(person);
                //删除列表中的旧数据
                personData.remove(editPerson);
            }else {
                //把新的客户信息添加到数据源
                personDao.add(person);
                //把新的客户信息添加到列表
            }
            personData.add(person);
            clearForm();
            //通过LIST_PERSON枚举对象得到列表窗口的标题和代表布局文件路径的URL对象，
            //通过(Node)event.getSource()得到发生事件的组件对象
            //把3个对象传递给navigate方法，从而实现窗口的切换
            navigate(LIST_PERSON.getTitle(), (Node) event.getSource(), LIST_PERSON.getPage());
        } catch (Exception e) {
            showWarningDialog(e.toString());
            e.printStackTrace();
        }
    }
    //清空文本框中的内容
    private void clearForm(){
        idText.clear();
        nameText.clear();
        deptText.clear();
        jobText.clear();
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
        sexText.getItems().addAll("男","女");

        // 如果editPerson对象不为null，就在文本框中显示客户信息，并把提交按钮上显示的文字改为“更新”
        if (nonNull(editPerson)) {
            idText.setText(editPerson.getId());
            // 身份证号码不能修改
            idText.setDisable(true);
            nameText.setText(editPerson.getName());
            sexText.setValue(editPerson.getSex());
            deptText.setText(editPerson.getDept());
            jobText.setText(editPerson.getJob());
            otherText.setText(editPerson.getOther());
            submitButton.setText("更新");
        }
    }
}
