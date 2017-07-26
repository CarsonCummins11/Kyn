import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Member {
	final static int MALE = 0;
	final static int FEMALE = 1;
	final static int IMAGE_SIZE = 100;
	int Gender;
	boolean Carrier;
	ArrayList<Member> Married = new ArrayList<Member>();
	ArrayList<Member> Parents = new ArrayList<Member>();
	ArrayList<Member> Children = new ArrayList<Member>();
	BufferedImage maleCarrier = null;
	BufferedImage femaleCarrier = null;
	BufferedImage maleNonCarrier = null;
	BufferedImage femaleNonCarrier = null;
	int X;
	int Y;
	int column = -1;
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

	public void draw(Graphics g) {
		if (Carrier && Gender == MALE) {
			g.drawImage(maleCarrier, X, Y, IMAGE_SIZE, IMAGE_SIZE, null);
		} else if (Carrier && Gender == FEMALE) {
			g.drawImage(femaleCarrier, X, Y, IMAGE_SIZE, IMAGE_SIZE, null);
		} else if (!Carrier && Gender == MALE) {
			g.drawImage(maleNonCarrier, X, Y, IMAGE_SIZE, IMAGE_SIZE, null);
		} else if (!Carrier && Gender == FEMALE) {
			g.drawImage(femaleNonCarrier, X, Y, IMAGE_SIZE, IMAGE_SIZE, null);
		}

	}

	public int countAncestors(int i) {
		int ret = i+1;
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int j = 0; j < Parents.size(); j++) {
			temp.add(Parents.get(i).countAncestors(0));
		}
		int greatest = Integer.MIN_VALUE;
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
			return -1;
		}
		int parMax = Integer.MIN_VALUE;
		for (int i = 0; i < Parents.size(); i++) {
			int a;
			if((a=Parents.get(i).relations(score+1, jumps+1, maxJumps))>parMax){
				parMax = a;
			}
		}
		int marMax = Integer.MIN_VALUE;
		for (int i = 0; i < Married.size(); i++) {
			int a;
			if((a=Married.get(i).relations(score, jumps+1, maxJumps))>marMax){
				marMax = a;
			}
		}
		int chilMax = Integer.MIN_VALUE;
		for (int i = 0; i < Children.size(); i++) {
			int a;
			if((a=Children.get(i).relations(score-1, jumps+1, maxJumps))>chilMax){
				chilMax = a;
			}
		}
		if(parMax>=marMax&&parMax>chilMax){
			return parMax;
		}else if(marMax>=parMax&&marMax>=chilMax){
			return marMax;
		}else{
			return chilMax;
		}
	}

}
