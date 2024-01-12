package com.assetsms.dao;

import com.assetsms.model.Assets;
import com.assetsms.model.Person;
import com.assetsms.util.DBUtil;
import com.assetsms.model.AssetsTrjn;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AssetsTrjnDaoMySQLImpl implements AssetsTrjnDao {
    private DBUtil dbUtil;

    public AssetsTrjnDaoMySQLImpl() {

        dbUtil = new DBUtil();
    }

    

    @Override
    public List<AssetsTrjn>  findAll() throws Exception {
        dbUtil.getConnection();
        ResultSet rs = dbUtil.executeQuery("select * from AssetsTrjn as t left outer join Assets as a on t.AssetsID=a.AssetsID left outer join Person as p on t.PersonID=p.PersonID");
        List<AssetsTrjn> list = new ArrayList<>();
        AssetsTrjn assetsTrjn = null;
        while (rs.next()) {
            assetsTrjn = new AssetsTrjn(rs.getString("JourNo"),
                    rs.getString("FromAcc"),
                    new Assets(
                            rs.getString("a.assetsID"),
                            rs.getString("a.name"),
                            rs.getString("a.TypeID"),
                            rs.getString("a.Model"),
                            rs.getString("a.Price"),
                            rs.getDate("a.BuyDate"),
                            rs.getString("a.Status"),
                            rs.getString("a.Other")
                
                    ),
                    rs.getDate("RegDate"),
                    new Person(
                            rs.getString("p.id"),
                            rs.getString("p.Name"),
                            rs.getString("p.Sex"),
                            rs.getString("p.Dept"),
                            rs.getString("p.Job"),
                            rs.getString("p.Other")
                    ),
                    rs.getString("purpose"),
                    rs.getString("Other")
            );
            list.add(assetsTrjn);
        }
        dbUtil.closeAll();
        return list;
    }

    


   

    @Override
    public String add(AssetsTrjn assetsTrjn) throws Exception {
        String JourNo = null;
        dbUtil.getConnection();
        dbUtil.executeUpdate("insert into AssetsTrjn(JourNo,FromAcc,AssetsID,RegDate,PersonID,Purpose,Other) " +
                        "values(uuid(),?,?,?,?,?,?)",assetsTrjn.getFromAcc(),assetsTrjn.getAssets().getAssetsID(), assetsTrjn.getRegDate(),
                assetsTrjn.getPerson().getId(), assetsTrjn.getPurpose(), assetsTrjn.getOther());
        ResultSet rs = dbUtil.executeQuery("select JourNo from AssetsTrjn where FromAcc=? and AssetsID=? and RegDate=? and " +
                        "PersonID=? and Purpose=? and Other=?",assetsTrjn.getFromAcc(),assetsTrjn.getAssets().getAssetsID(), assetsTrjn.getRegDate(),
                assetsTrjn.getPerson().getId(), assetsTrjn.getPurpose(), assetsTrjn.getOther());
        if (rs.next()) {
            JourNo = rs.getString(1);
        }
        dbUtil.closeAll();
        return JourNo;
    }
}
