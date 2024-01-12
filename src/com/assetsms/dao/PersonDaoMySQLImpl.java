package com.assetsms.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.assetsms.model.Person;
import com.assetsms.util.DBUtil;

public class PersonDaoMySQLImpl implements PersonDao{
    private DBUtil dbUtil;
    public PersonDaoMySQLImpl() {

        dbUtil=new DBUtil();
    }

    @Override
    public List<Person> findAll() throws Exception {
        dbUtil.getConnection();
        ResultSet rs=dbUtil.executeQuery("select * from Person",null);
        List<Person> list=new ArrayList<>();
        Person Person=null;
        while (rs.next()){
            Person=new Person(rs.getString("PersonID"),rs.getString("name"),rs.getString("sex"),rs.getString("dept"),rs.getString("job"),rs.getString("other"));
            list.add(Person);
        }
        dbUtil.closeAll();
        return list;
    }

    @Override
    public Person findById(String id) throws Exception {
        dbUtil.getConnection();
        ResultSet rs=dbUtil.executeQuery("select * from Person where id=?",id);
        Person Person=null;
        if(rs.next()){
            Person=new Person(rs.getString("PersonID"),rs.getString("name"),rs.getString("sex"),rs.getString("dept"),rs.getString("job"),rs.getString("other"));
        }
        dbUtil.closeAll();
        return Person;
    }

    @Override
    public void delete(String id) throws Exception {
        dbUtil.getConnection();
        dbUtil.executeUpdate("delete from Person where id=?", id);
        dbUtil.closeAll();
    }

    @Override
    public void update(Person Person) throws Exception {
        dbUtil.getConnection();
        dbUtil.executeUpdate("update Person set name=?,sex=?,dept=?,job=?,other=? where id=?", Person.getName(),Person.getSex(),Person.getDept(),Person.getJob(),Person.getOther(),Person.getId());
        dbUtil.closeAll();
    }

    @Override
    public void add(Person Person) throws Exception {
        dbUtil.getConnection();
        dbUtil.executeUpdate("insert into Person values(?,?,?,?,?,?)",Person.getId(),Person.getName(),Person.getSex(),Person.getDept(),Person.getJob(),Person.getOther());
        dbUtil.closeAll();
    }
}

