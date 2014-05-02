package com.endpoints;

import java.util.List;

/**
 * Created by michal on 02/05/14.
 */
public interface ResourceService<X> {

    public List<X> getAll();
    public X get(String id);
    public void add(X resource);
    public void remove(X resource);
    public void update(X resource);

}
