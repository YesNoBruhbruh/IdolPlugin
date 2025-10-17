package dev.maanraj514.idolplugin.gui

import org.bukkit.entity.Player

class GUIService(private val guiRegistry: GUIRegistry) {

    fun openGUI(player: Player, gui: GUI) {
        val inventory = gui.inventory
        guiRegistry.registerGUI(inventory, gui)

        player.openInventory(inventory)
    }
}