package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandType;
import com.synacy.poker.util.PokerUtil;

import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Full_house">What is a Full House?</a>
 */
public class FullHouse extends Hand {

    private List<Card> threeOfAKindCards;
    private List<Card> pairCards;

    public FullHouse(List<Card> threeOfAKindCards, List<Card> pairCards) {
        this.threeOfAKindCards = threeOfAKindCards;
        this.pairCards = pairCards;
    }

    @Override
    public HandType getHandType() {
        return HandType.FULL_HOUSE;
    }

    @Override
    public List<Card> getCards() {
        return PokerUtil.getMergedCardList(threeOfAKindCards, pairCards);
    }

    @Override
    public Integer getHandTypeInt() {
        return HandType.FULL_HOUSE.ordinal();
    }

    @Override
    public List<Card> getHandTypeCards() {
        return PokerUtil.getMergedCardList(threeOfAKindCards, pairCards);
    }

    @Override
    public List<Card> getHighCards() {
        return PokerUtil.getMergedCardList(threeOfAKindCards, pairCards);
    }

    /**
     * @return The name of the hand with rank of the three pair and two pair, e.g.
     * 444AA - Full House (4,A)
     */
    @Override
    public String toString() {
        return HandType.FULL_HOUSE.toString()
                .replace("#t", threeOfAKindCards.get(0).getRank().toString())
                .replace("#p", pairCards.get(0).getRank().toString());
    }

}
