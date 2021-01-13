package connect4.game;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;


public class Gui {
    //declaration of Gui objects
    private static JFrame frame;
    private JLabel[][] slots;
    private JButton[] buttons;
    //variables used in grid
    private int xsize = 7;
    private int ysize = 6;
    private int currentPlayer = 1;
    //game variables to communicate with top program
    private boolean hasWon = false;
    private boolean hasDraw = false;
    private  boolean quit = false;
    private  boolean newGame = false;
    private String player1;
    private String player2;
    
    //image of coin for player1
   private  ImageIcon image1 = new ImageIcon("PoroImages/player1_image.png");
    //image of coin for player2 
   private  ImageIcon image2 = new ImageIcon("PoroImages/player2_image.png");
   private  ImageIcon image3 = new ImageIcon("PoroImages/welcomeImage.png");
   

   //menubar 
      static JMenuBar mb;
    
   //JMenu 
     static JMenu x1,x2,x3; 
    
    
    //making of grid and Logic:
    Grid my_grid = new Grid();
    Logic my_logic = new Logic(my_grid); //create game Logic

    public Gui() {

    	//drawing the background campus layout for the game with a try and catch block for errors
        try {
			frame = new JFrame("Connect 4: Poro Edition") {
				private Image backgroundImage = ImageIO.read(new File("PoroImages/connect4 background image.png"));
			public void paint( Graphics g ) { 
			    super.paint(g);
			    g.drawImage(backgroundImage, 0, 0, null);
			  }
			
			};
			//catch blocks associated with drawing the background
		} catch (HeadlessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        //create a menu bar:
        mb = new JMenuBar(); 
 
       // create menu:
        x1= new JMenu("How to Play"); 
        x2= new JMenu("Restart Game");
        x3= new JMenu("Exit Game");     
    
      //add menu to menu bar 
        mb.add(x1); 
        mb.add(x2);
        mb.add(x3);
           
     // add menu bar to frame 
        frame.setJMenuBar(mb); 
        
        //to set action to the menu "How to Play" on menu bar:      
        x1.addMenuListener(new MenuListener() {

            public void menuSelected(MenuEvent e) {
            	aboutConnect4(); 
            	
            }
			@Override
			public void menuCanceled(MenuEvent arg0) {
				// TODO Auto-generated method stub				
				
			}

			@Override
			public void menuDeselected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
        });
                 
       
      //to set action to the menu "Restart" on menu bar: 
        x2.addMenuListener(new MenuListener() {

            public void menuSelected(MenuEvent e) {          	
            	
            	int n3 = JOptionPane.showConfirmDialog(null, 
	                    "Are you sure you want to Restart the game?", "Restart Game",
	                    JOptionPane.YES_NO_OPTION);
	           if(n3 < 1) 
	           {
	        	 
		            frame.dispose();      
		            newGame = true;
	           }
	           else {
	        	   //if pressed NO, then game will be resumed:
            	   setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);         	   
	           }      	
            	
            }

			@Override
			public void menuCanceled(MenuEvent arg0) {
				// TODO Auto-generated method stub						
			}

			@Override
			public void menuDeselected(MenuEvent arg0) {
				// TODO Auto-generated method stub					
			}
			
        });
        
        
      //to set action to the menu "Exit" on menu bar: 
        x3.addMenuListener(new MenuListener() {

            public void menuSelected(MenuEvent e) {
            	
            	System.out.println("menuSelected");

               //Prompting the user if they want to close a second time:
                int n1 = JOptionPane.showConfirmDialog(null, 
                        "Are you sure you want to Exit the Game? :(", "Exit Game",
                        JOptionPane.YES_NO_OPTION);
               if(n1 < 1) 
               {
            	   //exit game if pressed YES:
            	   frame.dispose();
            	   quit=true; 
               }
               else 
               {
            	   //if pressed NO, then game will be resumed:
            	   setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
            	           	 
               }
            	             	
            }
			
			@Override
			public void menuDeselected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("menuDeselected");			
				
			}
			@Override
			public void menuCanceled(MenuEvent arg0) {
				// TODO Auto-generated method stub	
				System.out.println("menuCanceled");					
			}		
        });
        
        
        JPanel panel = (JPanel) frame.getContentPane();
        panel.setLayout(new GridLayout(xsize, ysize + 1));

        slots = new JLabel[xsize][ysize];
        buttons = new JButton[xsize];
        
        for (int i = 0; i < xsize; i++) {
            buttons[i] = new JButton("" + (i + 1));
            buttons[i].setBackground(Color.cyan);
            buttons[i].setActionCommand("" + i);
            buttons[i].addActionListener(
                    new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            int a = Integer.parseInt(e.getActionCommand());
                            int y = my_grid.find_y(a);//check for space in column
                            if (y != -1) {
                                //sets a place to current player
                                if (my_logic.set_and_check(a, y, currentPlayer)) {
                                    hasWon = true;
                                } else if (my_logic.draw_game()) {//checks for draw game
                                    hasDraw = true;
                                } else {
                                    //change player
                                    currentPlayer = my_grid.changeplayer(currentPlayer, 2);
                                    //to display whose turn on the title:
                                    if(currentPlayer == 1)
                                    frame.setTitle("Connect 4 Game - " + player1 + "'s turn");
                                    else if(currentPlayer == 2)
                                    frame.setTitle("Connect 4 Game -  " + player2 + "'s turn");
                                }
                            } else {
                            	JOptionPane.showMessageDialog(null, "This column is filled. Please choose another one", "Error Message", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    });
            panel.add(buttons[i]);
        }
        for (int column = 0; column < ysize; column++) {
            for (int row = 0; row < xsize; row++) {
            	//create a new JLabel holding the default image for tile display
                slots[row][column] = new JLabel();
                slots[row][column].setHorizontalAlignment(SwingConstants.CENTER);
                slots[row][column].setBorder(new LineBorder(Color.black));
                panel.add(slots[row][column]);
            }
        }

        //jframe stuff
        frame.setContentPane(panel);
        frame.setSize(
                700, 600); //sets panel dimensions 
        frame.setVisible(
                true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
    }

    protected void setDefaultCloseOperation(int doNothingOnClose) {
		// TODO Auto-generated method stub
		
	}

	public void updateBoard() {//keeps the Gui in sync with the Logic and grid
        for (int row = 0; row < xsize; row++) {
            for (int column = 0; column < ysize; column++) {
                if (my_grid.matrix_equals(row, column, 1)) {
                	//to set poro image 1:
                	slots[row][column].setIcon(image1);                	
                    //literally add the image to the panel//
                } 
                if (my_grid.matrix_equals(row, column, 2)) {
                	//to set poro image 2:
                  slots[row][column].setIcon(image2);
                }
            }
        }
    }

//displaying who wins the game 
public void showWon() {
	
	int n;
	String winner = "";
	
	//when player 1 wins:
	if(currentPlayer == 1)
	{
		 winner =  player1 + " Wins";
		 
		 n= JOptionPane.showConfirmDialog(null,
		            winner +"\n" + "Start a New Game?",
		            "WINNER",
		           JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE,  
		            image1);	  
	
	 if  (n <1) {
         frame.dispose();
         newGame = true;               
     } 
     
     else {
     	 //Prompting the user if they want to close a second time:
         int n1 = JOptionPane.showConfirmDialog(null, 
                 "Are you sure you want to Exit the Game? :(", "Exit Game",
                 JOptionPane.YES_NO_OPTION);
        if(n1 < 1) 
        {
     	   frame.dispose();
     	   quit=true; 
        }
        else {
     	  
         frame.dispose();
         newGame = true;          
     }
  }
	 
}
	
	
	//when player 2 wins:
	else if (currentPlayer == 2)
	{
		 winner = player2 + " Wins";
		
		 n= JOptionPane.showConfirmDialog(null,
		            winner+"\n" + "Start a New Game?",
		            "WINNER",
		           JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE,  
		            image2);
		  
		  if  (n <1) {
	            frame.dispose();
	            newGame = true;               
	        } 
	        
	        else {
	        	 //Prompting the user if they want to close a second time:
	            int n1 = JOptionPane.showConfirmDialog(null, 
	                    "Are you sure you want to Exit the Game? :(", "Exit Game",
	                    JOptionPane.YES_NO_OPTION);
	           if(n1 < 1) 
	           {
	        	   frame.dispose();
	        	   quit=true; 
	           }
	           else {
	        	  
	            frame.dispose();
	            newGame = true;          
	        }
	    }
		  
	}
       
}
 
	public void showDraw() {
        String winner = "Draw Game";
        int n = JOptionPane.showConfirmDialog(
                frame,
                "There are no winners. Start New Game?",
                winner,
                JOptionPane.YES_NO_OPTION);
        if (n < 1) 
        {
            frame.dispose();
            newGame = true;
        } else 
        {  	
            frame.dispose();
            quit = true;
        }
    }
    
    
    //enter player name:
    public void playerName() 
    {
    	
	     player1 = JOptionPane.showInputDialog(null,"Enter player 1 name ", "Player 1", JOptionPane.INFORMATION_MESSAGE );
    
         //code if pressed OK by player 1: 
	    if (player1 != null)
	    {
	    	
	    	player2 = JOptionPane.showInputDialog(null,"Enter player 2 name ", "Player 2", JOptionPane.INFORMATION_MESSAGE );
	    	if(player2 != null)
	    	{
	    		//code if pressed OK by player 2
	    		
	    	}   	
	    	
	    	//code if pressed cancel by player 2
	    	else if (player2 == null)
	    	{	    		
		    	int n3 = JOptionPane.showConfirmDialog(null, 
	                    "Are you sure you want to Exit the Game? :(", "Exit Game",
	                    JOptionPane.YES_NO_OPTION);
		    	//if yes:
	           if(n3 < 1) 
	           {
	        	   frame.dispose();
	        	   quit=true; 
	           }
	           //if no:
	           else {
	        	frame.dispose();      
	            newGame = true;
	           }
	    	}

	    }
	    
	  //code if pressed CANCEL by player 1
	    else if(player1 == null){      	        
	    	int n2 = JOptionPane.showConfirmDialog(null, 
                    "Are you sure you want to Exit the Game? :(", "Exit Game",
                    JOptionPane.YES_NO_OPTION);
           if(n2 < 1) 
           {
        	   frame.dispose();
        	   quit=true; 
           }
           else {      	
            frame.dispose();      
            newGame = true;
           }
	    }
    }

    public boolean getHasWon() {
        return hasWon;
    }

    public boolean getHasDraw() {
        return hasDraw;
    }

    public boolean getQuit() {
        return quit;
    }

    public boolean getNewGame() {
        return newGame;
    }
    
    //Menu bar -  game description:
    private void aboutConnect4() {
    	
    	JOptionPane.showMessageDialog(null, " WELCOME TO CONNECT 4: PORO EDITION ! \n The objective of this game is you must be the \n "
    			+ "first Player to get Four of your colored Poro icons \n in a row either horizontally, vertically or diagonally.\n HAVE FUN!");
    }
    
    public void welcome()
    {
    	 int message= JOptionPane.showConfirmDialog(null,
		           "WELCOME TO CONNECT 4: PORO EDITION \n************************************************ \nClick yes to begin game! Click no if you changed your mind...",
		            "Welcome!",
		           JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE,  
		            image3);
    	 if (message < 1) 
         {
            playerName();
         } else 
         {  	
           
        	 int n3 = JOptionPane.showConfirmDialog(null, 
	                    "Are you sure you want to Exit the Game? :(", "Exit Game",
	                    JOptionPane.YES_NO_OPTION);
		    	//if yes:
	           if(n3 < 1) 
	           {
	        	   frame.dispose();
	        	   quit=true; 
	           }
	           //if no:
	           else {
	        	frame.dispose();      
	            newGame = true;
	           } 
        	 
        	 
         }
    	 
    }

   
  
}


