package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandType;

import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Flush">What is a flush?</a>
 */
public class Flush extends Hand {

    private List<Card> cards;

    public Flush(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public HandType getHandType() {
        return HandType.FLUSH;
    }

    @Override
    public List<Card> getCards() {
        return cards;
    }

    @Override
    public Integer getHandTypeInt() {
        return HandType.FLUSH.ordinal();
    }

    @Override
    public List<Card> getHandTypeCards() {
        return cards;
    }

    @Override
    public List<Card> getHighCards() {
        return cards;
    }

    /**
     * @return Returns the name of the hand and the highest card, e.g. Flush (K High)
     */
    @Override
    public String toString() {
        return HandType.FLUSH.toString()
                .replace("#h", cards.get(0).getRank().toString());
    }

}
