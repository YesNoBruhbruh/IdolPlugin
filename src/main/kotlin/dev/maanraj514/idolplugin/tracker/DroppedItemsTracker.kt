package dev.maanraj514.idolplugin.tracker

import dev.maanraj514.idolplugin.idol.IdolPlayer
import org.bukkit.entity.Item
import java.util.UUID

class DroppedItemsTracker {

    //                                                  item   player
    private val playerDroppedItems = mutableMapOf<UUID, UUID>()

    fun onDrop(item: Item, idolPlayer: IdolPlayer) {
        if (isTracked(item)) return

        val uuid = item.uniqueId

        playerDroppedItems[uuid] = idolPlayer.uuid
    }

    fun onPickup(item: Item) {
        playerDroppedItems.remove(item.uniqueId)
    }

    fun isTracked(item: Item): Boolean {
        val uuid = item.uniqueId

        return playerDroppedItems.containsKey(uuid)
    }

    fun getDroppedItems(): Map<UUID, UUID> {
        return playerDroppedItems
    }
}