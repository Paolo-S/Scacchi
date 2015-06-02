package scacchi.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import scacchi.model.Casella;

/*
 *  PromotionFrame: finestra di dialogo che appare alla promozione di un pedone.
 *  Visualizza 4 pulsanti contenenti la pedina da scegliere in sostituzione
 */
public class PromotionFrame extends JDialog{
	private static final long serialVersionUID = 1L;
	private final JToolBar tools = new JToolBar();
	private final JPanel choosePanel = new JPanel();
	private final JPanel panel = new JPanel(new FlowLayout());
	
	private final JButton Torre = new JButton();
	private final JButton Alfiere = new JButton();
	private final JButton Cavallo = new JButton();
	private final JButton Regina = new JButton();
	
	private final ImageIcon torreBianca = new ImageIcon("images/Rook-W55.png");
	private final ImageIcon torreNera = new ImageIcon("images/Rook-B55.png");
	private final ImageIcon cavalloBianco = new ImageIcon("images/Knight-W55.png");
	private final ImageIcon cavalloNero = new ImageIcon("images/Knight-B55.png");
	private final ImageIcon alfiereBianco = new ImageIcon("images/Bishop-W55.png");
	private final ImageIcon alfiereNero = new ImageIcon("images/Bishop-B55.png");
	private final ImageIcon reginaBianca = new ImageIcon("images/Queen-W55.png");
	private final ImageIcon reginaNera = new ImageIcon("images/Queen-B55.png");
	
	private final static int HEIGHT = 280;
	private final static int WIDTH = 150;
	private final static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

	public PromotionFrame(Casella casella){
		setSize(HEIGHT, WIDTH);
		setModal(true); // Resta in primo piano
		setResizable(false); // Dimensione fissa
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocation( (dim.width - HEIGHT) / 2, (dim.height - WIDTH) / 2 );
		
		Torre.setPreferredSize(new Dimension(56, 56));
		Alfiere.setPreferredSize(new Dimension(56, 56));
		Cavallo.setPreferredSize(new Dimension(56, 56));
		Regina.setPreferredSize(new Dimension(56, 56));
		
		// Sostituisce il pedone con il pezzo selezionato (colore bianco o nero) e chiude la finestra di dialogo
		if(casella.getColorePedina() == 0){ // Colore bianco
			Torre.setIcon(torreBianca);
			Alfiere.setIcon(alfiereBianco);
			Cavallo.setIcon(cavalloBianco);
			Regina.setIcon(reginaBianca);
			Torre.addActionListener
			(event -> { casella.setPedina("Torre", casella.getColorePedina(), torreBianca); dispose();});
			Alfiere.addActionListener
			(event -> { casella.setPedina("Alfiere", casella.getColorePedina(), alfiereBianco); dispose();});
			Cavallo.addActionListener
			(event -> { casella.setPedina("Cavallo", casella.getColorePedina(), cavalloBianco); dispose();});
			Regina.addActionListener
			(event -> { casella.setPedina("Regina", casella.getColorePedina(), reginaBianca); dispose();});
		}
		else{ // Colore nero
			Torre.setIcon(torreNera);
			Alfiere.setIcon(alfiereNero);
			Cavallo.setIcon(cavalloNero);
			Regina.setIcon(reginaNera);
			Torre.addActionListener
			(event -> { casella.setPedina("Torre", casella.getColorePedina(), torreNera); dispose();});
			Alfiere.addActionListener
			(event -> { casella.setPedina("Alfiere", casella.getColorePedina(), alfiereNero); dispose();});
			Cavallo.addActionListener
			(event -> { casella.setPedina("Cavallo", casella.getColorePedina(), cavalloNero); dispose();});
			Regina.addActionListener
			(event -> { casella.setPedina("Regina", casella.getColorePedina(), reginaNera); dispose();});
		}
		
		tools.add(new JLabel("Scegli tra le sequenti pedine!"));
		
		choosePanel.setBorder(new EmptyBorder(15,15,15,15));
		choosePanel.add(tools);
		panel.add(Torre);
		panel.add(Alfiere);
		panel.add(Cavallo);
		panel.add(Regina);
		choosePanel.add(panel);
		
		add(choosePanel);
		setVisible(true);
		pack();
	}
}