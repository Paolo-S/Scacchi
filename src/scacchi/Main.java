package scacchi;

import java.awt.EventQueue;
import javax.swing.JFrame;
import scacchi.view.ScacchieraFrame;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
					public void run() {
						ScacchieraFrame frame = new ScacchieraFrame();
						frame.setSize(660, 700);
						frame.setResizable(false);
						frame.setTitle("Scacchi versione 3.1415");
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						frame.setVisible(true);	
					}
				});
		}

	}
