package com.synacy.poker.card;

import java.util.Objects;

/**
 * The card in a deck. A combination of {@link CardRank} and {@link CardSuit}
 */
public class Card {

	private CardRank rank;
	private CardSuit suit;

	public Card(CardRank rank, CardSuit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	/**
	 * @return The {@link CardRank}
	 */
	public CardRank getRank() {
		return rank;
	}

	/**
	 * @return The {@link CardSuit}
	 */
	public CardSuit getSuit() {
		return suit;
	}

	/**
	 * @return The CSS class of the card, e.g. <code>card-red</code>
	 */
	public String styleClass() {
		return "card-" + suit.getColor();
	}

	/**
	 * @return The combination of the {@link CardRank} and {@link CardSuit}, e.g. Ace of Hearts is
	 * <code>A&hearts;</code>
	 */
	public String toString() {
		return getRank().toString() + getSuit().toString();
	}

	public Integer getRankToInt() {
		return rank.ordinal();
	}

	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		} else if(!(o instanceof Card)) {
			return false;
		} else {
			Card card2 = (Card)o;
			return rank.equals(card2.getRank()) && suit.equals(card2.getSuit());
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(rank, suit);
	}

}
