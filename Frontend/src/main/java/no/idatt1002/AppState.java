package no.idatt1002;

import no.idatt1002.repository.Cache;

/**
 * Appstate is a singelton class for managing the state of the application
 */
public class AppState {
    private static AppState instance;
    private Cache cache;
    private String jwtToken;

    private AppState() {
        cache = new Cache();
    }

    /**
     * Method for getting the instance of the AppState
     * 
     * @return instance of AppState
     */
    public static synchronized AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public Cache getCache() {
        return cache;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
