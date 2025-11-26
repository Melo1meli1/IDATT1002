package no.idatt1002.repository;

import java.util.HashMap;

import no.idatt1002.Model.User;

import javafx.collections.ObservableList;

/**
 * The Cache class stores data and user objects in memory for quick access and
 * retrieval. It uses the singelton principle and is used by the AppState class
 * as cache.
 */
public class Cache {
    private HashMap<String, Object> dataCache;
    private HashMap<String, User> userCache;

    /**
     * Constructs a new Cache object with two empty hash maps for data and user
     * objects.
     */
    public Cache() {
        dataCache = new HashMap<>();
        userCache = new HashMap<>();
    }

    /**
     * Retrieves cached data from the data hash map.
     *
     * @param key the key used to store the data
     * @return the cached data as type T
     */
    @SuppressWarnings("unchecked")
    public <T> T getCachedData(String key) {
        return (T) dataCache.get(key);
    }

    /**
     * Stores data in the data hash map using a key.
     *
     * @param key  the key used to store the data
     * @param data the data to store in the cache
     */
    public <T> void setCachedData(String key, T data) {
        dataCache.put(key, data);
    }

    /**
     * Retrieves a cached observable list of data from the data hash map.
     *
     * @param key the key used to store the data
     * @return the cached data as an observable list of type T
     */
    @SuppressWarnings("unchecked")
    public <T> ObservableList<T> getObservableListCachedData(String key) {
        return (ObservableList<T>) dataCache.get(key);
    }

    /**
     * Stores an observable list of data in the data hash map using a key
     *
     * @param key  the key used to store the data
     * @param data the observable list of data to store in the cache
     */
    public <T> void setObservableListCachedData(String key, ObservableList<T> data) {
        dataCache.put(key, data);
    }

    /**
     * Retrieves a cached user object from the user hash map.
     *
     * @param key the key used to store the user object
     * @return the cached user object
     */
    public User getUser(String key) {
        return userCache.get(key);
    }

    /**
     * Stores a user object in the user hash map.
     *
     * @param key  the key used to store the user object
     * @param user the user object to store in the cache
     */
    public void setUser(String key, User user) {
        userCache.put(key, user);
    }

    /**
     * Clears all cached data from the data hash map.
     */
    public void clearCache() {
        dataCache.clear();
    }

}