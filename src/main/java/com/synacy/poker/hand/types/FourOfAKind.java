package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandType;
import com.synacy.poker.util.PokerUtil;
import jdk.nashorn.internal.runtime.arrays.ContinuousArrayData;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Four_of_a_kind">What is a Four of a Kind?</a>
 */
public class FourOfAKind extends Hand {

    private List<Card> fourOfAKindCards;
    private List<Card> kickers;

    public FourOfAKind(List<Card> fourOfAKindCards, List<Card> kickers) {
        this.fourOfAKindCards = fourOfAKindCards;
        this.kickers = kickers;
    }

    @Override
    public HandType getHandType() {
        return HandType.FOUR_OF_A_KIND;
    }

    @Override
    public List<Card> getCards() {
        List<Card> cardList = new ArrayList<>();
        cardList.addAll(fourOfAKindCards);
        cardList.addAll(kickers);

        return cardList;
    }

    @Override
    public Integer getHandTypeInt() {
        return HandType.FOUR_OF_A_KIND.ordinal();
    }

    @Override
    public List<Card> getHandTypeCards() {
        return fourOfAKindCards;
    }

    @Override
    public List<Card> getHighCards() {
        return kickers;
    }

    /**
     * @return Returns the name of the hand plus kicker, e.g. Quads (4) - A High
     */
    @Override
    public String toString() { ;
        return HandType.FOUR_OF_A_KIND.toString()
                .replace("#q", fourOfAKindCards.get(0).getRank().toString())
                .replace("#k", kickers.get(0).getRank().toString());
    }

}
