package dev.maanraj514.idolplugin.idol.filter

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

abstract class IdolHandler {

    protected val handledItems = mutableMapOf<Material, Int>()

    fun isIdolItem(itemStack: ItemStack): Boolean {
        return handledItems.contains(itemStack.type)
    }

    fun addIdolItem(itemStack: ItemStack, trustPoints: Int) {
        if (isIdolItem(itemStack)) return

        handledItems[itemStack.type] = trustPoints
    }

    fun removeIdolItem(itemStack: ItemStack) {
        handledItems.remove(itemStack.type)
    }
}