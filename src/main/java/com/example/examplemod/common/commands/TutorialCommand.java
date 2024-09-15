package com.example.examplemod.common.commands;

import com.example.examplemod.ExampleMod;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class TutorialCommand
{
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        LiteralArgumentBuilder<CommandSourceStack> tutorialCommand =
                // the base of the command ( /tutorial ) (can be anything)
                Commands.literal(ExampleMod.MODID)
                        // the permissions for the command: https://minecraft.fandom.com/wiki/Permission_level#Java_Edition_2
                        .requires(source -> source.hasPermission(2))
                        // next piece of the command ( /tutorial test )
                        .then(Commands.literal("test")
                                // run some function, using a method reference (only if there are only one parameter)
                                .executes(TutorialCommand::test)
                        )
                        // different second piece of the command ( /tutorial test2 )
                        .then(Commands.literal("test2")
                                // run another function, using a lambda
                                .executes(context -> test2(context))
                        )
                        // another different second piece ( /tutorial parameterTest )
                        .then(Commands.literal("parameterTest")
                                // create an integer input argument, with bound of 0 to 255 ( /tutorial parameterTest <input> )
                                .then(Commands.argument("input", IntegerArgumentType.integer(0, 255))
                                        // execute a function with the argument as an input
                                        .executes(context -> parameterTest(context, IntegerArgumentType.getInteger(context, "input")))
                                )
                        );

        dispatcher.register(tutorialCommand); // register the command
    }

    /**
     * A method run with an int as the input from the command.
     *
     * @param context - command context
     * @param input - integer from command parameter
     * @return - end function with return value of 1
     */
    static int parameterTest(CommandContext<CommandSourceStack> context, int input)
    {
        Player player = context.getSource().getPlayer(); // get the player who ran the command

        // check if the player is null; in case the command is run using a command block
        if (player != null)
            // display the message
            player.displayClientMessage(Component.literal("you inputted: " + input), false);

        return Command.SINGLE_SUCCESS; // return with an error code of 1
    }

    /**
     * Command method that demonstrates the flag parameter from Player.displayClientMessage().
     * Using false will display the chat message in the chat window.
     *
     * @param context - command context
     * @return - end function with return value of 1
     */
    static int test(CommandContext<CommandSourceStack> context)
    {
        Player player = context.getSource().getPlayer(); // get the player who ran the command

        // check if the player is null; in case the command is run using a command block
        if (player != null)
            // display the message
            player.displayClientMessage(Component.literal("command run..."), false);

        return Command.SINGLE_SUCCESS; // return with an error code of 1
    }

    /**
     * Command method that demonstrates the flag parameter from Player.DisplayClientMessage().
     * Using true will display the message above where item names show up above the player hotbar.
     *
     * @param context - command context
     * @return - end function with return value of 1
     */
    static int test2(CommandContext<CommandSourceStack> context)
    {
        Player player = context.getSource().getPlayer(); // get the player who ran the command

        // check if the player is null; in case the command is run using a command block
        if (player != null)
            // display the message
            player.displayClientMessage(Component.literal("command run..."), true);

        return Command.SINGLE_SUCCESS; // return with an error code of 1
    }
}