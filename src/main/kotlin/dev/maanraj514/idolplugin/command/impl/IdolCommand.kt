package dev.maanraj514.idolplugin.command.impl

import dev.maanraj514.idolplugin.command.Command
import dev.maanraj514.idolplugin.command.CommandInfo
import dev.maanraj514.idolplugin.idol.IdolManager
import dev.maanraj514.idolplugin.util.LocationUtil
import org.bukkit.Material
import org.bukkit.entity.Player

@CommandInfo("idol", "main idol command", "idolplugin.command.idol")
class IdolCommand(private val idolManager: IdolManager) : Command("idol") {

    //TODO turn the /idol into a core command, putting everything into one class
    // is really messy :/

    /*
    * /idol create <idolName>
    * /idol donationFilter <idolName> <add/remove/list> <material> <trustPoints>
    * */

    override fun execute(player: Player, args: Array<out String>) {
        if (args.size < 2) return

        val args0 = args[0]
        val idolName = args[1]

        when (args0) {
            "create" -> {
                val cuboid = LocationUtil.createCuboid(player.uniqueId)

                if (cuboid == null) {
                    player.sendMessage("Make sure you have set posA and posB!")
                    return
                }

                idolManager.createIdol(idolName, cuboid, mutableMapOf())
                player.sendMessage("Successfully created idol!")
            }
            "donationFilter" -> {
                val idol = idolManager.getIdol(idolName)

                if (idol == null) {
                    player.sendMessage("That idol doesn't exist!")
                    return
                }

                val args2 = args[2]

                if (args.size == 3 && args2 == "list") {
                    idol.sendDonationFilterMessage(player)
                    return
                }

                if (args.size < 4) return

                val materialString = args[3].uppercase()
                val material = Material.getMaterial(materialString)
                if (material == null) {
                    player.sendMessage("The material is not valid!")
                    return
                }

                when (args[2]) {
                    "add" -> {
                        if (args.size < 5) return

                        val trustPoints = args[4].toInt()

                        if(!idol.addDonationFilter(material, trustPoints)){
                            player.sendMessage("This material has already been filtered!")
                            return
                        }

                        player.sendMessage("$materialString with $trustPoints has been added to the filter!")
                    }
                    "remove" -> {
                        if (!idol.removeDonationFilter(material)){
                            player.sendMessage("This material has not been filtered yet!")
                            return
                        }

                        player.sendMessage("$materialString has been removed from the filter!")
                    }
                }
            }
        }
    }
}