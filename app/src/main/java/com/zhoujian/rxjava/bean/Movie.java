package com.zhoujian.rxjava.bean;

import java.util.List;

/**
 * Created by zhoujian on 2016/12/29.
 */

public class Movie
{

    private String name;
    private int id;
    private String data;
    private List<Actor> mactorList;

    public Movie(String data, int id, List<Actor> mactorList, String name)
    {
        this.data = data;
        this.id = id;
        this.mactorList = mactorList;
        this.name = name;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Actor> getMactorList() {
        return mactorList;
    }

    public void setMactorList(List<Actor> mactorList) {
        this.mactorList = mactorList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Movie{" +
                "data='" + data + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", mactorList=" + mactorList +
                '}';
    }
}
