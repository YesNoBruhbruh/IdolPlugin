package dev.maanraj514.idolplugin.listener

import dev.maanraj514.idolplugin.idol.IdolManager
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent

class IdolListener(private val idolManager: IdolManager) : Listener {

    @EventHandler
    fun onItemDropped(event: PlayerDropItemEvent) {
        val item = event.itemDrop
        if (item.itemStack.type == Material.AIR) return

        val player = event.player
        val idolPlayer = idolManager.getOrCreateIdolPlayer(player)

        for (idolEntry in idolManager.getIdols()) {
            val idol = idolEntry.value

            if (idol.onDonation(item, idolPlayer)){ // this means the correct idol was found
                break
            }
        }
    }
}