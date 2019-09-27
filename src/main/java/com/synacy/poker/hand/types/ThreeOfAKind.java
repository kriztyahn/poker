package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandType;
import com.synacy.poker.util.PokerUtil;

import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Three_of_a_kind">What is a Three of a Kind?</a>
 */
public class ThreeOfAKind extends Hand {

    private List<Card> threeOfAKindCards;
    private List<Card> otherCards;

    public ThreeOfAKind(List<Card> threeOfAKindCards, List<Card> otherCards) {
        this.threeOfAKindCards = threeOfAKindCards;
        this.otherCards = otherCards;
    }

    @Override
    public HandType getHandType() {
        return HandType.THREE_OF_A_KIND;
    }

    @Override
    public List<Card> getCards() {
        return PokerUtil.getMergedCardList(threeOfAKindCards, otherCards);
    }

    @Override
    public Integer getHandTypeInt() {
        return HandType.THREE_OF_A_KIND.ordinal();
    }

    @Override
    public List<Card> getHandTypeCards() {
        return threeOfAKindCards;
    }

    @Override
    public List<Card> getHighCards() {
        return otherCards;
    }

    /**
     * @return The name of the hand plus kickers in descending rank, e.g. Trips (4) - A,2 High
     */
    @Override
    public String toString() {
        return HandType.THREE_OF_A_KIND.toString()
                .replace("#t", threeOfAKindCards.get(0).getRank().toString())
                .replace("#h1", otherCards.get(0).getRank().toString())
                .replace("#h2", otherCards.get(1).getRank().toString());
    }
}
