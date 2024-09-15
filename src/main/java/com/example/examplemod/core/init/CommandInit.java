package com.example.examplemod.core.init;

import com.example.examplemod.common.commands.TutorialCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CommandInit
{
    @SubscribeEvent
    public static void registerModCommands(final RegisterCommandsEvent event)
    {
        // get the game's command dispatcher
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        // register the commands
        TutorialCommand.register(dispatcher);
    }
}