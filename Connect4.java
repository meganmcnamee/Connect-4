/* -- Connect 4 Game -- CSC 330 Software Design Project  
 * by Megan McNamee, Brandon Torres and Rinu Xavier
 */


package connect4.game;

public class Connect4 {

    public static void main(String[] args) {
        int state = 0;
        boolean useGui = true;//sets if we want the cli or Gui.
        
        Gui Gui = new Gui();
        Gui.welcome();      

        if (useGui) {
           
            while (state != -1) {//checks if program is in quitting stage
                switch (state) {
                    case 0://runtime
                        Gui.updateBoard();
                        if (Gui.getHasWon()) {
                            state = 1;
                        } else if (Gui.getHasDraw()) {
                            state = 2;
                        } else if (Gui.getNewGame()) {
                            Gui = new Gui();
                          //Updates the player information for second game
                            Gui.playerName();
                            state = 0;
                        }
                        break;
                    case 1://endgame with winner
                        Gui.showWon();
                        if (Gui.getQuit()) {
                            state = -1;
                        } else  if (Gui.getNewGame()) {
                            Gui = new Gui();
                            Gui.playerName();
                            state = 0;
                        }
                        break;
                    case 2://endgame with drawgame
                        Gui.showDraw();
                        if (Gui.getQuit()) {
                            state = -1;
                        } else if (Gui.getNewGame()) {
                            Gui = new Gui();
                            state = 0;
                        }
                        break;
                }
            }
        } else {
            Cli Cli = new Cli();
            while (state != -1) {//checks if program is in quitting stage
                switch (state) {
                    case 0:
                        Cli.runtime();
                        if (Cli.getHasWon()) {
                            state = 1;
                        } else if (Cli.getHasDraw()) {
                            state = 2;
                        }
                        break;
                    case 1://prints endgame with winner
                    	Gui.showWon();
                        Cli.showWin();
                        if (Cli.getQuit()) {
                            state = -1;
                        } else if (Cli.getNewGame()) {
                            Cli = new Cli();
                            state = 0;
                        }
                        break;
                    case 2://prints end game with draw game
                        Cli.showDraw();
                        if (Cli.getQuit()) {
                            state = -1;
                        } else if (Cli.getNewGame()) {
                        	
                            Cli = new Cli();
                            state = 0;
                        }
                        break;
                }
            }
        }
    }

	
}