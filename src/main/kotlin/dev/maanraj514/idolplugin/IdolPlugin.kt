package dev.maanraj514.idolplugin

import dev.maanraj514.idolplugin.command.impl.IdolCommand
import dev.maanraj514.idolplugin.command.impl.PosCommand
import dev.maanraj514.idolplugin.gui.GUIController
import dev.maanraj514.idolplugin.gui.GUIRegistry
import dev.maanraj514.idolplugin.gui.GUIService
import dev.maanraj514.idolplugin.idol.IdolManager
import dev.maanraj514.idolplugin.listener.GUIListener
import dev.maanraj514.idolplugin.listener.IdolListener
import dev.maanraj514.idolplugin.task.DroppedItemsTrackerTask
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class IdolPlugin : JavaPlugin() {

    private lateinit var idolManager: IdolManager

    private lateinit var guiRegistry: GUIRegistry
    private lateinit var guiController: GUIController
    private lateinit var guiService: GUIService

    private var droppedItemsTaskId = -1

    override fun onEnable() {
        initClasses()

        droppedItemsTaskId = Bukkit.getScheduler().runTaskTimer(
            this, DroppedItemsTrackerTask(idolManager), 0, 10).taskId

        registerListeners()
        registerCommands()
    }

    override fun onDisable() {
        if (droppedItemsTaskId != -1) {
            Bukkit.getScheduler().cancelTask(droppedItemsTaskId)
        }

        guiRegistry.cleanup()
        idolManager.cleanup()
    }

    private fun initClasses() {
        guiRegistry = GUIRegistry()
        guiController = GUIController(guiRegistry)
        guiService = GUIService(guiRegistry)

        idolManager = IdolManager(guiService)
    }

    private fun registerListeners() {
        val pluginManager = server.pluginManager

        pluginManager.registerEvents(IdolListener(idolManager), this)
        pluginManager.registerEvents(GUIListener(guiController), this)
    }

    private fun registerCommands() {
        val commandMap = server.commandMap

        commandMap.register("pos", PosCommand())
        commandMap.register("idol", IdolCommand(idolManager))
    }
}