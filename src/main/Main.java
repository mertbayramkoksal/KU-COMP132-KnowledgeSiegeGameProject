/************** Pledge of Honor ******************************************
I hereby certify that I have completed this programming project on my own without
any help from anyone else. The effort in the project thus belongs completely to me.
I did not search for a solution, or I did not consult any program written by others
or did not copy any program from other sources. I read and followed the guidelines
provided in the project description.
READ AND SIGN BY WRITING YOUR NAME SURNAME AND STUDENT ID
SIGNATURE: <Mert Bayram Köksal>
*************************************************************************/


package main;

import javax.swing.SwingUtilities;
import screens.MainFrame;

/**
 * The {@code Main} class serves as the entry point for the Knowledge Siege application.
 * It initializes the application's main frame on the Event Dispatch Thread (EDT) using {@link SwingUtilities}.
 *
 * This class ensures that the GUI is created and updated in a thread-safe manner, as recommended by the Swing framework.
 *
 * Usage:
 * {@code java main.Main}
 *
 * Responsibilities:
 * -Launch the game via the {@link MainFrame} class
 * -Ensure Swing components are initialized on the EDT
 *
 */
public class Main {

    public static void main(String[] args) {
    	SwingUtilities.invokeLater(new Runnable() {
    	    @Override
    	    public void run() {
    	        MainFrame frame = new MainFrame();
    	        frame.setVisible(true);
    	    }
    	});
    }
}
