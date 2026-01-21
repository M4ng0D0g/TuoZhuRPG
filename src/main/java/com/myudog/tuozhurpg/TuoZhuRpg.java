package com.myudog.tuozhurpg;

import com.myudog.tuozhurpg.command.ModCommands;
import com.myudog.tuozhurpg.event.InteractionHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TuoZhuRpg implements ModInitializer {
    public static final String MOD_ID = "tuozhurpg";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Hello Fabric world!");

        UseBlockCallback.EVENT.register(InteractionHandler::onBlockInteract);
        CommandRegistrationCallback.EVENT.register(ModCommands::register);
    }
}
