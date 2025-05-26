package fr.mirage.api.data;

public interface Database {

    void connect();
    void disconnect();

    boolean isConnected();

}