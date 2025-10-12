package dev.maanraj514.idolplugin.command

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

abstract class Command(name: String) : Command(name) {

    private val commandInfo = javaClass.getDeclaredAnnotation<CommandInfo>(CommandInfo::class.java)

    init {
        this.description = commandInfo.description
        this.permission = commandInfo.permission
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (!sender.hasPermission(commandInfo.permission)) return true

        if (sender !is Player) {
            if (commandInfo.playerOnly) {
                return true
            }
            execute(sender, args)
            return true
        }

        execute(sender, args)
        return true
    }

    open fun execute(sender: CommandSender, args: Array<out String>) {}
    open fun execute(player: Player, args: Array<out String>) {}
}