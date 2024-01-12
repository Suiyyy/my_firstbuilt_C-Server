package com.assetsms.dao;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.assetsms.model.Person;
import com.assetsms.util.FileUtil;


public class PersonDaoFileImpl implements PersonDao{

    @Override
    public List<Person> findAll() throws Exception {
        List<Person> list= null;
        try {
            list = (List<Person>) FileUtil.readObject("Person");
        } catch (EOFException e) {
            list=new ArrayList<>();
        }
        return list;
    }

    @Override
    public Person findById(String id) throws Exception{
        List<Person> list=findAll();
        return list.stream().filter(item->item.getId().equals(id)).findFirst().get();
    }

    @Override
    public void delete(String id) throws Exception{
        List<Person> list=findAll();
        list.stream().filter(item->item.getId().equals(id)).findFirst().ifPresent(item->list.remove(item));
        FileUtil.writeObject(list,"Person");
    }

    @Override
    public void update(Person Person) throws Exception{
        List<Person> list=findAll();
        list.stream().filter(item->item.getId().equals(Person.getId())).findFirst().ifPresent(item->{
            list.remove(item);
            list.add(Person);
        });
        FileUtil.writeObject(list,"Person");
    }

    @Override
    public void add(Person Person) throws Exception {
        List<Person> list=findAll();
        list.add(Person);
        FileUtil.writeObject(list,"Person");
    }

    public static void main(String[] args) throws Exception {
        PersonDao PersonDao=new PersonDaoFileImpl();
        PersonDao.add(new Person("123456789012345671","张三","男","销售部","销售员",""));
        List<Person> list=PersonDao.findAll();
        System.out.println(list);
    }
}

