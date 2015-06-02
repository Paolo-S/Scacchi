package scacchi.controller;

import scacchi.view.*;
import scacchi.model.Casella;

public class Controller{
	ScacchieraFrame frame;
	private Casella[][] caselle = null;
	Casella start;
	int turn = 0;

	public Controller(Casella[][] caselle,  ScacchieraFrame frame){
		this.frame = frame;
		this.caselle = caselle;
	}
	public void setCaselle(Casella[][] caselle){
		this.caselle = caselle;
	}
	public void azzeraStart(){
		start = null;
	}
	public void azzeraTurn(){
		turn = 0;
	}
	private void unSetMinacce(){
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++){
					caselle[i][j].setMinacciaDaBianchi(0);
					caselle[i][j].setMinacciaDaNeri(0);
			}
	}
	
	private void setMinacce(){
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++){
				if(caselle[i][j].getPedina().equals("Pedone"))
					setMinaccePedone(caselle[i][j]);
				if(caselle[i][j].getPedina().equals("Torre"))
					setMinacceTorre(caselle[i][j]);
				if(caselle[i][j].getPedina().equals("Alfiere"))
					setMinacceAlfiere(caselle[i][j]);
				if(caselle[i][j].getPedina().equals("Cavallo"))
					setMinacceCavallo(caselle[i][j]);
				if(caselle[i][j].getPedina().equals("Regina")){
					setMinacceTorre(caselle[i][j]);
					setMinacceAlfiere(caselle[i][j]);
				}
				if(caselle[i][j].getPedina().equals("Re"))
					setMinacceRe(caselle[i][j]); 
			}
	}
	/**
	 * @param daControllare
	 * Setta le minacce delle caselle di destinazione legali per il pedone
	 */
	private void setMinaccePedone(Casella daControllare){
		if(daControllare.getColorePedina() == 0 && daControllare.getRow() > 0){
			if(daControllare.getColumn() > 0)
				if(caselle[daControllare.getRow()-1][daControllare.getColumn()-1].isEmpty() || caselle[daControllare.getRow()-1][daControllare.getColumn()-1].getColorePedina() == daControllare.getColorePedina() || (caselle[daControllare.getRow()-1][daControllare.getColumn()-1].getColorePedina() != daControllare.getColorePedina() && caselle[daControllare.getRow()-1][daControllare.getColumn()-1].getPedina().equals("Re")))
					caselle[daControllare.getRow()-1][daControllare.getColumn()-1].setMinacciaDaBianchi(1);
				else
					caselle[daControllare.getRow()-1][daControllare.getColumn()-1].setMinacciaDaBianchi(0);

			if(daControllare.getColumn() < 7)
				if(caselle[daControllare.getRow()-1][daControllare.getColumn()+1].isEmpty() || caselle[daControllare.getRow()-1][daControllare.getColumn()+1].getColorePedina() == daControllare.getColorePedina() || (caselle[daControllare.getRow()-1][daControllare.getColumn()+1].getColorePedina() != daControllare.getColorePedina() && caselle[daControllare.getRow()-1][daControllare.getColumn()+1].getPedina().equals("Re")))
					caselle[daControllare.getRow()-1][daControllare.getColumn()+1].setMinacciaDaBianchi(1);
				else
					caselle[daControllare.getRow()-1][daControllare.getColumn()+1].setMinacciaDaBianchi(0);
			}
		
			if(daControllare.getColorePedina() == 1 && daControllare.getRow() < 7){
				
				if(daControllare.getColumn() < 7 )
					if(caselle[daControllare.getRow()+1][daControllare.getColumn()+1].isEmpty() || caselle[daControllare.getRow()+1][daControllare.getColumn()+1].getColorePedina() == daControllare.getColorePedina() || (caselle[daControllare.getRow()+1][daControllare.getColumn()+1].getColorePedina() != daControllare.getColorePedina() && caselle[daControllare.getRow()+1][daControllare.getColumn()+1].getPedina().equals("Re")))
						caselle[daControllare.getRow()+1][daControllare.getColumn()+1].setMinacciaDaNeri(1);
					else
						caselle[daControllare.getRow()+1][daControllare.getColumn()+1].setMinacciaDaNeri(0);
				if(daControllare.getColumn() > 0)
					if(caselle[daControllare.getRow()+1][daControllare.getColumn()-1].isEmpty() || caselle[daControllare.getRow()+1][daControllare.getColumn()-1].getColorePedina() == daControllare.getColorePedina() || (caselle[daControllare.getRow()+1][daControllare.getColumn()-1].getColorePedina() != daControllare.getColorePedina() && caselle[daControllare.getRow()+1][daControllare.getColumn()-1].getPedina().equals("Re")))
						caselle[daControllare.getRow()+1][daControllare.getColumn()-1].setMinacciaDaNeri(1);
					else 
						caselle[daControllare.getRow()+1][daControllare.getColumn()-1].setMinacciaDaNeri(0);
			}
	}
	
	private void setMinacceTorre(Casella daControllare){
		int i = daControllare.getRow()-1;
		int j = daControllare.getColumn();
			//while che controlla il movimento della torre verso sù
			while(i >= 0){
				if(caselle[i][j].isEmpty()) //se la casella e' vuota settiamo le minacce e continuiamo a ciclare
					if(daControllare.getColorePedina() == 0)
						caselle[i][j].setMinacciaDaBianchi(1);
					else
						caselle[i][j].setMinacciaDaNeri(1);
				//se la casella non contiene un re, settiamo la minaccia e usciamo dal ciclo
				else if(!caselle[i][j].getPedina().equals("Re")){
						if(daControllare.getColorePedina() == 0)
							caselle[i][j].setMinacciaDaBianchi(1);
						else
							caselle[i][j].setMinacciaDaNeri(1);
					break;
				}
				//se la casella contiene il re del colore opposto alla pedina di partenza, settiamo la minaccia sulla casella del re e quella successiva
				if(caselle[i][j].getColorePedina() != daControllare.getColorePedina() && caselle[i][j].getPedina().equals("Re")  ){
						if(daControllare.getColorePedina() == 0){
							caselle[i][j].setMinacciaDaBianchi(1);
							if(i - 1 > 0)
								caselle[i-1][j].setMinacciaDaBianchi(1);
				        }
						else{
							caselle[i][j].setMinacciaDaNeri(1);
							if(i - 1 > 0)
								caselle[i - 1][j].setMinacciaDaNeri(1);
						}
						
						break;
				}
				i--;
			}
			
		    i = daControllare.getRow()+1;
			j = daControllare.getColumn();
			//fa� lo stesso lavoro del ciclo soprastante, ma andiamo verso destra
			while(i <= 7){
				if(caselle[i][j].isEmpty())
					if(daControllare.getColorePedina() == 0)
						caselle[i][j].setMinacciaDaBianchi(1);
					else
						caselle[i][j].setMinacciaDaNeri(1);
				
				else if(!caselle[i][j].getPedina().equals("Re")){
						if(daControllare.getColorePedina() == 0)
							caselle[i][j].setMinacciaDaBianchi(1);
						else
							caselle[i][j].setMinacciaDaNeri(1);
					break;
				}
				if(caselle[i][j].getColorePedina() != daControllare.getColorePedina() && caselle[i][j].getPedina().equals("Re")  ){
						if(daControllare.getColorePedina() == 0){
							caselle[i][j].setMinacciaDaBianchi(1);
							if(i + 1 < 7)
								caselle[i + 1][j].setMinacciaDaBianchi(1);
				        }
						else{
							caselle[i][j].setMinacciaDaNeri(1);
							if(i + 1 < 7)
								caselle[i + 1][j].setMinacciaDaNeri(1);
						}
						
						break;
				}
				i++;
			}
			
			i = daControllare.getRow();
			j = daControllare.getColumn() + 1; 
			//idem verso giù
			while(j <= 7){
				if(caselle[i][j].isEmpty())
					if(daControllare.getColorePedina() == 0)
						caselle[i][j].setMinacciaDaBianchi(1);
					else
						caselle[i][j].setMinacciaDaNeri(1);
				
				else if(!caselle[i][j].getPedina().equals("Re")){
						if(daControllare.getColorePedina() == 0)
							caselle[i][j].setMinacciaDaBianchi(1);
						else
							caselle[i][j].setMinacciaDaNeri(1);
					break;
				}
				
				if(caselle[i][j].getColorePedina() != daControllare.getColorePedina() && caselle[i][j].getPedina().equals("Re")  ){
						if(daControllare.getColorePedina() == 0){
							caselle[i][j].setMinacciaDaBianchi(1);
							if(j + 1 < 7 )
								caselle[i][j+1].setMinacciaDaBianchi(1);
				        }
						else{
							caselle[i][j].setMinacciaDaNeri(1);
							if(j + 1 < 7)
								caselle[i][j+1].setMinacciaDaNeri(1);
						}
						
						break;}
			
				j++;
			}
			 i = daControllare.getRow();
			 j = daControllare.getColumn() - 1;
			//idem verso sù
			while(j >= 0){
				if(caselle[i][j].isEmpty())
					if(daControllare.getColorePedina() == 0)
						caselle[i][j].setMinacciaDaBianchi(1);
					else
						caselle[i][j].setMinacciaDaNeri(1);
				
				else if(!caselle[i][j].getPedina().equals("Re")){
						if(daControllare.getColorePedina() == 0)
							caselle[i][j].setMinacciaDaBianchi(1);
						else
							caselle[i][j].setMinacciaDaNeri(1);
					break;
				}
				
				if(caselle[i][j].getColorePedina() != daControllare.getColorePedina() && caselle[i][j].getPedina().equals("Re")  ){
						if(daControllare.getColorePedina() == 0){
							caselle[i][j].setMinacciaDaBianchi(1);
							if(j - 1 > 0)
				            caselle[i][j - 1].setMinacciaDaBianchi(1);
				        }
						else{
							caselle[i][j].setMinacciaDaNeri(1);
								if(j - 1 > 0)
							caselle[i][j - 1].setMinacciaDaNeri(1);
						}
						
						break;
				}
				j--;
			}
		
	}
	
	private void setMinacceAlfiere(Casella daControllare){//funziona esattamente come la torre, ma in diagonale per cui i controlli sono effettuati sia su i che su j
		int i = daControllare.getRow() - 1;
		int j = daControllare.getColumn() + 1;
			//andiamo a controllare in diagonale verso sù a destra e fà la stessa cosa della torre
			while(i >= 0 && j <= 7){
				if(caselle[i][j].isEmpty())
					if(daControllare.getColorePedina() == 0)
						caselle[i][j].setMinacciaDaBianchi(1);
					else
						caselle[i][j].setMinacciaDaNeri(1);
				
				else if(!caselle[i][j].getPedina().equals("Re")){
						if(daControllare.getColorePedina() == 0)
							caselle[i][j].setMinacciaDaBianchi(1);
						else
							caselle[i][j].setMinacciaDaNeri(1);
					break;
				}
				
				if(caselle[i][j].getColorePedina() != daControllare.getColorePedina() && caselle[i][j].getPedina().equals("Re")  ){
						if(daControllare.getColorePedina() == 0){
							caselle[i][j].setMinacciaDaBianchi(1);
							if(i - 1 > 0 && j + 1 < 7)
								caselle[i - 1][j + 1].setMinacciaDaBianchi(1);
				        }
						else{
							caselle[i][j].setMinacciaDaNeri(1);
							if(i - 1 > 0 && j + 1 < 7)
								caselle[i - 1][j + 1].setMinacciaDaNeri(1);
						}
						
						break;
				}
				i--;
				j++;
			}
			
		
		    i = daControllare.getRow()+1;
			j = daControllare.getColumn()+1;
			//andiamo verso giù a destra
			while(i <= 7 && j <= 7){
				if(caselle[i][j].isEmpty())
					if(daControllare.getColorePedina() == 0)
						caselle[i][j].setMinacciaDaBianchi(1);
					else
						caselle[i][j].setMinacciaDaNeri(1);
				
				else if(!caselle[i][j].getPedina().equals("Re")){
						if(daControllare.getColorePedina() == 0)
							caselle[i][j].setMinacciaDaBianchi(1);
						else
							caselle[i][j].setMinacciaDaNeri(1);
					break;
				}
				if(caselle[i][j].getColorePedina() != daControllare.getColorePedina() && caselle[i][j].getPedina().equals("Re")  ){
						if(daControllare.getColorePedina() == 0){
							caselle[i][j].setMinacciaDaBianchi(1);
							if(i + 1 < 7 && j + 1< 7)
								caselle[i + 1][j + 1].setMinacciaDaBianchi(1);
				        }
						else{
							caselle[i][j].setMinacciaDaNeri(1);
							if(i + 1< 7 && j + 1 < 7)
								caselle[i + 1][j + 1].setMinacciaDaNeri(1);
						}
						
						break;
				}
				i++;
				j++;
			}

			i = daControllare.getRow() + 1;
			j = daControllare.getColumn() - 1; 
			//andiamo verso giù a sinistra
			while(j >= 0 && i <= 7){
				if(caselle[i][j].isEmpty())
					if(daControllare.getColorePedina() == 0)
						caselle[i][j].setMinacciaDaBianchi(1);
					else
						caselle[i][j].setMinacciaDaNeri(1);
				
				else if(!caselle[i][j].getPedina().equals("Re")){
						if(daControllare.getColorePedina() == 0)
							caselle[i][j].setMinacciaDaBianchi(1);
						else
							caselle[i][j].setMinacciaDaNeri(1);
					break;
				}
				
				if(caselle[i][j].getColorePedina() != daControllare.getColorePedina() && caselle[i][j].getPedina().equals("Re")  ){
						if(daControllare.getColorePedina() == 0){
							caselle[i][j].setMinacciaDaBianchi(1);
							if(i + 1< 7 && j - 1 > 0)
								caselle[i + 1][j - 1].setMinacciaDaBianchi(1);
				        }
						else{
							caselle[i][j].setMinacciaDaNeri(1);
							if(i + 1 < 7 && j - 1 > 0)
								caselle[i + 1][j - 1].setMinacciaDaNeri(1);
						}
						
						break;}
			
				j--;
				i++;
			}
			
			 i = daControllare.getRow() - 1;
			 j = daControllare.getColumn() - 1;
			 //andiamo verso sù a sinistra
			while(j >= 0 && i >= 0){
				if(caselle[i][j].isEmpty())
					if(daControllare.getColorePedina() == 0)
						caselle[i][j].setMinacciaDaBianchi(1);
					else
						caselle[i][j].setMinacciaDaNeri(1);
				
				else if(!caselle[i][j].getPedina().equals("Re")){
						if(daControllare.getColorePedina() == 0)
							caselle[i][j].setMinacciaDaBianchi(1);
						else
							caselle[i][j].setMinacciaDaNeri(1);
					break;
				}
				
				if(caselle[i][j].getColorePedina() != daControllare.getColorePedina() && caselle[i][j].getPedina().equals("Re")  ){
						if(daControllare.getColorePedina() == 0){
							caselle[i][j].setMinacciaDaBianchi(1);
							if(i - 1 > 0 && j - 1 > 0)
								caselle[i - 1][j - 1].setMinacciaDaBianchi(1);
				        }
						else{
							caselle[i][j].setMinacciaDaNeri(1);
							if(i - 1 > 0 && j - 1 > 0)
								caselle[i - 1][j - 1].setMinacciaDaNeri(1);
						}
						
						break;
				}
				j--;
				i--;
			}
	}
	
	private void setMinacceCavallo(Casella daControllare){
		int i = daControllare.getRow();
		int j = daControllare.getColumn();
		
		//di seguito 8 if(), che eseguono gli stessi controlli perche' il cavallo ha al massimo 8 caselle su cui spostarsi
		if(i - 2 >= 0 && j + 1 <= 7)
			//se la casella su cui spostarsi e' vuota, oppure se contiene una pedina dello stesso colore, oppure colore diverso ma e' un re
			if(caselle[i - 2][j + 1].isEmpty() || caselle[i - 2][j + 1].getColorePedina() == daControllare.getColorePedina() || (caselle[i - 2][j + 1].getColorePedina() != daControllare.getColorePedina() && caselle[i - 2][j + 1].getPedina().equals("Re")))
				//settiamo le minacce
				if(daControllare.getColorePedina() == 0)
					caselle[i - 2][j + 1].setMinacciaDaBianchi(1);
				else
					caselle[i - 2][j + 1].setMinacciaDaNeri(1);	
		//idem per tutti i controlli qui sotto
		if(i - 1 >= 0 && j + 2 <= 7)
			if(caselle[i - 1][j + 2].isEmpty() || caselle[i - 1][j + 2].getColorePedina() == daControllare.getColorePedina() || (caselle[i - 1][j + 2].getColorePedina() != daControllare.getColorePedina() && caselle[i - 1][j + 2].getPedina().equals("Re")))
				if(daControllare.getColorePedina() == 0)
					caselle[i - 1][j + 2].setMinacciaDaBianchi(1);
				else
					caselle[i - 1][j + 2].setMinacciaDaNeri(1);	
			
		if(i + 1 <= 7 && j + 2 <= 7)
			if(caselle[i + 1][j + 2].isEmpty() || caselle[i + 1][j + 2].getColorePedina() == daControllare.getColorePedina() || (caselle[i + 1][j + 2].getColorePedina() != daControllare.getColorePedina() && caselle[i + 1][j + 2].getPedina().equals("Re")))
				if(daControllare.getColorePedina() == 0)
					caselle[i + 1][j + 2].setMinacciaDaBianchi(1);
				else
					caselle[i + 1][j + 2].setMinacciaDaNeri(1);	
			
		if(i + 2 <= 7 && j + 1 <= 7)
			if(caselle[i + 2][j + 1].isEmpty() || caselle[i + 2][j + 1].getColorePedina() == daControllare.getColorePedina() || (caselle[i + 2][j + 1].getColorePedina() != daControllare.getColorePedina() && caselle[i + 2][j + 1].getPedina().equals("Re")))
				if(daControllare.getColorePedina() == 0)
					caselle[i + 2][j + 1].setMinacciaDaBianchi(1);
				else
					caselle[i + 2][j + 1].setMinacciaDaNeri(1);		
			
		if(i + 2 <= 7 && j - 1 >= 0)
			if(caselle[i + 2][j - 1].isEmpty() || caselle[i + 2][j - 1].getColorePedina() == daControllare.getColorePedina() || (caselle[i + 2][j - 1].getColorePedina() != daControllare.getColorePedina() && caselle[i + 2][j - 1].getPedina().equals("Re")))
				if(daControllare.getColorePedina() == 0)
					caselle[i + 2][j - 1].setMinacciaDaBianchi(1);
				else
					caselle[i + 2][j - 1].setMinacciaDaNeri(1);	
			
		if(i + 1 <= 7 && j - 2 >= 0)
			if(caselle[i + 1][j - 2].isEmpty() || caselle[i + 1][j - 2].getColorePedina() == daControllare.getColorePedina() || (caselle[i + 1][j - 2].getColorePedina() != daControllare.getColorePedina() && caselle[i + 1][j - 2].getPedina().equals("Re")))
				if(daControllare.getColorePedina() == 0)
					caselle[i + 1][j - 2].setMinacciaDaBianchi(1);
				else
					caselle[i + 1][j - 2].setMinacciaDaNeri(1);	
			
		if(i - 1 >= 0 && j - 2 >= 0)
			if(caselle[i - 1][j - 2].isEmpty() || caselle[i - 1][j - 2].getColorePedina() == daControllare.getColorePedina()|| (caselle[i - 1][j - 2].getColorePedina() != daControllare.getColorePedina() && caselle[i - 1][j - 2].getPedina().equals("Re")))
				if(daControllare.getColorePedina() == 0)
					caselle[i - 1][j - 2].setMinacciaDaBianchi(1);
				else
					caselle[i - 1][j - 2].setMinacciaDaNeri(1);	
			
		if(i - 2 >= 0 && j - 1 >= 0)
			if(caselle[i - 2][j - 1].isEmpty() || caselle[i - 2][j - 1].getColorePedina() == daControllare.getColorePedina() || (caselle[i - 2][j - 1].getColorePedina() != daControllare.getColorePedina() && caselle[i - 2][j - 1].getPedina().equals("Re")))
				if(daControllare.getColorePedina() == 0)
					caselle[i - 2][j - 1].setMinacciaDaBianchi(1);
				else
					caselle[i - 2][j - 1].setMinacciaDaNeri(1);	
			
	}
	private void setMinacceRe(Casella daControllare){
		int i = daControllare.getRow();
		int j = daControllare.getColumn();
		
		if(i - 1 >= 0)//controllo sulla casella soppra quella del re
			//se la casella e' vuota oppure contiene una pedina dello stesso colore
			if(caselle[i - 1][j].isEmpty() || caselle[i - 1][j].getColorePedina() == daControllare.getColorePedina()){
				//settiamo le minacce
				if(daControllare.getColorePedina() == 0)
					  caselle[i - 1][j].setMinacciaDaBianchi(1);
				if(daControllare.getColorePedina() == 1)
					  caselle[i - 1][j].setMinacciaDaNeri(1);
			}
		//idem per i controlli qui sotto
		if(i - 1 >= 0 && j + 1 <= 7)//controllo sulla casella soppra a destra quella del re
			if(caselle[i - 1][j + 1].isEmpty() ||  caselle[i - 1][j + 1].getColorePedina() == daControllare.getColorePedina()){
				if(daControllare.getColorePedina() == 0)
					  caselle[i - 1][j + 1].setMinacciaDaBianchi(1);
				if(daControllare.getColorePedina() == 1)
					  caselle[i - 1][j + 1].setMinacciaDaNeri(1);
			}
		if(j + 1 <= 7)//controllo sulla casella soppra a destra di quella del re
			if(caselle[i][j + 1].isEmpty() || caselle[i][j + 1].getColorePedina() == daControllare.getColorePedina()){
				if(daControllare.getColorePedina() == 0)
					  caselle[i][j + 1].setMinacciaDaBianchi(1);
				if(daControllare.getColorePedina() == 1)
					  caselle[i][j + 1].setMinacciaDaNeri(1);
			}
		if(i + 1 <= 7 && j + 1 <= 7)//controllo sulla casella giù a destra di quella del re
			if(caselle[i + 1][j + 1].isEmpty() || caselle[i + 1][j + 1].getColorePedina() == daControllare.getColorePedina()){
				if(daControllare.getColorePedina() == 0)
					  caselle[i + 1][j + 1].setMinacciaDaBianchi(1);
				if(daControllare.getColorePedina() == 1)
					  caselle[i + 1][j + 1].setMinacciaDaNeri(1);
			}
		if(i + 1 <= 7)//controllo sulla casella sotto quella del re
			if(caselle[i + 1][j].isEmpty() || caselle[i + 1][j].getColorePedina() == daControllare.getColorePedina()){
				if(daControllare.getColorePedina() == 0)
					  caselle[i + 1][j].setMinacciaDaBianchi(1);
				if(daControllare.getColorePedina() == 1)
					  caselle[i + 1][j].setMinacciaDaNeri(1);
			}
		if(i + 1 <= 7 && j - 1 >= 0)//controllo sulla casella giù a sinistra di quella del re
			if(caselle[i + 1][j - 1].isEmpty() || caselle[i + 1][j - 1].getColorePedina() == daControllare.getColorePedina()){
				if(daControllare.getColorePedina() == 0)
					  caselle[i + 1][j - 1].setMinacciaDaBianchi(1);
				if(daControllare.getColorePedina() == 1)
					  caselle[i + 1][j - 1].setMinacciaDaNeri(1);
			}
		if(j - 1 >= 0)//controllo sulla casella a sinistra di quella del re
			if(caselle[i][j - 1].isEmpty() || caselle[i][j - 1].getColorePedina() == daControllare.getColorePedina()){
				if(daControllare.getColorePedina() == 0)
					  caselle[i][j - 1].setMinacciaDaBianchi(1);
				if(daControllare.getColorePedina() == 1)
					  caselle[i][j - 1].setMinacciaDaNeri(1);
			}
		if(i - 1 >= 0 && j - 1 >= 0)//controllo sulla casella soppra a sinistra di quella del re
			if(caselle[i - 1][j - 1].isEmpty() || caselle[i - 1][j - 1].getColorePedina() == daControllare.getColorePedina()){
				if(daControllare.getColorePedina() == 0)
					  caselle[i - 1][j - 1].setMinacciaDaBianchi(1);
				if(daControllare.getColorePedina() == 1)
					  caselle[i - 1][j - 1].setMinacciaDaNeri(1);
			}
	}
	/*funzione che parte dalla prima casella fino all'ultima e sposta le pedine in tutte le possibili caselle in cui possono andare,
	se dopo avere spostato la pedina in quella casella vi e' ancora scacco, allora quella caselle viene deselezionata in modo che non vengo visualizzata verde*/
	private void deselectIfCheckAfterMove(){
		Casella temp = new Casella(1,1,1);//creiamo una caselle fittizia

		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
				if(caselle[i][j].thisSelected() && caselle[i][j] != start){
					//salviamo la pedina della casella di destinazione
					temp.setPedina(caselle[i][j].getPedina(), caselle[i][j].getColorePedina(), caselle[i][j].getImagePedina());
					//spostiamo la pedina della casella di partenza in quella di destinazione
					caselle[i][j].setPedina(start.getPedina(), start.getColorePedina(), start.getImagePedina());
					//lasciamo vuota la casella di partenza
					start.setPedina("", -1, null);
		
					unSetMinacce();
					setMinacce();  //aggiorniamo le minacce
	
					if((turn == 0 && checkScaccoBianchi()) || (turn == 1 && checkScaccoNeri()))
						//se il giocatore di cui e' il turno e' sotto scacco deselezioniamo la casella
						caselle[i][j].thisDeSelect();
					
					//rimettiamo le pedine al loro posto di prima
					start.setPedina(caselle[i][j].getPedina(), caselle[i][j].getColorePedina(), caselle[i][j].getImagePedina());
					caselle[i][j].setPedina(temp.getPedina(), temp.getColorePedina(), temp.getImagePedina());
				}
		unSetMinacce();
		setMinacce();
	}
	
	private boolean hasDestinations(){//funzione che controlla se rimangono caselle selezionate
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
				if(caselle[i][j].thisSelected() && caselle[i][j] != start)
					return true;
		return false;
	}
	
	public void onClick(Casella caselle){//funzione che gestisce le azioni a seconda che sia il primo click o il secondo		
		if(caselle.getSelected() != 1 && !caselle.isEmpty()){// se e' il primo click
			start = caselle;	//settiamo la casella di partenza
			setDestinations();	//settiamo di verde le caselle disponibili se ve ne sono
			deselectIfCheckAfterMove();		//deselezioniamo le caselle che lasciano in situazione di scacco
			if(!hasDestinations())	//se non ci sono caselle "verdi" non possiamo muovere la pedina, quindi deselezioniamo
				deSelectAll();	
		}
		else if(caselle.getColorePedina() == turn){
				deSelectAll();
				if(caselle != start){
					start = caselle;
					setDestinations();	//settiamo di verde le caselle disponibili se ve ne sono
					deselectIfCheckAfterMove();		//deselezioniamo le caselle che lasciano in situazione di scacco
					if(!hasDestinations())	//se non ci sono caselle "verdi" non possiamo muovere la pedina, quindi deselezioniamo
						deSelectAll();
				}
		}
		else if(caselle.thisSelected() && start != caselle){//se e' il secondo click
			if(!caselle.getPedina().equals("Re")){//se la casella di destinazione non e' un re allora possiamo muoverci
				move(caselle);	//funzione che applica il movimento
				start = null;	//azzeriamo la casella di partenza
				unSetMinacce();
				setMinacce();	//aggiorniamo le minacce dopo il movimento poiche' la configurazione e' diversa
			}
			deSelectAll();
			
		}
		else	//se non entriamo nelle condizioni sopra, deselezioniamo
			deSelectAll();
		/*else if(caselle.thisSelected() && start == caselle){
			deSelectAll();
		}
		else if(!caselle.thisSelected()){
			deSelectAll();	
		}*/
		if(turn == 0)//controlliamo se vi e' scacco ad ogni turno
			checkScaccoBianchi();
		else
			checkScaccoNeri();
	}
	
	private void setDestinations(){
		if(turn == start.getColorePedina()){//se la pedina su cui abbiamo cliccato ci appartiene possiamo settare le caselle di destinazione
			//di seguito le chiamate alle funzione che gestiscono le destinazione dei vari pezzi
			if(start.getPedina().equals("Pedone"))
				setDestinationsPedone();
			if(start.getPedina().equals("Torre"))
				setDestinationsTorre();
			if(start.getPedina().equals("Alfiere"))
				setDestinationsAlfiere();
			if(start.getPedina().equals("Cavallo"))
				setDestinationsCavallo();
			if(start.getPedina().equals("Regina")){	//la regina, altro non e' che una torre ed un alfiere messi assieme, basta chiamare i loro metodi
				setDestinationsTorre();
				setDestinationsAlfiere();
			}
			if(start.getPedina().equals("Re"))
				setDestinationsRe();
		}		
	}
	
	private void setDestinationsPedone(){    //metodo che setta le caselle di destinazione disponibili per il pedone
		if(start.getColorePedina() == 0 && start.getRow() > 0){//se il pedone e' bianco e siamo nella penultima riga andando verso sù
			start.select(); //settiamo verde la casella
			if(caselle[start.getRow()-1][start.getColumn()].isEmpty())	// se la casella di soppra e libera		
				caselle[start.getRow()-1][start.getColumn()].select();  // possiamo andarci
			
			if(start.getColumn() > 0)// se non siamo sul bordo di sinistra
				//se la caselle soppra a sinistra ha una pedina di colore opposto
				if(!caselle[start.getRow()-1][start.getColumn()-1].isEmpty() && caselle[start.getRow()-1][start.getColumn()-1].getColorePedina() != start.getColorePedina())
					caselle[start.getRow()-1][start.getColumn()-1].select(); //possiamo andarci
			
			if(start.getColumn() < 7)//se non siamo sul bordo destro faccio la stessa cosa ma per la casella soppra a destra
				if(!caselle[start.getRow()-1][start.getColumn()+1].isEmpty() && caselle[start.getRow()-1][start.getColumn()+1].getColorePedina() != start.getColorePedina())
					caselle[start.getRow()-1][start.getColumn()+1].select();
			}
			//faccio gli stessi controlli per il pedone nero ma con le coordinate rovesciate poiche' si sposta verso giù
			if(start.getColorePedina() == 1 && start.getRow() < 7){
				start.select();
				if(caselle[start.getRow()+1][start.getColumn()].isEmpty())
					caselle[start.getRow()+1][start.getColumn()].select();
				
				if(start.getColumn() < 7)
					if(!caselle[start.getRow()+1][start.getColumn()+1].isEmpty() && caselle[start.getRow()+1][start.getColumn()+1].getColorePedina() != start.getColorePedina())
						caselle[start.getRow()+1][start.getColumn()+1].select();
				
				if(start.getColumn() > 0)
					if(!caselle[start.getRow()+1][start.getColumn()-1].isEmpty() && caselle[start.getRow()+1][start.getColumn()-1].getColorePedina() != start.getColorePedina())
						caselle[start.getRow()+1][start.getColumn()-1].select();
			}
		}

	private void setDestinationsTorre(){//fa lo stesso lavoro dei checkMinacceTorre ma setta le caselle disponibili di verde
		int i = start.getRow()-1;
		int j = start.getColumn();
			start.select();
			while(i >= 0 && (caselle[i][j].isEmpty() || caselle[i][j].getColorePedina() != start.getColorePedina())){
				caselle[i][j].select();
				if(caselle[i][j].getColorePedina() != start.getColorePedina() && caselle[i][j].getColorePedina() != -1)
					break;
			i--;
			}
			
		    i = start.getRow()+1;
			j = start.getColumn();
			
			while(i <= 7 && (caselle[i][j].isEmpty() || caselle[i][j].getColorePedina() != start.getColorePedina())){
				caselle[i][j].select();
				if(caselle[i][j].getColorePedina() != start.getColorePedina() && caselle[i][j].getColorePedina() != -1)
					break;
			i++;
			}
			
			 i = start.getRow();
			 j = start.getColumn()+1;
				
			while(j <= 7 && (caselle[i][j].isEmpty() || caselle[i][j].getColorePedina() != start.getColorePedina())){
				caselle[i][j].select();
				if(caselle[i][j].getColorePedina() != start.getColorePedina() && caselle[i][j].getColorePedina() != -1)
					break;
				j++;
				}
			 i = start.getRow();
			 j = start.getColumn()-1;
				
			while(j >= 0 && (caselle[i][j].isEmpty() || caselle[i][j].getColorePedina() != start.getColorePedina())){
				caselle[i][j].select();
				if(caselle[i][j].getColorePedina() != start.getColorePedina() && caselle[i][j].getColorePedina() != -1)
					break;
				j--;
				}
		
	}
	
	private void setDestinationsAlfiere(){
		int i = start.getRow()-1;
		int j = start.getColumn()+1;
			start.select();
			while((i >= 0 && j <= 7) && (caselle[i][j].isEmpty() || caselle[i][j].getColorePedina() != start.getColorePedina())){
				caselle[i][j].select();
				if(caselle[i][j].getColorePedina() != start.getColorePedina() && caselle[i][j].getColorePedina() != -1)
					break;
				i--;
				j++;
			}
			
		    i = start.getRow()+1;
			j = start.getColumn()-1;
			while((i <= 7 && j >= 0) && (caselle[i][j].isEmpty() || caselle[i][j].getColorePedina() != start.getColorePedina())){
				caselle[i][j].select();
				if(caselle[i][j].getColorePedina() != start.getColorePedina() && caselle[i][j].getColorePedina() != -1)
					break;
				i++;
				j--;
			}
			
			 i = start.getRow()-1;
			 j = start.getColumn()-1;
				
			while((i >= 0 && j >= 0) && (caselle[i][j].isEmpty() || caselle[i][j].getColorePedina() != start.getColorePedina())){
				caselle[i][j].select();
				if(caselle[i][j].getColorePedina() != start.getColorePedina() && caselle[i][j].getColorePedina() != -1)
					break;
				i--;
				j--;
			}
			 i = start.getRow()+1;
			 j = start.getColumn()+1;
				
			while((j <= 7 && i <= 7) && (caselle[i][j].isEmpty() || caselle[i][j].getColorePedina() != start.getColorePedina())){
				caselle[i][j].select();
				if(caselle[i][j].getColorePedina() != start.getColorePedina() && caselle[i][j].getColorePedina() != -1)
					break;
				i++;
				j++;
			}
	}
	
	private void setDestinationsCavallo(){//fa lo stesso lavoro dei checkMinacceCavallo ma setta le caselle disponibili di verde
		int i = start.getRow();
		int j = start.getColumn();
		start.select();
		if(i - 2 >= 0 && j + 1 <= 7)
			if((caselle[i - 2][j + 1].isEmpty() || caselle[i - 2][j + 1].getColorePedina() != start.getColorePedina()))
				caselle[i - 2][j + 1].select();
		
		if(i - 1 >= 0 && j + 2 <= 7)
			if((caselle[i - 1][j + 2].isEmpty() || caselle[i - 1][j + 2].getColorePedina() != start.getColorePedina()))
				caselle[i - 1][j + 2].select();
		
		if(i + 1 <= 7 && j + 2 <= 7)
			if((caselle[i + 1][j + 2].isEmpty() || caselle[i + 1][j + 2].getColorePedina() != start.getColorePedina()))
				caselle[i + 1][j + 2].select();
		
		if(i + 2 <= 7 && j + 1 <= 7)
			if((caselle[i + 2][j + 1].isEmpty() || caselle[i + 2][j + 1].getColorePedina() != start.getColorePedina()))
				caselle[i + 2][j + 1].select();
		
		if(i + 2 <= 7 && j - 1 >= 0)
			if((caselle[i + 2][j - 1].isEmpty() || caselle[i + 2][j - 1].getColorePedina() != start.getColorePedina()))
				caselle[i + 2][j - 1].select();
		
		if(i + 1 <= 7 && j - 2 >= 0)
			if((caselle[i + 1][j - 2].isEmpty() || caselle[i + 1][j - 2].getColorePedina() != start.getColorePedina()))
				caselle[i + 1][j - 2].select();
		
		if(i - 1 >= 0 && j - 2 >= 0)
			if((caselle[i - 1][j - 2].isEmpty() || caselle[i - 1][j - 2].getColorePedina() != start.getColorePedina()))
				caselle[i - 1][j - 2].select();
		
		if(i - 2 >= 0 && j - 1 >= 0)
			if((caselle[i - 2][j - 1].isEmpty() || caselle[i - 2][j - 1].getColorePedina() != start.getColorePedina()))
				caselle[i - 2][j - 1].select();
	
	}
	
	private void setDestinationsRe(){//fa lo stesso lavoro dei checkMinacceRe ma setta le caselle disponibili di verde
		int i = start.getRow();
		int j = start.getColumn();
		start.select();
		
		if(i - 1 >= 0)
			if(     (caselle[i - 1][j].isEmpty()  &&  (start.getColorePedina() == 0? caselle[i - 1][j].getMinacciaDaNeri() : caselle[i - 1][j].getMinacciaDaBianchi()) == 0 )     ||       (caselle[i - 1][j].getColorePedina() != start.getColorePedina()   &&   (start.getColorePedina() == 0? caselle[i - 1][j].getMinacciaDaNeri() : caselle[i - 1][j].getMinacciaDaBianchi()) == 0))
				caselle[i - 1][j].select();
		
		if(i - 1 >= 0 && j + 1 <= 7)
			if(     (caselle[i - 1][j + 1].isEmpty()  &&  (start.getColorePedina() == 0? caselle[i - 1][j + 1].getMinacciaDaNeri() : caselle[i - 1][j + 1].getMinacciaDaBianchi()) == 0 )     ||       (caselle[i - 1][j + 1].getColorePedina() != start.getColorePedina()   &&   (start.getColorePedina() == 0? caselle[i - 1][j + 1].getMinacciaDaNeri() : caselle[i - 1][j + 1].getMinacciaDaBianchi()) == 0))
				caselle[i - 1][j + 1].select();
		
		if(j + 1 <= 7)
			if(     (caselle[i][j + 1].isEmpty()  &&  (start.getColorePedina() == 0? caselle[i][j + 1].getMinacciaDaNeri() : caselle[i][j + 1].getMinacciaDaBianchi()) == 0 )     ||       (caselle[i][j + 1].getColorePedina() != start.getColorePedina()   &&   (start.getColorePedina() == 0? caselle[i][j + 1].getMinacciaDaNeri() : caselle[i][j + 1].getMinacciaDaBianchi()) == 0))
				caselle[i][j + 1].select();
		
		if(i + 1 <= 7 && j + 1 <= 7)
			if(     (caselle[i + 1][j + 1].isEmpty()  &&  (start.getColorePedina() == 0? caselle[i + 1][j + 1].getMinacciaDaNeri() : caselle[i + 1][j + 1].getMinacciaDaBianchi()) == 0 )     ||       (caselle[i + 1][j + 1].getColorePedina() != start.getColorePedina()   &&   (start.getColorePedina() == 0? caselle[i + 1][j + 1].getMinacciaDaNeri() : caselle[i + 1][j + 1].getMinacciaDaBianchi()) == 0))
					caselle[i + 1][j + 1].select();
		
		if(i + 1 <= 7)
			if(     (caselle[i + 1][j].isEmpty()  &&  (start.getColorePedina() == 0? caselle[i + 1][j].getMinacciaDaNeri() : caselle[i + 1][j].getMinacciaDaBianchi()) == 0 )     ||       (caselle[i + 1][j].getColorePedina() != start.getColorePedina()   &&   (start.getColorePedina() == 0? caselle[i + 1][j].getMinacciaDaNeri() : caselle[i + 1][j].getMinacciaDaBianchi()) == 0))
					caselle[i + 1][j].select();
		
		if(i + 1 <= 7 && j - 1 >= 0)
			if(     (caselle[i + 1][j - 1].isEmpty()  &&  (start.getColorePedina() == 0? caselle[i + 1][j - 1].getMinacciaDaNeri() : caselle[i + 1][j - 1].getMinacciaDaBianchi()) == 0 )     ||       (caselle[i + 1][j - 1].getColorePedina() != start.getColorePedina()   &&   (start.getColorePedina() == 0? caselle[i + 1][j - 1].getMinacciaDaNeri() : caselle[i + 1][j - 1].getMinacciaDaBianchi()) == 0))
					caselle[i + 1][j - 1].select();
		
		if(j - 1 >= 0)
			if(     (caselle[i][j - 1].isEmpty()  &&  (start.getColorePedina() == 0? caselle[i][j - 1].getMinacciaDaNeri() : caselle[i][j - 1].getMinacciaDaBianchi()) == 0 )     ||       (caselle[i][j - 1].getColorePedina() != start.getColorePedina()   &&   (start.getColorePedina() == 0? caselle[i][j - 1].getMinacciaDaNeri() : caselle[i][j - 1].getMinacciaDaBianchi()) == 0))
				caselle[i][j - 1].select();
		
		if(i - 1 >= 0 && j - 1 >= 0)
			if(     (caselle[i - 1][j - 1].isEmpty()  &&  (start.getColorePedina() == 0? caselle[i - 1][j - 1].getMinacciaDaNeri() : caselle[i - 1][j - 1].getMinacciaDaBianchi()) == 0 )     ||       (caselle[i - 1][j - 1].getColorePedina() != start.getColorePedina()   &&   (start.getColorePedina() == 0? caselle[i - 1][j - 1].getMinacciaDaNeri() : caselle[i - 1][j - 1].getMinacciaDaBianchi()) == 0))
				caselle[i - 1][j - 1].select();	
	}
	
	private void cambiaTurno(){
		if(turn == 0)
			turn = 1;
		else
			turn = 0;
	}

	private boolean checkScaccoMattoBianchi(){//metodo che controlla se vi e' scacco matto per i bianchi
		if(checkScaccoBianchi()){//se c'e' scacco per i bianchi
		for(int i = 0; i < 8; i++)//andiamo a controllare ogni casella
			for(int j = 0; j < 8; j++)
				if(caselle[i][j].getColorePedina() == 0){//se la pedina e' bianca
					start = caselle[i][j];
					deSelectAll();
					setDestinations();//settiamo di verdele caselle dove può andare
					deselectIfCheckAfterMove();//deselezioniamo le caselle che dopo lo spostamento portano a una situazione di scacco
					if(hasDestinations())//se dopo tutto ciò, abbiamo destinazioni disponibili, allora non c'e' scacco matto
						return false;
				}
		deSelectAll();
		return true;
		}
		return false;	
	}
	private boolean checkScaccoMattoNeri(){// stessa cosa ma per i neri
		if(checkScaccoNeri()){
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
					if(caselle[i][j].getColorePedina() == 1){
						start = caselle[i][j];
						deSelectAll();
						setDestinations();
				
						deselectIfCheckAfterMove();
						
						if(hasDestinations())
							return false;
					}
		deSelectAll();
		return true;
		}
		return false;
	}
	
	public boolean isCheckmate(){
		return checkScaccoMattoBianchi() || checkScaccoMattoNeri();
	}
	
	private boolean checkScaccoBianchi(){
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
				if(caselle[i][j].scaccoBianchi()){
				//	caselle[i][j].setBackground(Color.RED);
					return true;
				}
		return false;
			
	}
	private boolean checkScaccoNeri(){
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
				if(caselle[i][j].scaccoNeri()){
			//		caselle[i][j].setBackground(Color.RED);
					return true;
				}
		return false;
			
	}
	private void promozione(Casella casellaPromossa){
		deSelectAll();
		casellaPromossa.select();
		new PromotionFrame(casellaPromossa);//crea la finestra	
	}
	
	private void move(Casella destination){
			Casella temp = new Casella(destination.getRow(), destination.getColumn(), destination.getColor());
			temp.setPedina(destination.getPedina(), destination.getColorePedina(), destination.getImagePedina());
			
			destination.setPedina(start.getPedina(), start.getColorePedina(), start.getImagePedina());
			start.setPedina("", -1, null);
	
			for(int j = 0; j < 8; j++)
				if(caselle[0][j].getPedina().equals("Pedone"))
					promozione(caselle[0][j]);
				
			for(int j = 0; j < 8; j++)
				if(caselle[7][j].getPedina().equals("Pedone"))
					promozione(caselle[7][j]);
			
			unSetMinacce();
			setMinacce();
			cambiaTurno();
			if(checkScaccoMattoBianchi()){
				checkScaccoBianchi();
				new EndMessage(0, frame);
				
			}
			if(checkScaccoMattoNeri()){
				checkScaccoNeri();
				new EndMessage(1, frame);
				
			}		
	}
	public void deSelectAll(){
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
				caselle[i][j].deSelect();
			
	}
}