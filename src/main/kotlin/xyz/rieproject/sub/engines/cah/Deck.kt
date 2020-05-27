package xyz.rieproject.sub.engines.cah

class Deck(private val cards: MutableList<String>) {
    val decks = shuffle(cards)

    @ExperimentalStdlibApi
    fun draw(): String {
        if (decks.size < 1) shuffle(decks)
        val card = decks[0]
        decks.removeFirst()
        return card
    }

    fun shuffle(cards: MutableList<String>): MutableList<String> {
        cards.shuffle()
        return cards
    }
}