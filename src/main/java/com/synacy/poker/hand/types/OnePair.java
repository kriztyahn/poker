package com.synacy.poker.hand.types;

import com.synacy.poker.card.Card;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandType;
import com.synacy.poker.util.PokerUtil;

import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#One_pair">What is a One Pair?</a>
 */
public class OnePair extends Hand {

    private List<Card> pairCards;
    private List<Card> otherCards;

    public OnePair(List<Card> pairCards, List<Card> otherCards) {
        this.pairCards = pairCards;
        this.otherCards = otherCards;
    }

    @Override
    public HandType getHandType() {
        return HandType.ONE_PAIR;
    }

    @Override
    public List<Card> getCards() {
        return PokerUtil.getMergedCardList(pairCards, otherCards);
    }

    @Override
    public Integer getHandTypeInt() {
        return HandType.ONE_PAIR.ordinal();
    }

    @Override
    public List<Card> getHandTypeCards() {
        return pairCards;
    }

    @Override
    public List<Card> getHighCards() {
        return otherCards;
    }

    /**
     * @return The name of the hand plus kickers ordered by descending rank, e.g. One Pair (2) - A,K,Q High,
     * or the name of the hand and rank if there are no community cards yet in play, e.g. One Pair (2)
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(HandType.ONE_PAIR)
            .append(" (")
            .append(pairCards.get(0).getRank())
            .append(")");

        if(this.otherCards != null && otherCards.size() > 0) {
            sb.append(" - ");
            for (Card card : otherCards) {
                sb.append(card.getRank());

                if (otherCards.indexOf(card) < otherCards.size() - 1) {
                    sb.append(",");
                }
            }

            sb.append(" High");
        }

        return sb.toString();
    }

}
