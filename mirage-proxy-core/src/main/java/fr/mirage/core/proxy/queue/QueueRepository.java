package fr.mirage.core.proxy.queue;

import fr.mirage.api.queue.MatchQueue;
import fr.mirage.api.queue.QueueManager;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class QueueRepository implements QueueManager {

    private static final int MAX_THREADS = 7; // ?? TODO: NEED INVESTIGATIONS
    private static final boolean USE_VIRTUAL_THREAD = true;

    private final Set<MatchQueue> queues;

    private final QueueRunnable queueRunnable;

    public QueueRepository() {
        this.queues = new HashSet<>();
        this.queueRunnable = new QueueRunnable(queues, MAX_THREADS, USE_VIRTUAL_THREAD);
    }

}