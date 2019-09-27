package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandType;
import com.synacy.poker.util.PokerUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Two_pair">What is a Two Pair?</a>
 */
public class TwoPair extends Hand {

    private List<Card> firstPairCards;
    private List<Card> secondPairCards;
    private List<Card> kickers;

    public TwoPair(List<Card> firstPairCards, List<Card> secondPairCards, List<Card> kickers) {
        this.firstPairCards = firstPairCards;
        this.secondPairCards = secondPairCards;
        this.kickers = kickers;
    }

    @Override
    public HandType getHandType() {
        return HandType.TWO_PAIR;
    }

    @Override
    public List<Card> getCards() {
        List<Card> cardList = PokerUtil.getMergedCardList(firstPairCards, secondPairCards);
        cardList.addAll(kickers);

        return cardList;
    }

    @Override
    public Integer getHandTypeInt() {
        return HandType.TWO_PAIR.ordinal();
    }

    @Override
    public List<Card> getHandTypeCards() {
        return PokerUtil.getMergedCardList(firstPairCards, secondPairCards);
    }

    @Override
    public List<Card> getHighCards() {
        return kickers;
    }

    /**
     * @return The name of the hand with kicker ranked in descending order, e.g. Two Pair (4,3) - A High
     */
    @Override
    public String toString() {
        return HandType.TWO_PAIR.toString()
                .replace("#p1", firstPairCards.get(0).getRank().toString())
                .replace("#p2", secondPairCards.get(0).getRank().toString())
                .replace("#h", kickers.get(0).getRank().toString());
    }

}
