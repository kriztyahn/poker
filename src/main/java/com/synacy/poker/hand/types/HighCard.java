package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandType;
import com.synacy.poker.util.PokerUtil;

import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#High_card">What is a High Card?</a>
 */
public class HighCard extends Hand {

    private List<Card> cards;

    public HighCard(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public HandType getHandType() {
        return HandType.HIGH_CARD;
    }

    @Override
    public List<Card> getCards() {
        return cards;
    }

    @Override
    public Integer getHandTypeInt() {
        return HandType.HIGH_CARD.ordinal();
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
     * @return The cards ordered by descending rank, e.g. A,K,Q,3,2
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        PokerUtil.getOrderedCardList(this.cards);
        for(Card card: this.cards) {
            sb.append(card.getRank());

            if(this.cards.indexOf(card) < this.cards.size()-1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }

}
