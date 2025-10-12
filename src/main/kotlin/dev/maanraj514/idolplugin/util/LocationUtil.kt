package dev.maanraj514.idolplugin.util

import org.bukkit.Location
import java.util.UUID

object LocationUtil {

    val playerPosA = mutableMapOf<UUID, Location>()
    val playerPosB = mutableMapOf<UUID, Location>()

    fun createCuboid(uuid: UUID): Cuboid? {
        val posA = playerPosA[uuid]
        val posB = playerPosB[uuid]

        if (posA == null || posB == null) return null

        return Cuboid(posA, posB)
    }
}