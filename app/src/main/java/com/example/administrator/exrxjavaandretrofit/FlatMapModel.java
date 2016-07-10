package com.example.administrator.exrxjavaandretrofit;

/**
 * Created by Administrator on 2016/7/10 0010.
 */
public class FlatMapModel {

    private String name;
    private String id;
    private Course course;

    public FlatMapModel(Course course, String id, String name) {
        this.course = course;
        this.id = id;
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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
}
