package gg.dimensionalmc.utils.commands

import org.bukkit.command.CommandExecutor

interface ICommand : CommandExecutor {
    val name: String
}

