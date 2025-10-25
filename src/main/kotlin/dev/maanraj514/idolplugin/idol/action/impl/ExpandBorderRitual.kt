package dev.maanraj514.idolplugin.idol.action.impl

import dev.maanraj514.idolplugin.idol.action.Ritual
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.util.UUID

class ExpandBorderRitual(
    override val requiredItems: MutableList<ItemStack>,
    override val requiredTrust: Int,
    private val incrementSize: Int,
    private val worldId: UUID
) : Ritual {

    override fun getIcon(): Material {
        return Material.BARRIER
    }

    override fun performRitual() {
        val world = Bukkit.getWorld(worldId) ?: return

        val border = world.worldBorder
        border.size += incrementSize
    }
}