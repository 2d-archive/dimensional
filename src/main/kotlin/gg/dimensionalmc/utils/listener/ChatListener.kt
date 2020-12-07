package gg.dimensionalmc.utils.listener

import gg.dimensionalmc.utils.Dimensional
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class ChatListener(private val plugin: Dimensional) : Listener {
  @EventHandler
  fun onMessage(evt: AsyncPlayerChatEvent) {
    if (plugin.webhookClient == null) {
      return
    }

    plugin.webhookClient!!.send("**${Dimensional.escape(evt.player.name)}**: ${Dimensional.escape(evt.message)}")
  }
}