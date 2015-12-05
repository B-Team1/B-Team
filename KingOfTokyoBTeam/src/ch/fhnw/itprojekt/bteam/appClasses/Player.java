package ch.fhnw.itprojekt.bteam.appClasses;

public class Player {
	
	int honorPoints, lifePoints, energyPoints, futureLifePoints, futureHonorPoints, futureEnergyPoints;
	boolean inTokyo = false;
	
	// futurepoints getter und setter aber nicht im konstruktor!!  Gesamte Klasse zählt als Objetk!!
	
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
}
