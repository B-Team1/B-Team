package ch.fhnw.itprojekt.bteam.appClasses;
/**
 * StatsObjekt, wird benutzt um die Stats von der Datenbank auf den Client zu verschieben
 * @author Luzian
 *
 */
public class Stats {
	private int SumGames, WonGames, LosedGames;
	
	public Stats(int SumGames, int WonGames, int LosedGames ){ 
         this.SumGames = SumGames;
         this.WonGames = WonGames;
         this.LosedGames = LosedGames;
    }
	
	public int getSumGames() {
		return SumGames;
	}
	public void setSumGames(int sumGames) {
		SumGames = sumGames;
	}
	public int getWonGames() {
		return WonGames;
	}
	public void setWonGames(int wonGames) {
		WonGames = wonGames;
	}
	public int getLosedGames() {
		return LosedGames;
	}
	public void setLosedGames(int losedGames) {
		LosedGames = losedGames;
	} 

}
