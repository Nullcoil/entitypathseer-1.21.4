package net.nullcoil.entitypathseer;

import net.fabricmc.api.ClientModInitializer;
import net.nullcoil.entitypathseer.network.NetworkHandler;

public class EntityPathSeerClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityPathSeer.LOGGER.info("[EntityPathSeer] Initializing client-side networking");
        NetworkHandler.initializeClient();
    }
}
