package gg.dimensionalmc.utils.listener

import club.minnced.discord.webhook.send.AllowedMentions
import club.minnced.discord.webhook.send.WebhookMessageBuilder
import gg.dimensionalmc.utils.Dimensional
import gg.dimensionalmc.utils.Dimensional.Companion.escape
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.util.*

class ChatListener(private val plugin: Dimensional) : Listener {
    @EventHandler
    fun onMessage(evt: AsyncPlayerChatEvent) {
        if (plugin.webhookClient == null) {
            return
        }

        val message = WebhookMessageBuilder()
            .setUsername(evt.player.name)
            .setAvatarUrl(getPlayerAvatar(evt.player.uniqueId))
            .setContent(escape(evt.message))
            .setAllowedMentions(AllowedMentions.none())

        plugin.webhookClient!!.send(message.build())
    }

    companion object {
        /**
         * Returns the URL of a player's avatar.
         * @param uuid The player's ID.
         */
        fun getPlayerAvatar(uuid: UUID) = "https://minotar.net/avatar/$uuid"
    }
}
