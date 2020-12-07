package gg.dimensionalmc.utils.commands

import gg.dimensionalmc.utils.Dimensional
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class DiscordCommand(private val plugin: Dimensional) : ICommand {
  override val name = "discord"

  override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
    val discord = plugin.config.getString("links.discord")
    if (discord.isNullOrEmpty()) {
      sender.sendMessage(
        "${plugin.chatPrefix} ${
          ChatColor.translateAlternateColorCodes(
            '&',
            "&4&lError&r: No link provided, Contact the Server Admins."
          )
        }"
      )
      return true
    }

    val text = ChatColor.translateAlternateColorCodes('&', buildString {
      appendLine("Here's the link to our Discord Server")
      appendLine("&9&l$discord&r")
    })

    sender.sendMessage(text)
    return true
  }
}