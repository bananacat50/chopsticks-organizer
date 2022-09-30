/** @author Elliott Cook */
// 6/17
// CSE 142
// Ms Myers
// This class represents human players, who choose their own moves.
import java.util.*;

public class HumanPlayer extends Player {
   Scanner s;
   
   /**
   * constructs a human player, with a scanner for the player's moves to be 
   * inputted in via text
   */
   public HumanPlayer() {
      super();
      s = new Scanner(System.in);
   }
   
   /**
   * asks the player to choose a move with a few questions, then executes the 
   * move
   * @param Opponent - the player to make the moves against
   * @param print -  does nothing, put here so that it can be called in the same 
   *                 way as AiPlayer's makeMove
   * @return whether the move was valid
   */
   public boolean makeMove(Player Opponent, boolean print) {
      System.out.println("input hand to do action (left/right)");
      String hand = s.next();
      System.out.println("input action to do (hit/swap)");
      String action = s.next();
      if (action.equals("swap")) {
         System.out.println("swap how many");
         int amount = 0;
         try {
            amount = s.nextInt();
         } catch (java.util.InputMismatchException e) {
            s.next();
            return false;
         }
         if (hand.equals("right")) {
            return RightHand.swap(LeftHand, amount);
         } else {
            return LeftHand.swap(RightHand, amount);
         }
      } else {
         System.out.println("hit which (left/right)");
         String target = s.next();
         if (hand.equals("right")) {
            if (target.equals("right")) {
               return RightHand.hit(Opponent.RightHand);
            } else {
               return RightHand.hit(Opponent.LeftHand);
            }
         } else {
            if (target.equals("right")) {
               return LeftHand.hit(Opponent.RightHand);
            } else {
               return LeftHand.hit(Opponent.LeftHand);
            }
         }
      }
   }
}