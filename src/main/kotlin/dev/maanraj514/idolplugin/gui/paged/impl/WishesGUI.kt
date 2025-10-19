package dev.maanraj514.idolplugin.gui.paged.impl

import dev.maanraj514.idolplugin.gui.GUI
import dev.maanraj514.idolplugin.gui.GUIButton
import dev.maanraj514.idolplugin.gui.GUISettings
import dev.maanraj514.idolplugin.gui.paged.PagedGUI
import dev.maanraj514.idolplugin.idol.IdolPlayer
import dev.maanraj514.idolplugin.util.ItemBuilder
import dev.maanraj514.idolplugin.util.toComponent
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class WishesGUI(idolPlayer: IdolPlayer) : PagedGUI(idolPlayer, GUISettings("<gold>Wishes!</gold>".toComponent(), 54)) {

    init {
        guiSettings.guiTitle = "<gold><bold>Current points: ${idolPlayer.trust}</bold></gold>".toComponent()

        cancelClicks = true

        for (i in 0 until 50) {
            addButton(i,GUIButton(
                {
                    ItemStack(Material.DIRT)
                },
                {
                    val slot = it.slot
                    println("You clicked on my DIRT slot! which is ${slot}!")
                }))
        }
        for (i in 50 until 100) {
            addButton(i,GUIButton(
                {
                    ItemStack(Material.BEDROCK)
                }, {
//                    val player = (it.whoClicked as Player)
//                    player.sendMessage("<light_purple>You have clicked on wish 1!</light_purple>".toComponent())
                    val slot = it.slot
                    println("You clicked on my BEDROCK slot! which is ${slot}!")
                }))
        }
    }
}