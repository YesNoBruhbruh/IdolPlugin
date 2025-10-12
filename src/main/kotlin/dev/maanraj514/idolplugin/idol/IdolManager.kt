package dev.maanraj514.idolplugin.idol

import java.util.UUID

class IdolManager {

    private val idols = mutableMapOf<String, Idol>()
    private val idolPlayers = mutableMapOf<UUID, IdolPlayer>()

    fun insertIdol(name: String, idol: Idol) {
        idols[name] = idol
    }

    fun getIdol(name: String): Idol? {
        return idols[name]
    }

    fun getOrCreateIdolPlayer(uuid: UUID): IdolPlayer {
        return idolPlayers.getOrDefault(uuid, IdolPlayer(uuid, 0))
    }

    // save all

    fun clear() {
        idols.clear()
        idolPlayers.clear()
    }
}