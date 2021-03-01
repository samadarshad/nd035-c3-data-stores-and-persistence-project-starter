package com.udacity.jdnd.course3.critter.user;


import java.util.List;

public abstract class UserService<T extends User> {

    public abstract T get(Long id);
    public abstract List<T> getByName(String name);
    public abstract List<T> list();
    public abstract T save(T t);
}
