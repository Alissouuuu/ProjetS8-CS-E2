package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
	private FileReader fichier;
	private BufferedReader lignes;
	private String ligne;
	private String [] fileColumns, ligne1,ligne2,ligne3,ligne4,ligne5;
	private int cpt=0;
	
	public CSVReader(String cheminFichier) {
		try {
			fichier = new FileReader(cheminFichier);
			lignes = new BufferedReader(fichier);
			
			ligne = lignes.readLine();
			ligne1 = ligne.split(",");
			
			ligne = lignes.readLine();
			ligne2 = ligne.split(",");
			
			ligne = lignes.readLine();
			ligne3 = ligne.split(",");
			
			ligne = lignes.readLine();
			ligne4 = ligne.split(",");
			
			ligne = lignes.readLine();
			ligne5 = ligne.split(",");
			System.out.println("Nombre de colonnes : "+ligne1.length);
			for(int i=0;i<ligne1.length;i++) {
				System.out.print("Colonne "+(i+1)+" = "+ligne1[i]+" : "+ligne3[i]);
				System.out.println("");
			}
			
			/*while((ligne = lignes.readLine()) != null && cpt <5) {
				System.out.println(ligne);
				cpt++;
			}*/
		}
		catch(IOException e) {
			System.out.println(cheminFichier+" est introuvable !");
		}
		
	}
}
