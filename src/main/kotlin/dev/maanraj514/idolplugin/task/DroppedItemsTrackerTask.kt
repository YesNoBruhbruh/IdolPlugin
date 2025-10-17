package dev.maanraj514.idolplugin.task

import dev.maanraj514.idolplugin.idol.IdolManager
import org.bukkit.Bukkit
import org.bukkit.entity.Item
import kotlin.collections.iterator

// Should probably remove the need for idolManager as it doesn't fit with the class's
// responsibility and name.
class DroppedItemsTrackerTask(private val idolManager: IdolManager) : Runnable {

    override fun run() {
        for (droppedItemEntry in idolManager.droppedItemsTracker.getDroppedItems()) {
            val itemEntity = Bukkit.getEntity(droppedItemEntry.key) ?: continue
            val idolPlayer = idolManager.getOrCreateIdolPlayer(droppedItemEntry.value)

            for (idolEntry in idolManager.getIdols()) {
                val idol = idolEntry.value

                if (idol.onDonation(itemEntity as Item, idolPlayer)){ // this means the correct idol was found
                    break
                }
            }
        }
    }
}