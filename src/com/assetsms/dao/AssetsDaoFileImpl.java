package com.assetsms.dao;

import com.assetsms.model.Assets;
import com.assetsms.util.FileUtil;

import java.io.EOFException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AssetsDaoFileImpl implements AssetsDao {

    @Override
    public List<Assets> findAll() throws Exception {
        List<Assets> list= null;
        try {
            list = (List<Assets>) FileUtil.readObject("Assets");
        } catch (EOFException e) {
            list=new ArrayList<>();
        }
        return list;
    }

    @Override
    public Assets findById(String assetsID) throws Exception{
        List<Assets> list=findAll();
        return list.stream().filter(item->item.getAssetsID().equals(assetsID)).findFirst().get();
    }

    @Override
    public void delete(String assetsID) throws Exception{
        List<Assets> list=findAll();
        list.stream().filter(item->item.getAssetsID().equals(assetsID)).findFirst().ifPresent(item->list.remove(item));
        FileUtil.writeObject(list,"Assets");
    }

    @Override
    public void update(Assets assets) throws Exception{
        List<Assets> list=findAll();
        list.stream().filter(item->item.getAssetsID().equals(assets.getAssetsID())).findFirst().ifPresent(item->{
            list.remove(item);
            list.add(assets);
        });
        FileUtil.writeObject(list,"Assets");
    }

    @Override
    public String add(Assets assets) throws Exception {
        List<Assets> list=findAll();
        list.add(assets);
        FileUtil.writeObject(list,"Assets");
        return assets.getAssetsID();
    }


    public static void main(String[] args) throws Exception {
        AssetsDao assetsDao=new AssetsDaoFileImpl();
        List<Assets> list=assetsDao.findAll();
        System.out.println(list);
    }
}
