/** @author Elliott Cook */
// 6/17
// CSE 142
// Ms Myers
// a generic player class that is put into games, a parent class for ai and human players

import java.util.*;
import java.io.*;

public class Player {
   Hand RightHand;
   Hand LeftHand;
   ArrayList<String> movesThisGame;
   ArrayList<String> winningMoves;
   ArrayList<String> losingMoves;
   String mode;
   
   /**
   * constructs a player with two hands in their default state
   */
   public Player() {
      RightHand = new Hand();
      LeftHand = new Hand();
   }
   
   /**
   * a placeholder method so that the human and ai players can make moves when defined more generally. throws an error when called
   * @param opponent - does nothing
   * @param print - does nothing
   * @return would return whether the move was valid if it was ever reached
   */
   public boolean makeMove(Player opponent, boolean print) {
      System.out.println("no");
      throw new RuntimeException("no");
   }
   
   
   /**
   * returns whether a player has lost the game yet
   * @return whether the player has lost
   */
   public boolean dead() {
      return LeftHand.getFingers() + RightHand.getFingers() == 0;
   }
   
   
   /**
   * resets each hand of the player
   * @param win - whether the player won (only used by AiPlayer
   */
   public void reset(int win) {
      LeftHand.reset();
      RightHand.reset();
   }
   
   
   /**
   * a placeholder for AiPlayers to save to files (saves an empty file if called elsewhere)
   * @param name - what would be the name of the file if this was ever called
   */
   public void save(String name) {
      try {
         FileWriter writer = new FileWriter(name);
         writer.write(winningMoves.toString());
         writer.write(losingMoves.toString());
         writer.close();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}