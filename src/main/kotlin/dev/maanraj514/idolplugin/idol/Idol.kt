package dev.maanraj514.idolplugin.idol

import dev.maanraj514.idolplugin.gui.GUIService
import dev.maanraj514.idolplugin.gui.paged.impl.WishesGUI
import dev.maanraj514.idolplugin.idol.action.Ritual
import dev.maanraj514.idolplugin.idol.action.impl.ExpandBorderRitual
import dev.maanraj514.idolplugin.util.Cuboid
import dev.maanraj514.idolplugin.util.toComponent
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BundleMeta
import java.util.concurrent.ThreadLocalRandom

class Idol(
    val name: String,
    val cuboid: Cuboid,
    // maybe could have made an abstraction DonationItem -> Implementation..
    private val guiService: GUIService,
    //                               DonationMaterial required Points
    private val donationToPoints: MutableMap<Material, Int>,
    //                     wishMaterial   required points
    val wishToPoints: MutableMap<Material, Int>,
    //                             ritualActionType    required Items    required Points
    val rituals: MutableList<Ritual>) {

    private val random = ThreadLocalRandom.current()

    init {
        //purely for visuals
        cuboid.display()

        //TODO remove this after testing too

        val requiredItems = mutableListOf<ItemStack>()
        requiredItems.add(ItemStack(Material.OAK_LOG))

        rituals.add(ExpandBorderRitual(requiredItems, 100, 5000000, cuboid.world.uid))
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
        val isWish = material != Material.BUNDLE

        if (isWish) {
            val amount = itemStack.amount
            // the -1 is for deduction for wrong offering,
            // but make sure to allow for configurability in end product.
            val trustPoints = donationToPoints.getOrDefault(material, -1) * amount
            idolPlayer.trust += trustPoints

            guiService.openGUI(idolPlayer, WishesGUI(idolPlayer, this))
            return
        }

        handleRitual(itemStack, idolPlayer)
    }

    private fun handleRitual(bundleItem: ItemStack, idolPlayer: IdolPlayer) {
        val itemMeta = bundleItem.itemMeta ?: return
        val bundleItemMeta = itemMeta as BundleMeta
        if (!bundleItemMeta.hasItems()) return

        val player = Bukkit.getPlayer(idolPlayer.uuid) ?: return // not online?

        val ritual = getRitual(bundleItemMeta.items)
        if (ritual == null) {
            player.sendMessage("<red>Couldn't find the ritual with your items!</red>".toComponent())
            return
        }

        val ritualCost = ritual.requiredTrust

        // can't perform ritual if they don't have enough points
        if (idolPlayer.trust < ritualCost){
            // put the sack back into their inventory
            player.inventory.addItem(bundleItem)
            return
        }

        idolPlayer.trust -= ritualCost
        println("before worldSize = ${cuboid.world.worldBorder.size}")
        ritual.performRitual()
        player.sendMessage("<light_purple>Performing ritual...</light_purple>".toComponent())
        println("after worldSize = ${cuboid.world.worldBorder.size}")
    }

    private fun getRitual(items: List<ItemStack>): Ritual? {
        for (ritual in rituals) {
            val requiredItems = ritual.requiredItems

            if (items.containsAll(requiredItems)) {
                return ritual
            }
        }

        return null
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

        val randomResult = random.nextInt(100)

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

    fun isAlreadyFiltered(material: Material): Boolean {
        return donationToPoints.containsKey(material)
    }
}