/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatedhearts;

import carddeck.*;
import java.util.NoSuchElementException;

/**
 *
 * @author Wilson
 */
public class Game {

    static final PlayingCard LEAD = new PlayingCard(Face.TWO, Suit.CLUBS, 0);
    static final int CONTROL = 26;
    static final int CAP = 100;
    HeartsDeck<PlayingCard> deck;
    int playerTurn;
    Player[] players;
    Trick[] tricks;

    Game(int players) {
        deck = new HeartsDeck();
        deck.createDeck();
        this.players = new Player[players];
        this.tricks = new Trick[deck.getSize() / players];
        createPlayers();
    }

    private void setUp() {
        deck.shuffle();
        deal();
        playerTurn = find(LEAD);
    }

    private void reclaimCards(Trick[] tricks) {
        for (Trick trick : tricks) {
            for (PlayingCard card : trick.getTrick())
                if (card != null)
                    deck.add(card);
            trick = null;
        }
    }

    public void play() {
        System.out.println("Starting Playing");

        while (!hasLoser()) {
            System.out.println();
            setUp();
            playRound();
            reclaimCards(tricks);
        }

        System.out.println();
        gameSummary();
    }

    public void playRound() {
        System.out.println("New Round");
        tricks[0] = firstTrick();
        for (int i = 1; i < tricks.length; i++)
            tricks[i] = playTrick();

        playerSummary();
    }

    private Trick firstTrick() {
        int newLead = 0;
        Trick trick = new Trick(players.length);
        players[playerTurn].play(trick, LEAD);
        for (int j = playerTurn + 1; j < players.length; j++)
            if (players[j].play(trick).equals(trick.getWinner()))
                newLead = j;

        for (int j = 0; j < playerTurn; j++)
            if (players[j].play(trick).equals(trick.getWinner()))
                newLead = j;
        playerTurn = newLead;
        players[newLead].addPoints(trick.getPoints());
        return trick;
    }

    private Trick playTrick() {
        int newLead = playerTurn;

        Trick trick = new Trick(players.length);

        for (int j = playerTurn; j < players.length; j++)
            if (players[j].play(trick).equals(trick.getWinner()))
                newLead = j;

        for (int j = 0; j < playerTurn; j++)
            if (players[j].play(trick).equals(trick.getWinner()))
                newLead = j;

        players[newLead].addPoints(trick.getPoints());
        playerTurn = newLead;

        return trick;
    }

    private void createPlayers() {
        System.out.println("Creating Players");
        String[] names = {"Lain", "Bender", "Spike", "Rick", "Cartman"};
        for (int i = 0; i < players.length; i++)
            players[i] = new Player(null, names[i]);
    }

    private void deal() {
        if (deck.getSize() == 0)
            return;
        HeartsHand[] hands = deck.deal();
        for (int i = 0; i < players.length; i++)
            players[i].setHand(hands[i]);
    }

    private int find(PlayingCard target) {
        for (int i = 0; i < players.length; i++)
            if (players[i].hasCard(target))
                return i;
        throw new NoSuchElementException();
    }

    public boolean hasLoser() {
        for (Player player : players)
            if (player.getScore() >= CAP)
                return true;
        return false;
    }

    public void playerSummary() {
        for (Player player : players)
            System.out.println("\t" + player.toString());
    }

    public void gameSummary() {
        System.out.println("GAME OVER");
        StringBuilder summary = new StringBuilder();
        Player[] ranked = orderByScore();

        System.out.print("\t" + ranked[0].getName());

        int index = 1;
        while (ranked[index++].getScore() == ranked[0].getScore())
            System.out.println(" and " + ranked[index].getName());
        System.out.println(" is the winner");

        System.out.print("\t" + ranked[ranked.length - 1].getName());
        index = ranked.length - 2;
        while (ranked[index--].getScore() == ranked[ranked.length - 1].getScore())
            System.out.print(" and " + ranked[index].getName());
        System.out.println(" is the loser");

        System.out.println("Total Scores:");
        for (Player p : ranked)
            System.out.println("\t" + p.toString());

        System.out.println(summary.toString());
    }

    private Player[] orderByScore() {
        Player[] ranked = new Player[players.length];
        for (Player p : players) {
            Player current = p;
            for (int i = 0; i < ranked.length; i++)
                if (ranked[i] == null) {
                    ranked[i] = current;
                    break;
                } else if (current.getScore() < ranked[i].getScore()) {
                    Player temp = ranked[i];
                    ranked[i] = current;
                    current = temp;
                }
        }
        return ranked;
    }
}
