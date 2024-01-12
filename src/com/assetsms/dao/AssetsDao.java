package com.assetsms.dao;

import com.assetsms.model.Assets;

import java.util.List;
import java.util.Set;

public interface AssetsDao {
    List<Assets> findAll() throws Exception;
    Assets findById(String assetsID) throws Exception;
    void delete(String assetsID) throws Exception;
    void update(Assets assets) throws Exception;
    String add(Assets assets) throws Exception;
}
