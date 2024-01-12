package com.assetsms.util;

import java.net.URL;

public enum FXMLPage {
    ASSETS_MAIN("双泽资产管理系统", "/com/assetsms/view/AssetsMSMain.fxml"),
    LIST_ASSETS_TYPE("双泽资产管理系统——资产类型列表", "/com/assetsms/view/ListAssetsType.fxml"),
    ADD_ASSETS_TYPE("双泽资产管理系统——添加资产类型信息", "/com/assetsms/view/AddEditAssetsType.fxml"),
    UPDATE_ASSETS_TYPE("双泽资产管理系统——更新资产类型信息", "/com/assetsms/view/AddEditAssetsType.fxml"),
    LIST_ASSETS("双泽资产管理系统——资产信息列表", "/com/assetsms/view/ListAssets.fxml"),
    ADD_ASSETS("双泽资产管理系统——添加资产信息", "/com/assetsms/view/AddEditAssets.fxml"),
    UPDATE_ASSETS("双泽资产管理系统——更新资产信息", "/com/assetsms/view/AddEditAssets.fxml"),
    LIST_PERSON("双泽资产管理系统——人员信息列表", "/com/assetsms/view/ListPerson.fxml"),
    ADD_PERSON("双泽资产管理系统——添加人员信息", "/com/assetsms/view/AddEditPerson.fxml"),
    UPDATE_PERSON("双泽资产管理系统——更新人员信息", "/com/assetsms/view/AddEditPerson.fxml"),
    LIST_ASSETSUSING("双泽资产管理系统——资产领用列表", "/com/assetsms/view/ListAssetsUsing.fxml"),
    ADD_ASSETSUSING("双泽资产管理系统——资产领用信息", "/com/assetsms/view/AssetsUsing.fxml"),
    UPDATE_ASSETSUSING("双泽资产管理系统——更新资产领用信息", "/com/assetsms/view/AssetsUsing.fxml"),
    LIST_ASSETSBACK("双泽资产管理系统——资产归还列表", "/com/assetsms/view/ListAssetsBack.fxml"),
    ADD_ASSETSBACK("双泽资产管理系统——资产归还信息", "/com/assetsms/view/AssetsBack.fxml"),
    UPDATE_ASSETSBACK("双泽资产管理系统——更新资产归还信息", "/com/assetsms/view/AssetsBack.fxml"),
    LIST_ASSETSINVALID("双泽资产管理系统——资产报废列表", "/com/assetsms/view/ListAssetsInvalid.fxml"),
    ADD_ASSETSINVALID("双泽资产管理系统——资产报废信息","/com/assetsms/view/AssetsInvalid.fxml"),
    UPDATE_ASSETSINVALID("双泽资产管理系统——更新资产报废信息", "/com/assetsms/view/AssetsInvalid.fxml");
    private final String location;
    private final String title;
    FXMLPage(String title,String location) {
        this.title=title;
        this.location = location;
    }
    public String getTitle(){
        return title;
    }
    public URL getPage() {
        return  getClass().getResource(location) ;

    }
}
