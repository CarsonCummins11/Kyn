import java.awt.Color;
import java.awt.Graphics;

public class RelationMenu {
	int X;
	int Y;
	int Status;
	public static final int MARRIED = 0;
	public static final int DESCENDANT = 1;
	public static final int WIDTH = 150;
	public static final int HEIGHT = 200;
	public static final int LINE_GAP = 20;

	public RelationMenu(int x, int y, int Stat) {
		X = x;
		Y = y;
		Status = Stat;
	}

	public void draw(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(X, Y, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		g.drawString("Married" + (Status == 0 ? "<" : ""), X, Y + LINE_GAP);
		g.drawString("Descendant" + (Status == 0 ? "" : "<"), X, Y + 2 * LINE_GAP);
	}
}
