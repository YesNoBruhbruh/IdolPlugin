package dev.maanraj514.idolplugin.config.impl

import dev.maanraj514.idolplugin.IdolPlugin
import dev.maanraj514.idolplugin.config.CustomConfig
import dev.maanraj514.idolplugin.idol.Idol
import org.bukkit.Material

class IdolConfig(idol: Idol, plugin: IdolPlugin) : CustomConfig(idol.name, plugin) {

    init {
        val donationSection = configuration.createSection("donation")

        val pos1Section = donationSection.createSection("pos1")
        pos1Section.set("world", idol.cuboid.world.name)
        pos1Section.set("x", idol.cuboid.x1)
        pos1Section.set("y", idol.cuboid.y1)
        pos1Section.set("z", idol.cuboid.z1)

        val pos2Section = donationSection.createSection("pos2")
        pos2Section.set("world", idol.cuboid.world.name)
        pos2Section.set("x", idol.cuboid.x2)
        pos2Section.set("y", idol.cuboid.y2)
        pos2Section.set("z", idol.cuboid.z2)

        val acceptableItemStringList = mutableListOf<String>()

        for (donationEntry in idol.getDonationToPoints().entries) {
            val donationMaterial = donationEntry.key
            val donationPoints = donationEntry.value

            acceptableItemStringList.add(serializeItem(donationMaterial, donationPoints))
        }
        
        donationSection.set(
            "acceptable-item-filter",
            acceptableItemStringList)

        val unacceptableItemStringList = mutableListOf<String>()

        for (penalizedItemEntry in idol.getPenalizedItems().entries) {
            val penalizedMaterial = penalizedItemEntry.key
            val penalizedPoints = penalizedItemEntry.value

            unacceptableItemStringList.add(serializeItem(penalizedMaterial, penalizedPoints))
        }

        donationSection.set(
            "unacceptable-item-filter",
            unacceptableItemStringList
        )

        val superProfitCycleSection = donationSection.createSection("super-profit-cycle")
        superProfitCycleSection.set("enabled", true)
        superProfitCycleSection.set("multiplier", 1.2)
        superProfitCycleSection.set("interval", 1)

        val wishingSection = configuration.createSection("wishing")

        val wishList = mutableListOf<String>()

        for (wishEntry in idol.getWishToPoints()) {
            val wishMaterial = wishEntry.key
            val wishPoints = wishEntry.value

            wishList.add(serializeItem(wishMaterial, wishPoints))
        }

        wishingSection.set("wish-list", wishList)

        val successRate = wishingSection.createSection("success-rate")
        successRate.set("always-default", false)
        successRate.set("scaling", 1)

        save()
        reload()
    }

    fun updateConfig(idol: Idol) {
        //TODO just do the same methods as init, but don't create any sections, just get.
    }

    fun getIdol(): Idol {
        // TODO return a new Idol from the information of the config.
    }

    private fun serializeItem(material: Material, points: Int): String {
        return "${material.name}-$points"
    }

    private fun deserializeItem(path: String): Pair<Material, Int> {
        val split = path.split("-")
        val material = Material.getMaterial(split[0]) ?: Material.BEDROCK
        val cost = split[1].toInt()

        return Pair(material, cost)
    }
}