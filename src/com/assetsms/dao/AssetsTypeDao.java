package com.assetsms.dao;

import com.assetsms.model.AssetsType;

import java.util.List;

public interface AssetsTypeDao {

    List<AssetsType> findAll() throws Exception;
    AssetsType findById(String id) throws Exception;
    void delete(String id) throws Exception;
    void update(AssetsType assetsType) throws Exception;
    String add(AssetsType assetsType) throws Exception;

}
