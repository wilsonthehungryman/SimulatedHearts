/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatedhearts;
/**
 *
 * @author Wilson
 */
public class SimulatedHearts {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        run();
    }
    
    public static void run(){
        Game game = new Game(4);
        game.play();
    }
    
    
}
