import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Center implements MouseListener, ActionListener, MouseMotionListener {
	JFrame f = new JFrame();
	ArrayList<Member> Members = new ArrayList<Member>();
	Member m = null;
	JButton addMem = new JButton();
	Container menu = new Container();
	Surface s = new Surface();
	boolean movable = true;
	Menu showing;
	Member strt = null;
	Point PP;
	JButton startCalc = new JButton();
	Member[] parents = new Member[2];
public static final String DATA_OUTPUT_FILE = "data.txt"; 
	public Center() {
		
		s.addMouseListener(this);
		s.addMouseMotionListener(this);
		f.setVisible(true);
		menu.setLayout(new GridLayout(3, 1));
		menu.add(addMem);
		f.setSize(500, 500);
		menu.add(new JPanel());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		f.add(s, BorderLayout.CENTER);
		f.add(menu, BorderLayout.EAST);
		addMem.setMargin(new Insets(0, 0, 0, 0));
		addMem.setBorder(null);
		addMem.setBackground(null);
		startCalc.setMargin(new Insets(0,0,0,0));
		startCalc.setBorder(null);
		startCalc.setBorder(null);
		startCalc.addActionListener(this);
		menu.add(startCalc);
		try {
			Image img1 = ImageIO.read(new File("addMember.png"));
			addMem.setIcon(new ImageIcon(img1));
			Image img2= ImageIO.read(new File("startCalculation.png"));
			startCalc.setIcon(new ImageIcon(img2));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		addMem.addActionListener(this);

	}

	public static void main(String[] args) {
		new Center();

	}

	public void mouseClicked(MouseEvent e) {

	}

	public Member getClicked(int x, int y) {
	
		for (int i = 0; i < Members.size(); i++) {
			int XX = s.members.get(i).X;
			int YY = s.members.get(i).Y;
			if ((x > XX && x < XX + Member.IMAGE_SIZE) && (y > YY && y < YY + Member.IMAGE_SIZE)) {
				return Members.get(i);
			}
		}
		return null;
	}

	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if(e.getClickCount()<2){
		if (e.getButton() == MouseEvent.BUTTON1 && movable) {
			
			if (m == null) {
				m = getClicked(x, y);

			} else {
				m = null;
			}
			// If you press the middle button
		} else if (e.getButton() == MouseEvent.BUTTON3 && movable) {
			m = getClicked(x, y);
			if (m != null) {
				movable = false;
				showing = new Menu(x, y, m);
				s.menu = showing;
				f.repaint();
			} else {
				if (isNode(x, y)) {
					s.relmenu = new RelationMenu(x, y, s.Relations.get(findIndexOfMidpoint(s.lines,x,y)));
					movable = false;
					PP = new Point(x,y);
					f.repaint();
			
				}
			}
		} else if (e.getButton() == MouseEvent.BUTTON1 && !movable) {
			if (s.relmenu == null) {
				int clicked = getClickedOption(x, y);
				if (clicked == 5) {
					s.menu = null;
					f.repaint();
					movable = true;
					m = null;
					return;
				} else {
				
					switch (clicked) {
					case 1:
						m.Gender = Member.MALE;
						break;
					case 2:
						m.Gender = Member.FEMALE;
						break;
					case 3:
				
						m.Carrier = true;
						break;
					case 4:
					
						m.Carrier = false;
						break;
					default:
						// nothing
					}
					s.menu.linked = m;

					f.repaint();

				}
			} else {
				int clicked = getClickedRel(x, y);
				switch (clicked) {
				case 1:
					s.relmenu.Status = RelationMenu.MARRIED;
					break;
				case 2:
					s.relmenu.Status = RelationMenu.DESCENDANT;
					break;
				case 3:
					s.relmenu = null;
					movable = true;
					f.repaint();
					PP = new Point();
					break;
				}
				if (clicked != 3) {
					s.Relations.set(findIndexOfMidpoint(s.lines, PP.x, PP.y), s.relmenu.Status);
					f.repaint();
					
				}
			}
			// If there you click on the second button, the middle button
		} else if (e.getButton() == MouseEvent.BUTTON2) {
			// If the click is on an object
			if (getClicked(x, y) != null) {
				if (strt != null) {
					Member[] temp = { getClicked(x, y), strt };
					s.lines.add(temp);
					s.Relations.add(RelationMenu.MARRIED);
					// Redraw the frame
					f.repaint();
					strt = null;
				} else {
					strt = getClicked(x, y);
				}
			}

		}
		}else{
			if(e.getButton()==MouseEvent.BUTTON1){
				if(getClicked(x, y)!=null&&(!getClicked(x,y).equals(parents[0]))&&(!getClicked(x,y).equals(parents[1]))){
					if(parents[0]==null){
						parents[0]=getClicked(x,y);
					}else if(parents[1]==null){
						parents[1]=getClicked(x,y);
					}else{
						parents[(int)Math.round(Math.random())]=getClicked(x,y);
					}
					s.Parents[0] = parents[0];
					s.Parents[1] = parents[1];
					f.repaint();
				}
			}
		}
	}

	public int findIndexOfMidpoint(ArrayList<Member[]> lines, int x, int y) {
		System.out.println(x+","+y);
		for (int i = 0; i < lines.size(); i++) {
			Point pp = getMidpoint(lines.get(i)[0].X+Member.IMAGE_SIZE/2, lines.get(i)[0].Y+Member.IMAGE_SIZE/2, lines.get(i)[1].X+Member.IMAGE_SIZE/2, lines.get(i)[1].Y+Member.IMAGE_SIZE/2);
			System.out.println(pp.x+","+pp.y);
			if (pp.distance(new Point(x,y)) < Surface.CIRCLE_DIAMETER) {
				return i;
			}
		}
		return -1;
	}

	public int getClickedRel(int x, int y) {
		if (x > s.relmenu.X && x < s.relmenu.X + RelationMenu.WIDTH) {
			if (y > s.relmenu.Y && y < s.relmenu.Y + Menu.HEIGHT) {
				int offset = y - s.relmenu.Y;
				offset = (int) Math.round(offset / RelationMenu.LINE_GAP);
				if (offset > 2) {
					return 2;
				} else if (offset <= 0) {
					return 1;
				} else {
					return offset;
				}
			} else {
				return 3;
			}
		} else {
			return 3;
		}

	}

	public boolean isNode(int x, int y) {
		for (int i = 0; i < s.lines.size(); i++) {
			Point p = getMidpoint(s.lines.get(i)[0].X + Member.IMAGE_SIZE / 2,
					s.lines.get(i)[0].Y + Member.IMAGE_SIZE / 2, s.lines.get(i)[1].X + Member.IMAGE_SIZE / 2,
					s.lines.get(i)[1].Y + Member.IMAGE_SIZE / 2);
			
			if (Point.distance(p.x, p.y, x, y) < Surface.CIRCLE_DIAMETER) {
				return true;
			}
		}
		
		return false;
	}

	public Point getMidpoint(int x1, int y1, int x2, int y2) {
		return new Point((x1 + x2) / 2, (y1 + y2) / 2);
	}

	public int getClickedOption(int x, int y) {
		if (x > s.menu.X && x < s.menu.X + Menu.WIDTH) {
			if (y > s.menu.Y && y < s.menu.Y + Menu.HEIGHT) {
				int offset = y - s.menu.Y;
				offset = (int) Math.round(offset / Menu.LINE_GAP);
				if (offset > 4) {
					return 4;
				} else if (offset <= 0) {
					return 1;
				} else {
					return offset;
				}
			} else {
				return 5;
			}
		} else {
			return 5;
		}
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(addMem)){
		Member mem = new Member(Member.MALE, false, 0, 0);
		s.members.add(mem);
		Members.add(mem);
		f.repaint();
		}else{
			String familyTree = buildStringFromTree();
			try {
				PrintWriter write = new PrintWriter(DATA_OUTPUT_FILE);
				write.print(familyTree);
				write.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
		}

	}

	public String buildStringFromTree() {
		String ret = "";
		for (int i = 0; i < s.lines.size(); i++) {
			if(s.Relations.get(i)==RelationMenu.MARRIED){
				if(!s.lines.get(i)[0].Married.contains(s.lines.get(i)[1])){
					s.lines.get(i)[0].Married.add(s.lines.get(i)[1]);
				}
				if(!s.lines.get(i)[1].Married.contains(s.lines.get(i)[0])){
					s.lines.get(i)[1].Married.add(s.lines.get(i)[0]);
				}
			}else{
				Member P1 = s.lines.get(i)[0];
				Member P2 = s.lines.get(i)[1];
				if(P1.Y>P2.Y&&!P1.Parents.contains(P2)&&!P2.Children.contains(P1)){
					P1.Parents.add(P2);
					P2.Children.add(P1);
				}else if (P2.Y>P1.Y&&P2.Parents.contains(P1)&&!P1.Children.contains(P2)){
					P2.Parents.add(P1);
					P1.Children.add(P2);
				}
			}
		}
		for (int i = 0; i < s.members.size(); i++) {
			ret+=s.members.get(i).Carrier?"true\n":"false\n";
			if(s.Parents[0].equals(s.members.get(i))||s.Parents[1].equals(s.members.get(i))){
				ret+="true\n";
			}else{
				ret+="false\n";
			}
			ret+=getRow(s.members.get(i))+"\n";
			ret+=getColumn(s.members.get(i))+"\n";
			for (int j = 0; j < s.members.get(i).Parents.size(); j++) {
				ret+=getRow(s.members.get(i).Parents.get(i));
				ret+=getColumn(s.members.get(i).Parents.get(i));
			}
			for (int j = 0; j < s.members.get(i).Married.size(); j++) {
				ret+=getRow(s.members.get(i).Married.get(i));
				ret+=getColumn(s.members.get(i).Married.get(i));
			}
			
		}
		return ret;
	}

	private String getRow(Member mm) {
		
		return Integer.toString(mm.relations(0,0,s.members.size()*2));
	}

	

	private String getColumn(Member member) {
		int a;
		ArrayList<Integer> usedCols =new ArrayList<Integer>();
		for (int i = 0; i < s.members.size(); i++) {
			if(s.members.get(i).column!=-1){
				usedCols.add(s.members.get(i).column);
			}
		}
		while(!usedCols.contains((a=(int)Math.round(s.members.size()*Math.random())))){
		//nothing	
		}
		return Integer.toString(a);
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (m != null && movable) {
			m.X = e.getX() - Member.IMAGE_SIZE / 2;
			m.Y = e.getY() - Member.IMAGE_SIZE / 2;
			f.repaint();
		}

	}

	

}
