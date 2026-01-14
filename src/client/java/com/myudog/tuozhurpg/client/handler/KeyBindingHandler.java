package com.myudog.tuozhurpg.client.handler;

import com.myudog.tuozhurpg.TuoZhuRpg;
import com.myudog.tuozhurpg.client.gui.TestScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class KeyBindingHandler {

    public static KeyBinding testScreenKey;

    private static final Identifier GENERAL_ID = Identifier.of("key.category." + TuoZhuRpg.MOD_ID + ".general");
    private static final Identifier TEST_ID = Identifier.of("key.category." + TuoZhuRpg.MOD_ID + ".test");

    public static final KeyBinding.Category GENERAL_CATEGORY = new KeyBinding.Category(GENERAL_ID);
    public static final KeyBinding.Category TEST_CATEGORY = new KeyBinding.Category(TEST_ID);

    public static void register() {
        registerKeys();
        ClientTickEvents.END_CLIENT_TICK.register(KeyBindingHandler::onClientTick);
    }

    private static void registerKeys() {
        testScreenKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                toKey("test_screen"), InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_K, TEST_CATEGORY
        ));
    }

    private static void onClientTick(MinecraftClient client) {
        if (client.player == null) return;

        handleGlobalOverride(client);
    }

    private static void handleGlobalOverride(MinecraftClient client) {
        while (testScreenKey.wasPressed()) {
            MinecraftClient.getInstance().setScreen(new TestScreen());
        }
    }

    // --- 輔助方法 ---
    private static String toKey(String id) {
        return "key." + TuoZhuRpg.MOD_ID + "." + id;
    }


}
