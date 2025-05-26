package fr.mirage.api.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public interface QueueManager {

    Logger log = LoggerFactory.getLogger(QueueManager.class);

    Set<MatchQueue> getQueues();

}