package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandType;

import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Straight">What is a Straight?</a>
 */
public class Straight extends Hand {

    private List<Card> cards;

    public Straight(List<Card> cards) {
        this.cards = cards;
    }

    public HandType getHandType() {
        return HandType.STRAIGHT;
    }

    @Override
    public List<Card> getCards() {
        return cards;
    }

    @Override
    public Integer getHandTypeInt() {
        return HandType.STRAIGHT.ordinal();
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
     * @return The name of the hand and the high card, e.g. Straight (A High)
     */
    @Override
    public String toString() {
       return HandType.STRAIGHT.toString()
               .replace("#h", cards.get(0).getRank().toString());
    }

}
