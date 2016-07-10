package com.example.administrator.exrxjavaandretrofit;

/**
 * Created by Administrator on 2016/7/10 0010.
 */
public class Course {
    private String id;
    private String className;

    public Course(String className, String id) {
        this.className = className;
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
