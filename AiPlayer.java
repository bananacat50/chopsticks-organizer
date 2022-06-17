/** @author Elliott Cook */
// 6/17
// CSE 142
// Ms Myers
// a class that represents an AiPlayer, that plays in games either randomly or with moves chosen based on data

import java.util.*;
import java.io.*;

public class AiPlayer extends Player {
   /**
   * constructs an AiPlayer 
   * @param modeInput - determines whether the Ai uses the data it gathers or not
   */
   public AiPlayer(String modeInput) {
      super();
      mode = modeInput;
      movesThisGame = new ArrayList<String>();
      winningMoves = new ArrayList<String>();
      losingMoves = new ArrayList<String>();
   }
   
   /**
   * loads an AiPlayer from a file
   * @param modeInput - determines whether the Ai uses the data it gathers or not
   * @param fileName - the name of the file for the data to be loaded from
   */
   public AiPlayer(String modeInput, String fileName) {
      super();
      mode = modeInput;
      movesThisGame = new ArrayList<String>();
      try {
         File f = new File(fileName);
         Scanner s = new Scanner(f);
         winningMoves = new ArrayList<>(Arrays.asList(s.nextLine().split(", ")));
         losingMoves = new ArrayList<>(Arrays.asList(s.nextLine().split(", ")));
         s.close();
      } catch (FileNotFoundException e) {
         throw new RuntimeException(e);
      }
   }
   
   
   /**
   * finds all possible moves, chooses one, records it for later (to movesThisGame), and executes it
   * @param Opponent - the opposing player that it targets with its move
   * @param print - whether or not it should print what is happening in the game
   * @return whether the move was valid or not
   */
   public boolean makeMove(Player Opponent, boolean print) {
      ArrayList<String> moves = findPossibleMoves(Opponent);
      String move = chooseMove(moves, mode, Opponent);
      movesThisGame.add(LeftHand.getFingers() + " " + RightHand.getFingers() + " " +
                        Opponent.LeftHand.getFingers() + " " + Opponent.RightHand.getFingers() + " " + move);
      return executeMove(move, Opponent, print);
   }
   
   /**
   * finds a list of all valid moves available
   * @param Opponent - the player that it considers whether moves can be played against
   * @return the arraylist of all valid moves
   */
   public ArrayList<String> findPossibleMoves(Player Opponent) {
      ArrayList<String> possibleMoves = new ArrayList<String>();
      if (LeftHand.getFingers() > 0) {
         for (int i = LeftHand.getFingers(); i > 0 ; i--) {
            if (RightHand.getFingers()+i != LeftHand.getFingers()) {
               possibleMoves.add("left swap " + i);
            }
         }
         if (Opponent.LeftHand.getFingers() > 0) {
            possibleMoves.add("left hit left");
         }
         if (Opponent.RightHand.getFingers() > 0) {
            possibleMoves.add("left hit right");
         }
      }
      if (RightHand.getFingers() > 0) {
         for (int i = RightHand.getFingers(); i > 0 ; i--) {
            if (LeftHand.getFingers()+i != RightHand.getFingers()) {
               possibleMoves.add("right swap " + i);
            }
         }
         if (Opponent.LeftHand.getFingers() > 0) {
            possibleMoves.add("right hit left");
         }
         if (Opponent.RightHand.getFingers() > 0) {
            possibleMoves.add("right hit right");
         }
      }
      return possibleMoves;
   }
   
   /**
   * chooses which valid move to play
   * @param moves - the list of valid moves that it chooses between
   * @param mode - how it chooses which move to do: "chosen" to use gathered data and "random" to choose with no regard for the data
   * @param Opponent - the opponent that it is choosing the move to play against
   * @return the string that says the move chosen
   */
   public String chooseMove(ArrayList<String> moves, String mode, Player Opponent) {
      if (mode == "chosen") {
         ArrayList<String> chosenMoves = new ArrayList<String>();
         String boardState = LeftHand.getFingers() + " " + RightHand.getFingers() + " " +
                        Opponent.LeftHand.getFingers() + " " + Opponent.RightHand.getFingers();
         for (String s : winningMoves) {
            if (s.substring(0, 7).equals(boardState)) {
               chosenMoves.add(s.substring(8));
            }
         }
         if (chosenMoves.size() == 0) {
            return chooseMove(moves, "random", Opponent);
         }
         return chooseMove(chosenMoves, "random", Opponent);
      } else {
         return moves.get((int)(Math.random()*moves.size()));
      }
   }
   
   /**
   * loads an AiPlayer from a file
   * @param move - the move to be interpreted and executed
   * @param Opponent - the player to use the move against
   * @param print - whether or not to print what is going on in the game
   * @return whether the move was valid
   */
   public boolean executeMove(String move, Player Opponent, boolean print) {
      if (print) {
         System.out.println(move);
      }
      if (move.equals("left hit left")) {
         return LeftHand.hit(Opponent.LeftHand);
      }
      if (move.equals("left hit right")) {
         return LeftHand.hit(Opponent.RightHand);
      }
      if (move.equals("right hit left")) {
         return RightHand.hit(Opponent.LeftHand);
      }
      if (move.equals("right hit right")) {
         return RightHand.hit(Opponent.RightHand);
      }
      if (move.charAt(0) == 'l') {
         return LeftHand.swap(RightHand, Integer.parseInt(move.substring(move.length()-1)));
      }
      if (move.charAt(0) == 'r') {
         return RightHand.swap(LeftHand, Integer.parseInt(move.substring(move.length()-1)));
      }
      return false;
   }
   
   
   /**
   * applies the moves this game to update the winning moves and losing moves, then clears moves this game
   * @param win - whether the ai won or not (1 for a win, anything else for a loss)
   */
   public void reset(int win) {
      if (win == 1) {
         for (String s : movesThisGame) {
            if (losingMoves.contains(s)) {
               losingMoves.remove(losingMoves.indexOf(s));
            } else {
               winningMoves.add(s);
            }
         }
      } else {
         for (String s : movesThisGame) {
            if (winningMoves.contains(s)) {
               winningMoves.remove(winningMoves.indexOf(s));
            } else {
               losingMoves.add(s);
            }
         }
      }
      movesThisGame.clear();
      LeftHand.reset();
      RightHand.reset();
   }
   
   /**
   * saves an AiPlayer to a file
   * @param name - the name of the file to write the Ai to
   */
   public void save(String name) {
      try {
         FileWriter writer = new FileWriter(name);
         writer.write(winningMoves.toString().substring(1,winningMoves.toString().length()-1)  + System.lineSeparator());
         writer.write(losingMoves.toString().substring(1,losingMoves.toString().length()-1));
         writer.close();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}