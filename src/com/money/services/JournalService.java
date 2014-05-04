package com.money.services;

import com.google.appengine.api.datastore.*;
import com.money.model.Account;
import com.money.model.Journal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michal on 02/05/14.
 */
public enum JournalService {

    INSTANCE;

    private static final String ENTITY_NAME = "Journal";

    private DatastoreService datastoreService;
    private JournalService() {
        datastoreService = DatastoreServiceFactory.getDatastoreService();
    }

    public List<Journal> getAll(String accountId) {
        List<Journal> returnValue = new ArrayList<Journal>();
        for(Entity entity: getAllAsEntities(accountId)) {
            returnValue.add(toJournal(entity));
        }
        return returnValue;
    }

    public Iterable<Entity> getAllAsEntities(String accountId) {
        Iterable<Entity> returnValue = new ArrayList<Entity>();
        Entity ancestor = AccountsService.INSTANCE.getAsEntity(accountId);
        if(ancestor!=null) {
            Query query = new Query(ENTITY_NAME).setAncestor(ancestor.getKey());
            PreparedQuery preparedQuery = datastoreService.prepare(query);
            returnValue = preparedQuery.asIterable();
        }
        return returnValue;
    }

    public Journal get(String journalId) {
        Journal returnValue = null;
        Entity entity = getAsEntity(journalId);
        if(entity!=null) {
            returnValue = toJournal(entity);
        }
        return returnValue;
    }

    public Entity getAsEntity(String journalId) {
        Query.Filter filter = new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, journalId);
        Query query = new Query(ENTITY_NAME).setFilter(filter);
        PreparedQuery preparedQuery = datastoreService.prepare(query);
        return preparedQuery.asSingleEntity();
    }

    public void add(String accountId, Journal journal) {
        Entity ancestor = AccountsService.INSTANCE.getAsEntity(accountId);
        if(ancestor!=null) {
            Entity entity = toEntity(ancestor, journal);
            datastoreService.put(entity);
        }
    }

    public void remove(String journalId) {
        Entity entity = getAsEntity(journalId);
        if (entity != null) {
            for (Entity entry : EntryService.INSTANCE.getAllAsEntities(journalId)) {
                datastoreService.delete(entry.getKey());
            }
            datastoreService.delete(entity.getKey());
        }
    }

    private Entity toEntity(Entity ancestor, Journal journal) {
        Entity returnValue = new Entity(ENTITY_NAME, ancestor.getKey());
        returnValue.setProperty("id", journal.getId());
        return returnValue;
    }

    private Journal toJournal(Entity entity) {
        Journal returnValue = new Journal();
        returnValue.setId((String) entity.getProperty("id"));
        return returnValue;
    }

}
