package com.synacy.poker.hand;

import com.synacy.poker.card.Card;
import com.synacy.poker.card.CardRank;
import com.synacy.poker.card.CardSuit;
import com.synacy.poker.hand.types.RoyalFlush;
import com.synacy.poker.hand.types.StraightFlush;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StraightFlushTest {

	@Test
	public void toString_withKingHighStraightFlush() {
		List<Card> cards = Arrays.asList(
				new Card(CardRank.KING, CardSuit.CLUBS),
				new Card(CardRank.QUEEN, CardSuit.CLUBS),
				new Card(CardRank.JACK, CardSuit.CLUBS),
				new Card(CardRank.TEN, CardSuit.CLUBS),
				new Card(CardRank.NINE, CardSuit.CLUBS)
		);

		StraightFlush straightFlush = new StraightFlush(cards);

		assertEquals("Straight Flush (K High)", straightFlush.toString());
	}

	@Test
	public void toString_withFiveHighStraightFlush() {
		List<Card> cards = Arrays.asList(
				new Card(CardRank.FIVE, CardSuit.CLUBS),
				new Card(CardRank.FOUR, CardSuit.CLUBS),
				new Card(CardRank.THREE, CardSuit.CLUBS),
				new Card(CardRank.TWO, CardSuit.CLUBS),
				new Card(CardRank.ACE, CardSuit.CLUBS)
		);

		StraightFlush straightFlush = new StraightFlush(cards);

		assertEquals("Straight Flush (5 High)", straightFlush.toString());
	}

	@Test
	public void toString_withRoyalFlush() {
		List<Card> cards = Arrays.asList(
				new Card(CardRank.ACE, CardSuit.CLUBS),
				new Card(CardRank.KING, CardSuit.CLUBS),
				new Card(CardRank.QUEEN, CardSuit.CLUBS),
				new Card(CardRank.JACK, CardSuit.CLUBS),
				new Card(CardRank.TEN, CardSuit.CLUBS)
		);

		RoyalFlush royalFlush = new RoyalFlush(cards);

		assertEquals("Royal Flush", royalFlush.toString());
	}

}
