package com.assetsms.dao;

import com.assetsms.model.AssetsType;
import com.assetsms.util.FileUtil;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AssetsTypeDaoFileImpl implements AssetsTypeDao {

    @Override
    public List<AssetsType> findAll() throws Exception {
        List<AssetsType> list= null;
        try {
            list = (List<AssetsType>) FileUtil.readObject("AssetsType");
        } catch (EOFException e) {
            list=new ArrayList<>();
        }
        return list;
    }

    @Override
    public AssetsType findById(String id) throws Exception{
        List<AssetsType> list=findAll();
        return list.stream().filter(item->item.getTypeID().equals(id)).findFirst().get();
    }

    @Override
    public void delete(String id) throws Exception{
        List<AssetsType> list=findAll();
        list.stream().filter(item->item.getTypeID().equals(id)).findFirst().ifPresent(item->list.remove(item));
        FileUtil.writeObject(list,"AssetsType");
    }

    @Override
    public void update(AssetsType assetsType) throws Exception{
        List<AssetsType> list=findAll();
        list.stream().filter(item->item.getTypeID().equals(assetsType.getTypeID())).findFirst().ifPresent(item->{
            list.remove(item);
            list.add(assetsType);
        });
        FileUtil.writeObject(list,"AssetsType");
    }

    @Override
    public String add(AssetsType assetsType) throws Exception {
        String id=UUID.randomUUID().toString();
        assetsType.setTypeID(id);
        List<AssetsType> list=findAll();
        list.add(assetsType);
        FileUtil.writeObject(list,"AssetsType");
        return id;
    }


    public static void main(String[] args) throws Exception {
        AssetsTypeDao assetsTypeDao=new AssetsTypeDaoFileImpl();
        assetsTypeDao.add(new AssetsType(UUID.randomUUID().toString(),"办公用品","计算机"));
        assetsTypeDao.add(new AssetsType(UUID.randomUUID().toString(),"办公用品","打印机"));
        assetsTypeDao.add(new AssetsType(UUID.randomUUID().toString(),"耗材","硒鼓"));
        assetsTypeDao.add(new AssetsType(UUID.randomUUID().toString(),"耗材","硒鼓"));
        List<AssetsType> list=assetsTypeDao.findAll();
        System.out.println(list);
    }
}
