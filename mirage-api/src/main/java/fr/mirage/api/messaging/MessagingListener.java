package fr.mirage.api.messaging;

import java.util.function.Consumer;

public interface MessagingListener<T extends MessagingPacket> {

    String getChannel();

    Consumer<T> onReceive();

}