package com.money.services;


import com.google.appengine.api.datastore.*;
import com.money.model.Entry;
import com.money.model.Journal;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michal on 02/05/14.
 */
public enum EntryService {

    INSTANCE;

    private static final String ENTITY_NAME = "Entry";

    private DatastoreService datastoreService;

    private EntryService() {
        datastoreService = DatastoreServiceFactory.getDatastoreService();
    }

    public List<Entry> getAll(String journalId) {
        List<Entry> returnValue = new ArrayList<Entry>();
        for(Entity entity: getAllAsEntities(journalId)) {
            returnValue.add(toEntry(entity));
        }
        return returnValue;
    }

    public Iterable<Entity> getAllAsEntities(String journalId) {
        Iterable<Entity> returnValue = new ArrayList<Entity>();
        Entity ancestor = JournalService.INSTANCE.getAsEntity(journalId);
        if(ancestor!=null) {
            Query query = new Query(ENTITY_NAME).setAncestor(ancestor.getKey());
            PreparedQuery preparedQuery = datastoreService.prepare(query);
            returnValue = preparedQuery.asIterable();
        }
        return returnValue;
    }

    public Entry get(String entryId) {
        Entry returnValue = null;
        Entity entity = getAsEntity(entryId);
        if(entity!=null) {
            returnValue = toEntry(entity);
        }
        return returnValue;
    }

    public Entity getAsEntity(String entryId) {
        Query.Filter filter = new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, entryId);
        Query query = new Query(ENTITY_NAME).setFilter(filter);
        PreparedQuery preparedQuery = datastoreService.prepare(query);
        return preparedQuery.asSingleEntity();
    }

    public void add(String journalId, Entry entry) {
        Entity ancestor = JournalService.INSTANCE.getAsEntity(journalId);
        if(ancestor!=null) {
            Entity entity = toEntity(ancestor, entry);
            datastoreService.put(entity);
        }
    }

    public void remove(String entryId) {
        Entity entity = getAsEntity(entryId);
        if(entity!=null) {
            datastoreService.delete(entity.getKey());
        }
    }

    private Entity toEntity(Entity ancestor, Entry entry) {
        Entity returnValue = new Entity(ENTITY_NAME, ancestor.getKey());
        returnValue.setProperty("id", entry.getId());
        returnValue.setProperty("dateFormatted", entry.getDateFormatted());
        returnValue.setProperty("amount", entry.getAmount());
        return returnValue;
    }

    private Entry toEntry(Entity entity) {
        Entry returnValue = new Entry();
        returnValue.setId((String) entity.getProperty("id"));
        returnValue.setAmount((Double) entity.getProperty("amount"));
        returnValue.setDateFromFormattedString((String) entity.getProperty("dateFormatted"));
        return returnValue;
    }

}
