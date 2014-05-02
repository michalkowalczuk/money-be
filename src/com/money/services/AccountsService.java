package com.money.services;

import com.money.model.Account;
import com.endpoints.ResourceService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michal on 02/05/14.
 */
public enum AccountsService implements ResourceService<Account> {

    INSTANCE;

    private List<Account> tempList =
            new ArrayList<Account>();

    private AccountsService() {
        add(Account.newInstance());
    }

    @Override
    public List<Account> getAll() {
        return tempList;
    }

    @Override
    public Account get(String id) {
        Account returnValue = null;
        for(Account account: tempList) {
            if(account.getId().equals(id)) {
                returnValue = account;
                break;
            }
        }
        return returnValue;
    }

    @Override
    public void add(Account resource) {
        tempList.add(resource);
    }

    @Override
    public void remove(Account resource) {
        tempList.remove(resource);
    }

    @Override
    public void update(Account resource) {
        Account toUpdate = get(resource.getId());
        if(toUpdate!=null) {
            remove(toUpdate);
            add(resource);
        }
    }
}