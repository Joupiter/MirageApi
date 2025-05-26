package fr.mirage.core.proxy;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import fr.mirage.api.MirageApi;
import fr.mirage.core.proxy.listener.ProxyConnectionListener;
import fr.mirage.core.proxy.listener.ProxyPingListener;
import org.slf4j.Logger;

@Plugin(
        id = "mirageapi",
        name = "MirageApi",
        version = "1.0.0"
)
public class MirageProxyPlugin {

    private final ProxyServer server;
    private final Logger logger;

    private MirageApi alvaApi;

    @Inject
    public MirageProxyPlugin(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;

        logger.info("Mirage api proxy started...");
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        // Initialization
        this.alvaApi = MirageApi.setProvider(new MirageApiProvider(MirageApiProvider.ApiMode.DEV_NODB));

        alvaApi.init();

        registerEvents(
                new ProxyPingListener(server),
                new ProxyConnectionListener(logger, alvaApi.getUserManager())
        );
    }

    private void registerEvents(Object... events) {
        for (Object event : events)
            server.getEventManager().register(this, event);
    }

    @Subscribe
    public void onShutdown(ProxyShutdownEvent event) {
        alvaApi.shutdown();
    }

}