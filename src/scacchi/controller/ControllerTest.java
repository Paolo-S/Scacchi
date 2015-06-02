package scacchi.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import scacchi.model.Casella;
import scacchi.controller.Controller;
import scacchi.view.ScacchieraFrame;

public class ControllerTest {
	private Casella[][] caselle = new Casella[8][8];
	private Controller controller;
	
	@Before /* 	Inizializza la scacchiera con tutte caselle vuote e campi inizializzati
			 *	e crea il controller. Viene eseguito all'inizio di ogni metodo Test.
			 */
	public void init(){
		controller = new Controller(caselle, new ScacchieraFrame());
		for(int i = 0; i<8;i++){
			for(int j = 0; j<8; j++){
				caselle[i][j] = null;
				caselle[i][j] = new Casella(i, j, (i + j) % 2 == 0 ? 0 : 1);
				caselle[i][j].deSelect(); //deseleziona tutte le caselle per resettare il campo static Casella.selected
				caselle[i][j].setPedina("", -1, null);
			}
		}
	} 
	
	// Controlla se il re bianco e' sotto scacco da una torre nera nella stessa riga
	@Test
	public void scaccoBiancoDaTorreNeraTest(){
		caselle[4][4].setPedina("Re", 0, null);
		caselle[4][1].setPedina("Torre", 1, null);
		controller.onClick(caselle[4][4]);
		Assert.assertEquals(1, caselle[4][4].getMinacciaDaNeri());
	}
	
	// Controlla se il re bianco e' sotto scacco da un alfiere nero nella stessa diagonale
	@Test
	public void scaccoBiancoDaAlfiereNeroTest(){
		caselle[4][4].setPedina("Re", 0, null);
		caselle[7][7].setPedina("Alfiere", 1, null);
		controller.onClick(caselle[4][4]);
		Assert.assertEquals(1, caselle[4][4].getMinacciaDaNeri());
	}
	
	// Controlla se il re bianco e' sotto scacco da un cavallo nero posto la riga sotto e due colonne a destra
	@Test
	public void scaccoBiancoDaCavalloNeroTest(){
		caselle[4][4].setPedina("Re", 0, null);
		caselle[5][6].setPedina("Cavallo", 1, null);
		controller.onClick(caselle[4][4]);
		Assert.assertEquals(1, caselle[4][4].getMinacciaDaNeri());
	}
	
	// Controlla se il re bianco e' sotto scacco da un pedone nero posto in alto a sinistra rispetto al re
	@Test
	public void scaccoBiancoDaPedoneNeroTest(){
		caselle[4][4].setPedina("Re", 0, null);
		caselle[3][3].setPedina("Pedone", 1, null);
		controller.onClick(caselle[4][4]);
		Assert.assertEquals(1, caselle[4][4].getMinacciaDaNeri());
	}
	
	// Controlla se il re bianco e' sotto scacco da una regina nera in diagonale,
	// ovvero controlla se la regina si comporta come un alfiere
	@Test
	public void scaccoBiancoDaReginaNeraDiagonaleTest(){
		 
		caselle[4][4].setPedina("Re", 0, null);
		caselle[7][7].setPedina("Regina", 1, null);
		controller.onClick(caselle[4][4]);
		Assert.assertEquals(1, caselle[4][4].getMinacciaDaNeri());
	}
	
	// Controlla se il re bianco e' sotto scacco da una regina nera sulla stessa riga,
	// ovvero controlla se la regina si comporta come una torre
	@Test
	public void scaccoBiancoDaReginaNeraOrizzontaleTest(){
		caselle[4][4].setPedina("Re", 0, null);
		caselle[4][1].setPedina("Regina", 1, null);
		controller.onClick(caselle[4][4]);
		Assert.assertEquals(1, caselle[4][4].getMinacciaDaNeri());
	}
	
	// Controlla se le caselle davanti a destra e a sinistra rispetto al pedone bianco sono minacciate dai bianchi
	@Test
	public void minaccePedoneBiancoTest(){
		caselle[4][4].setPedina("Pedone", 0, null);
		controller.onClick(caselle[4][4]);
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(i==3 && (j==3 || j == 5))
					Assert.assertEquals(1, caselle[i][j].getMinacciaDaBianchi());
				else
					Assert.assertEquals(0, caselle[i][j].getMinacciaDaBianchi());
			}
		}
	}
	
	// Controlla se il re bianco e' in scacco matto se attaccato da una regina e due torri
	// che minacciano la sua stessa riga e quelle sopra e sotto
	@Test
	public void scaccoMattoTest(){
		caselle[4][4].setPedina("Re", 0, null);
		caselle[4][7].setPedina("Regina", 1, null);
		caselle[3][0].setPedina("Torre", 1, null);
		caselle[5][0].setPedina("Torre", 1, null);
		controller.onClick(caselle[4][4]);
		Assert.assertTrue(controller.isCheckmate());
	}
	
	// Controlla se la riga e la colonna della torre bianca sono minacciate da bianchi
	// (tranne la casella in cui si trova la torre stessa)
	@Test
	public void minacceTorreBiancaTest(){
		caselle[4][4].setPedina("Torre", 0, null);
		controller.onClick(caselle[4][4]);
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(i==4 || j==4)
					if(i==4 && j==4)
						Assert.assertEquals(0, caselle[i][j].getMinacciaDaBianchi());
					else
						Assert.assertEquals(1, caselle[i][j].getMinacciaDaBianchi());
				else
					Assert.assertEquals(0, caselle[i][j].getMinacciaDaBianchi());	
			}
		}
	}
	
	// Controlla se la riga, la colonna e le diagonali della regina bianca sono minacciate da bianchi
	// (tranne la casella in cui si trova la regina stessa)
	@Test
	public void minacceReginaBiancaTest(){
		caselle[4][4].setPedina("Regina", 0, null);
		controller.onClick(caselle[4][4]);
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if((i==j || i==(8-j))|| i==4 || j==4){
					if(i==4 && j==4)
						Assert.assertEquals(0, caselle[i][j].getMinacciaDaBianchi());
					else
						Assert.assertEquals(1, caselle[i][j].getMinacciaDaBianchi());
				}
				else
					Assert.assertEquals(0, caselle[i][j].getMinacciaDaBianchi());
			}
		}
	}
	
	// Controlla se le caselle sfasate di una riga e due colonne (o viceversa) rispetto al cavallo bianco
	// sono minacciate da bianchi
	@Test
	public void minacceCavalloBiancoTest(){
		caselle[4][4].setPedina("Cavallo", 0, null);
		controller.onClick(caselle[4][4]);
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(((i==3 || i==5) && (j==2 || j==6)) || ((i==2 || i==6) && (j==3 || j==5)))
					Assert.assertEquals(1, caselle[i][j].getMinacciaDaBianchi());
				else
					Assert.assertEquals(0, caselle[i][j].getMinacciaDaBianchi());
			}
		}
	}
	
	// Controlla se le diagonali dell'alfiere bianco sono minacciate da bianchi
	// (tranne la casella in cui si trova l'alfiere stesso)
	@Test
	public void minacceAlfiereBiancoTest(){
		caselle[4][4].setPedina("Alfiere", 0, null);
		controller.onClick(caselle[4][4]);
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(i==j || i==(8-j))
					if(i==4 && j==4)
						Assert.assertEquals(0, caselle[i][j].getMinacciaDaBianchi());
					else
						Assert.assertEquals(1, caselle[i][j].getMinacciaDaBianchi());
				else
					Assert.assertEquals(0, caselle[i][j].getMinacciaDaBianchi());
			}
		}
	}
	
	// Controlla se le otto caselle attorno al re bianco sono minacciate da bianchi
	@Test
	public void minacceReBiancoTest(){
		caselle[4][4].setPedina("Re", 0, null);
		controller.onClick(caselle[4][4]);
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(((i==3||i==5) &&(j==3||j==4||j==5)) || (i==4 &&(j==3||j==5)))
					Assert.assertEquals(1, caselle[i][j].getMinacciaDaBianchi());
				else
					Assert.assertEquals(0, caselle[i][j].getMinacciaDaBianchi());
			}
		}
	}
	
	// Controlla se dopo aver mosso un pedone bianco il turno passa ai neri
	// e se dopo aver mosso un pedone nero il turno passa ai bianchi
	@Test
	public void cambioTurnoTest(){
		caselle[4][4].setPedina("Pedone", 0, null);
		caselle[1][1].setPedina("Pedone", 1, null);
		controller.onClick(caselle[4][4]);
		controller.onClick(caselle[3][4]);
		Assert.assertEquals(1, controller.turn);
		controller.onClick(caselle[1][1]);
		controller.onClick(caselle[2][1]);
		Assert.assertEquals(0, controller.turn);
	}
	
	// Controlla che il pedone bianco che difende il suo stesso re (altrimenti sotto scacco)
	// non si possa muovere
	@Test
	public void pedoneBiancoBloccatoInCasoDiScaccoDaTorreNeraTest(){
		caselle[4][6].setPedina("Torre", 1, null);
		caselle[4][4].setPedina("Pedone", 0, null);
		caselle[4][3].setPedina("Re", 0, null);
		controller.onClick(caselle[4][4]);
		controller.onClick(caselle[3][4]);
		Assert.assertEquals("Pedone", caselle[4][4].getPedina());
		Assert.assertTrue(caselle[3][4].isEmpty());
	}
	
	// Controlla che il re sotto scacco non possa muoversi in una casella minacciata
	@Test
	public void mossaIllegaleReBiancoSottoScaccoTest(){
		 
		caselle[4][6].setPedina("Torre", 1, null);
		caselle[4][4].setPedina("Re", 0, null);
		controller.onClick(caselle[4][4]);
		controller.onClick(caselle[4][3]);
		Assert.assertEquals("Re", caselle[4][4].getPedina());
		Assert.assertTrue(caselle[4][3].isEmpty());
	}
	
	// Controlla che il re NON sotto scacco non possa muoversi in una casella minacciata
	@Test
	public void mossaIllegaleReBiancoSenzaScaccoTest(){
		caselle[4][6].setPedina("Torre", 1, null);
		caselle[5][4].setPedina("Re", 0, null);
		controller.onClick(caselle[5][4]);
		controller.onClick(caselle[4][4]);
		Assert.assertEquals("Re", caselle[5][4].getPedina());
		Assert.assertTrue(caselle[4][4].isEmpty());
	}
	
	// Controlla le mosse legali del pedone
	@Test
	public void destinazioniPedoneBiancoTest(){
		caselle[4][4].setPedina("Pedone", 0, null);
		controller.onClick(caselle[4][4]);
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(j==4 && (i==3||i==4))
					Assert.assertTrue(caselle[i][j].thisSelected());
				else
					Assert.assertFalse(caselle[i][j].thisSelected());
			}
		}
	}
	
	// Controlla le mosse legali della torre
	@Test
	public void destinazioniTorreBiancaTest(){
		caselle[4][4].setPedina("Torre", 0, null);
		controller.onClick(caselle[4][4]);
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(i==4 || j==4)
					Assert.assertTrue(caselle[i][j].thisSelected());
				else
					Assert.assertFalse(caselle[i][j].thisSelected());	
			}
		}
	}
	
	// Controlla le mosse legali del cavallo
	@Test
	public void destinazioniCavalloBiancoTest(){
		caselle[4][4].setPedina("Cavallo", 0, null);
		controller.onClick(caselle[4][4]);
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(((i==3 || i==5) && (j==2 || j==6)) || ((i==2 || i==6) && (j==3 || j==5)) || (i==4 && j==4))
					Assert.assertTrue(caselle[i][j].thisSelected());
				else
					Assert.assertFalse(caselle[i][j].thisSelected());
			}
		}
	}
	
	// Controlla le mosse legali dell'alfiere
	@Test
	public void destinazioniAlfiereBiancoTest(){
		caselle[4][4].setPedina("Alfiere", 0, null);
		controller.onClick(caselle[4][4]);
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
				if(i==j || i==(8-j))
					Assert.assertTrue(caselle[i][j].thisSelected());
				else
					Assert.assertFalse(caselle[i][j].thisSelected());
	}
	
	// Controlla le mosse legali della regina
	@Test
	public void destinazioniReginaBiancaTest(){
		caselle[4][4].setPedina("Regina", 0, null);
		controller.onClick(caselle[4][4]);
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if((i==j || i==(8-j))|| i==4 || j==4)
					Assert.assertTrue(caselle[i][j].thisSelected());
				else
					Assert.assertFalse(caselle[i][j].thisSelected());
			}
		}
	}
	
	// Controlla le mosse legali del re
	@Test
	public void destinazioniReBiancoTest(){
		caselle[4][4].setPedina("Re", 0, null);
		controller.onClick(caselle[4][4]);
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(((i==3||i==4||i==5) &&(j==3||j==4||j==5)))
					Assert.assertTrue(caselle[i][j].thisSelected());
				else
					Assert.assertFalse(caselle[i][j].thisSelected());
					
			}
		}
	}
}
