/**
 * la classe club pour gérer les licenciés, les événements ou les actulités des clubs de chaque fédération 
 * @author imane
 * @version 1.0
 */

package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Club {
	// initialisation des attributs de chaque club
	/**
	 * Liste des tranches d'age fixes + le NR nom répertorié selon le fichier
	 * desdonnées
	 */
	private static final List<String> TRANCHES_AGE = Arrays.asList("0-4", "5-9", "10-14", "15-19", "20-24", "25-29",
			"30-34", "35-39", "40-44", "45-49", "50-54", "55-59", "60-64", "65-69", "70-74", "75-79", "80-99", "NR");

	/** L'id de chaque club */
	private int idClub;
	/** Le libelle de chaque club */
	private String libelleClub;
	/**
	 * Le montant de cotisation de chaque club, qu'un membre du monde sportif peut
	 * modifer, voir jsp indexMembre
	 */
	private float montantCotisation;
	/**
	 * Une collection de type HashMap pour gerer les licenciés de chaque club,
	 * filtrés selon leur tranche d'age et leur genre La map licencies aura deux
	 * clés : clé 1 genre et clé 2 : la tranche d'age sous forme String Il n'y aura
	 * pas de conflit des doublons car les clés sont differents Genre.HOMME !=
	 * Genre.FEMME
	 */
	private Map<Genre, Map<String, Integer>> licencies;
	/** Liste des actualités d'un club */
	private ArrayList<ActualiteClub> actualitesClub;
	/** Liste des evenemnts organisé par un club */
	private ArrayList<Evenement> evenements;

	/**
	 * Constructeur de la classe Club
	 * 
	 * @param idClub      identifiant du club
	 * @param libelleClub nom du club
	 */
	public Club(int idClub, String libelleClub) {
		this.idClub = idClub;
		this.libelleClub = libelleClub;
		this.actualitesClub = new ArrayList<>();
		this.evenements = new ArrayList<>();
	}

	/**
	 * getter de l'id de chaque club
	 * 
	 * @return l'id du club
	 */
	public int getIdClub() {
		return idClub;
	}

	public void setIdClub(int idClub) {
		this.idClub = idClub;
	}

	/**
	 * getter de l'id de chaque club
	 * 
	 * @return l'id de chaque club
	 */
	public String getLibelleClub() {
		return libelleClub;
	}

	/**
	 * setter de l'id de chaque club
	 * 
	 * @param libelleClub
	 */
	public void setLibelleClub(String libelleClub) {
		this.libelleClub = libelleClub;
	}

	/**
	 * getter du libelle de chaque club
	 * 
	 * @return le libelle de chaque club
	 */
	public float getMontantCotisation() {
		return montantCotisation;
	}

	/**
	 * setter du libelle de chaque club
	 * 
	 * @param libelleClub
	 */
	public void setMontantCotisation(float montantCotisation) {
		this.montantCotisation = montantCotisation;
	}

}
