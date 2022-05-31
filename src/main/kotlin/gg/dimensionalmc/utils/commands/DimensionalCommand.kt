package gg.dimensionalmc.utils.commands

import gg.dimensionalmc.utils.Dimensional
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class DimensionalCommand(private val plugin: Dimensional) : ICommand {
    override val name: String
        get() = "dimensional"

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage("${plugin.chatPrefix} DimensionalUtils ${plugin.description.version} by 2D (@melike2d)")
            return true
        }

        if (args[0].equals("reload", true)) {
            plugin.reloadConfig()
            sender.sendMessage("${plugin.chatPrefix} Reloaded the Configuration.")
            return true
        }

        sender.sendMessage("${plugin.chatPrefix} Unrecognized Command \"${args[0]}\".")
        return true
    }
}
