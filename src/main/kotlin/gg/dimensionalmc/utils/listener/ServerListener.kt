package gg.dimensionalmc.utils.listener

import club.minnced.discord.webhook.send.WebhookEmbed
import club.minnced.discord.webhook.send.WebhookEmbedBuilder
import gg.dimensionalmc.utils.Dimensional
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.server.ServerListPingEvent
import java.io.File
import java.time.Instant

class ServerListener(private val plugin: Dimensional) : Listener {
    private var colorInt: Int

    init {
        val hex = plugin.config.getString("main-color")!!.replace("#", "")
        colorInt = hex.toInt(16)
    }

    @EventHandler
    fun onServerListPing(evt: ServerListPingEvent) {
        val mainColor = plugin.config.getString("main-color")!!

        val motd: TextComponent.Builder = Component.text()
            .append(Component.text("Welcome to "))
            .append(
                Component.text("Dimensional")
                    .color(TextColor.fromHexString(mainColor))
                    .decoration(TextDecoration.BOLD, true)
            )
            .append(Component.newline())
            .append(Component.text("Have fun! "))

        val discord = plugin.config.getString("links.discord")
        if (discord != null) {
            motd.append(
                Component.text(discord)
                    .color(TextColor.fromHexString(mainColor))
                    .decorate(TextDecoration.ITALIC)
                    .decorate(TextDecoration.UNDERLINED)
            )
        }

        evt.motd(motd.build())
        evt.setServerIcon(Bukkit.loadServerIcon(File("server-icon.png")))
    }

    @EventHandler
    fun onPlayerJoined(evt: PlayerJoinEvent) {
        val webhook = plugin.webhookClient
            ?: return

        val embed = WebhookEmbedBuilder()
            .setAuthor(WebhookEmbed.EmbedAuthor("Player Joined",
                ChatListener.getPlayerAvatar(evt.player.uniqueId),
                null))
            .setDescription("Player **${evt.player.name}** has joined.")
            .setTimestamp(Instant.now())
            .setColor(colorInt)

        webhook.send(embed.build())
    }

    @EventHandler
    fun onPlayerQuit(evt: PlayerQuitEvent) {
        val webhook = plugin.webhookClient
            ?: return

        val embed = WebhookEmbedBuilder()
            .setAuthor(WebhookEmbed.EmbedAuthor("Player Joined",
                ChatListener.getPlayerAvatar(evt.player.uniqueId),
                null))
            .setDescription("Player **${evt.player.name}** has quit.")
            .setTimestamp(Instant.now())
            .setColor(colorInt)

        webhook.send(embed.build())
    }
}
