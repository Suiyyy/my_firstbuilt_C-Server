
package com.assetsms.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Jinyu
 */
public class AssetsType implements Serializable{
    private String typeID;
    private String bigType;
    private String smallType;

    public AssetsType(){}

    public AssetsType(String bType, String sType) {
        this.bigType = bType;
        this.smallType = sType;
    }

    public AssetsType(String typeID, String bType, String sType) {
        this.typeID = typeID;
        this.bigType = bType;
        this.smallType = sType;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getBigType() {
        return bigType;
    }

    public void setBigType(String bType) {
        this.bigType = bType;
    }

    public String getSmallType() {
        return smallType;
    }

    public void setSmallType(String sType) {
        this.smallType = sType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeID, bigType, smallType);
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
        final AssetsType other = (AssetsType) obj;
        if (this.typeID != other.typeID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "资产类别编号：" + typeID + ", 资产大类：" +bigType + ", 资产小类：" + smallType ;
    }
    
}
