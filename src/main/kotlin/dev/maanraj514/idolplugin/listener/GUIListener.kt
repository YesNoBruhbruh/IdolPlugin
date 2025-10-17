package dev.maanraj514.idolplugin.listener

import dev.maanraj514.idolplugin.gui.GUIController
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class GUIListener(private val guiController: GUIController) : Listener {

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        guiController.handleClick(event)
    }

    @EventHandler
    fun onOpen(event: InventoryOpenEvent) {
        guiController.handleOpen(event)
    }

    @EventHandler
    fun onClose(event: InventoryCloseEvent) {
        guiController.handleClose(event)
    }
}