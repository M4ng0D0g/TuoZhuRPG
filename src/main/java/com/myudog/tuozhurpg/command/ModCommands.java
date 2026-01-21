package com.myudog.tuozhurpg.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.myudog.tuozhurpg.command.custom.PermissionLogic;
import com.myudog.tuozhurpg.command.custom.TestCommand;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ModCommands {

    // 定義角色建議清單 (Tab 補全用)
    private static final SuggestionProvider<ServerCommandSource> ROLE_SUGGESTIONS = (context, builder) -> {
        builder.suggest("visitor");
        builder.suggest("member");
        builder.suggest("operator");
        builder.suggest("none");
        return builder.buildFuture();
    };

    public static void register(
            CommandDispatcher<ServerCommandSource> dispatcher,
            CommandRegistryAccess registryAccess,
            CommandManager.RegistrationEnvironment environment) {

        dispatcher.register(
                literal("test_command").executes(TestCommand::runTestCommand)
        );

        dispatcher.register(
                literal("permission_command").requires(source -> source.hasPermissionLevel(2))
                .then(literal("check").executes(TestCommand::runPermissionCommand))
        );

        dispatcher.register(literal("permission")
                .requires(source -> source.hasPermissionLevel(2))

                .then(literal("allow")
                        .then(argument("targets", EntityArgumentType.players())
                                .then(literal("global")
                                        .then(argument("level", IntegerArgumentType.integer(0))
                                                .executes(PermissionLogic::executeGlobalSet)
                                        )
                                )
                                .then(literal("region")
                                        .then(argument("role", StringArgumentType.word())
                                                .suggests(ROLE_SUGGESTIONS) // 綁定 Tab 建議
                                                .executes(PermissionLogic::executeRegionSet)
                                        )
                                )
                                .then(literal("block")
                                        .then(literal("lock")
                                                .executes(PermissionLogic::executeBlockLock)
                                        )
                                        .then(argument("role", StringArgumentType.word())
                                                .suggests(ROLE_SUGGESTIONS)
                                                .executes(PermissionLogic::executeBlockSet)
                                        )
                                )
                        )

                )
                .then(literal("deny"))
                .then(literal("set")
                        .then(argument("targets", EntityArgumentType.players())
                                .then(literal("global")
                                        .then(argument("level", IntegerArgumentType.integer(0))
                                                .executes(PermissionLogic::executeGlobalSet)
                                        )
                                )
                                .then(literal("region")
                                        .then(argument("role", StringArgumentType.word())
                                                .suggests(ROLE_SUGGESTIONS) // 綁定 Tab 建議
                                                .executes(PermissionLogic::executeRegionSet)
                                        )
                                )
                                .then(literal("block")
                                        .then(literal("lock")
                                                .executes(PermissionLogic::executeBlockLock)
                                        )
                                        .then(argument("role", StringArgumentType.word())
                                                .suggests(ROLE_SUGGESTIONS)
                                                .executes(PermissionLogic::executeBlockSet)
                                        )
                                )
                        )
                )
                .then(literal("query"))
                .then(literal("modify")
                        .then(literal("global")
                                .then(argument("level", IntegerArgumentType.integer(0))
                                        .executes(PermissionLogic::executeGlobalSet)
                                )
                        )
                        .then(literal("region")
                                .then(argument("role", StringArgumentType.word())
                                        .suggests(ROLE_SUGGESTIONS) // 綁定 Tab 建議
                                        .executes(PermissionLogic::executeRegionSet)
                                )
                        )
                        .then(literal("block")
                                .then(literal("lock")
                                        .executes(PermissionLogic::executeBlockLock)
                                )
                                .then(argument("role", StringArgumentType.word())
                                        .suggests(ROLE_SUGGESTIONS)
                                        .executes(PermissionLogic::executeBlockSet)
                                )
                        ))
        );
    }




}
