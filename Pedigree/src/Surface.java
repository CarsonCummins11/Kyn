import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
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
	Member[] Parents = new Member[2];
	public double zoom = 1;
	public static final int CIRCLE_DIAMETER = 20;
	public static final Dimension DELETE_BOX = new Dimension(75,75);
	public Surface() {

	}

	public void paintComponent(Graphics g) {
		int delBoxX =(int) (getWidth()-DELETE_BOX.getWidth());
		int delBoxY = (int) (getHeight()-DELETE_BOX.getHeight());
	
		zoom = Math.abs(zoom);
		super.paintComponent(g);
		for (int i = 0; i < members.size(); i++) {
			members.get(i).draw(g,zoom);
		}
		g.setColor(Color.BLACK);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke((int)Math.round(zoom*5)));
		for (int i = 0; i < lines.size(); i++) {
			Member[] temp = lines.get(i);
			g2.drawLine(temp[0].X + (int)Math.round(zoom*Member.IMAGE_SIZE / 2), temp[0].Y + (int)Math.round(zoom*Member.IMAGE_SIZE / 2),
					temp[1].X + (int)Math.round(zoom*Member.IMAGE_SIZE / 2), temp[1].Y + (int)Math.round(zoom*Member.IMAGE_SIZE / 2));
			int x = (temp[0].X + (int)Math.round(zoom*Member.IMAGE_SIZE / 2) + temp[1].X + (int)Math.round(zoom*Member.IMAGE_SIZE / 2)) / 2;
			int y = (temp[0].Y + (int)Math.round(zoom*Member.IMAGE_SIZE / 2) + temp[1].Y +(int)Math.round(zoom*Member.IMAGE_SIZE / 2)) / 2;
			if (Relations.get(i) == RelationMenu.MARRIED) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.BLUE);
			}
			g.fillOval(x - (int)Math.round(zoom*CIRCLE_DIAMETER / 2), y - (int)Math.round(zoom*CIRCLE_DIAMETER / 2), (int)Math.round(zoom*CIRCLE_DIAMETER), (int)Math.round(zoom*CIRCLE_DIAMETER));
			g.setColor(Color.black);
		}
		for (int i = 0; i < Parents.length; i++) {
			float r = (float) .99;
			float gg = (float).98;
			float b = (float).41;
			float a = (float).50;
			if(Parents[i]!=null){
			g.setColor(new Color(r,gg,b,a));
			g.fillRect(Parents[i].X, Parents[i].Y, (int)Math.round(zoom*Member.IMAGE_SIZE),(int)Math.round( zoom*Member.IMAGE_SIZE));
			}
		
		}
		g.fillRect(delBoxX, delBoxY, (int)DELETE_BOX.getWidth(), (int)DELETE_BOX.getHeight());
		g2.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 0));
		g2.setColor(Color.red);
		int nn = 5;
		g2.drawLine(delBoxX+nn, delBoxY+nn, getWidth()-nn, getHeight()-nn);
		g2.drawLine(delBoxX+nn, getHeight()-nn,getWidth()-nn , delBoxY+nn);
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(9));
		g2.drawLine(20, 0, 20, this.getHeight());
		g2.drawLine(20, 0, 0, 20);
		g2.drawLine(20,0,40,20);
		g2.drawLine(20, this.getHeight(), 0, this.getHeight()-20);
		g2.drawLine(20, this.getHeight(), 40, this.getHeight()-20);
		g.drawString("Oldest", 29, 40);
		g.drawString("Youngest", 29, this.getHeight()-40);
		if (relmenu != null) {
			relmenu.draw(g);
		}
		if (menu != null) {
			menu.draw(g);
		}
	}
}
