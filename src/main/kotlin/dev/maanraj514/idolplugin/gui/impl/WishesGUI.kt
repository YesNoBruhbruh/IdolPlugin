package dev.maanraj514.idolplugin.gui.impl

import dev.maanraj514.idolplugin.gui.GUI
import dev.maanraj514.idolplugin.gui.GUIButton
import dev.maanraj514.idolplugin.gui.GUISettings
import dev.maanraj514.idolplugin.util.toComponent
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class WishesGUI : GUI(GUISettings("<gold>Wishes!</gold>".toComponent(), 9*3)) {

    init {

        cancelClicks = true

        val wish1 = GUIButton(
            {
            ItemStack(Material.AMETHYST_SHARD)
        }, {
            val player = (it.whoClicked as Player)
                player.sendMessage("<light_purple>You have clicked on wish 1!</light_purple>".toComponent())
        })
        val wish2 = GUIButton(
            {
                ItemStack(Material.AMETHYST_SHARD)
            }, {
                val player = (it.whoClicked as Player)
                player.sendMessage("<light_purple>You have clicked on wish 2!</light_purple>".toComponent())
            })
        val wish3 = GUIButton(
            {
                ItemStack(Material.AMETHYST_SHARD)
            }, {
                val player = (it.whoClicked as Player)
                player.sendMessage("<light_purple>You have clicked on wish 3!</light_purple>".toComponent())
            })
        val wish4 = GUIButton(
            {
                ItemStack(Material.AMETHYST_SHARD)
            }, {
                val player = (it.whoClicked as Player)
                player.sendMessage("<light_purple>You have clicked on wish 4!</light_purple>".toComponent())
            })

        addButton(10, wish1)
        addButton(12, wish2)
        addButton(14, wish3)
        addButton(16, wish4)
    }
}