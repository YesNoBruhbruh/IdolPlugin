package dev.maanraj514.idolplugin.idol

import dev.maanraj514.idolplugin.util.Cuboid
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.UUID

class IdolManager {

    private val idols = mutableMapOf<String, Idol>()
    private val idolPlayers = mutableMapOf<UUID, IdolPlayer>()

    fun createIdol(name: String, cuboid: Cuboid, donationFilter: MutableMap<Material, Int>) {
        idols[name] = Idol(name, cuboid, donationFilter)
    }

    fun getIdol(name: String): Idol? {
        return idols[name]
    }

    fun getOrCreateIdolPlayer(player: Player): IdolPlayer {
        val uuid = player.uniqueId
        if (!idolPlayers.containsKey(uuid)) {
            println("the uuid $uuid is not present in the map!")
        }
        return idolPlayers.getOrPut(uuid, {IdolPlayer(uuid, 0)})
    }

    // TODO save all to database

    fun getIdols(): Map<String, Idol> {
        return idols
    }

    fun clear() {
        idols.clear()
        idolPlayers.clear()
    }
}