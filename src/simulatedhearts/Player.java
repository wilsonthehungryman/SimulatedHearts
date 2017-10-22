/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatedhearts;

import carddeck.HeartsHand;
import carddeck.PlayingCard;
import carddeck.Trick;

/**
 *
 * @author Wilson
 */
public class Player {

    HeartsHand<PlayingCard> hand;
    String name;
    int score;

    public Player(HeartsHand hand) {
        this(hand, "Anon");
    }
    
    public Player(String name){
        this(null, name);
    }
    
    public Player(HeartsHand hand, String name){
        this.hand = hand;
        this.name = name;
        score = 0;
    }

    public HeartsHand<PlayingCard> getHand() {
        return hand;
    }

    public void setHand(HeartsHand<PlayingCard> hand) {
        this.hand = hand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addPoints(int points) {
        this.score += points;
    }
    
    public void removePoints(int points){
        this.score -= points;
    }
    
    public boolean hasCard(PlayingCard target){
        return hand.hasCard(target);
    }

    public PlayingCard play(Trick trick) {
        PlayingCard myPlay;
        if (trick.lead() == null)
            myPlay =  playLead();
        else
            myPlay = playValid(trick.lead());
        trick.add(myPlay);
        return myPlay;
    }
    
    public PlayingCard play(Trick trick, PlayingCard target){
        PlayingCard returnValue = hand.play(target);
        trick.add(returnValue);
        return returnValue;
    }

    // Replace with actual decision
    PlayingCard playLead() {
        return hand.play(0);
    }

    // Replace with actual decision
    PlayingCard playValid(PlayingCard lead) {
        if (!hand.hasSuit(lead.getSuit()))
            return hand.play(0);
        int index = 0;
        for (PlayingCard c : hand) {
            if (c.getSuit() == lead.getSuit())
                break;
            index++;
        }
        return hand.play(index);
    }
    
    PlayingCard[] validPlays(PlayingCard lead){
        if(lead == null || !hand.hasSuit(lead.getSuit()))
            return hand.getHand();
        PlayingCard[] returnValues = new PlayingCard[hand.getSuitCount(
                lead.getSuit())];
        int returnValuesIndex = 0;
        for(int i = 0; i < hand.getSize() && returnValuesIndex < returnValues.length; i++){
            PlayingCard c = hand.getAt(i);
            if(c.getSuit() == lead.getSuit()){
                returnValues[returnValuesIndex] = c;
                returnValuesIndex++;
            }
        }
        return returnValues;
    }
    
    @Override
    public String toString(){
        return name + " has a total score of " + score;
    }
}
