package eg.edu.alexu.csd.datastructure.iceHockey.cs;
import java.awt.Point;

public class PlayersFinder implements IPlayersFinder {
	final int MaxNoPlayer = 2500;
	int yMaxPlayer;int xMaxPlayer;int yMinPlayer;int xMinPlayer;int areaOfPlayer;
	public java.awt.Point[] findPlayers(String[] photo,int team,int threshold){
		if(photo == null)
			return null;
		int Xmax = photo[0].length();
		int Ymax = photo.length;
		Point[] players = new Point[MaxNoPlayer];
		boolean[][] bitMap = new boolean[Xmax][Ymax];
		bitMap = bitmap(photo,team,Xmax,Ymax);
		int noPlayers = 0;
		int i,j;
		for(i=0;i<Xmax;i++) {
			for(j=0;j<Ymax;j++) {
				yMaxPlayer = j;xMaxPlayer =  i;yMinPlayer = j;xMinPlayer = i;areaOfPlayer = 0;
				searchPlayer(bitMap,Xmax,Ymax,i,j);
				if(areaOfPlayer>=threshold) {
					players[noPlayers] = new Point();
					players[noPlayers].setLocation((xMaxPlayer+xMinPlayer+1),(yMaxPlayer+yMinPlayer+1));
					noPlayers++;
				}
			}
		}
		Point[] finalPlayers = new Point[noPlayers];
		sortPlayers(players,noPlayers);
		if(noPlayers == 0)
			return null;
		for(i=0;i<noPlayers;i++) {
			finalPlayers[i] = new Point();
			finalPlayers[i].setLocation(players[i]);
		}
		return finalPlayers;
	}
	void searchPlayer (boolean[][] bitMap,int Xmax,int Ymax,int i,int j){
		if(bitMap[i][j] == true) {
			bitMap[i][j]=false;
			if(i>xMaxPlayer)
				xMaxPlayer =i;
			else if(j>yMaxPlayer)
				yMaxPlayer =j;
			//if(i<xMinPlayer)
				//xMinPlayer =i;
			else if(j<yMinPlayer)
				yMinPlayer =j;
			areaOfPlayer+=4;
			if(i+1<Xmax)
				searchPlayer(bitMap,Xmax,Ymax,i+1,j);
			if(i-1>-1)
				searchPlayer(bitMap,Xmax,Ymax,i-1,j);
			if(j+1<Ymax)
				searchPlayer(bitMap,Xmax,Ymax,i,j+1);
			if((j-1)>-1)
				searchPlayer(bitMap,Xmax,Ymax,i,j-1);
		}
	}
	boolean[][] bitmap(String[] photo,int team,int Xmax,int Ymax) {
		int i,j;
		boolean[][] bitMap = new boolean[Xmax][Ymax];
		for(i=0;i<Xmax;i++) {
			for(j=0;j<Ymax;j++) {
				if((Character.getNumericValue(photo[j].charAt(i)))== team) 
					bitMap[i][j]=true;
				else
					bitMap[i][j]=false;
			}
		}
		return bitMap;
	}
	void sortPlayers(Point[] players,int noPlayers) {
		int i,j;Boolean sorted;Point temp = new Point();
		for(i=0;i<noPlayers;i++) {
			sorted = true;
			for(j=0;j<noPlayers-i-1;j++) {
				if(players[j].getX()>players[j+1].getX()||((players[j].getX()==players[j+1].getX())&&players[j].getY()>players[j+1].getY())) {
					temp.setLocation(players[j+1]);
					players[j+1].setLocation(players[j]);
					players[j].setLocation(temp);
					sorted = false;
				}
			}
			if(sorted)
				break;
		}
	}
}
