/** @author Elliott Cook */
// 6/17
// CSE 142
// Ms Myers
// a class that represents a game played by two players, it can be ran multiple times.

import java.util.*;

public class Game {
   Player p1;
   Player p2;
   boolean firstTime;
   
   /**
   * constructs a game with two players
   * @param player1 - the player that moves first in the game
   * @param player2 - the player that moves second in the game
   */
   public Game(Player player1, Player player2) {
      p1 = player1;
      p2 = player2;
      firstTime = true;
   }
   
   /**
   * asks whether the player wants instructions for the first game ran, then alternates letting the players make moves,
      checking whether the game ended between each move. it then resets the players' moves of the game
   * @param print - whether or not the details of the game should be shown to the user
   * @return which player number won the game
   */
   public int play(boolean print) {
      if (firstTime && print) {
         firstTime = false;
         if (p1 instanceof HumanPlayer) {
            System.out.println("Player 1 is a human player");
         }
         if (p2 instanceof HumanPlayer) {
            System.out.println("Player 2 is a human player");
         }
         System.out.println("print instructions? (y/n)");
         Scanner s = new Scanner(System.in);
         if (s.next().equals("y")) {
            System.out.println("Each player has 2 hands, with 5 fingers each.");
            System.out.println("The goal of the game is to reduce the number of");
            System.out.println("fingers on each of your opponent's hands to 0.");
            System.out.println("The two players take turns, starting with player 1.");
            System.out.println("On your turn you can either hit an opponent's hand with");
            System.out.println("one of yours or swap fingers between your hands.");
            System.out.println("Hitting an opponent's hand adds fingers to it");
            System.out.println("equal to the amount that is on the hand that hit it.");
            System.out.println("Swapping fingers reduces one of your hands by a chosen");
            System.out.println("amount and adds that amount to the other hand.");
            System.out.println("You can't swap more fingers than you have or do a");
            System.out.println("swap that just mirrors your side of the board.");
            System.out.println("If a hand goes to 5 fingers or over, it loops back to 0.");
         }
      }
      int rounds = 0;
      while(true) {
         if (print) {
            System.out.println("player 1:");
         }
         while(!p1.makeMove(p2, print)) {
            System.out.println("invalid move");
         }
         if (print) {
            System.out.println("Player 2 (left/right): " + p2.LeftHand.getFingers() + " " + p2.RightHand.getFingers() + "\nPlayer 1 (left/right): " + p1.LeftHand.getFingers() + " " + p1.RightHand.getFingers());
         }
         if (p2.dead()) {
            reset(1, print);
            return 1;
         } else if (p1.dead()) {
            reset(2, print);
            return 0;
         }
         if (print) {
            System.out.println("player 2:");
         }
         while(!p2.makeMove(p1, print)) {
            System.out.println("invalid move");
         }
         if (print) {
            System.out.println("Player 2: " + p2.LeftHand.getFingers() + " " + p2.RightHand.getFingers() + "\nPlayer 1 (left/right):" + p1.LeftHand.getFingers() + " " + p1.RightHand.getFingers());
         }
         if (p2.dead()) {
            reset(1, print);
            return 1;
         } else if (p1.dead()) {
            reset(2, print);
            return 0;
         }
         rounds++;
         if (rounds >= 100) {
            reset(3, print);
            return 3;
         }
      }
   }
   
   /**
   * prints the result of the game, then resets the players, telling them whether they won
   * @param winner - which player won (1 for the first player, 2 for the second, 3 for a tie)
   * @param print - whether the results of the game should be printed
   */
   public void reset(int winner, boolean print) {
      if (print) {
         if (winner < 3) {
            System.out.println("Player " + winner + " wins");
         } else {
            System.out.println("tie.");
         }
      }
      p1.reset(winner);
      p2.reset((winner+1)%2);
   }
}