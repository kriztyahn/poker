package com.synacy.poker.hand;

import com.synacy.poker.game.Player;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * A service class used to calculate the winning hand.
 */
@Component
public class WinningHandCalculator {

	/**
	 * @param playerHands
	 * @return The winning {@link Hand} from a list of player hands.
	 */
	public Hand calculateWinningHand(List<Hand> playerHands) {
		return playerHands.stream()
				.max(Comparator.comparing(c -> c.getHandTypeInt()))
				.orElse(null);
	}
}
