import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.gson
import io.ktor.html.respondHtml
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import kotlinx.html.body
import kotlinx.html.p
import kotlinx.html.title
import model.Location
import model.Player
import kotlinx.html.*

fun Application.main() {
    // This adds Date and Server headers to each response, and allows custom additional headers
    install(DefaultHeaders)
    // This uses use the logger to log every call (request/response)
    install(CallLogging)

    install(ContentNegotiation) {
        gson { }
    }
    install(StatusPages)

    routing {
        get("/") {
            call.respondHtml {
                head {
                    title { +"Mister X" }
                }
                body {
                    p {
                        +"Jo, Welcome to the Base url of the amazing Mister X game"
                    }
                }
            }
        }

        post(GAME_ENDPOINT) {
            val player: Player = call.receive()
            log.debug(player.toString())
            val game = GameStore.createGame(player)
            GameEngine.start()
            log.debug(game.toString())
            call.respond(status = HttpStatusCode.OK, message = game)
        }

        get(GAME_ENDPOINT) {
            call.respond(GameStore.game)
        }

        post(GAME_PLAYER_ENDPOINT) {
            val player: Player = call.receive()
            log.debug(player.toString())
            val game = GameStore.addPlayer(player)
            log.debug(game.toString())
            call.respond(status = HttpStatusCode.OK, message = game)
        }

        post(GAME_MISTERX_ENDPOINT) {
            val player: Player = call.receive()
            log.debug(player.toString())
            val game = GameStore.addMisterX(player)
            log.debug(game.toString())
            call.respond(status = HttpStatusCode.OK, message = game)
        }

        put(GAME_PLAYER_LOCATION_ENDPOINT) {
            val location: Location = call.receive()
            val playerId: String = requireNotNull(call.parameters[PLAYER_ID_PARAM])
            log.debug(location.toString())
            log.debug(playerId)
            val game = GameStore.updatePlayerLocation(playerId, location)
            log.debug(game.toString())
            call.respond(status = HttpStatusCode.OK, message = game)
        }

        put(GAME_MISTERX_LOCATION_ENDPOINT) {
            val location: Location = call.receive()
            val playerId: String = requireNotNull(call.parameters[PLAYER_ID_PARAM])
            log.debug(location.toString())
            log.debug(playerId)
            val game = GameStore.updateMisterXLocation(playerId, location)
            call.respond(status = HttpStatusCode.OK, message = game)
        }
    }
}

const val GAME = "game"
const val MISTER_X = "misterx"
const val PLAYER = "player"
const val PLAYER_ID_PARAM = "playerId"
const val LOCATION = "location"

const val GAME_ENDPOINT = "/$GAME"
const val GAME_PLAYER_ENDPOINT = "/$GAME/$PLAYER"
const val GAME_PLAYER_LOCATION_ENDPOINT = "/$GAME/$PLAYER/{$PLAYER_ID_PARAM}/$LOCATION"
const val GAME_MISTERX_ENDPOINT = "/$GAME/$MISTER_X"
const val GAME_MISTERX_LOCATION_ENDPOINT = "/$GAME/$MISTER_X/{$PLAYER_ID_PARAM}/$LOCATION"
