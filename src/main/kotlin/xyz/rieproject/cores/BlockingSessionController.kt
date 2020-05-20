package xyz.rieproject.cores

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.utils.SessionControllerAdapter
import net.dv8tion.jda.internal.utils.JDALogger

class BlockingSessionController : SessionControllerAdapter() {
    private val MAX_DELAY = 60 * 1000 // 1 minute
    override fun runWorker() {
        synchronized(lock) {
            if (workerHandle == null) {
                workerHandle = BlockingQueueWorker()
                workerHandle.start()
            }
        }
    }

    protected inner class BlockingQueueWorker : QueueWorker() {
        override fun run() {
            try {
                if (delay > 0) {
                    val interval = System.currentTimeMillis() - lastConnect
                    if (interval < delay) Thread.sleep(delay - interval)
                }
            } catch (ex: InterruptedException) {
                JDALogger.getLog(SessionControllerAdapter::class.java).error("Unable to backoff", ex)
            }
            while (!connectQueue.isEmpty()) {
                val node = connectQueue.poll()
                try {
                    node.run(connectQueue.isEmpty())
                    lastConnect = System.currentTimeMillis()
                    if (connectQueue.isEmpty()) break
                    if (delay > 0) Thread.sleep(delay)
                    var total = 0
                    while (node.jda.status != JDA.Status.CONNECTED && node.jda.status != JDA.Status.SHUTDOWN && total < MAX_DELAY
                    ) {
                        total += 100
                        Thread.sleep(100)
                    }
                } catch (e: InterruptedException) {
                    JDALogger.getLog(SessionControllerAdapter::class.java).error("Failed to run node", e)
                    appendSession(node)
                }
            }
            synchronized(lock) {
                workerHandle = null
                if (!connectQueue.isEmpty()) runWorker()
            }
        }
    }
}