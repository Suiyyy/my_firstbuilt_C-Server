package com.assetsms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;

import java.io.IOException;

import static com.assetsms.util.FXMLPage.*;

public class MenuController extends com.assetsms.controller.BaseController {
    @FXML
    private MenuBar menuBar;

    @FXML
    private void addAssetsType(ActionEvent event) throws IOException {
        navigate(ADD_ASSETS_TYPE.getTitle(),menuBar, ADD_ASSETS_TYPE.getPage());
    }

    @FXML
    private void listAssetsType(ActionEvent event) throws IOException {
        navigate(LIST_ASSETS_TYPE.getTitle(),menuBar, LIST_ASSETS_TYPE.getPage());
    }

    @FXML
    private void addAssets(ActionEvent event) throws IOException {
        navigate(ADD_ASSETS.getTitle(),menuBar, ADD_ASSETS.getPage());
    }

    @FXML
    private void listAssets(ActionEvent event) throws IOException {
        navigate(LIST_ASSETS.getTitle(),menuBar, LIST_ASSETS.getPage());
    }

    @FXML
    private void addPerson(ActionEvent event) throws IOException {
        navigate(ADD_PERSON.getTitle(),menuBar, ADD_PERSON.getPage());
    }

    @FXML
    private void listPerson(ActionEvent event) throws IOException {
        navigate(LIST_PERSON.getTitle(),menuBar, LIST_PERSON.getPage());
    }

    @FXML
    private void assetsUsing(ActionEvent event) throws IOException {
        navigate(ADD_ASSETSUSING.getTitle(),menuBar, ADD_ASSETSUSING.getPage());
    }

    @FXML
    private void listAssetsUsing(ActionEvent event) throws IOException {
        navigate(LIST_ASSETSUSING.getTitle(),menuBar, LIST_ASSETSUSING.getPage());
    }

    @FXML
    private void assetsBack(ActionEvent event) throws IOException {
        navigate(ADD_ASSETSBACK.getTitle(),menuBar, ADD_ASSETSBACK.getPage());
    }

    @FXML
    private void listAssetsBack(ActionEvent event) throws IOException {
        navigate(LIST_ASSETSBACK.getTitle(),menuBar, LIST_ASSETSBACK.getPage());
    }

    @FXML
    private void assetsInvalid(ActionEvent event) throws IOException {
        navigate(ADD_ASSETSINVALID.getTitle(),menuBar, ADD_ASSETSINVALID.getPage());
    }

    @FXML
    private void listAssetsInvalid(ActionEvent event) throws IOException {
        navigate(LIST_ASSETSINVALID.getTitle(),menuBar, LIST_ASSETSINVALID.getPage());
    }

    @FXML
    private void returnMain(ActionEvent event) throws IOException {
        navigate(ASSETS_MAIN.getTitle(),menuBar, ASSETS_MAIN.getPage());
    }
    @FXML
    private void exit(ActionEvent event) throws IOException {
        System.exit(0);
    }

}
