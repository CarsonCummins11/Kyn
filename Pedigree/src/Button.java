import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;

public class Button extends JButton implements MouseListener, MouseMotionListener{
String Description = "";
boolean mouseOn = false;
public final static int LETTER_WIDTH = 7;
public final static int LETTER_HEIGHT = 16;
int mouseX = 0;
int mouseY = 0;
	public Button() {
		super();
		this.addMouseListener(this);
	}
	public Button(String text){
		super(text);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(mouseOn){
			g.setColor(new Color(244,226,66));
			g.fillRect(mouseX, mouseY+3, getStringWidth(g,Description), LETTER_HEIGHT*(1+numNewLines(Description)));
			g.setColor(Color.black);
			drawStringNewline(g,Description, mouseX, mouseY);
		}
	}
	private int getStringWidth(Graphics g, String s) {
		String line = getLongestLine(s,g);
		return g.getFontMetrics().stringWidth(line);
		
	}
	private String getLongestLine(String s, Graphics g) {
		int longest = Integer.MIN_VALUE;
		String longString = "";
		 for (String line : s.split("\n")){
			 if (g.getFontMetrics().stringWidth(line)>longest){
				 longest = g.getFontMetrics().stringWidth(line);
				 longString = line;
			 }
		 }
		return longString;
	}
	private void drawStringNewline(Graphics g, String d, int x, int y) {
		 for (String line : d.split("\n")){
		        g.drawString(line, x, y += LETTER_HEIGHT);
		 }
		
	}
	private int numNewLines(String in){
		int ret = 0;
		for (int i = 0; i < in.length(); i++) {
			if(in.charAt(i)=='\n'){
				ret++;
			}
		}
		return ret;
	}
	public void setDescription(String desc){
		Description=desc;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		mouseOn = true;
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		mouseOn = false;
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY()+LETTER_HEIGHT/2;
		this.repaint();
		
	}

}
