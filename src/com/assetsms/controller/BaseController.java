package com.assetsms.controller;

import com.assetsms.dao.PersonDao;
import com.assetsms.dao.PersonDaoFileImpl;
import com.assetsms.dao.PersonDaoMySQLImpl;
import com.assetsms.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import static com.assetsms.util.AlertUtil.showWarningDialog;

import com.assetsms.dao.AssetsTypeDao;
import com.assetsms.dao.AssetsTypeDaoFileImpl;
import com.assetsms.dao.AssetsTypeDaoMySQLImpl;
import com.assetsms.model.AssetsType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import com.assetsms.dao.AssetsDao;
import com.assetsms.dao.AssetsDaoFileImpl;
import com.assetsms.dao.AssetsDaoMySQLImpl;
import com.assetsms.model.Assets;

import com.assetsms.dao.AssetsTrjnDao;
import com.assetsms.dao.AssetsTrjnDaoMySQLImpl;
import com.assetsms.model.AssetsTrjn;

/**
 * 所有控制器类的父类
 * 在所有控制器类中要特别注意的是：导入类时,要导入javafx包中的组件类，
 * 而不是awt或swing包中的同名组件类!
 * @author Liu Jinyu
 */
public class BaseController {
    // 通过创建AssetsTypeFileDaoImpl类的对象用文件持久化数据
//    protected static   final AssetsTypeDao assetsTypeDao=new AssetsTypeDaoFileImpl();
    /* 通过创建AssetsTypeMySQLDaoImpl类的对象用MySQL数据库持久化数据，
       如果选用MySQL数据库作为数据源注释掉上面的语句，并把下面的语句的注释去掉，
       要先安装MySQL数据库，把lib目录下的MySQL数据库JDBC驱动程序jar文件添加到项目的库中，
       把config目录下的create.sql数据库脚本文件的SQL语句复制到MySQL的命令行窗口中运行，
       创造数据库名为AssetsDB的数据库，在数据库中创建AssetsType表，并在AssetsType表中插入4条资产类别信息。
       修改config目录下的database.properties属性文件，使用户名、密码等参数和自己的数据库保持一致
    * */
    protected static   final AssetsTypeDao assetsTypeDao=new AssetsTypeDaoMySQLImpl();

    /* 用来存放表格组件数据的集合对象，该对象中的数据发生变化时表格组件会自动刷新,
       由于多个窗口需要共享表格中的数据，AssetsTypeData对象要定义成静态的，
       以便所有的控制器都能操作它
    */
    protected static  ObservableList<AssetsType> assetsTypeData;

    // assetsTypeData对象的静态初始化，从数据源中装载数据
    static{
        try {
            assetsTypeData =FXCollections.<AssetsType>observableArrayList(assetsTypeDao.findAll());

        } catch (Exception e) {
            showWarningDialog(e.toString());
            e.printStackTrace();
        }
    }
    // 用来保存在资产类别列表中选中的要更新的资产类别信息，列表窗口和编辑窗口共享该对象，需要设置成静态的
    protected static AssetsType editAssetsType = null;

    /********************/
//    protected static final AssetsDao assetsDao = new AssetsDaoFileImpl();
    protected static  final AssetsDao assetsDao = new AssetsDaoMySQLImpl();

    protected static ObservableList<Assets> assetsData;
    // PersonData对象的静态初始化，从数据源中装载数据
    static {
        try {
            assetsData = FXCollections.<Assets>observableArrayList(assetsDao.findAll());
        } catch (Exception e) {
            showWarningDialog(e.toString());
            e.printStackTrace();
        }
    }

    protected static Assets editAssets = null;
    /*****************************************/

    /********************/
    // 通过创建PersonFileDaoImpl类的对象用文件持久化数据
//    protected static final PersonDao PersonDao = new PersonDaoFileImpl();
    /* 通过创建PersonMySQLDaoImpl类的对象用MySQL数据库持久化数据，
       如果选用MySQL数据库作为数据源注释掉上面的语句，并把下面的语句的注释去掉，
       要先安装MySQL数据库，把lib目录下的MySQL数据库JDBC驱动程序jar文件添加到项目的库中，
       把config目录下的create.sql数据库脚本文件的SQL语句复制到MySQL的命令行窗口中运行，
       创造数据库名为bankdb的数据库，在数据库中创建Person表，并在Person表中插入4条客户信息。
       修改config目录下的database.properties属性文件，使用户名、密码等参数和自己的数据库保持一致
    * */
    protected static   final PersonDao personDao=new PersonDaoMySQLImpl();

    /* 用来存放表格组件数据的集合对象，该对象中的数据发生变化时表格组件会自动刷新,
       由于多个窗口需要共享表格中的数据，PersonData对象要定义成静态的，
       以便所有的控制器都能操作它
    */
    protected static ObservableList<Person> personData;

    // PersonData对象的静态初始化，从数据源中装载数据
    static {
        try {
            personData = FXCollections.<Person>observableArrayList(personDao.findAll());
        } catch (Exception e) {
            showWarningDialog(e.toString());
            e.printStackTrace();
        }
    }
    // 用来保存在人员列表中选中的要更新的人员信息，列表窗口和编辑窗口共享该对象，需要设置成静态的
    protected static Person editPerson = null;
    /*****************************************/

    /********************/
    protected static   final AssetsTrjnDao assetsTrjnDao=new AssetsTrjnDaoMySQLImpl();

    protected static ObservableList<AssetsTrjn> assetsTrjnData;
    // AssetsTrjnData对象的静态初始化，从数据源中装载数据
    static {
        try {
            assetsTrjnData = FXCollections.<AssetsTrjn>observableArrayList(assetsTrjnDao.findAll());
        } catch (Exception e) {
            showWarningDialog(e.toString());
            e.printStackTrace();
        }
    }

    protected static AssetsTrjn editAssetsTrjn = null;
    /*****************************************/

    /**
     * 用于切换窗口的导航方法
     * @param title 新窗口的标题
     * @param node 发生事件的组件对象，该对象是Node类的子类的对象，
     *             通过该对象可以获取舞台对象，以便切换舞台的场景，
     *             从而实现窗口的切换
     * @param fxmlDocName 代表窗口布局文件路径的URL对象
     * @throws IOException
     */
    protected void navigate(String title,Node node, URL fxmlDocName) throws IOException {
        //加载新的fxml UI文档
        Parent pageParent = FXMLLoader.load(fxmlDocName);
        //创建新场景
        Scene scene = new Scene(pageParent);
        //获得当前舞台
        Stage appStage =(Stage) node.getScene().getWindow();
        appStage.setTitle(title);
        //为舞台设置新场景
        appStage.setScene(scene);
        //显示舞台
        appStage.show();
    }

}


