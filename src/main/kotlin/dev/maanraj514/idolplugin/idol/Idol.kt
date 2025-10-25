package dev.maanraj514.idolplugin.idol

import dev.maanraj514.idolplugin.gui.GUIService
import dev.maanraj514.idolplugin.gui.paged.impl.WishesGUI
import dev.maanraj514.idolplugin.util.Cuboid
import dev.maanraj514.idolplugin.util.toComponent
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.entity.TextDisplay
import org.bukkit.inventory.ItemStack
import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

class Idol(
    val name: String,
    val cuboid: Cuboid,
    // maybe could have made an abstraction DonationItem -> Implementation..
    private val guiService: GUIService,
    //                               DonationMaterial required Points
    private val donationToPoints: MutableMap<Material, Int>,
    //TODO implement handling for this
    private val penalizedItems: MutableMap<Material, Int>,
    //                     wishMaterial   required points
    private val wishToPoints: MutableMap<Material, Int>) {

    private val random = ThreadLocalRandom.current()

    private var hologramId = UUID.randomUUID()

    init {
        //purely for visuals
        cuboid.display()

        val hologram = cuboid.world.spawnEntity(cuboid.findCenter(), EntityType.TEXT_DISPLAY) as TextDisplay
        hologramId = hologram.uniqueId
        hologram.text("<gold><bold>$name</bold></gold>".toComponent())

        //TODO remove this after testing too
        wishToPoints[Material.DIAMOND] = 50
        wishToPoints[Material.BUNDLE] = 5
        wishToPoints[Material.NETHERITE_INGOT] = 100
    }

    // also factor in how many of that specific item was donated, and multiply.
    fun onDonation(item: Item, idolPlayer: IdolPlayer): Boolean {
        if (!canDonate(item.location)) return false

        val itemStack = item.itemStack
        item.remove() // to show visually it got accepted

        handleDonation(itemStack, idolPlayer)

        // if not active anymore, totally fine, we just need
        // to send a message anyway
        val player = Bukkit.getPlayer(idolPlayer.uuid) ?: return true
        player.sendMessage("Your trustPoints have been updated to ${idolPlayer.trust}")

        return true
    }

    private fun handleDonation(itemStack: ItemStack, idolPlayer: IdolPlayer) {
        val material = itemStack.type

        val amount = itemStack.amount
        // the -1 is for deduction for wrong offering,
        // but make sure to allow for configurability in end product.
        val trustPoints = donationToPoints.getOrDefault(material, -1) * amount
        idolPlayer.trust += trustPoints

        guiService.openGUI(idolPlayer, WishesGUI(idolPlayer, this))
        return
    }

    //method for chance, it should return true or not

    fun isWishSuccess(wishMaterial: Material, idolPlayer: IdolPlayer): Boolean {
        // they can't wish for what's not on the map.
        val wishCost = wishToPoints[wishMaterial] ?: return false

        val trustPoints = idolPlayer.trust

        if (wishCost > trustPoints) return false

        // Probably in the future, make this more configurable
        // Wishes with rarities
        // and different success rate scaling options for each of em.

        // default: 50% of getting what you wish for if trustPoints == cost
        // if you have more than cost, it scales up.

        val randomResult = random.nextInt(1, 101)

        //TODO implement configurable scaling

        var successRate = 50

        // already subtract
        idolPlayer.trust -= wishCost

//        println("trustPoints = $trustPoints")
//        println("pointsFromPlayer = ${idolPlayer.trust}")

        if (trustPoints > wishCost) {
            var i = trustPoints - wishCost
            while (i > 0) {
                successRate++ // for every point above cost, success rate increments 1
                i-- // the while loop gotta end somehow lol
            }
        }

        println("randomResult = $randomResult, successRate = $successRate")

        return randomResult < successRate
    }

    fun canDonate(itemLocation: Location): Boolean {
        return cuboid.inRegion(itemLocation)
    }

    fun addDonationFilter(material: Material, trustPoints: Int): Boolean {
        if (isAlreadyFiltered(material)) return false

        donationToPoints[material] = trustPoints
        return true
    }

    fun removeDonationFilter(material: Material): Boolean {
        if (!isAlreadyFiltered(material)) return false // doesn't exist

        donationToPoints.remove(material)
        return true
    }

    fun sendDonationFilterMessage(player: Player) {
        if (donationToPoints.isEmpty()){
            player.sendMessage("No filter yet!")
            return
        }

        player.sendMessage("==========DONATION FILTER==========")

        for (filterEntry in donationToPoints.entries) {
            val material = filterEntry.key
            val trustPoints = filterEntry.value

            player.sendMessage("${material.name.uppercase()} -> $trustPoints")
        }

        player.sendMessage("===================================")
    }

    fun destroy() {

        cuboid.world.getEntity(hologramId)?.remove()

        donationToPoints.clear()
        penalizedItems.clear()
        wishToPoints.clear()
    }

    fun isAlreadyFiltered(material: Material): Boolean {
        return donationToPoints.containsKey(material)
    }

    fun getWishToPoints(): Map<Material, Int> {
        return wishToPoints
    }

    fun getDonationToPoints(): Map<Material, Int> {
        return donationToPoints
    }

    fun getPenalizedItems(): Map<Material, Int> {
        return penalizedItems
    }
}