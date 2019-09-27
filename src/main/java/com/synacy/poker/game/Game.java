package com.synacy.poker.game;

import com.synacy.poker.card.Card;
import com.synacy.poker.card.CardRank;
import com.synacy.poker.deck.Deck;
import com.synacy.poker.deck.DeckBuilder;
import com.synacy.poker.hand.Hand;
import com.synacy.poker.hand.HandIdentifier;
import com.synacy.poker.hand.HandType;
import com.synacy.poker.hand.WinningHandCalculator;
import com.synacy.poker.hand.types.OnePair;
import com.synacy.poker.util.PokerUtil;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The game engine.
 */
@Component
public class Game {

    private List<Player> players = new ArrayList<>();

    private List<Card> communityCards = new ArrayList<>();

    private DeckBuilder deckBuilder;
    private HandIdentifier handIdentifier;
    private WinningHandCalculator winningHandCalculator;

    private Deck deck;

    private Hand winningHand = null;

    private static final int MAX_PLAYER_CARDS = 2;
    private static final int MAX_COMMUNITY_CARDS = 5;

    public Game(DeckBuilder deckBuilder,
                HandIdentifier handIdentifier,
                WinningHandCalculator winningHandCalculator) {
        players.add(new Player("Alex"));
        players.add(new Player("Bob"));
        players.add(new Player("Jane"));

        this.deckBuilder = deckBuilder;
        this.handIdentifier = handIdentifier;
        this.winningHandCalculator = winningHandCalculator;

        startNewGame();
    }

    /**
     * Starts a new game.
     *
     * <h3>The following describes a new game.</h3>
     * <ul>
     * <li>Players' previous hands are cleared</li>
     * <li>Community cards are cleared</li>
     * <li>A new deck is used</li>
     * <li>The deck is shuffled</li>
     * <li>Players' are dealt with new cards.</li>
     * </ul>
     */
    public void startNewGame() {
        players.forEach(Player::clearHand);
        communityCards.clear();

        deck = deckBuilder.buildDeck();
        deck.shuffle();

        dealHands();
    }

    /**
     * The action to take after a new game has been started.
     *
     * <ol>
     * <li>Deal three community cards</li>
     * <li>Deal one community card</li>
     * <li>Deal another community card</li>
     * <li>Determine the winner/s</li>
     * </ol>
     * <p>
     * Dealt community are of course removed from the deck at the time their placed on the table.
     */
    public void nextAction() {
        if (communityCards.isEmpty()) {
            burnCard();
            dealThreeCommunityCards();
        } else if (communityCards.size() < MAX_COMMUNITY_CARDS) {
            burnCard();
            dealOneCommunityCard();
        }

        if (hasEnded()) {
            identifyWinningHand();
        }
    }

    /**
     * Checks the combination of the players and community cards to identify the winning hand.
     *
     * @see <a href="https://www.youtube.com/watch?v=GAoR9ji8D6A">Poker rules</a>
     */
    public void identifyWinningHand() {
        List<Hand> playerHands = players.stream()
                .map(this::identifyPlayerHand)
                .collect(Collectors.toList());
        winningHand = winningHandCalculator.calculateWinningHand(playerHands);

        for(Player player : players) {
            Hand playerHand = handIdentifier.identifyHand(player.getHand(), communityCards);
            if(winningHand.getHandTypeInt() == playerHand.getHandTypeInt()) {
                Hand highHand = checkHighSequenceWinner(winningHand, playerHand);
                if(highHand == null) {
                    highHand = checkHighCardWinner(winningHand, playerHand);
                }

                if(highHand != null) {
                    winningHand = highHand;
                }
            }
        }
    }

    private Hand checkHighSequenceWinner(Hand winningHand, Hand playerHand) {
        Integer winner = PokerUtil.getSequenceSum(winningHand.getHandTypeCards());
        Integer player = PokerUtil.getSequenceSum(playerHand.getHandTypeCards());

        if(winner > player) {
            return winningHand;
        } else if(winner < player) {
            return playerHand;
        } else {
            winner = PokerUtil.getSequenceSum(winningHand.getHighCards());
            player = PokerUtil.getSequenceSum(playerHand.getHighCards());
            if(winner > player) {
                return winningHand;
            } else if(winner < player){
                return playerHand;
            }
        }

        return null;
    }

    private Hand checkHighCardWinner(Hand winningHand, Hand playerHand) {
        Integer winnerCard = winningHand != null && winningHand.getHighCards()!=null && winningHand.getHighCards().size()>0
                ? winningHand.getHighCards().get(0).getRankToInt() : -1;
        Integer playerCard = playerHand != null && playerHand.getHighCards()!=null && playerHand.getHighCards().size()>0
                ? playerHand.getHighCards().get(0).getRankToInt() : -1;

        if(winnerCard > playerCard) {
            return winningHand;
        } else if(winnerCard < playerCard) {
            return  playerHand;
        } else {
            winnerCard = winningHand!=null && winningHand.getHighCards()!=null && winningHand.getHighCards().size()>1
                    ? winningHand.getHighCards().get(1).getRankToInt() : -1;
            playerCard = playerHand!=null && playerHand.getHighCards()!=null && playerHand.getHighCards().size()>1
                    ? playerHand.getHighCards().get(1).getRankToInt() : -1;

            if(winnerCard >= playerCard) {
                return winningHand;
            } else {
                return playerHand;
            }
        }
    }

    /**
     * Checks if the player won
     *
     * @param player
     * @return true if the player's hand is equal to the winning hand.
     */
    public boolean checkIfPlayerWon(Player player) {
        Hand playerHand = identifyPlayerHand(player);
        return winningHand!=null && playerHand!=null && winningHand.toString().equals(playerHand.toString());
    }

    /**
     * Identifies the player's hand. A hand is combination of the two cards in the player's
     * possession and the community cards on the table.
     *
     * @param player
     * @return The {@link} of a player, e.g. High Card, One Pair, Straight, etc.
     * @see <a href="https://www.youtube.com/watch?v=GAoR9ji8D6A">Poker rules</a>
     */
    public Hand identifyPlayerHand(Player player) {
        List<Card> playerCards = player.getHand();
        return handIdentifier.identifyHand(playerCards, communityCards);
    }

    /**
     * @return The list of {@link Player}s
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @return The list of community cards {@link Card}
     */
    public List<Card> getCommunityCards() {
        return communityCards;
    }

    /**
     * @return true if the number of community cards is equal to the maximum community cards allowed.
     */
    public boolean hasEnded() {
        return communityCards.size() >= MAX_COMMUNITY_CARDS;
    }

    private void dealHands() {
        for (int i = 0; i < MAX_PLAYER_CARDS; i++) {
            dealOneCardToEachPlayer();
        }
    }

    private void dealOneCardToEachPlayer() {
        players.forEach(player -> player.addToHand(deck.removeFromTop()));
    }

    private void dealThreeCommunityCards() {
        communityCards.add(deck.removeFromTop());
        communityCards.add(deck.removeFromTop());
        communityCards.add(deck.removeFromTop());
    }

    private void dealOneCommunityCard() {
        communityCards.add(deck.removeFromTop());
    }

    private void burnCard() {
        deck.removeFromTop();
    }

}
