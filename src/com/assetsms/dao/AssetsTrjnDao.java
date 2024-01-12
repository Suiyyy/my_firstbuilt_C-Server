package com.assetsms.dao;
import com.assetsms.model.AssetsTrjn;

import java.util.List;


public interface AssetsTrjnDao {
    List<AssetsTrjn> findAll() throws Exception;
    //AssetsTrjn findById(String assetsID) throws Exception;
    //void delete(String assetsID) throws Exception;
    //void update(AssetsTrjn assetsTrjn) throws Exception;
    String add(AssetsTrjn assetsTrjn) throws Exception;
}
