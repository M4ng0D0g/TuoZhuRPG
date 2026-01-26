package com.myudog.tuozhurpg.component.item;

import com.myudog.tuozhurpg.TuoZhuRpg;
import com.myudog.tuozhurpg.api.attribute.custom.RpgInnate;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItemComponents {

    public static final ComponentType<AttributeContainer<RpgInnate>> INNATES = ComponentType.<AttributeContainer<RpgInnate>>builder()
            .codec(AttributeContainer.createCodec(RpgInnate.class))
            .build();

    public static final ComponentType<SocketContainer> SOCKETS = ComponentType.<SocketContainer>builder()
            .codec(SocketContainer.CODEC)
            .build();

    public static void register() {
        Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(TuoZhuRpg.MOD_ID, "innates"), INNATES);
        Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(TuoZhuRpg.MOD_ID, "sockets"), SOCKETS);
    }
}
