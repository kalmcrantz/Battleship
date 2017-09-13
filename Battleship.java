import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.util.*;

/* BATTLESHIP */

public class Battleship extends JFrame implements ActionListener {
  
  //Stores player 1's set ships array
  private String[][] set1;
  
  //Stores player 2's set ships arrays
  private String[][] set2;
  
  //Stores player 1's active board array
  private JButton[][] play1;
  
  //Stores player 2's active board array
  private JButton[][] play2;
  
  //Stores player 1's board
  private JPanel board1;
  
  //Stores player 2's board
  private JPanel board2;
  
  //Stores playing board layout
  private JPanel boardLayout = new JPanel(new GridLayout(1, 2, 10, 0));
  
  //Stores the text at the top of the board
  private JLabel header = new JLabel("Welcome to Battleship! Please press OK button to begin setting each player's ships");
  
  //Stores the different strings displayed when setting ships
  private String[] headerArray = {"Player 1: Please place your Carrier (5 long) on the left board then press OK to lock in, or cancel to restart",
    "Player 1: Please place your Battleship (4 long) on the left board then press OK to lock in, or cancel to restart",
    "Player 1: Please place your Cruiser (3 long) on the left board then press OK to lock in, or cancel to restart",
    "Player 1: Please place your Submarine (3 long) on the left board then press OK to lock in, or cancel to restart",
    "Player 1: Please place your Destroyer (2 long) on the left board then press OK to lock in, or cancel to restart",
    "Player 2: Please place your Carrier (5 long) on the left board then press OK to lock in, or cancel to restart",
    "Player 2: Please place your Battleship (4 long) on the left board then press OK to lock in, or cancel to restart",
    "Player 2: Please place your Cruiser (3 long) on the left board then press OK to lock in, or cancel to restart",
    "Player 2: Please place your Submarine (3 long) on the left board then press OK to lock in, or cancel to restart",
    "Player 2: Please place your Destroyer (2 long) on the left board then press OK to lock in, or cancel to restart"};
  
  //Stores the index of which string is displayed for setting ships
  private int headerIndex = -1;
  
  //Stores whether or not it is player 1's turn or not
  private boolean player1 = false;
  
  //Stores whether or not the players are setting ships or playing the game
  private boolean setting = true;
  
  //Keeps track of the number of spaces player 1 has turned red (sunken of player 2)
  private int red1 = 0;
  
  //Keeps track of the number of spaces player 2 has turned red (sunken of player 1)
  private int red2 = 0;
  
  /*****************************************************CONSTRUCTORS********************************************************/
  
  //Constructor
  public Battleship(int n) {
    play1 = new JButton[n + 1][n + 1];
    play2 = new JButton[n + 1][n + 1];
    set1 = new String[n + 1][n + 1];
    set2 = new String[n + 1][n + 1];
    board1 = new JPanel(new GridLayout(n + 1, n + 1));
    board2 = new JPanel(new GridLayout(n + 1, n + 1));
    Container c = this.getContentPane();
    boardLayout.add(board1, -1);
    boardLayout.add(board2, -1);
    this.setSize(150 * n, 40 + (75 * n));
    header.setPreferredSize(new Dimension(this.getWidth(), 40));
    header.setFont(new Font("Header", Font.PLAIN, 25));
    header.setHorizontalAlignment(SwingConstants.CENTER);
    c.add(header, "North");
    c.add(boardLayout, "Center");
    for (int i = 0; i < play1.length; i++) {
      for (int j = 0; j < play1[i].length; j++) {
        if  (j == 0 && i != 0) {
          play1[i][j] = new JButton(String.valueOf((char)('A' + i - 1)));
          play2[i][j] = new JButton(String.valueOf((char)('A' + i - 1)));
          play1[i][j].setBackground(Color.lightGray);
          play2[i][j].setBackground(Color.lightGray);
        }
        else if (i == 0 && j != 0) {
          play1[i][j] = new JButton(String.valueOf(j));
          play2[i][j] = new JButton(String.valueOf(j));
          play1[i][j].setBackground(Color.lightGray);
          play2[i][j].setBackground(Color.lightGray);
        }
        else if (i == 0 && j == 0) {
          play1[i][j] = new JButton("OK");
          play2[i][j] = new JButton("CANCEL");
          play1[i][j].addActionListener(new Ok());
          play2[i][j].addActionListener(new Cancel());
          play1[i][j].setBackground(Color.GREEN);
          play2[i][j].setBackground(Color.RED);
        }
        else {
          play1[i][j] = new JButton();
          play2[i][j] = new JButton();
          play1[i][j].addActionListener(this);
          play2[i][j].addActionListener(this);
          play1[i][j].setBackground(Color.WHITE);
          play2[i][j].setBackground(Color.WHITE);
        }
        play1[i][j].setFont(new Font("Board Font", Font.PLAIN, 15));
        play2[i][j].setFont(new Font("Board Font", Font.PLAIN, 15));
        play1[i][j].setMargin(new Insets(0,0,0,0));
        play2[i][j].setMargin(new Insets(0,0,0,0));
        board1.add(play1[i][j], -1);
        board2.add(play2[i][j], -1);
      }
    }
    board1.setVisible(true);
    board2.setVisible(true);
    this.setVisible(true);
  }
  
  public static void main(String[] args) {
    try {
      if (args.length == 0) {
        new Battleship(10);
      }
      else if (args.length == 1) {
        new Battleship(Integer.parseInt(args[0]));
      }
      else {
        System.out.println("Cannot accomodate more than 1 integer");
      }
    }
    catch (NumberFormatException e) {
      System.out.println("Can only accomodate integers");
    }
  }
  
  public void actionPerformed (ActionEvent e) {
    JButton b = (JButton)e.getSource();
    if (header.getText().contains("Welcome")) {
    }
    else if (b.getBackground().equals(Color.WHITE)) {
      if (setting) {
        if (header.getText().contains("Player 1: Please place") && getPlay(b).equals(play1) && getShipNum() > getBlackCount(play1)) {
          b.setBackground(Color.BLACK);
        }
        else if (header.getText().contains("Player 2: Please place") && getPlay(b).equals(play2) && getShipNum() > getBlackCount(play2)) {
          b.setBackground(Color.BLACK);
        }
      }
      else if (player1 && getPlay(b).equals(play1)) {
        if (set2[getRow(b)][getColumn(b)] != null) {
          b.setBackground(Color.RED);
          checkSunkenShips(b, set2, play1);
          red1++;
        }
        else {
          b.setBackground(Color.GRAY);
        }
        changePlayer();
      }
      else if (!player1 && getPlay(b).equals(play2)) {
        if (set1[getRow(b)][getColumn(b)] != null) {
          b.setBackground(Color.RED);
          checkSunkenShips(b, set1, play2);
          red2++;
        }
        else {
          b.setBackground(Color.GRAY);
        }
        changePlayer();
      }
    }
    if (red1 ==  17 || red2 == 17) {
      endGame();
    }
  }
  
  private class Ok implements ActionListener {
    
    @Override
    public void actionPerformed (ActionEvent e) {
      if (header.getText().contains("Welcome"))
        headerIndex++;
      else if (setting) {
        if (player1Active() && getBlackCount(play1) == getShipNum()) {
          if (straight(play1)) {
            blackToGray(play1);
            headerIndex++;
          }
          else {
            notStraight();
          }
        }
        else if (!player1Active() && getBlackCount(play2) == getShipNum()) {
          if (straight(play2)) {
            blackToGray(play2);
            headerIndex++;
          }
          else {
            notStraight();
          }
        }
        else {
          notLongEnough();  
        }
      }
      if (headerIndex == 5) {
        turnWhite(play1);
      }
      if (headerIndex >= headerArray.length) {
        changePlayer();
        turnWhite(play2);
        setting = false;
      }
      else {
        header.setText(headerArray[headerIndex]);
      }
    }
  }
  
  private class Cancel implements ActionListener {
    
    @Override
    public void actionPerformed(ActionEvent e) {
      if (header.getText().contains("Player 1: Please place")) {
        for (int i = 1; i < play1.length; i++) {
          for (int j = 1; j < play1[i].length; j++) {
            if (play1[i][j].getBackground().equals(Color.BLACK)) {
              play1[i][j].setBackground(Color.WHITE);
            }
          }
        }
      }
      else if (header.getText().contains("Player 2: Please place")) {
        for (int i = 1; i < play2.length; i++) {
          for (int j = 1; j < play2[i].length; j++) {
            if (play2[i][j].getBackground().equals(Color.BLACK)) {
              play2[i][j].setBackground(Color.WHITE);
            }
          }
        }
      }
    }
  }
  
  //Ends the game
  public void endGame() {
    if (red1 == 17) {
      JOptionPane.showMessageDialog(null, "Congratualtions Player 1!");
    }
    else {
      JOptionPane.showMessageDialog(null, "Congratualtions Player 2!");
    }
  }
  
  //Changes spaces that are black to grey (locking in ship placement)
  public void blackToGray(JButton[][] play) {
    for (int i = 0; i < play.length; i++) {
      for (int j = 0; j < play[i].length; j++) {
        if (play[i][j].getBackground().equals(Color.BLACK)) {
          if (player1Active()) {
            set1[i][j] = getShipName();
          }
          else{
            set2[i][j] = getShipName();
          }
          play[i][j].setBackground(Color.GRAY);
        }
      }
    }
  }
  
  //Returns the board the button belongs to
  public JButton[][] getPlay(JButton b) {
    for (int i = 0; i < play1.length; i++) {
      for (int j = 0; j < play1[i].length; j++) {
        if (b.equals(play1[i][j]))
          return play1;
      }
    }
    return play2;
  }
  
  //Counts and returns the number of black spaces
  public int getBlackCount (JButton[][] play) {
    int count = 0;
    for (int i = 0; i < play.length; i++) {
      for (int j = 0; j < play[i].length; j++) {
        if (play[i][j].getBackground().equals(Color.BLACK)) 
          count++;
      }
    }
    return count;
  }
  
  //Returns true of player 1 is active in placing ships, false if player 2 is active
  public boolean player1Active() {
    if (header.getText().contains("Player 1: Please place"))
      return true;
    else
      return false;
  }
  
  //Returns the ship the header claims the player is placing
  public String getShipName() {
    if (header.getText().contains("Carrier"))
      return "Carrier";
    else if (header.getText().contains("Battleship"))
      return "Battleship";
    else if (header.getText().contains("Submarine"))
      return "Submarine";
    else if (header.getText().contains("Cruiser"))
      return "Cruiser";
    else if (header.getText().contains("Destroyer"))
      return "Destroyer";
    else
      return null;
  }
  
  public String getShipName(JButton b) {
    if (player1)
      return set2[getRow(b)][getColumn(b)];
    else
      return set1[getRow(b)][getColumn(b)];
  }
  
  //returns the number of spaces the current ship being placed takes up
  public int getShipNum() {
    if (header.getText().contains("Carrier"))
      return 5;
    else if (header.getText().contains("Battleship") )
      return 4;
    else if (header.getText().contains("Submarine") || header.getText().contains("Cruiser"))
      return 3;
    else if (header.getText().contains("Destroyer"))
      return 2;
    else
      return -1;
  }
  
  //Turns the board white
  public void turnWhite(JButton[][] play) {
    for (int i = 1; i < play.length; i++) {
      for (int j = 1; j < play[i].length; j++) {
        play[i][j].setBackground(Color.WHITE);
      }
    }
  }
  
  //Creates pop up window saying there are not enough spaces selected for the ship placement
  public void notLongEnough() {
    JOptionPane.showMessageDialog(null, "Your ship is not long enough");
  }
  
  //Returns the column of the button pressed
  public int getColumn(JButton b) {
    int index = -1;
    for (int i = 1; i < getPlay(b).length; i++) {
      for (int j = 1; j < getPlay(b)[i].length; j++) {
        if (getPlay(b)[i][j].equals(b))
          index = j;
      }
    }
    return index;
  }
  
  //Returns the row of the button pressed
  public int getRow(JButton b) {
    int index = -1;
    for (int i = 1; i < getPlay(b).length; i++) {
      for (int j = 1; j < getPlay(b)[i].length; j++) {
        if (getPlay(b)[i][j].equals(b))
          index = i;
      }
    }
    return index;
  }
  
  //Changes the player
  public void changePlayer() {
    player1 = !player1;
    if (player1)
      header.setText("Player 1's turn");
    else
      header.setText("Player 2's turn");
  }
  
  public boolean straight(JButton[][] play) {
    JButton b = firstSpace(play);
    if (checkDown(b) || checkRight(b) || checkDownLeft(b) || checkDownRight(b))
      return true;
    else
      return false;
  }
  
  public boolean checkDown(JButton b) {
    int count = 0;
    for (int i = getRow(b); i < getPlay(b).length; i++) {
      if (getPlay(b)[i][getColumn(b)].getBackground().equals(Color.BLACK)) 
        count++;
    }
    if (count == getShipNum())
      return true;
    else
      return false;
  }
  
  public boolean checkRight(JButton b) {
    int count = 0;
    for (int j = getColumn(b); j < getPlay(b)[getRow(b)].length; j++) {
      if (getPlay(b)[getRow(b)][j].getBackground().equals(Color.BLACK))
        count++;
    }
    if (count == getShipNum())
      return true;
    else
      return false;
  }
  
  public boolean checkDownLeft(JButton b) {
    int count = 0;
    for (int i = getRow(b), j = getColumn(b); i < getPlay(b).length && j > -1; i++, j--) {
      if (getPlay(b)[i][j].getBackground().equals(Color.BLACK))
        count++;
    }
    if (count == getShipNum())
      return true;
    else
      return false;
  }
  
  public boolean checkDownRight(JButton b) {
    int count = 0;
    for (int i = getRow(b), j = getColumn(b); i < getPlay(b).length && j < getPlay(b)[getRow(b)].length; i++, j++) {
      if (getPlay(b)[i][j].getBackground().equals(Color.BLACK))
        count++;
    }
    if (count == getShipNum())
      return true;
    else
      return false;
  }
  
  public JButton firstSpace(JButton[][] play) {
    for (int i = 0; i < play.length; i++) {
      for (int j = 0; j < play[i].length; j++) {
        if (play[i][j].getBackground().equals(Color.BLACK)) {
          return play[i][j];
        }
      }
    }
    return null;
  }
  
  public void notStraight() {
    JOptionPane.showMessageDialog(null, "Your ship placement is not in a line");
  }
  
  public void checkSunkenShips(JButton b, String[][] set, JButton[][] play) {
    boolean sunken = true;
    String s = set[getRow(b)][getColumn(b)];
    for (int i = 0; i < set.length; i++) {
      for (int j = 0; j < set[i].length; j++) {
        if (set[i][j] != null && set[i][j].equals(getShipName(b)) && !play[i][j].getBackground().equals(Color.RED))
          sunken = false;
      }
    }
    if (sunken) {
      for (int i = 0; i < set.length; i++) {
        for (int j = 0; j < set[i].length; j++) {
          if (set[i][j] != null && set[i][j].equals(getShipName(b))) {
            play[i][j].setFont(new Font("Header", Font.PLAIN, 80));
            play[i][j].setText("X");
          }
        }
      }
      JOptionPane.showMessageDialog(null, "You have sunken your opponent's " + set[getRow(b)][getColumn(b)]);
    }
  }
}