package dev.maanraj514.idolplugin

import dev.maanraj514.idolplugin.command.impl.IdolCommand
import dev.maanraj514.idolplugin.command.impl.PosCommand
import dev.maanraj514.idolplugin.idol.IdolManager
import dev.maanraj514.idolplugin.listener.IdolListener
import org.bukkit.plugin.java.JavaPlugin

class IdolPlugin : JavaPlugin() {

    private lateinit var idolManager: IdolManager

    override fun onEnable() {
        idolManager = IdolManager()

        registerListeners()
        registerCommands()
    }

    override fun onDisable() {
        idolManager.clear()
    }

    private fun registerListeners() {
        val pluginManager = server.pluginManager

        pluginManager.registerEvents(IdolListener(idolManager), this)
    }

    private fun registerCommands() {
        val commandMap = server.commandMap

        commandMap.register("pos", PosCommand())
        commandMap.register("idol", IdolCommand(idolManager))
    }
}