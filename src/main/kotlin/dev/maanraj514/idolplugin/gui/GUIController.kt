package dev.maanraj514.idolplugin.gui

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class GUIController(private val guiRegistry: GUIRegistry) {

    fun handleClick(event: InventoryClickEvent) {
        val handler = guiRegistry.getHandler(event.inventory) ?: return
        handler.onClick(event)
    }

    fun handleOpen(event: InventoryOpenEvent) {
        val handler = guiRegistry.getHandler(event.inventory) ?: return
        handler.onOpen(event)
    }

    fun handleClose(event: InventoryCloseEvent) {
        val handler = guiRegistry.getHandler(event.inventory) ?: return
        handler.onClose(event)
    }
}