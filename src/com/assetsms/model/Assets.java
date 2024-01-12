package com.assetsms.model;

import sun.security.rsa.RSAKeyFactory;

import javax.swing.plaf.basic.BasicIconFactory;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class Assets implements Serializable {
    private String assetsID;
    private String name;
    private String model;
    private String price;
    private Date buyDate;
    private String status;
    private String other;
    /*********外键成员变量************/
    private AssetsType assetsType=new AssetsType();
    /******************************/
    public Assets(){}

    public Assets(String assetsID, String name, String typeID, String model, String price, Date buyDate, String status, String other) {
        this.assetsID = assetsID;
        this.name = name;
        this.assetsType.setTypeID(typeID);
        this.model = model;
        this.price = price;
        this.buyDate = buyDate;
        this.status = status;
        this.other = other;
    }

    public Assets(String name, String typeID, String model, String price, Date buyDate, String status, String other) {
        this.name = name;
        this.assetsType.setTypeID(typeID);
        this.model = model;
        this.price = price;
        this.buyDate = buyDate;
        this.status = status;
        this.other = other;
    }

    public String getAssetsID() {
        return assetsID;
    }

    public void setAssetsID(String assetsID) {
        this.assetsID = assetsID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AssetsType getAssetsType() {
        return assetsType;
    }

    public void setAssetsType(AssetsType assetsType) {
        this.assetsType=assetsType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public int hashCode() {
        return Objects.hash(assetsID,name,assetsType.getTypeID(),model,price, buyDate,status,other);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Assets other = (Assets) obj;
        if (this.assetsID != other.assetsID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "资产编号：" + assetsID + ", 资产名称：" + name + ", 所属类型：" + assetsType.getTypeID()
                + ", 型号：" + model+ ", 价格：" + price + ", 购买日期：" + buyDate
                + ", 状态：" + status + ", 备注：" + other;
    }

}
