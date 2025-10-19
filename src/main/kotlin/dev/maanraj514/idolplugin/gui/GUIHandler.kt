package dev.maanraj514.idolplugin.gui

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

interface GUIHandler {

    fun onClick(event: InventoryClickEvent)

    fun onOpen(event: InventoryOpenEvent)

    fun onClose(event: InventoryCloseEvent)
}