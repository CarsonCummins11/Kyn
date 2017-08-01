import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Center implements MouseListener, ActionListener, MouseMotionListener, MouseWheelListener {
	// Defines all the wide scoped variables in the class
	JFrame f = new JFrame("Tree Builder");
	Member m = null;
	JButton addMem = new JButton("Add");
	Container menu = new Container();
	Surface s = new Surface();
	boolean movable = true;
	Menu showing;
	Member strt = null;
	Point PP;
	JButton save = new JButton("Save");
	JButton startCalc = new JButton("Calculate");
	Member[] parents = new Member[2];
	JTextField searchBar = new JTextField("Search for Traits");
	final static Color THEME_COLOR = new Color(103,173,110);
	public static final String DATA_OUTPUT_FILE = "Ancestors.txt";

	public Center() {
		// setting up the frame/general GUI
		try {
			f.setIconImage(ImageIO.read(new File("Logo_Square.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		f.addMouseWheelListener(this);
		s.addMouseListener(this);
		s.addMouseMotionListener(this);
		menu.setLayout(new GridLayout(4, 1));
		menu.add(startCalc);
		menu.add(save);
		save.addActionListener(this);
		menu.add(addMem);
		searchBar.addActionListener(this);
		menu.add(searchBar);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setLayout(new BorderLayout());
		f.add(s, BorderLayout.CENTER);
		f.add(menu, BorderLayout.EAST);
		f.setSize(500, 500);
		f.setVisible(true);
		startCalc.addActionListener(this);
		addMem.addActionListener(this);
		addMem.setBackground(THEME_COLOR);
		addMem.setForeground(Color.black);
		startCalc.setForeground(Color.black);
		save.setForeground(Color.black);
		startCalc.setBackground(THEME_COLOR);
		save.setBackground(THEME_COLOR);
		

	}

	public Center(ArrayList<Member> mems, ArrayList<Integer> rels, ArrayList<Member[]> lins) {
		// setting up the frame/general GUI
		s.members = mems;
		s.Relations = rels;
		s.lines = lins;
		f.addMouseWheelListener(this);
		s.addMouseListener(this);
		s.addMouseMotionListener(this);
		f.setVisible(true);
		menu.setLayout(new GridLayout(3, 1));
		menu.add(addMem);
		f.setSize(500, 500);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		searchBar.addActionListener(this);
		menu.add(searchBar);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setLayout(new BorderLayout());
		f.add(s, BorderLayout.CENTER);
		f.add(menu, BorderLayout.EAST);
		addMem.setMargin(new Insets(0, 0, 0, 0));
		addMem.setBorder(null);
		addMem.setBackground(null);
		startCalc.setMargin(new Insets(0, 0, 0, 0));
		startCalc.setBorder(null);
		startCalc.setBorder(null);
		startCalc.addActionListener(this);
		menu.add(startCalc);
		try {
			Image img1 = ImageIO.read(new File("addMember.png"));
			addMem.setIcon(new ImageIcon(img1));
			Image img2 = ImageIO.read(new File("startCalculation.png"));
			startCalc.setIcon(new ImageIcon(img2));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		addMem.addActionListener(this);

	}
	/*
	 * public static void main(String[] args) { new Center();
	 * 
	 * }
	 */

	public void mouseClicked(MouseEvent e) {

	}

	public Member getClicked(int x, int y) {
		// Gets the member at coordinates x,y, if none returns null
		for (int i = 0; i < s.members.size(); i++) {
			int XX = s.members.get(i).X;
			int YY = s.members.get(i).Y;
			if ((x > XX && x < XX + (int) Math.round(s.zoom * Member.IMAGE_SIZE))
					&& (y > YY && y < YY + (int) Math.round(s.zoom * Member.IMAGE_SIZE))) {
				return s.members.get(i);
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
		// all the possible ways mouse could be pressed, also handles menu
		// interaction
		int x = e.getX();
		int y = e.getY();
		if (e.getClickCount() < 2) {
			// if left click and no menu
			if (e.getButton() == MouseEvent.BUTTON1 && movable) {
				if (m == null) {
					m = getClicked(x, y);

				} else {
					f.repaint();
					m = null;
				}
				// If you press the right button and no menu
			} else if (e.getButton() == MouseEvent.BUTTON3 && movable) {
				m = getClicked(x, y);
				if (m != null) {
					movable = false;
					showing = new Menu(x, y, m);
					s.menu = showing;
					f.repaint();
				} else {
					if (isNode(x, y)) {
						// if a node was clicked
						s.relmenu = new RelationMenu(x, y, s.Relations.get(findIndexOfMidpoint(s.lines, x, y)));
						movable = false;
						PP = new Point(x, y);
						f.repaint();

					}
				}
				// if left click and menu is popped up
			} else if (e.getButton() == MouseEvent.BUTTON1 && !movable) {
				// menu about a member is popped up
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
					// menu about relations is popped up
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
						f.repaint();
						strt = null;
					} else {
						strt = getClicked(x, y);
					}
				}

			}
		} else {
			// make a line in between
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (getClicked(x, y) != null && (!getClicked(x, y).equals(parents[0]))
						&& (!getClicked(x, y).equals(parents[1]))) {
					if (parents[0] == null) {
						parents[0] = getClicked(x, y);
					} else if (parents[1] == null) {
						parents[1] = getClicked(x, y);
					} else {
						parents[(int) Math.round(Math.random())] = getClicked(x, y);
					}
					s.Parents[0] = parents[0];
					s.Parents[1] = parents[1];
					f.repaint();
				}
			}
		}
	}

	public int findIndexOfMidpoint(ArrayList<Member[]> lines, int x, int y) {
		for (int i = 0; i < lines.size(); i++) {
			Point pp = getMidpoint(lines.get(i)[0].X + (int) Math.round(s.zoom * Member.IMAGE_SIZE / 2),
					lines.get(i)[0].Y + (int) Math.round(s.zoom * Member.IMAGE_SIZE / 2),
					lines.get(i)[1].X + (int) Math.round(s.zoom * Member.IMAGE_SIZE / 2),
					lines.get(i)[1].Y + (int) Math.round(s.zoom * Member.IMAGE_SIZE / 2));
			if (pp.distance(new Point(x, y)) < s.zoom * Surface.CIRCLE_DIAMETER) {
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
					s.lines.get(i)[0].Y + (int) Math.round(s.zoom * Member.IMAGE_SIZE / 2),
					s.lines.get(i)[1].X + Member.IMAGE_SIZE / 2,
					s.lines.get(i)[1].Y + (int) Math.round(s.zoom * Member.IMAGE_SIZE / 2));

			if (Point.distance(p.x, p.y, x, y) < (int) Math.round(s.zoom * Surface.CIRCLE_DIAMETER)) {
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
		if (e.getSource().equals(addMem)) {
			Member mem = new Member(Member.MALE, false, 70, 0);
			s.members.add(mem);
			f.repaint();
		} else if (e.getSource().equals(startCalc)) {
			String familyTree = buildStringFromTree();
			if (familyTree != null) {
				try {
					familyTree = familyTree.substring(0,familyTree.length() - 2);
					PrintWriter write = new PrintWriter(DATA_OUTPUT_FILE);
					write.print(familyTree);
					write.close();
					/*
					 * Process p; try { p = Runtime.getRuntime().
					 * exec("\"c:/program files/PedigreeAnalysis.exe\""); try {
					 * p.waitFor(); Scanner fScan = new Scanner(new
					 * File(DATA_OUTPUT_FILE)); 
					 * String perc = fScan.nextLine();
					 * searchBar.setText("The likelihood is " + perc);
					 * fScan.close(); } catch (InterruptedException e1) {
					 * e1.printStackTrace(); } } catch (IOException e1) {
					 * e1.printStackTrace(); }
					 */
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}

			} else {
				return;
			}
		} else if (e.getSource().equals(searchBar)) {
			try {
				String str = traitSearch.searchFile(traitSearch.TRAITS_FILE, searchBar.getText());
				if (str != null) {
					searchBar.setText(str);
				} else {
					searchBar.setText("No record of trait, or it was spelled incorrectly");
					f.setSize(f.getSize().width + 1, f.getSize().height);
					f.setSize(f.getSize().width - 1, f.getSize().height);
				}
			} catch (FileNotFoundException e1) {

				e1.printStackTrace();
			}
		} else if (e.getSource().equals(save)) {
			String name = JOptionPane.showInputDialog(f, "Input File Name", "ex. tree");
			if (name != null) {
				try {
					save.setText("Saving...");
					String output = buildStringFromTree();
					if (output != null) {
						output = output.substring(0, output.length() - 2);
						PrintWriter write = new PrintWriter(new File(name + ".txt"));
						write.print(output);
						write.close();
					}
					save.setText("Save");
				} catch (FileNotFoundException e1) {

					e1.printStackTrace();
				}
			}else{
				JOptionPane.showMessageDialog(f,"Please enter a name");
			}

		}

	}

	public String buildStringFromTree() {
		if (s.members.size() < 2 || s.lines.size() < 1) {
			JOptionPane.showMessageDialog(f, "Please add at least two members with at least one connection");
			return null;
		}
		String ret = "";
		ret += s.members.size() + "\r\n";
		// ret+=getAllInts(searchBar.getText())+"\r\n";
		// good^^
		for (int i = 0; i < s.lines.size(); i++) {
			if (s.Relations.get(i) == RelationMenu.MARRIED) {
				s.lines.get(i)[0].Married.add(s.lines.get(i)[1]);
				s.lines.get(i)[1].Married.add(s.lines.get(i)[0]);
			} else {
				Member P1 = s.lines.get(i)[0];
				Member P2 = s.lines.get(i)[1];
				if (P1.Y > P2.Y) {
					P1.Parents.add(P2);
					P2.Children.add(P1);
				} else {
					P2.Parents.add(P1);
					P1.Children.add(P2);
				}
			}
		}
		setColumns();
		for (int i = 0; i < s.members.size(); i++) {
			ret += s.members.get(i).Carrier ? "1\r\n" : "0\r\n";
			if (s.Parents[0] == null || s.Parents[1] == null) {
				JOptionPane.showMessageDialog(f, "Please select two parents by double clicking");
				return null;
			}
			if (s.Parents[0].equals(s.members.get(i)) || s.Parents[1].equals(s.members.get(i))) {
				ret += "1\r\n";
			} else {
				ret += "0\r\n";
			}
			if (s.members.get(i).Parents.size() >= 2) {
				ret += "1\r\n";
			} else {
				ret += "0\r\n";
			}
			ret += getRow(s.members.get(i)) + "\r\n";
			ret += s.members.get(i).column + "\r\n";
			for (int j = 0; j < s.members.get(i).Parents.size(); j++) {
				ret += getRow(s.members.get(i).Parents.get(j)) + "\r\n";
				ret += s.members.get(i).Parents.get(j).column + "\r\n";
			}

		}
		return ret;
	}

	private String getAllInts(String text) {
		String ret = "";
		for (int i = 0; i < text.length(); i++) {
			if (Character.isDigit(text.charAt(i))) {
				ret += text.charAt(i);
			}
		}
		return ret;
	}

	public String getRow(Member mm) {
		int ret = mm.relations(0, 0, s.members.size());

		return Integer.toString(ret);
	}

	public void setColumns() {
		ArrayList<ArrayList<Member>> generations = new ArrayList<ArrayList<Member>>();
		int totalGenerations = getYoungest(s.members).countAncestors(0);
		for (int i = 0; i < totalGenerations; i++) {
			generations.add(new ArrayList<Member>());
		}
		for (int i = 0; i < s.members.size(); i++) {
			int b = s.members.get(i).relations(0, 0, s.members.size());
			generations.get(b).add(s.members.get(i));
		}
		for (int i = 0; i < generations.size(); i++) {
			for (int j = 0; j < generations.get(i).size(); j++) {
				generations.get(i).get(j).column = j;
			}

		}

	}

	private Member getYoungest(ArrayList<Member> mm) {
		Member ret = null;
		int greatestGen = Integer.MIN_VALUE;
		for (int i = 0; i < mm.size(); i++) {
			int a;
			if (greatestGen < (a = mm.get(i).countAncestors(0))) {
				greatestGen = a;
				ret = mm.get(i);
			}
		}
		return ret;
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (m != null && movable) {
			m.X = e.getX() - (int) Math.round(s.zoom * Member.IMAGE_SIZE / 2);
			m.Y = e.getY() - (int) Math.round(s.zoom * Member.IMAGE_SIZE / 2);
			f.repaint();
		}

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int rolls = e.getWheelRotation();
		double zoomPrevious = s.zoom;
		double zoomSpeed = .05;
		double moveSpeed = 7;
		int xx = e.getX();
		int yy = e.getY();
		if (!e.isControlDown() && !e.isShiftDown()) {
			if (rolls > 0) {

				// Rolled towards user
				if ((s.zoom - zoomSpeed * rolls) >= .5) {
					s.zoom -= zoomSpeed * rolls;
				} else {

					s.zoom = .5;
				}
			} else if (rolls < 0) {
				// Rolled Away
				if ((s.zoom + zoomSpeed * rolls) <= 5) {
					s.zoom -= zoomSpeed * rolls;
				} else {
					s.zoom = 5;
				}

			}
			if (zoomPrevious != s.zoom) {
				for (int i = 0; i < s.members.size(); i++) {
					Member m = s.members.get(i);
					m.X += (int) Math.round(
							(-rolls * zoomSpeed) * ((m.X + (int) Math.round(s.zoom * Member.IMAGE_SIZE / 2)) - xx));
					m.Y += (int) Math.round(
							(-rolls * zoomSpeed) * ((m.Y + (int) Math.round(s.zoom * Member.IMAGE_SIZE / 2)) - yy));
				}
			}
		} else if (e.isControlDown()) {
			for (int i = 0; i < s.members.size(); i++) {
				int xPrev = s.members.get(i).X;
				s.members.get(i).X += rolls *.5*(int)Math.round(s.zoom*Member.IMAGE_SIZE);
				if(s.members.get(i).X<0&&xPrev>=0){
					s.members.get(i).X-=(int)Math.round(s.zoom*Member.IMAGE_SIZE);
				}
			}
		} else if (e.isShiftDown()) {
			for (int i = 0; i < s.members.size(); i++) {
				int yPrev = s.members.get(i).Y;
				s.members.get(i).Y += rolls * .5*(int)Math.round(s.zoom*Member.IMAGE_SIZE);
				if(s.members.get(i).Y<0&&yPrev>=0){
					s.members.get(i).Y-=(int)Math.round(s.zoom*Member.IMAGE_SIZE);
				}
			}
		}
		f.repaint();

	}

	
}
