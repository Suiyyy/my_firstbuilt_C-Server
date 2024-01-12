package com.assetsms.model;

import java.io.Serializable;
import java.util.Objects;

public class Person implements Serializable {
    private String id;
    private String name;
    private String sex;
    private String dept;
    private String job;
    private String other;

    public Person() {
    }

    public Person(String id, String name, String sex, String dept, String job,String other) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.dept = dept;
        this.job = job;
        this.other = other;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person Person = (Person) o;
        return Objects.equals(id, Person.id) &&
                Objects.equals(name, Person.name) &&
                Objects.equals(sex, Person.sex) &&
                Objects.equals(dept, Person.dept)&&
                Objects.equals(job, Person.job)&&
                Objects.equals(other, Person.other);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sex, dept, job, other);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", dept='" + dept + '\'' +
                ", job='" + job + '\'' +
                ", other='" + other + '\'' +
                '}';
    }
}