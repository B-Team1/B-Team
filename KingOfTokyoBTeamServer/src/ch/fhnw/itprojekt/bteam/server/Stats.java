package ch.fhnw.itprojekt.bteam.server;

/**
 * StatsObjekt, wird benutzt um die Stats von der Datenbank auf den Client zu verschieben und umgekehrt
 * @author Luzian
 *
 */
public class Stats {
	private int SumGames, WonGames, LosedGames;
	private String NickName;
	
	public Stats(String NickName, int SumGames, int WonGames, int LosedGames ){ 
		 this.NickName = NickName;
         this.SumGames = SumGames;
         this.WonGames = WonGames;
         this.LosedGames = LosedGames;
    }
	
	
	public String getNickName() {
		return NickName;
	}
	public void setNickName(String nickName) {
		NickName = nickName;
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
