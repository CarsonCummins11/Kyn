import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
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

	public Center() {
		s.addMouseListener(this);
		s.addMouseMotionListener(this);
		f.setVisible(true);
		menu.setLayout(new GridLayout(3, 1));
		menu.add(addMem);
		f.setSize(500, 500);
		menu.add(new JPanel());
		menu.add(new JPanel());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		f.add(s, BorderLayout.CENTER);
		f.add(menu, BorderLayout.EAST);
		addMem.setMargin(new Insets(0, 0, 0, 0));
		addMem.setBorder(null);
		addMem.setBackground(null);
		try {
			Image img = ImageIO.read(new File("addMember.png"));
			addMem.setIcon(new ImageIcon(img));
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
		System.out.println(x+","+y);
		for (int i = 0; i < Members.size(); i++) {
			int XX = s.members.get(i).X;
			int YY = s.members.get(i).Y;
			if ((x>XX&&x<XX+Member.IMAGE_SIZE)&&(y>YY&&y<YY+Member.IMAGE_SIZE)) {
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
		if (e.getButton() == MouseEvent.BUTTON1 && movable) {
			if (m == null) {
				m = getClicked(x, y);

			} else {
				m = null;
			}
			//If you press the middle button
		} else if (e.getButton() == MouseEvent.BUTTON3 && movable) {
			m = getClicked(x, y);
			if (m != null) {
				movable = false;
				showing = new Menu(x, y, m);
				s.menu = showing;
				f.repaint();
			} else {
				if (isNode(x, y)) {
					s.relmenu = new RelationMenu(x, y);
					movable = false;
					PP = new Point();
					PP.x = x;
					PP.y = y;
					f.repaint();
					System.out.println("hello");
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
					System.out.println(clicked);
					switch (clicked) {
					case 1:
						m.Gender = Member.MALE;
						break;
					case 2:
						m.Gender = Member.FEMALE;
						break;
					case 3:
						System.out.println("here");
						m.Carrier = true;
						break;
					case 4:
						System.out.println("yo");
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
					break;
				}
				if(clicked!=3){
				s.Relations.set(findIndexOfMidpoint(s.lines, PP.x, PP.y), s.relmenu.Status);
				f.repaint();
				}
			}
			//If there you click on the second button, the middle button
		} else if (e.getButton() == MouseEvent.BUTTON2) {
			//If the click is on an object
			if (getClicked(x, y) != null) {
				if (strt != null) {
					Member[] temp = { getClicked(x, y), strt };
					s.lines.add(temp);
					s.Relations.add(RelationMenu.MARRIED);
					//Redraw the frame
					f.repaint();
					strt = null;
				} else {
					strt = getClicked(x, y);
				}
			}

		}
	}

	public int findIndexOfMidpoint(ArrayList<Member[]> lines, int x, int y) {
		for (int i = 0; i < lines.size(); i++) {
			if (Point.distance(
					getMidpoint(lines.get(i)[0].X, lines.get(i)[0].Y, lines.get(i)[1].X, lines.get(i)[1].Y).x,
					getMidpoint(lines.get(i)[0].X, lines.get(i)[0].Y, lines.get(i)[1].X, lines.get(i)[1].Y).y, x,
					y) < Surface.CIRCLE_DIAMETER) {
				return i;
			}
		}
		return 0;
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
			Point p = getMidpoint(s.lines.get(i)[0].X+Member.IMAGE_SIZE/2, s.lines.get(i)[0].Y+Member.IMAGE_SIZE/2, s.lines.get(i)[1].X+Member.IMAGE_SIZE/2, s.lines.get(i)[1].Y+Member.IMAGE_SIZE/2);
			System.out.println(p.x+","+p.y);
			System.out.println(x+","+y);
			if (Point.distance(p.x, p.y, x, y) < Surface.CIRCLE_DIAMETER) {
				System.out.println("eyy");
				return true;
			}
		}
		System.out.println("yo");
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
		Member mem = new Member(Member.MALE, false, 0, 0);
		s.members.add(mem);
		Members.add(mem);
		f.repaint();

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
