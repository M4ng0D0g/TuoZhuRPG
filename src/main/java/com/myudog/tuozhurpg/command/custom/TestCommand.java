package com.myudog.tuozhurpg.command.custom;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class TestCommand {

    public static int runTestCommand(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        source.sendFeedback(() -> Text.literal("Called /test_command."), false);
        return 1;

    }

    public static int runPermissionCommand(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        source.sendFeedback(() -> Text.literal("You have the permission."), false);
        return 1;
    }
}
