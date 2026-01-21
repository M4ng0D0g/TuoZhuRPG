package com.myudog.tuozhurpg;

import com.myudog.tuozhurpg.block.ModBlocks;
import com.myudog.tuozhurpg.block.entity.ModBlockEntities;
import com.myudog.tuozhurpg.item.ModItemGroups;
import com.myudog.tuozhurpg.item.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TuoZhuRpg implements ModInitializer {
    public static final String MOD_ID = "tuozhurpg";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Hello Fabric world!");

        ModItemGroups.init();
        ModItems.init();
        ModBlocks.init();
        ModBlockEntities.init();

    }
}
