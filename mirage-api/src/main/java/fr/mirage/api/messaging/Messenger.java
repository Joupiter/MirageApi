package fr.mirage.api.messaging;

import java.util.Set;

public interface Messenger {

    Set<MessagingListener<?>> getListeners();

    void registerListener(MessagingListener<?> messagingListener);

    void sendPacket(MessagingPacket packet);

}