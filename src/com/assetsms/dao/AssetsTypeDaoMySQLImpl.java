package com.assetsms.dao;

import com.assetsms.model.AssetsType;
import com.assetsms.util.DBUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AssetsTypeDaoMySQLImpl implements AssetsTypeDao {
    private DBUtil dbUtil;
    public AssetsTypeDaoMySQLImpl() {

        dbUtil=new DBUtil();
    }

    @Override
    public List<AssetsType> findAll() throws Exception {
        dbUtil.getConnection();
        ResultSet rs=dbUtil.executeQuery("select * from AssetsType");
        List<AssetsType> list=new ArrayList<>();
        AssetsType AssetsType=null;
        while (rs.next()){
            AssetsType=new AssetsType(rs.getString("TypeID"),rs.getString("B_Type"),rs.getString("S_Type"));
            list.add(AssetsType);
        }
        dbUtil.closeAll();
        return list;
    }

    @Override
    public AssetsType findById(String id) throws Exception {
        dbUtil.getConnection();
        ResultSet rs=dbUtil.executeQuery("select * from AssetsType where TypeID=?",id);
        AssetsType assetsType=null;
        if(rs.next()){
            assetsType=new AssetsType(rs.getString("TypeID"),rs.getString("B_Type"),rs.getString("S_Type"));
        }
        dbUtil.closeAll();
        return assetsType;
    }

    @Override
    public void delete(String id) throws Exception {
        dbUtil.getConnection();
        dbUtil.executeUpdate("delete from AssetsType where TypeID=?",id);
        dbUtil.closeAll();
    }

    @Override
    public void update(AssetsType assetsType) throws Exception {
        dbUtil.getConnection();
        dbUtil.executeUpdate("update AssetsType set B_Type=?,S_Type=? where TypeID=?",assetsType.getBigType(),assetsType.getSmallType(),assetsType.getTypeID());
        dbUtil.closeAll();
    }

    @Override
    public String add(AssetsType assetsType) throws Exception {
        String id=null;
        dbUtil.getConnection();
        dbUtil.executeUpdate("insert into AssetsType(TypeID,B_Type,S_Type) values(uuid(),?,?)",assetsType.getBigType(),assetsType.getSmallType());
        ResultSet rs=dbUtil.executeQuery("select TypeID from AssetsType where B_Type=? and S_Type=?",assetsType.getBigType(),assetsType.getSmallType());
        if (rs.next()){
            id=rs.getString(1);
        }
        dbUtil.closeAll();
        return id;
    }

}
