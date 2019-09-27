package com.synacy.poker.hand;

/**
 * An enum of {@link Hand}s
 *
 * <ul>
 * <li>High Card</li>
 * <li>One Pair</li>
 * <li>Two Pair</li>
 * <li>Three of a Kind</li>
 * <li>Straight</li>
 * <li>Flush</li>
 * <li>Full House</li>
 * <li>Four of a Kind</li>
 * <li>Straight Flush</li>
 * </ul>
 */
public enum HandType {

    HIGH_CARD("High Card"),
    ONE_PAIR("One Pair"),
    TWO_PAIR("Two Pair (#p1,#p2) - #h High"),
    THREE_OF_A_KIND("Trips (#t) - #h1,#h2 High"),
    STRAIGHT("Straight (#h High)"),
    FLUSH("Flush (#h High)"),
    FULL_HOUSE("Full House (#t,#p)"),
    FOUR_OF_A_KIND("Quads (#q) - #k High"),
    STRAIGHT_FLUSH("Straight Flush (#h High)"),
    ROYAL_FLUSH("Royal Flush");

    public final String value;

    HandType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
