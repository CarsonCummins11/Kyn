import java.awt.Color;
import java.awt.Graphics;

public class Menu {
	int X;
	int Y;
	Member linked;
	public static final int WIDTH = 150;
	public static final int HEIGHT = 200;
	public static final int LINE_GAP = 20;

	public Menu(int x, int y, Member m) {
		X = x;
		Y = y;
		linked = m;
	}

	public void draw(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(X, Y, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		g.drawString("Male " + (linked.Gender == Member.MALE ? "<" : ""), X, Y + LINE_GAP);
		g.drawString("Female " + (linked.Gender == Member.FEMALE ? "<" : ""), X, Y + 2 * LINE_GAP);
		g.drawString("Trait " + (linked.Carrier ? "<" : ""), X, Y + 3 * LINE_GAP);
		g.drawString("No Trait " + (linked.Carrier ? "" : "<"), X, Y + 4 * LINE_GAP);
		g.drawString("Delete", X,Y+5*LINE_GAP );

	}

}
