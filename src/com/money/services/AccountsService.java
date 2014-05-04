package com.money.services;

import com.google.appengine.api.datastore.*;
import com.money.model.Account;
import com.money.model.Entry;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by michal on 02/05/14.
 */
public enum AccountsService {

    INSTANCE;

    private static final String ENTITY_NAME = "Account";

    private DatastoreService datastoreService;
    private AccountsService() {
        datastoreService = DatastoreServiceFactory.getDatastoreService();
    }

    public List<Account> getAll() {
        List<Account> returnValue = new ArrayList<Account>();
        for(Entity entity: getAllAsEntities()) {
            Account account = toAccount(entity);
            account.setJournals(
                    JournalService.INSTANCE.getAll(account.getId())
            );
            returnValue.add(account);
        }
        return returnValue;
    }

    public Iterable<Entity> getAllAsEntities() {
        PreparedQuery preparedQuery = datastoreService.prepare(new Query(ENTITY_NAME));
        return preparedQuery.asIterable();
    }

    public Account get(String accountId) {
        Account returnValue = null;
        Entity entity = getAsEntity(accountId);
        if(entity!=null) {
            returnValue = toAccount(entity);
            returnValue.setJournals(
                    JournalService.INSTANCE.getAll(accountId)
            );
        }
        return returnValue;
    }

    public Entity getAsEntity(String accountId) {
        Query.Filter filter = new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, accountId);
        Query query = new Query(ENTITY_NAME).setFilter(filter);
        PreparedQuery preparedQuery = datastoreService.prepare(query);
        return preparedQuery.asSingleEntity();
    }

    public void add(Account account) {
        Entity entity = toEntity(account);
        datastoreService.put(entity);
    }

    public void remove(String accountId) {
        Entity entity = getAsEntity(accountId);
        if(entity!=null) {
            for(Entity journal: JournalService.INSTANCE.getAllAsEntities(accountId)) {
                datastoreService.delete(journal.getKey());
            }
            datastoreService.delete(entity.getKey());
        }
    }

    private Entity toEntity(Account account) {
        Entity returnValue = new Entity(ENTITY_NAME);
        returnValue.setProperty("id", account.getId());
        return returnValue;
    }

    private Account toAccount(Entity entity) {
        Account returnValue = new Account();
        returnValue.setId((String) entity.getProperty("id"));
        return returnValue;
    }

}