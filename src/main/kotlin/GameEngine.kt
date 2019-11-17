import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow

object GameEngine {

    private val gameStore = GameStore
    private lateinit var tickerChannel: ReceiveChannel<Unit>
    val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)

    fun start() {
        scope.launch {
            tickerChannel = ticker(delayMillis = 1000, initialDelayMillis = 0)
            tickerChannel.consumeAsFlow()
                .collect {
                    gameStore.updateTime(1)
                    if(gameStore.gameTime % FIVE_MINUTES == 0) {
                        gameStore.updateMisterXVisibleLocation()
                    }
                }
        }
    }

    fun stop() {
        tickerChannel.cancel()
        scope.cancel()
    }
}

const val FIVE_MINUTES = 60*5