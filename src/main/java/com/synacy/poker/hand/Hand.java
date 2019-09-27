package com.synacy.poker.hand;

import com.synacy.poker.card.Card;

import java.util.List;

/**
 * The base class of the different Hands such as {@link com.synacy.poker.hand.types.Flush},
 * {@link com.synacy.poker.hand.types.FullHouse}, etc.
 */
public abstract class Hand {

    /**
     * @return The {@link HandType}
     */
    public abstract HandType getHandType();

    public abstract List<Card> getCards();

    public abstract Integer getHandTypeInt();

    public abstract List<Card> getHandTypeCards();

    public abstract List<Card> getHighCards();
}
