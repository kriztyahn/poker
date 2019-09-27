package com.synacy.poker.util;

import com.synacy.poker.card.Card;
import com.synacy.poker.card.CardRank;
import com.synacy.poker.card.CardSuit;

import javax.sound.midi.SysexMessage;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PokerUtil {

    public static <T,R> R getCommonSuitRank(List<T> cardList, Function<? super T, ? extends R> mapper) {
        return cardList.stream()
                .map(mapper).filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse(null);
    }

    public static List<Card> getMergedCardList(List<Card> playerCards, List<Card> communityCards) {
        return Stream.concat(
                Optional.ofNullable(playerCards).map(Collection::stream).orElseGet(Stream::empty),
                Optional.ofNullable(communityCards).map(Collection::stream).orElseGet(Stream::empty)
        ).collect(Collectors.toList());
    }

    public static List<Card> getOrderedCardList(List<Card> cardList) {
        if(cardList != null) {
            return cardList.stream()
                    .sorted(Comparator.comparing(Card::getRankToInt).reversed())
                    .collect(Collectors.toList());
        }

        return null;
    }

    public static List<Card> getCardSequence(List<Card> mergedCards) {
        List<Card> orderedList = PokerUtil.getOrderedCardList(mergedCards);
        List<Card> sequenceList = new ArrayList<>();

        Card previousCard = null;
        for(Card card : orderedList) {
            if(previousCard != null) {
                if((card.getRankToInt()-previousCard.getRankToInt()) == -1) {
                    if(sequenceList.size() == 0) {
                        sequenceList.add(previousCard);
                    }
                    sequenceList.add(card);
                }
            }

            if(previousCard!=null && (card.getRankToInt()-previousCard.getRankToInt()) != -1) {
                break;
            }

            previousCard = card;
        }

        if(sequenceList.size() >= 5) {
            return PokerUtil.getOrderedCardList(sequenceList);
        }

        return null;
    }

    public static void removeDuplicateRank(List<Card> cardList) {
        Set<CardRank> seen = new HashSet<>();
        cardList.removeIf(c -> !seen.add(c.getRank()));
    }

    public static List<Card> getPairCardList(List<Card> mergedCardList) {
        CardRank cardRank = PokerUtil.getCommonSuitRank(mergedCardList, Card::getRank);

        return mergedCardList.stream()
                .filter(card -> card.getRank().equals(cardRank))
                .sorted(Comparator.comparing(Card::getRank).reversed())
                .collect(Collectors.toList());
    }
    public static List<Card> getKickers(List<Card> pairCardList, List<Card> mergedCardList, int noOfCards) {
        if(pairCardList != null && mergedCardList != null) {
            List<Card> kickerCardList = mergedCardList.stream().filter(mergeCard -> pairCardList.stream()
                    .noneMatch(pairCard -> pairCard.getRank().equals(mergeCard.getRank())))
                    .collect(Collectors.toList());

            Collections.sort(kickerCardList, Comparator.comparing(Card::getRank).reversed());

            if (kickerCardList != null) {
                return kickerCardList.stream().limit(noOfCards).collect(Collectors.toList());
            }
        }
        return null;
    }

    public static Integer getSequenceSum(List<Card> cardList) {
        return cardList.stream()
                .mapToInt(c -> c.getRankToInt()).sum();
    }
}
