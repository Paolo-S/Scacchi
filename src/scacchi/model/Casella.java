package scacchi.model;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/*
 * La classe Casella estende JButton e costituisce una casella
 * della scacchiera. E' dotata di un colore, un'eventuale pedina
 * corredata da immagine, e puo' essere selezionata al clic
 * del mouse. Ogni casella, come prevede il gioco degli scacchi,
 * può essere minacciata dalle pedine dei giocatori.
 */

public class Casella extends JButton{
	private static final long serialVersionUID = -4925019709314475589L;
	private int x; 						// riga
	private int y; 						// colonna
	private int color; 					// colore della casella
	private int minacciataDaBianchi; 	// 0 se la casella non e' minacciata, 1 in caso contrario
	private int minacciataDaNeri;    	// 0 se la casella non e' minacciata, 1 in caso contrario
 	private String pedina;				// nome della pedina
	private int colorePedina;			// colore della pedina
	private int thisSelected = 0;		// 0 se questa casella non è selezionata, 1 se e' selezionata
	private static int selected = 0;	// 0 se non c'e' alcuna casella selezionata nella scacchiera, 1 in caso contrario
	private ImageIcon image;			// immagine della pedina
	
	public Casella(int x, int y, int color){
		this.x = x;
		this.y = y;
		this.color = color;	
	}
	
	public void setMinacciaDaBianchi(int minaccia){
		minacciataDaBianchi = minaccia;
	}
	public void setMinacciaDaNeri(int minaccia){
		minacciataDaNeri = minaccia;
	}
	public int getMinacciaDaBianchi(){
		return minacciataDaBianchi;
	}
	public int getMinacciaDaNeri(){
		return minacciataDaNeri;
	}
	public int getColorePedina(){
		return colorePedina;
	}
	public int getRow(){
		return x;
	}
	public int getColumn(){
		return y;
	}
	public boolean selected(){
		if (selected == 1)
			return true;
		else 
			return false;
	}
	public ImageIcon getImagePedina(){
		return image;
	}
	public boolean thisSelected(){
		if (thisSelected == 1)
			return true;
		else 
			return false;
	}
	
	/* Ritorna vero e setta lo sfondo di colore rosso
	 * se nella casella e' presente un re bianco e la casella e'
	 * minacciata dai neri, altrimenti ritorna falso
	 */
	public boolean scaccoBianchi(){
		if((pedina.equals("Re") && colorePedina == 0 && minacciataDaNeri == 1)){
			this.setBackground(Color.RED);
			return true;
		}
		else if(thisSelected == 1)
			this.setBackground(Color.GREEN);
		else
			this.setBackground( color == 0? Color.WHITE : Color.DARK_GRAY);
		return false;
	}
	
	/* Ritorna vero e setta lo sfondo di colore rosso
	 * se nella casella e' presente un re nero e la casella e'
	 * minacciata dai bianchi, altrimenti ritorna falso
	 */
	public boolean scaccoNeri(){
		if((pedina.equals("Re") && colorePedina == 1 && minacciataDaBianchi == 1)){
			this.setBackground(Color.RED);
			return true;
		}
		else if(thisSelected == 1)
			this.setBackground(Color.GREEN);
		else
			this.setBackground( color == 0? Color.WHITE : Color.DARK_GRAY);
		return false;
	}
	
	public boolean isEmpty(){
		if(pedina.equals(""))
			return true;
		else
			return false;
	}
	// Ripristina i colori di sfondo una volta deselezionata la casella
	public void thisDeSelect(){
		thisSelected = 0;
		this.setBackground( color == 0? Color.WHITE : Color.DARK_GRAY);
	}
	
	// Imposta lo sfondo di colore verde per le caselle di destinazione legali
	public void select(){
		selected = 1;
		thisSelected = 1;
		this.setBackground(Color.GREEN);
		}
	// Deseleziona la casella e azzera selected
	public void deSelect(){
		selected = 0;
		thisSelected = 0;
		this.setBackground( color == 0? Color.WHITE : Color.DARK_GRAY);
	}
	
	public int getColor(){
		return color;
	}
	public int getSelected(){
		return selected;
	}
	
	public String getPedina(){
		return pedina;
	}
	
	public void setPedina(String pedina, int colorePedina, ImageIcon image){
		this.pedina = pedina;
		this.colorePedina = colorePedina;
		this.image = image;
		setIcon(image);
	}
}