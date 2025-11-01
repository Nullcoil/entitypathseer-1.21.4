package net.nullcoil.entitypathseer;

import net.fabricmc.api.ModInitializer;

import net.nullcoil.entitypathseer.network.NetworkHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityPathSeer implements ModInitializer {
	public static final String MOD_ID = "entitypathseer";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        LOGGER.info("[EntityPathSeer] Initializing server-side networking");
        NetworkHandler.initialize();
	}
}