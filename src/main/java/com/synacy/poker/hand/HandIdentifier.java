package com.synacy.poker.hand;

import com.synacy.poker.card.Card;
import com.synacy.poker.card.CardRank;
import com.synacy.poker.card.CardSuit;
import com.synacy.poker.hand.types.*;
import com.synacy.poker.util.PokerUtil;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A service that is to used to identify the {@link Hand} given the player's cards and the community
 * cards.
 */
@Component
public class HandIdentifier {

    /**
     * Given the player's cards and the community cards, identifies the player's hand.
     *
     * @param playerCards
     * @param communityCards
     * @return The player's {@link Hand} or `null` if no Hand was identified.
     */
    public Hand identifyHand(List<Card> playerCards, List<Card> communityCards) {
        Hand hand = getStraightFlush(playerCards, communityCards);
        if(hand != null) {
            return hand;
        }

        hand = getFourOfAKind(playerCards, communityCards);
        if(hand != null) {
            return hand;
        }

        hand = getFullHouse(playerCards, communityCards);
        if(hand != null) {
            return hand;
        }

        hand = getFlush(playerCards, communityCards);
        if(hand != null) {
            return hand;
        }

        hand = getStraight(playerCards, communityCards);
        if(hand != null) {
            return hand;
        }

        hand = getThreeOfAKind(playerCards, communityCards);
        if(hand != null) {
            return hand;
        }

        hand = getTwoPairs(playerCards, communityCards);
        if(hand != null) {
            return hand;
        }

        hand = getOnePair(playerCards, communityCards);
        if(hand != null) {
            return hand;
        }

        hand = getHighCards(playerCards, communityCards);
        return hand;
    }

    private StraightFlush getStraightFlush(List<Card> playerCards, List<Card> communityCard) {
        List<Card> mergedCards = PokerUtil.getMergedCardList(playerCards, communityCard);

        CardSuit cardSuit = PokerUtil.getCommonSuitRank(mergedCards, Card::getSuit);

        mergedCards.removeIf(c -> !c.getSuit().equals(cardSuit));

        List<Card> cardList = PokerUtil.getCardSequence(mergedCards);
        if(cardList != null && cardList.size() >= 5) {
            if(cardList.get(0).getRank().equals(CardRank.ACE)) {
                return new RoyalFlush(cardList.stream().limit(5).collect(Collectors.toList()));
            }
            return new StraightFlush(cardList.stream().limit(5).collect(Collectors.toList()));
        }

        return null;
    }

    private FourOfAKind getFourOfAKind(List<Card> playerCards, List<Card> communityCard) {
        List<Card> cardList = PokerUtil.getMergedCardList(playerCards, communityCard);
        List<Card> quadList = PokerUtil.getPairCardList(cardList);

        if(quadList != null && quadList.size() == 4) {
            List<Card> kickers = PokerUtil.getKickers(quadList, cardList, 1);
            return new FourOfAKind(quadList, kickers);
        }

        return  null;
    }

    private FullHouse getFullHouse(List<Card> playerCards, List<Card> communityCards) {
        List<Card> mergedCardList = PokerUtil.getMergedCardList(playerCards, communityCards);
        List<Card> trioCardList = PokerUtil.getPairCardList(mergedCardList);

        if(trioCardList != null && trioCardList.size() == 3) {
            mergedCardList.removeAll(trioCardList);
            List<Card> pairCards = PokerUtil.getPairCardList(mergedCardList);
            if(pairCards !=  null && pairCards.size() == 2) {
                return new FullHouse(trioCardList, pairCards);
            }
        }

        return null;
    }

    private Flush getFlush(List<Card> playerCards, List<Card> communityCards) {
        List<Card> mergedCardList = PokerUtil.getMergedCardList(playerCards, communityCards);
        List<Card> flushList = new ArrayList<>();

        CardSuit cardSuit = PokerUtil.getCommonSuitRank(mergedCardList, Card::getSuit);

        flushList = mergedCardList.stream()
                    .filter(card -> card.getSuit().equals(cardSuit))
                    .sorted(Comparator.comparing(Card::getRankToInt).reversed())
                    .limit(5)
                    .collect(Collectors.toList());


        if(flushList != null && flushList.size() >= 5) {
            return new Flush(flushList.stream().limit(5).collect(Collectors.toList()));
        }

        return null;
    }

    private Straight getStraight(List<Card> playerCards, List<Card> communityCards) {
        List<Card> mergedCards = PokerUtil.getMergedCardList(playerCards, communityCards);
        List<Card> cardList = PokerUtil.getCardSequence(mergedCards);

        if(cardList != null && cardList.size() >= 5) {
            return new Straight(cardList.stream().limit(5).collect(Collectors.toList()));
        }

        return null;
    }

    private ThreeOfAKind getThreeOfAKind(List<Card> playerCards, List<Card> communityCards) {
        List<Card> mergedCards = PokerUtil.getMergedCardList(playerCards, communityCards);
        List<Card> trioCards = PokerUtil.getPairCardList(mergedCards);

        if(trioCards != null && trioCards.size() == 3) {
            List<Card> kickerCards = PokerUtil.getKickers(trioCards, mergedCards, 2);
            if(kickerCards != null) {
                return new ThreeOfAKind(trioCards, kickerCards);
            }
        }

        return null;
    }

    private TwoPair getTwoPairs(List<Card> playerCards, List<Card> communityCards) {
        List<Card> mergedCardList = PokerUtil.getMergedCardList(playerCards, communityCards);
        List<Card> pair1CardList = PokerUtil.getPairCardList(mergedCardList);

        if(pair1CardList != null && pair1CardList.size() == 2) {
            mergedCardList.removeAll(pair1CardList);

            List<Card> pair2CardList = PokerUtil.getPairCardList(mergedCardList);
            List<Card> pairList = PokerUtil.getMergedCardList(pair1CardList, pair2CardList);
            List<Card> kickerCards = new ArrayList<>();

            if(pairList != null && mergedCardList!= null) {
                kickerCards = PokerUtil.getKickers(pairList, mergedCardList, 1);
            }

            if(pair2CardList != null && pair2CardList.size() == 2 && kickerCards != null) {
                if(pair1CardList.get(0).getRankToInt() >= pair2CardList.get(0).getRankToInt()) {
                    return new TwoPair(pair1CardList, pair2CardList, kickerCards);
                }

                return new TwoPair(pair2CardList, pair1CardList, kickerCards);
            }
        }

        return null;
    }

    private OnePair getOnePair(List<Card> playerCards, List<Card> communityCards) {
        List<Card> mergedCardList = PokerUtil.getMergedCardList(playerCards, communityCards);
        List<Card> pairCardList = PokerUtil.getPairCardList(mergedCardList);

        if(pairCardList != null && pairCardList.size() == 2) {
            List<Card> kickerCardList = PokerUtil.getKickers(pairCardList, mergedCardList, 3);
            if(kickerCardList != null) {
                return new OnePair(pairCardList, kickerCardList);
            }
        }

        return null;
    }

    private HighCard getHighCards(List<Card> playerCards, List<Card> communityCards) {
        List<Card> mergedCards = PokerUtil.getMergedCardList(playerCards, communityCards);
        List<Card> highCardList = PokerUtil.getOrderedCardList(mergedCards)
                                    .stream().limit(5).collect(Collectors.toList());

        if(highCardList != null) {
            return new HighCard(highCardList);
        }

        return null;
    }
}
