package scacchi.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import scacchi.model.Casella;
import scacchi.controller.Controller;

/*
 * ScacchieraFrame: finestra di gioco con la scacchiera, il pulsante di reset e di informazioni
 */

public class ScacchieraFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private final JPanel gui = new JPanel(new BorderLayout(2,1));
	private final JPanel scacchiera = new JPanel(new BorderLayout());
	private  Casella[][] caselle = new Casella[8][8];
	private final JPanel campoDaGioco = new JPanel(new GridLayout(8,8));
	private final ImageIcon torreBianca = new ImageIcon("images/Rook-W55.png");
	private final ImageIcon torreNera = new ImageIcon("images/Rook-B55.png");
	private final ImageIcon cavalloBianco = new ImageIcon("images/Knight-W55.png");
	private final ImageIcon cavalloNero = new ImageIcon("images/Knight-B55.png");
	private final ImageIcon alfiereBianco = new ImageIcon("images/Bishop-W55.png");
	private final ImageIcon alfiereNero = new ImageIcon("images/Bishop-B55.png");
	private final ImageIcon reginaBianca = new ImageIcon("images/Queen-W55.png");
	private final ImageIcon reginaNera = new ImageIcon("images/Queen-B55.png");
	private final ImageIcon reBianco = new ImageIcon("images/King-W55.png");
	private final ImageIcon reNero = new ImageIcon("images/King-B55.png");
	private final ImageIcon pedoneBianco = new ImageIcon("images/Pawn-W55.png");
	private final ImageIcon pedoneNero = new ImageIcon("images/Pawn-B55.png");
	private final static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private Controller controller = new Controller(caselle, this);
	
	public ScacchieraFrame(){
		setLocation( (dim.width - 660) / 2, (dim.height - 700) / 2 );
		JPanel toolBar = new JPanel();
		JButton newGame = new JButton("NEW GAME");
		// Inizializza la scacchiera alla pressione di NEW GAME
		newGame.addActionListener
		(event -> {inizializza();});
		toolBar.add(newGame);
		JButton about = new JButton("About");
		// Visualizza la finestra di dialogo con le informazioni
		about.addActionListener
		(event -> {JOptionPane.showMessageDialog(this, "Gioco degli scacchi versione 3.1415 \n\n Sviluppato da: \n\n Kevin Daniel Trudu \n Cristin Cebotari \n Paolo Stella \n\n Universita'  degli studi di Verona \n Corso di laurea in informatica \n Anno accademico 2014/2015");});
		toolBar.add(about);
		// Crea la scacchiera con le caselle bianche e nere alternate
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				// Crea la matrice di caselle
				caselle[i][j] = new Casella(i, j, (i + j) % 2 == 0 ? 0 : 1);
				// Imposta il colore di sfondo
				caselle[i][j].setBackground(caselle[i][j].getColor()==0 ? Color.WHITE : Color.DARK_GRAY);
				setActionListener(i, j); // Imposta l'action listener di ogni casella
				campoDaGioco.add(caselle[i][j]); // Aggiunge la casella alla GridView campoDaGioco
			}
		}
		inizializza(); // Inizializza a inizio partita
		gui.setBorder(new EmptyBorder(15,15,15,15));
		scacchiera.add(campoDaGioco, BorderLayout.CENTER);
		gui.add(toolBar, BorderLayout.NORTH);
		gui.add(scacchiera, BorderLayout.CENTER);
		add(gui, BorderLayout.CENTER);
		pack();
	}
	
	// Chiama il metodo onClick di Controller al clic sulla casella
	public void setActionListener(int i, int j){
		caselle[i][j].addActionListener
		(event -> { controller.onClick(caselle[i][j]);});	
	}
	
	// Resetta le minacce della casella e la deseleziona
	private void azzeraStatoCasella(Casella daSettare){
		daSettare.setMinacciaDaBianchi(0);
		daSettare.setMinacciaDaNeri(0);
		daSettare.deSelect();
	}
	
	// Ripristina il controller allo stato iniziale
	private void azzeraStatoController(){
		controller.azzeraTurn();
		controller.azzeraStart();
		controller.setCaselle(caselle);
	}
	
	// Ripristina il controller e setta i pezzi a inizio partita
	public void inizializza(){
		azzeraStatoController();
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++){
				azzeraStatoCasella(caselle[i][j]);
				
				if(i == 0 && (j == 0 || j == 7))
					caselle[i][j].setPedina("Torre", 1, torreNera);
				if(i == 0 && (j == 1 || j == 6))
					caselle[i][j].setPedina("Cavallo", 1, cavalloNero); 
				if(i == 0 && (j == 2 || j == 5))
					caselle[i][j].setPedina("Alfiere", 1, alfiereNero);
				if(i == 0 && j == 3)
					caselle[i][j].setPedina("Regina", 1, reginaNera);
				if(i == 0 && j == 4)
					caselle[i][j].setPedina("Re", 1, reNero);
				if(i == 1)
					caselle[i][j].setPedina("Pedone", 1, pedoneNero);
				if(i == 7 && (j == 0 || j == 7))
					caselle[i][j].setPedina("Torre", 0, torreBianca);
				if(i == 7 && (j == 1 || j == 6))
					caselle[i][j].setPedina("Cavallo", 0, cavalloBianco); 
				if(i == 7 && (j == 2 || j == 5))
					caselle[i][j].setPedina("Alfiere", 0, alfiereBianco);
				if(i == 7 && j == 3)
					caselle[i][j].setPedina("Regina", 0, reginaBianca);
				if(i == 7 && j == 4)
					caselle[i][j].setPedina("Re", 0, reBianco);
				if(i == 6)
					caselle[i][j].setPedina("Pedone", 0, pedoneBianco);
				if(i > 1 && i < 6)
					// Imposta le caselle vuote senza pedine
					caselle[i][j].setPedina("", -1, null);
			}	
	}
}