import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Surface extends JPanel {
	ArrayList<Member> members = new ArrayList<Member>();
	Menu menu = null;
	RelationMenu relmenu = null;
	ArrayList<Member[]> lines = new ArrayList<Member[]>();
	ArrayList<Integer> Relations = new ArrayList<Integer>();
	public static final int CIRCLE_DIAMETER = 20;

	public Surface() {

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < members.size(); i++) {
			members.get(i).draw(g);
		}
		g.setColor(Color.BLACK);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(5));
		for (int i = 0; i < lines.size(); i++) {
			Member[] temp = lines.get(i);
			g2.drawLine(temp[0].X + Member.IMAGE_SIZE / 2, temp[0].Y + Member.IMAGE_SIZE / 2,
					temp[1].X + Member.IMAGE_SIZE / 2, temp[1].Y + Member.IMAGE_SIZE / 2);
			int x = (temp[0].X + Member.IMAGE_SIZE / 2 + temp[1].X + Member.IMAGE_SIZE / 2) / 2;
			int y = (temp[0].Y + Member.IMAGE_SIZE / 2 + temp[1].Y + Member.IMAGE_SIZE / 2) / 2;
			if (Relations.get(i) == RelationMenu.MARRIED) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.BLUE);
			}
			g.drawOval(x - CIRCLE_DIAMETER / 2, y - CIRCLE_DIAMETER / 2, CIRCLE_DIAMETER, CIRCLE_DIAMETER);
			g.setColor(Color.black);
		}
		if (relmenu != null) {
			relmenu.draw(g);
		}
		if (menu != null) {
			menu.draw(g);
		}
	}
}
