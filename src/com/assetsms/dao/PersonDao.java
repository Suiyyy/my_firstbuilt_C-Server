package com.assetsms.dao;

import java.util.List;
import java.util.Set;

import com.assetsms.model.Person;

public interface PersonDao {
        List<Person> findAll() throws Exception;
        Person findById(String id) throws Exception;
        void delete(String id) throws Exception;
        void update(Person person) throws Exception;
        void add(Person person) throws Exception;
}
