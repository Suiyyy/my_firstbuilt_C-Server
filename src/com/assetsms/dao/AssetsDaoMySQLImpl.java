package com.assetsms.dao;

import com.assetsms.model.Assets;
import com.assetsms.util.DBUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AssetsDaoMySQLImpl implements AssetsDao {
    private DBUtil dbUtil;

    public AssetsDaoMySQLImpl() {

        dbUtil=new DBUtil();
    }

    @Override
    public List<Assets> findAll() throws Exception {
        dbUtil.getConnection();
        ResultSet rs=dbUtil.executeQuery("select * from Assets");
        List<Assets> list=new ArrayList<>();

        Assets assets=null;
        while (rs.next()){
            assets=new Assets(rs.getString("AssetsID"),rs.getString("Name"),
                    rs.getString("TypeID"),rs.getString("Model"),
                    rs.getString("Price"),rs.getDate("BuyDate"),
                    rs.getString("Status"),rs.getString("Other"));
            list.add(assets);
        }
        dbUtil.closeAll();
        return list;
    }

    @Override
    public Assets findById(String assetsID) throws Exception {
        dbUtil.getConnection();
        ResultSet rs=dbUtil.executeQuery("select * from Assets where AssetsID=?",assetsID);
        Assets assets=null;
        if(rs.next()){
            assets=new Assets(rs.getString("AssetsID"),rs.getString("Name")
                    ,rs.getString("TypeID"),rs.getString("Model")
                    ,rs.getString("Price"),rs.getDate("BuyDate")
                    ,rs.getString("Status"),rs.getString("Other"));
        }
        dbUtil.closeAll();
        return assets;
    }

    @Override
    public void delete(String assetsID) throws Exception {
        dbUtil.getConnection();
        dbUtil.executeUpdate("delete from Assets where AssetsID=?",assetsID);
        dbUtil.closeAll();
    }

    @Override
    public void update(Assets assets) throws Exception {
        dbUtil.getConnection();
        dbUtil.executeUpdate("update Assets set Name=?,TypeID=?,Model=?,Price=?,BuyDate=?" +
                ",Status=?,Other=? where AssetsID=?",assets.getName(),assets.getAssetsType().getTypeID(),assets.getModel()
                ,assets.getPrice(),assets.getBuyDate(),assets.getStatus(),assets.getOther(),assets.getAssetsID());
        dbUtil.closeAll();
    }

    @Override
    public String add(Assets assets) throws Exception {
        String assetsID=null;
        dbUtil.getConnection();
        dbUtil.executeUpdate("insert into Assets(AssetsID,Name,TypeID,Model,Price,BuyDate,Status,Other) " +
                        "values(uuid(),?,?,?,?,?,?,?)",assets.getName(),assets.getAssetsType().getTypeID(),assets.getModel()
                ,assets.getPrice(),assets.getBuyDate(),assets.getStatus(),assets.getOther());
        ResultSet rs=dbUtil.executeQuery("select AssetsID from Assets where Name=? and TypeID=? and Model=? and " +
                        "Price=? and BuyDate=? and Status=? and Other=?",assets.getName(),assets.getAssetsType().getTypeID(),assets.getModel()
                ,assets.getPrice(),assets.getBuyDate(),assets.getStatus(),assets.getOther());
        if (rs.next()){
            assetsID=rs.getString(1);
        }
        dbUtil.closeAll();
        return assetsID;
    }

}
