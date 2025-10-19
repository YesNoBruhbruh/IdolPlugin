package dev.maanraj514.idolplugin.idol.filter.impl

import dev.maanraj514.idolplugin.idol.filter.IdolHandler
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

// For handling adding/subtracting points
class DonationHandler : IdolHandler() {

    fun getIdolItemPoints(itemStack: ItemStack, amount: Int = 1): Int {
        return handledItems.getOrDefault(itemStack.type, -1) * amount
    }
}