/** @author Elliott Cook */
// 6/17
// CSE 142
// Ms Myers
// Chopsticks Optimiser
// loads an existing AI, plays it for a lot of games, then saves it, and then plays it against the user.

import java.io.*;

public class RunGame {
   /**
   * loads an Ai from a file, plays it against another Ai for 100 games 100 times or until one wins more than 3/4
      after 50 games have been played, then saves it and plays it against the user.
   */
   public static void main(String[] args) {
      Game g = new Game(new AiPlayer("chosen", "filename"), new AiPlayer("chosen"));
      int s = 0;
      int numGames = 100;
      int numloops = 0;
      Player betterPlayer = new Player();
      for (int j = 0; j < numloops; j++) {
         for (int i = 0; i < numGames; i++) {
            s += g.play(false);
         }
         double percent = s*100.0/numGames;
         System.out.println(percent + "%");
         if (percent>75&&j>=50) {
            betterPlayer = g.p1;
            break;
         } else if (percent<25&&j>=50) {
            betterPlayer = g.p2;
            break;
         }
         s = 0;
         if (j == 99) {
            betterPlayer = g.p1;
         }
      }
      if (numloops > 0) {
         betterPlayer.save("filename");
      } else {
         betterPlayer = g.p1;
      }
      Game g2 = new Game(betterPlayer, new HumanPlayer());
      for (int i = 0; i < 3; i++) {
         g2.play(true);
      }
   }
}