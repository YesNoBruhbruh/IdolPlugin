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
        x1 = posA.x.toInt()
        x2 = posB.x.toInt()

        y1 = posA.y.toInt()
        y2 = posB.y.toInt()

        z1 = posA.z.toInt()
        z2 = posB.z.toInt()

        if (x1 > x2 && y1 > y2 && z1 > z2) {
            // switch both.
            x1 = x2
            x2 = posA.x.toInt()

            y1 = y2
            y2 = posA.y.toInt()

            z1 = z2
            z2 = posA.z.toInt()
        }
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