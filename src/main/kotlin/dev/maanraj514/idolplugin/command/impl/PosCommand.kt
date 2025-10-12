package dev.maanraj514.idolplugin.command.impl

import dev.maanraj514.idolplugin.command.Command
import dev.maanraj514.idolplugin.command.CommandInfo
import dev.maanraj514.idolplugin.util.LocationUtil
import org.bukkit.entity.Player

@CommandInfo("pos", "for pos a and b", "idolplugin.command.pos")
class PosCommand : Command("pos") {

    override fun execute(player: Player, args: Array<out String>) {
        if (args.isEmpty()) return

        val args0 = args[0]

        val uuid = player.uniqueId
        val location = player.location

        when (args0) {
            "a" -> {
                LocationUtil.playerPosA[uuid] = location
            }
            "b" -> {
                LocationUtil.playerPosB[uuid] = location
            }
        }
    }
}