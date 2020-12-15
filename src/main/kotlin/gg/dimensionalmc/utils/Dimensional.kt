package gg.dimensionalmc.utils

import club.minnced.discord.webhook.WebhookClient
import gg.dimensionalmc.utils.commands.DimensionalCommand
import gg.dimensionalmc.utils.commands.DiscordCommand
import gg.dimensionalmc.utils.listener.ChatListener
import gg.dimensionalmc.utils.listener.ServerListener
import org.bukkit.ChatColor
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class Dimensional : JavaPlugin() {
  var webhookClient: WebhookClient? = null
    private set

  var chatPrefix = ChatColor.translateAlternateColorCodes('&', "&8&l(&9&lDimensionalUtils&8&l)&f")

  private val isChatLoggingEnabled: Boolean
    get() = !config.getString("chat-webhook.token")
      .isNullOrEmpty()

  override fun onEnable() {
    if (isChatLoggingEnabled) {
      val id = config.getLong("chat-webhook.id")
      val token = config.getString("chat-webhook.token")!!

      webhookClient = WebhookClient.withId(id, token)
      logger.info("Initialized the Webhook Client.")
    } else {
      logger.info("No webhook token specified.")
    }

    listOf(ServerListener(this), ChatListener(this))
      .forEach { this.server.pluginManager.registerEvents(it, this) }

    listOf(DiscordCommand(this), DimensionalCommand(this))
      .forEach { this.getCommand(it.name)?.setExecutor(it) }
  }

  override fun onDisable() {
    if (webhookClient != null) {
      webhookClient?.close()
      logger.info("Closed Webhook Client.")
    }
  }

  companion object {
    /**
     * Escapes text with format to Discord
     * @param str The string to escape
     * @return The escaped string
     */
    fun escape(str: String): String {
      return str
        .replace("*", "*****") // Escape '*' (workaround)
        .replace("_", "_____") // Escape '_' (yet another workaround)
        .replace("`", "'")
    }
  }
}