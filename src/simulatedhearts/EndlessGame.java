/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatedhearts;

import carddeck.PlayingCard;
import carddeck.Suit;
import carddeck.Trick;
import java.util.Stack;

/**
 *
 * @author Wilson
 */
public class EndlessGame extends Game {
    int count;
    Stack<Trick[]> roundResults;
    
    public EndlessGame(){
        super(4);
        roundResults = new Stack<Trick[]>();
    }
    
    @Override
    public void playRound(){
        setUp();
        tricks = new Trick[13];
        count = 0;
        play(0, playerTurn, playerTurn);
    }
    
    @Override 
    public void play(){
        playRound();
    }
    
    private void play(int trick, int p, int newLead){
        if(trick == 13){
            System.out.println("Reached Cap, returning");
            System.out.println("Count: " + ++count);
//            count++;
            roundResults.push(copyTricks());
            return;
        }
        if(tricks[trick] == null)
            tricks[trick] = new Trick(4);
        if(tricks[trick].isComplete()){
            System.out.println("\n\nTrick completed: " + tricks[trick].toString());
            
            play(trick + 1, newLead, newLead);
            return;
        }
        if(p == players.length)
            p = 0;
        PlayingCard[] plays = players[p].validPlays(tricks[trick].lead());
        for(int i = 0; i < plays.length; i++){
            try{
            System.out.println(" plays " + plays[i].toString());
            }catch(Exception e){
                System.out.println("EXCEPTION");
                for(PlayingCard c : plays)
                    System.out.println(((c == null) ? "null" : c.toString()));
                System.out.println("Actual hand, size: " + players[p].hand.getSize() + "counts h" + players[p].hand.getSuitCount(
                        Suit.HEARTS) + " s " + players[p].hand.getSuitCount(
                        Suit.SPADES) + " c " + players[p].hand.getSuitCount(
                        Suit.CLUBS) + " d " + players[p].hand.getSuitCount(
                        Suit.DIAMONDS));
                for(PlayingCard c : players[p].hand)
                    System.out.println(((c == null) ? "null" : c.toString()));
                System.out.println("Clean hand");
                for(PlayingCard c : players[p].hand.getCleanHand())
                    System.out.println(((c == null) ? "null" : c.toString()));
                throw e;
            }
            players[p].play(tricks[trick], plays[i]);
            if(plays[i].equals(tricks[trick].getWinner()))
                newLead = p;
            play(trick, p + 1, newLead);
            players[p].insert(plays[i]);
            tricks[trick].removeLast();
        }
    }
    
    private Trick[] copyTricks(){
        Trick[] copy = new Trick[tricks.length];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = tricks[i];
        }
        return copy;
    }
}
