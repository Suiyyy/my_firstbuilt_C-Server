package com.assetsms.model;

import com.assetsms.util.DBUtil;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Objects;

public class AssetsTrjn implements Serializable {
    private String jourNo;
    private String fromAcc;
    private Assets assets;
    private Date regDate;
    private Person person;
    private String purpose;
    private String other;
    /****外键成员变量********/
    
    
    public AssetsTrjn(String jourNo, String fromAcc, Assets assets, Date regDate, Person person, String purpose, String other) {
        this.jourNo = jourNo;
        this.fromAcc = fromAcc;
        this.assets = assets;
        this.regDate = regDate;
        this.person = person;
        this.purpose = purpose;
        this.other = other;
    }
    
    public AssetsTrjn(String fromAcc, Assets assets, Date regDate, Person person, String purpose, String other) {
        this.fromAcc = fromAcc;
        this.assets = assets;
        this.regDate = regDate;
        this.person = person;
        this.purpose = purpose;
        this.other = other;
    }
    
    public String getJourNo() {
        return jourNo;
    }
    
    public void setJourNo(String jourNo) {
        this.jourNo = jourNo;
    }
    
    public String getFromAcc() {
        return fromAcc;
    }
    
    public void setFromAcc(String fromAcc) {
        this.fromAcc = fromAcc;
    }
    
    public Date getRegDate() {
        return regDate;
    }
    
    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }
    
    public String getPurpose() {
        return purpose;
    }
    
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    
    public String getOther() {
        return other;
    }
    
    public void setOther(String other) {
        this.other = other;
    }
    
    public Assets getAssets() {
        return assets;
    }
    
    public void setAssets(Assets assets) {
        this.assets = assets;
    }
    
    public Person getPerson() {
        return person;
    }
    
    public void setPerson(Person person) {
        this.person = person;
    }
    
    
    

    @Override
    public int hashCode() {
    return Objects.hash(jourNo,fromAcc, assets.getAssetsID(),regDate, person.getId(), purpose,other);
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
        final AssetsTrjn other = (AssetsTrjn) obj;
        if (!this.jourNo.equals(other.jourNo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "编号：" + jourNo + ", 操作类型：" + fromAcc + ", 资产编号：" + assets.getAssetsID()
                + ", 操作时间：" + regDate+ ", 领用人：" + person.getId() + ", 用途：" + purpose
                +", 备注：" + other;
    }

}
