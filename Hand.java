/** @author Elliott Cook */
// 6/17
// CSE 142
// Ms Myers
// Creates an object which represents a hand, which actually does the moves in 
// the game and tracks the number of fingers. Each player has 2.

public class Hand {
   private int fingers;

   /**
   * constructs a hand in the default state of one finger
   */
   public Hand() {
      fingers = 1;
   }
   
   /**
   * returns the amount of fingers on the hand
   * @return the number of fingers on the hand
   */
   public int getFingers() {
      return fingers;
   }
   
   /**
   * adds fingers to a hand, looping around at five
   * @param more - the amount of fingers that are added to the hand
   */
   public void add(int more) {
      fingers = (fingers + more) % 5;
   }
   
   /**
   * hits another hand, adding its own number of fingers to the other
   * @param other - the other hand (which is getting hit)
   * @return whether this was a valid move or not
   */
   public boolean hit(Hand other) {
      if (fingers == 0 || other.getFingers() == 0) {
         return false;
      }
      other.add(fingers);
      return true;
   }
   
   /**
   * swaps fingers from one hand to the other
   * @param opposite -  the other hand of the same player, which is being 
   *                    swapped to
   * @param amount - the number of fingers to be swapped from one to the other
   * @return whether the move was valid
   */
   public boolean swap(Hand opposite, int amount) {
      if (amount>fingers || opposite.getFingers() + amount == fingers || 
            amount <= 0) {
         return false;
      }
      opposite.add(amount);
      add(-1*amount);
      return true;
   }
   
   /**
   * sets the number of fingers back to 1
   */
   public void reset() {
      fingers = 1;
   }
}