import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Member {
	final static int MALE = 0;
	final static int FEMALE = 1;
	final static int IMAGE_SIZE = 100;
	int Gender;
	boolean Carrier;
	BufferedImage maleCarrier = null;
	BufferedImage femaleCarrier = null;
	BufferedImage maleNonCarrier = null;
	BufferedImage femaleNonCarrier = null;
	int X;
	int Y;

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

}
