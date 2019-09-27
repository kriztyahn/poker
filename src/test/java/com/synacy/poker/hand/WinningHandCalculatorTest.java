package com.synacy.poker.hand;

import com.synacy.poker.card.Card;
import com.synacy.poker.card.CardRank;
import com.synacy.poker.card.CardSuit;
import com.synacy.poker.game.Player;
import com.synacy.poker.hand.types.*;
import org.junit.Test;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class WinningHandCalculatorTest {

    @Test
    public void calculateWinningHandTest() {
        List<Card> player1Cards = Arrays.asList(
                new Card(CardRank.QUEEN, CardSuit.SPADES),
                new Card(CardRank.JACK, CardSuit.SPADES),
                new Card(CardRank.TEN, CardSuit.SPADES),
                new Card(CardRank.NINE, CardSuit.SPADES),
                new Card(CardRank.EIGHT, CardSuit.SPADES)
        );

        List<Card> player2Cards = Arrays.asList(
                new Card(CardRank.EIGHT, CardSuit.CLUBS),
                new Card(CardRank.SEVEN, CardSuit.SPADES),
                new Card(CardRank.SIX, CardSuit.CLUBS),
                new Card(CardRank.TEN, CardSuit.HEARTS),
                new Card(CardRank.NINE, CardSuit.CLUBS)
        );

        List<Card> pair = Arrays.asList(
                new Card(CardRank.TWO, CardSuit.CLUBS),
                new Card(CardRank.TWO, CardSuit.HEARTS)
        );
        List<Card> kickers = Arrays.asList(
                new Card(CardRank.ACE, CardSuit.CLUBS),
                new Card(CardRank.KING, CardSuit.DIAMONDS),
                new Card(CardRank.QUEEN, CardSuit.SPADES)
        );

        List<Hand> playerHands = new ArrayList<>();
        playerHands.add(new StraightFlush(player1Cards));
        playerHands.add(new Straight(player2Cards));
        playerHands.add(new OnePair(pair, kickers));

        WinningHandCalculator winningHandCalculator = new WinningHandCalculator();
        Hand hand = winningHandCalculator.calculateWinningHand(playerHands);

        assertEquals(hand.getHandType(), HandType.STRAIGHT_FLUSH);
    }
}
