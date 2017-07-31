import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Member {
	final static int MALE = 0;
	final static int FEMALE = 1;
	final static int IMAGE_SIZE = 50;
	int Gender;
	boolean Carrier;
	ArrayList<Member> Married = new ArrayList<Member>();
	ArrayList<Member> Parents = new ArrayList<Member>();
	ArrayList<Member> Children = new ArrayList<Member>();
	BufferedImage maleCarrier = null;
	BufferedImage femaleCarrier = null;
	BufferedImage maleNonCarrier = null;
	BufferedImage femaleNonCarrier = null;
	ArrayList<Integer[]> ParentCoordinates=new ArrayList<Integer[]>();
	int X;
	int Y;
	int column = -1;
	int row = -1;
	public Member(int G, boolean C, int x, int y) {
		Gender = G;
		Carrier = C;
		X = x;
		Y = y;
		try {
			maleCarrier = ImageIO.read(new File("maleCarrier.png"));
			femaleCarrier = ImageIO.read(new File("femaleCarrier.png"));
			maleNonCarrier = ImageIO.read(new File("maleNonCarrier.png"));
			femaleNonCarrier = ImageIO.read(new File("femaleNonCarrier.png"));
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public void draw(Graphics g,double zoom) {
		if (Carrier && Gender == MALE) {
			g.drawImage(maleCarrier, X, Y, (int)Math.round(IMAGE_SIZE*zoom), (int)Math.round(IMAGE_SIZE*zoom), null);
		} else if (Carrier && Gender == FEMALE) {
			g.drawImage(femaleCarrier, X, Y, (int)Math.round(IMAGE_SIZE*zoom), (int)Math.round(IMAGE_SIZE*zoom), null);
		} else if (!Carrier && Gender == MALE) {
			g.drawImage(maleNonCarrier, X, Y, (int)Math.round(IMAGE_SIZE*zoom), (int)Math.round(IMAGE_SIZE*zoom), null);
		} else if (!Carrier && Gender == FEMALE) {
			g.drawImage(femaleNonCarrier, X, Y, (int)Math.round(IMAGE_SIZE*zoom), (int)Math.round(IMAGE_SIZE*zoom), null);
		}

	}

	public int countAncestors(int i) {
		int ret = i+1;
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int j = 0; j < Parents.size(); j++) {
			temp.add(Parents.get(i).countAncestors(0));
		}
		int greatest = 0;
		for (int j = 0; j < temp.size() ; j++) {
			if(temp.get(i)>greatest){
				greatest = temp.get(i);
			}
		}
		ret+=greatest;
		return ret;
	}

	public int relations(int score, int jumps, int maxJumps) {
		if(jumps>maxJumps){
			return score;
		}
		int parMax = score;
		for (int i = 0; i < Parents.size(); i++) {
			int a;
			a=Parents.get(i).relations(score+1, jumps+1, maxJumps);
			if(a>parMax){
				parMax = a;
			}
		}
		int marMax = score;
		for (int i = 0; i < Married.size(); i++) {
			int a=Married.get(i).relations(score, jumps+1, maxJumps);
			if(a>marMax){
				marMax = a;
			}
		}
		int chilMax = score;
		
		for (int i = 0; i < Children.size(); i++) {
			int a=Children.get(i).relations(score-1, jumps+1, maxJumps);
			if(a>chilMax){
				chilMax = a;
			}
		}
		int ret = 0;
		if(parMax>=marMax&&parMax>=chilMax){
			ret= parMax;
		}else if(marMax>=parMax&&marMax>=chilMax){
			ret =marMax;
		}else{
			ret =chilMax;
		}
		return ret;
	}

}
