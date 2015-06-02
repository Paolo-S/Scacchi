package scacchi.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/*
 * EndMessage mostra il messaggio di fine partita e propone ai giocatori
 * se iniziare o meno una nuova partita.
 */
public class EndMessage extends JDialog{
	private static final long serialVersionUID = 1L;
	private final JPanel end = new JPanel(new BorderLayout(3,1));
	private final JPanel northPanel = new JPanel(new BorderLayout());
	private final JPanel centerPanel = new JPanel(new BorderLayout());
	private final JPanel southPanel = new JPanel(new BorderLayout(1,2));
	private final JButton newGame = new JButton("New Game");
	private final JButton exit = new JButton("Cancel");
	private final JLabel message1, message2;
	private final static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private final static int HEIGHT = 300;
	private final static int WIDTH = 200;
	
	public EndMessage (int colore, ScacchieraFrame frame){
		setSize(HEIGHT, WIDTH);
		if(colore == 0){
			message1 = new JLabel("\n Hanno vinto i Neri.\n");
			message2 = new JLabel("\n Clicca New Game per rigiocare!");}
		else{
			message1 = new JLabel("\n Hanno vinto i Bianchi.\n");
			message2 = new JLabel("\n Clicca New Game per rigiocare!");}
		
		setLocation( (dim.width - HEIGHT) / 2, (dim.height - WIDTH) / 2 );
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		newGame.setPreferredSize(new Dimension(125, 50));
		exit.setPreferredSize(new Dimension(125, 50));
		setModal(true);
		setResizable(false);
		
		end.setBorder(new EmptyBorder(15,15,15,15));
		northPanel.add(message1);
		centerPanel.add(message2);
		southPanel.add(newGame, BorderLayout.EAST);
		southPanel.add(exit, BorderLayout.WEST);
		end.add(northPanel, BorderLayout.NORTH);
		end.add(centerPanel, BorderLayout.CENTER);
		end.add(southPanel, BorderLayout.SOUTH);
		
		// Se il giocatore clicca su New Game inizializza la scacchiera e chiude questo frame
		newGame.addActionListener
		(event -> { frame.inizializza(); dispose();});
		
		// Se il giocatore clicca su Cancel torna alla scacchiera
		exit.addActionListener
		(event -> { dispose();});
		
		add(end, BorderLayout.CENTER);
		setVisible(true);
		pack();
	}
	
}