package dev.maanraj514.idolplugin.listener

import dev.maanraj514.idolplugin.idol.IdolManager
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.entity.ItemDespawnEvent
import org.bukkit.event.player.PlayerDropItemEvent

class IdolListener(private val idolManager: IdolManager) : Listener {

    @EventHandler
    fun onItemDropped(event: PlayerDropItemEvent) {
        val item = event.itemDrop
        if (item.itemStack.type == Material.AIR) return

        val player = event.player
        val idolPlayer = idolManager.getOrCreateIdolPlayer(player)

        idolManager.droppedItemsTracker.onDrop(item, idolPlayer)
    }

    @EventHandler
    fun onItemDespawn(event: ItemDespawnEvent) {
        idolManager.droppedItemsTracker.onPickup(event.entity)
    }

    @EventHandler
    fun onItemPickup(event: EntityPickupItemEvent) {
        idolManager.droppedItemsTracker.onPickup(event.item)
    }
}