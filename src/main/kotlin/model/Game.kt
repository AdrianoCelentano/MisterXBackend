package model

data class Game(
        val name: String = "Game",
        var misterX: MisterX? = null,
        var time: Int = 0,
        var players: MutableList<Player> = mutableListOf()
)

data class MisterX(
        var player: Player,
        var visibleLocation: Location
)

data class Player(
    val id: String,
    val name: String,
    var location: Location
)

data class Location(
    val longitude: Double,
    val latitude: Double
)