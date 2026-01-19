package com.myudog.tuozhurpg.client;

import com.myudog.tuozhurpg.client.handler.KeyBindingHandler;
import net.fabricmc.api.ClientModInitializer;

public class TuoZhuRpgClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        KeyBindingHandler.register();
    }
}
