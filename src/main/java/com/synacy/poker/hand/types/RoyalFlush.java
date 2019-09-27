package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.HandType;

import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Straight_flush">What is a Straight Flush?</a>
 */
public class RoyalFlush extends StraightFlush {

    private List<Card> cards;

    public RoyalFlush(List<Card> cards) {
        super(cards);
        this.cards = cards;
    }

    @Override
    public HandType getHandType() {
        return HandType.STRAIGHT_FLUSH;
    }

    @Override
    public List<Card> getCards() {
        return  cards;
    }

    @Override
    public Integer getHandTypeInt() {
        return HandType.STRAIGHT_FLUSH.ordinal();
    }

    /**
     * @return Royal Flush if the hand is a royal flush, or Straight Flush with the highest rank card,
     * e.g. Straight Flush (K High)
     */
    @Override
    public String toString() {
        return HandType.ROYAL_FLUSH.toString();
    }
}
