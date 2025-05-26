package fr.mirage.core.spigot.queue;

import fr.mirage.api.queue.MatchQueue;
import fr.mirage.api.queue.QueueManager;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class QueueRepository implements QueueManager {

    private final Set<MatchQueue> queues;

    public QueueRepository() {
        this.queues = new HashSet<>();
    }

}