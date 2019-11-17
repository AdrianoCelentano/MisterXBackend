import model.Game
import model.Location
import model.MisterX
import model.Player
import java.lang.IllegalStateException

object GameStore {

    private var mutableGame: Game? = null

    val game: Game
        get() = mutableGame ?: throw IllegalStateException("There is no Game")

    val gameTime: Int
        get() = mutableGame?.time ?: 0

    fun createGame(player: Player): Game {
        val newGame = Game(
            players = mutableListOf(player)
        )
        mutableGame = newGame
        return newGame
    }

    fun addPlayer(newPlayer: Player): Game {
        game.players.add(newPlayer)
        return game
    }

    fun addMisterX(player: Player): Game {
        game.misterX = MisterX(
            player = player,
            visibleLocation = player.location
        )
        return game
    }

    fun updateTime(time: Int) {
        game.time += time
    }

    fun updateMisterXVisibleLocation() {
        val misterX = game.misterX ?: throw IllegalStateException("There is no MisterX")
        misterX.visibleLocation = misterX.player.location
        println("update visible location misterx: ${misterX.visibleLocation}")
    }

    fun updateMisterXLocation(playerId: String, location: Location): Game {
        val misterX = game.misterX ?: throw IllegalStateException("There is no MisterX")
        misterX.player.location = location
        return game
    }

    fun updatePlayerLocation(playerId: String, location: Location): Game {
        val player = game.players.find { it.id == playerId }
            ?: throw IllegalStateException("There is no Player with id: $playerId")
        player.location = location
        return game
    }
}