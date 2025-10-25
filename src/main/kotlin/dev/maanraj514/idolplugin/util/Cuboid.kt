package dev.maanraj514.idolplugin.util

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.World

data class Cuboid(private val posA: Location, private val posB: Location) {

    // make sure the pos1 and pos2 don't get switched up, rearrange.

    val world: World = posA.world

    var x1: Int = posA.blockX.coerceAtMost(posB.blockX)
    var x2: Int = posA.blockX.coerceAtLeast(posB.blockX)
    var y1: Int = posA.blockY.coerceAtMost(posB.blockY)
    var y2: Int = posA.blockY.coerceAtLeast(posB.blockY)
    var z1: Int = posA.blockZ.coerceAtMost(posB.blockZ)
    var z2: Int = posA.blockZ.coerceAtLeast(posB.blockZ)

    fun inRegion(location: Location): Boolean {
        val x = location.x.toInt()
        val y = location.y.toInt()
        val z = location.z.toInt()

        return x in x1..x2 && y in y1..y2 && z in z1..z2
    }

    fun findCenter(): Location {
        return Location(world, ((x1+x2)/2).toDouble(), ((y1+y2)/2).toDouble(), ((z1+z2)/2).toDouble())
    }

    fun display() {
        for (x in x1..x2) {
            for (y in y1..y2) {
                for (z in z1..z2) {
                    world.spawnParticle(
                        Particle.HAPPY_VILLAGER,
                        x.toDouble(),
                        y.toDouble(),
                        z.toDouble(),
                        5)
                }
            }
        }
    }
}