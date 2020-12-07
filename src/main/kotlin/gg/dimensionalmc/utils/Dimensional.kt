package gg.dimensionalmc.utils

import club.minnced.discord.webhook.WebhookClient
import gg.dimensionalmc.utils.commands.DimensionalCommand
import gg.dimensionalmc.utils.commands.DiscordCommand
import gg.dimensionalmc.utils.listener.ChatListener
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerListPingEvent
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Dimensional : JavaPlugin(), Listener {
  var webhookClient: WebhookClient? = null
    private set

  var chatPrefix = ChatColor.translateAlternateColorCodes('&', "&8&l(&9&lDimensionalUtils&8&l)&f")

  private val isChatLoggingEnabled: Boolean
    get() = !config.getString("chat-webhook.token")
      .isNullOrEmpty()

  private val serializer: LegacyComponentSerializer =
    LegacyComponentSerializer.builder().hexColors().useUnusualXRepeatedCharacterHexFormat().build()

  override fun onEnable() {
    if (isChatLoggingEnabled) {
      val id = config.getLong("chat-webhook.id")
      val token = config.getString("chat-webhook.token")!!

      webhookClient = WebhookClient.withId(id, token)
      logger.info("Initialized the Webhook Client.")
    } else {
      logger.info("No webhook token specified.")
    }

    listOf(this, ChatListener(this))
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

  @EventHandler
  fun onServerListPing(evt: ServerListPingEvent) {
    val mainColor = this.config.getString("main-color")!!

    val motd: TextComponent.Builder = Component.text()
      .append(Component.text("Welcome to "))
      .append(
        Component.text("Dimensional")
          .color(TextColor.fromHexString(mainColor))
          .decoration(TextDecoration.BOLD, true)
      )
      .append(Component.newline())
      .append(Component.text("Have fun! "))

    val discord = config.getString("links.discord")
    if (discord != null) {
      motd.append(
        Component.text(discord)
          .color(TextColor.fromHexString(mainColor))
          .decorate(TextDecoration.ITALIC)
          .decorate(TextDecoration.UNDERLINED)
      )
    }

    evt.motd = serializer.serialize(motd.build())
    evt.setServerIcon(Bukkit.loadServerIcon(File("server-icon.png")))
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