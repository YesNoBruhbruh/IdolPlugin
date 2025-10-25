package dev.maanraj514.idolplugin.idol

import dev.maanraj514.idolplugin.gui.GUIService
import dev.maanraj514.idolplugin.idol.action.Ritual
import dev.maanraj514.idolplugin.tracker.DroppedItemsTracker
import dev.maanraj514.idolplugin.util.Cuboid
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.UUID

class IdolManager(private val guiService: GUIService) {

    private val idols = mutableMapOf<String, Idol>()
    private val idolPlayers = mutableMapOf<UUID, IdolPlayer>()
    val droppedItemsTracker = DroppedItemsTracker()

    fun createIdol(
        name: String,
        cuboid: Cuboid,
        donationToPoints: MutableMap<Material, Int>,
        wishToPoints: MutableMap<Material, Int>,
        ritualToPoints: MutableList<Ritual>) {
        idols[name] = Idol(name, cuboid, guiService, donationToPoints, wishToPoints, ritualToPoints)
    }

    fun getIdol(name: String): Idol? {
        return idols[name]
    }

    fun getOrCreateIdolPlayer(player: Player): IdolPlayer {
        val uuid = player.uniqueId
        return getOrCreateIdolPlayer(uuid)
    }

    fun getOrCreateIdolPlayer(uuid: UUID): IdolPlayer {
        return idolPlayers.getOrPut(uuid, {IdolPlayer(uuid, 0)})
    }

    // TODO save all to database

    fun getIdols(): Map<String, Idol> {
        return idols
    }

    fun cleanup() {
        idols.clear()
        idolPlayers.clear()
    }
}