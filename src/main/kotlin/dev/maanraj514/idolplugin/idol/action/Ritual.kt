package dev.maanraj514.idolplugin.idol.action

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

// All the ritual functions will be hardcoded, except for the requiredItems and
// the requiredTrust.
interface Ritual {

    val requiredItems: MutableList<ItemStack>
    val requiredTrust: Int

    fun getIcon(): Material

    fun performRitual()
}