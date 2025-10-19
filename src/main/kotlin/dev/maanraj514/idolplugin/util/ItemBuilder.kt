package dev.maanraj514.idolplugin.util

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class ItemBuilder(private val itemStack: ItemStack) {

    constructor(material: Material) : this(ItemStack(material))

    private val itemMeta = itemStack.itemMeta

    fun name(name: Component): ItemBuilder {
        itemMeta.itemName(name)
        return this
    }

    fun lore(lore: List<Component>): ItemBuilder {
        itemMeta.lore(lore)
        return this
    }

    fun amount(amount: Int): ItemBuilder {
        itemStack.amount = amount
        return this
    }

    fun build(): ItemStack {
        itemStack.itemMeta = itemMeta
        return itemStack
    }
}