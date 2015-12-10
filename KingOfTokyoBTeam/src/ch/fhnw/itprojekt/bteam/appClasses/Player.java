package ch.fhnw.itprojekt.bteam.appClasses;

public class Player {
	
	int honorPoints, lifePoints, energyPoints, futureLifePoints, futureHonorPoints, futureEnergyPoints,
	actualDiceLifePoints, actualDiceEnergyPoints, actualDiceHonorPoints,
	actualCardLifePoints, actualCardEnergyPoints, actualCardHonorPoints;
	

	

	boolean inTokyo = false;
	
	/**
	 * Konstruktor um einen neuen Spieler zu erzeugen
	 * @author Marco
	 */
	public Player(int lifePoints, int energyPoints, int honorPoints, boolean inTokyo) {
		this.lifePoints = lifePoints;
		this.energyPoints = energyPoints;
		this.honorPoints = honorPoints;
		this.inTokyo = inTokyo;
	}
	
	public int getHonorPoints() {
		return honorPoints;
	}

	public void setHonorPoints(int honorPoints) {
		this.honorPoints = honorPoints;
	}

	public int getLifePoints() {
		return lifePoints;
	}

	public void setLifePoints(int lifePoints) {
		this.lifePoints = lifePoints;
	}

	public int getEnergyPoints() {
		return energyPoints;
	}

	public void setEnergyPoints(int energyPoints) {
		this.energyPoints = energyPoints;
	}

	public boolean isInTokyo() {
		return inTokyo;
	}

	public void setInTokyo(boolean inTokyo) {
		this.inTokyo = inTokyo;
	}

	public int getFutureLifePoints() {
		return futureLifePoints;
	}

	public void setFutureLifePoints(int futureLifePoints) {
		this.futureLifePoints = futureLifePoints;
	}

	public int getFutureHonorPoints() {
		return futureHonorPoints;
	}

	public void setFutureHonorPoints(int futureHonorPoints) {
		this.futureHonorPoints = futureHonorPoints;
	}

	public int getFutureEnergyPoints() {
		return futureEnergyPoints;
	}

	public void setFutureEnergyPoints(int futureEnergyPoints) {
		this.futureEnergyPoints = futureEnergyPoints;
	}
	
	public int getActualDiceLifePoints() {
		return actualDiceLifePoints;
	}

	public void setActualDiceLifePoints(int actualDiceLifePoints) {
		this.actualDiceLifePoints = actualDiceLifePoints;
	}

	public int getActualDiceEnergyPoints() {
		return actualDiceEnergyPoints;
	}

	public void setActualDiceEnergyPoints(int actualDiceEnergyPoints) {
		this.actualDiceEnergyPoints = actualDiceEnergyPoints;
	}

	public int getActualDiceHonorPoints() {
		return actualDiceHonorPoints;
	}

	public void setActualDiceHonorPoints(int actualDiceHonorPoints) {
		this.actualDiceHonorPoints = actualDiceHonorPoints;
	}
	
	public int getActualCardLifePoints() {
		return actualCardLifePoints;
	}

	public void setActualCardLifePoints(int actualCardLifePoints) {
		this.actualCardLifePoints = actualCardLifePoints;
	}

	public int getActualCardEnergyPoints() {
		return actualCardEnergyPoints;
	}

	public void setActualCardEnergyPoints(int actualCardEnergyPoints) {
		this.actualCardEnergyPoints = actualCardEnergyPoints;
	}

	public int getActualCardHonorPoints() {
		return actualCardHonorPoints;
	}

	public void setActualCardHonorPoints(int actualCardHonorPoints) {
		this.actualCardHonorPoints = actualCardHonorPoints;
	}
}
