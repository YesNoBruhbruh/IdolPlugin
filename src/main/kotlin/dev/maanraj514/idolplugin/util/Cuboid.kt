package dev.maanraj514.idolplugin.util

import org.bukkit.Location
import org.bukkit.Particle

class Cuboid(posA: Location, posB: Location) {

    // make sure the pos1 and pos2 don't get switched up, rearrange.

    val world = posA.world

    var x1: Int
    var x2: Int
    var y1: Int
    var y2: Int
    var z1: Int
    var z2: Int

    init {
        x2 = posA.blockX.coerceAtLeast(posB.blockX)
        x1 = posA.blockX.coerceAtMost(posB.blockX)

        y2 = posA.blockY.coerceAtLeast(posB.blockY)
        y1 = posA.blockY.coerceAtMost(posB.blockY)

        z2 = posA.blockZ.coerceAtLeast(posB.blockZ)
        z1 = posA.blockZ.coerceAtMost(posB.blockZ)
    }

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