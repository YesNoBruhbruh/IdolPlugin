package dev.maanraj514.idolplugin.gui

import dev.maanraj514.idolplugin.idol.IdolPlayer
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class GUIService(private val guiRegistry: GUIRegistry) {

    fun openGUI(player: Player, gui: GUI) {
        val inventory = gui.inventory
        guiRegistry.registerGUI(inventory, gui)

        player.openInventory(inventory)
    }

    fun openGUI(idolPlayer: IdolPlayer, gui: GUI) {
        val player = Bukkit.getPlayer(idolPlayer.uuid) ?: return
        openGUI(player, gui)
    }
}