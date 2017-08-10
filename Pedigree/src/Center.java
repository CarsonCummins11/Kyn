import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.util.Collections;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Center implements MouseListener, ActionListener, MouseMotionListener, MouseWheelListener {
	// Defines all the wide scoped variables in the class
	JFrame f = new JFrame("Tree Builder");
	Member m = null;
	Button addMem = new Button("Add");
	Container menu = new Container();
	Surface s = new Surface();
	boolean movable = true;
	Menu showing;
	Member strt = null;
	Point PP;
	Button save = new Button("Save");
	Button startCalc = new Button("Calculate");
	Member[] parents = new Member[2];
	JTextField searchBar = new JTextField("Search for Traits		");
	JTextArea outputBox = new JTextArea("Your results will appear here");
	final static Color THEME_COLOR = new Color(103,173,110);
	public static final String DATA_OUTPUT_FILE = "Chances.txt";
	public static final String DATA_INPUT_FILE = "Ancestors.tree";
	public static boolean IEEAV = true;
	JMenuBar bar = new JMenuBar();
	JMenu fileActions = new JMenu("File");
	JMenu editActions = new JMenu("Edit");
	JMenu calcActions = new JMenu("Calculate");
	JMenuItem calcSingleBranch = new JMenuItem("Single Branch");
	JMenuItem calcMultBranch = new JMenuItem("Multiple Branches");
	JMenuItem addMemberAction = new JMenuItem("Add Member");
	JMenuItem saveFile = new JMenuItem("Save");
	JMenuItem LoadFile = new JMenuItem("Load");
	JMenuItem searchTraits = new JMenuItem("Search Traits");
	public Center() {
		calcActions.add(calcMultBranch);
		calcActions.add(calcSingleBranch);
		editActions.add(addMemberAction);
		editActions.add(searchTraits);
		fileActions.add(saveFile);
		fileActions.add(LoadFile);
		LoadFile.addActionListener(this);
		calcMultBranch.addActionListener(this);
		calcSingleBranch.addActionListener(this);
		addMemberAction.addActionListener(this);
		searchTraits.addActionListener(this);
		saveFile.addActionListener(this);
		bar.add(editActions);
		bar.add(calcActions);
		bar.add(fileActions);
		f.setJMenuBar(bar);
		// setting up the frame/general GUI
		//save.setDescription("Save this\ntree");
		//startCalc.setDescription("Calculate\nthe likelihood\nof the trait");
		//addMem.setDescription("Add a new\nmember to\nthe tree");
		try {
			f.setIconImage(ImageIO.read(new File("Logo_Square.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		f.addMouseWheelListener(this);
		s.addMouseListener(this);
		s.addMouseMotionListener(this);
		//menu.setLayout(new GridLayout(5, 1));
		//menu.add(startCalc);
		//menu.add(save);
		//save.addActionListener(this);
		//menu.add(addMem);
		//searchBar.addActionListener(this);
		//menu.add(searchBar);
		//menu.add(outputBox);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setLayout(new BorderLayout());
		f.add(s, BorderLayout.CENTER);
		//f.add(menu, BorderLayout.EAST);
		f.setSize(500, 500);
		f.setVisible(true);
		//startCalc.addActionListener(this);
		/*addMem.addActionListener(this);
		addMem.setBackground(THEME_COLOR);
		addMem.setForeground(Color.black);
		startCalc.setForeground(Color.black);
		save.setForeground(Color.black);
		startCalc.setBackground(THEME_COLOR);
		save.setBackground(THEME_COLOR);
		*/
		

	}

	public Center(ArrayList<Member> mems, ArrayList<Integer> rels, ArrayList<Member[]> lins) {
		// setting up the frame/general GUI
		s.members = mems;
		s.Relations = rels;
		s.lines = lins;
		calcActions.add(calcMultBranch);
		calcActions.add(calcSingleBranch);
		editActions.add(addMemberAction);
		fileActions.add(saveFile);
		calcMultBranch.addActionListener(this);
		calcSingleBranch.addActionListener(this);
		addMemberAction.addActionListener(this);
		saveFile.addActionListener(this);
		bar.add(editActions);
		bar.add(calcActions);
		bar.add(fileActions);
		f.setJMenuBar(bar);
		// setting up the frame/general GUI
		//save.setDescription("Save this\ntree");
		//startCalc.setDescription("Calculate\nthe likelihood\nof the trait");
		//addMem.setDescription("Add a new\nmember to\nthe tree");
		try {
			f.setIconImage(ImageIO.read(new File("Logo_Square.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		f.addMouseWheelListener(this);
		s.addMouseListener(this);
		s.addMouseMotionListener(this);
		//menu.setLayout(new GridLayout(5, 1));
		//menu.add(startCalc);
		//menu.add(save);
		//save.addActionListener(this);
		//menu.add(addMem);
		//searchBar.addActionListener(this);
		//menu.add(searchBar);
		//menu.add(outputBox);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setLayout(new BorderLayout());
		f.add(s, BorderLayout.CENTER);
		//f.add(menu, BorderLayout.EAST);
		f.setSize(500, 500);
		f.setVisible(true);
		//startCalc.addActionListener(this);
		/*addMem.addActionListener(this);
		addMem.setBackground(THEME_COLOR);
		addMem.setForeground(Color.black);
		startCalc.setForeground(Color.black);
		save.setForeground(Color.black);
		startCalc.setBackground(THEME_COLOR);
		save.setBackground(THEME_COLOR);
		*/
		

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
			if (e.getButton() == MouseEvent.BUTTON1 && movable &&!e.isAltDown()) {
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
			} else if (e.getButton() == MouseEvent.BUTTON1 && !movable && !e.isAltDown()) {
				if (s.relmenu == null) {
					int clicked = getClickedOption(x, y);
					if (clicked == 6) {
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
						case 5:
							s.members.remove(m);
							for (int i = 0; i < s.lines.size(); i++) {
								if(s.lines.get(i)[0].equals(m)||s.lines.get(i)[1].equals(m)){
									s.lines.remove(i);
									s.Relations.remove(i);
								}
							}
							movable = true;
							s.menu = null;
							break;
						}
						if(s.menu!=null){
						s.menu.linked = m;
						}

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
						s.Relations.remove(findIndexOfMidpoint(s.lines,PP.x,PP.y));
						s.lines.remove(findIndexOfMidpoint(s.lines,PP.x,PP.y));
						s.relmenu = null;
						movable = true;
						f.repaint();
						PP = new Point();
						break;
					case 4:
						s.relmenu = null;
						movable = true;
						f.repaint();
						PP = new Point();
						break;
					}
					if (clicked !=4 &&clicked!=3) {
						s.Relations.set(findIndexOfMidpoint(s.lines, PP.x, PP.y), s.relmenu.Status);
						f.repaint();

					}
				}
				// If there you click on the second button, the middle button
			} else if (e.getButton() == MouseEvent.BUTTON2||(e.getButton()== MouseEvent.BUTTON1&&e.isAltDown())) {
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
			if (e.getButton() == MouseEvent.BUTTON1&&!e.isAltDown()) {
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
				if (offset > 3) {
					return 3;
				} else if (offset <= 0) {
					return 1;
				} else {
					return offset;
				}
			} else {
				return 4;
			}
		} else {
			return 4;
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
				if (offset > 5) {
					return 5;
				} else if (offset <= 0) {
					return 1;
				} else {
					return offset;
				}
			} else {
				return 6;
			}
		} else {
			return 6;
		}
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(addMem)||e.getSource().equals(addMemberAction)) {
			Member mem = new Member(Member.MALE, false, 70, 0);
			s.members.add(mem);
			f.repaint();
		} else if (e.getSource().equals(startCalc)||e.getSource().equals(calcMultBranch)||e.getSource().equals(calcSingleBranch)) {
			if(e.getSource().equals(calcMultBranch)||IEEAV){
			int[] xs = {s.Parents[0].X,s.Parents[1].X};
			int[] ys = {s.Parents[0].Y,s.Parents[1].Y};
			double[] output =geneCalculator.calculateLikelihoods(s.lines, s.Relations, xs , ys);
			  JOptionPane.showMessageDialog(f,"The likelihood of not showing the trait is %" + Double.toString(100*output[0]));
			  JOptionPane.showMessageDialog(f, "The likelihood of carrying the trait without showing it is %"+ Double.toString(100*output[1]));
			  JOptionPane.showMessageDialog(f,"The likelihood of showing the trait is %"+Double.toString(100*output[2]));
			
			}else if(e.getSource().equals(calcSingleBranch)){
			String familyTree = buildStringFromTree();
			if (familyTree != null) {
				try {					
					PrintWriter write = new PrintWriter(DATA_INPUT_FILE);
					write.print(familyTree);
					write.close();
					 Process p; 
					 try { 
					p = Runtime.getRuntime().exec("PedigreeAnalysis.exe"); 
					try {
					  p.waitFor(); 
					  Scanner fScan = new Scanner(new File(DATA_OUTPUT_FILE)); 
					 String one = fScan.nextLine();
					 String two = fScan.nextLine();
					 String three = fScan.nextLine();
					 if(getAllInts(one).length()>0&&getAllInts(two).length()>0&&getAllInts(three).length()>0){
					  JOptionPane.showMessageDialog(f,"The likelihood of not showing the trait is %" + 100*Double.parseDouble(one) );
					  JOptionPane.showMessageDialog(f, "The likelihood of carrying without showing the trait is %"+ 100*Double.parseDouble(two));
					  JOptionPane.showMessageDialog(f,"The likelihood of showing the trait is %"+100*Double.parseDouble(three) );
						 //outputBox.setText("The likelihood of not having the trait is " + Double.parseDouble(one)+"\r\n");
					  //outputBox.setText(outputBox.getText()+"The likelihood of carrying the trait is"+ Double.parseDouble(two)+"\r\n");
					  //outputBox.setText(outputBox.getText()+"The likelihood of showing the trait is"+Double.parseDouble(three));
					 }else{
						 JOptionPane.showMessageDialog(f,"No record of trait, or it was spelled incorrectly");
					 }
					 fScan.close(); 
					 } catch (InterruptedException e1) {
					  e1.printStackTrace(); 
					  } 
					} catch (IOException e1) {
					  e1.printStackTrace(); 
					  }
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			} else {
				return;
			}
			}
		
		} else if (e.getSource().equals(searchBar)) {
			try {
				String str = traitSearch.searchFile(traitSearch.TRAITS_FILE, searchBar.getText());
				if (str != null) {
					searchBar.setText(str);
				} else {
					JOptionPane.showMessageDialog(f,"No record of trait, or it was spelled incorrectly");
					
				}
			} catch (FileNotFoundException e1) {

				e1.printStackTrace();
			}
		}else if(e.getSource().equals(searchTraits)){
			String term = JOptionPane.showInputDialog(f,"Search for traits", "ex. Red Hair");
			try {
				String str = traitSearch.searchFile(traitSearch.TRAITS_FILE, term);
				if(str!=null){
					f.setTitle("Tree Builder For "+str);
				}else{
					f.setTitle("Tree Builder"+"Gene not found");
				}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if (e.getSource().equals(save)||e.getSource().equals(saveFile)) {
			//String name = JOptionPane.showInputDialog(f, "Input File Name", "ex. tree");
			JFileChooser choose = new JFileChooser();
			String name = null;
			int retVal = choose.showOpenDialog(f);
			if(retVal == JFileChooser.APPROVE_OPTION){
			name = choose.getSelectedFile().getAbsolutePath();
			}
			if (name != null) {
				try {
					save.setText("Saving...");
					String output = buildStringFromTree();
					if (output != null) {
						PrintWriter write = new PrintWriter(new File(name + ".tree"));
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

		}else if(e.getSource().equals(LoadFile)){
			String data = "";
			JFileChooser choose = new JFileChooser();
			choose.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Tree Files (.tree)", "tree");
			choose.setFileFilter(filter);
			int retVal = choose.showOpenDialog(f);
			if(retVal == JFileChooser.APPROVE_OPTION){
				File ff = choose.getSelectedFile();
				try {
					Scanner fRead = new Scanner(ff);
					while(fRead.hasNextLine()){
						data+=fRead.nextLine()+"\n";
					}
					fRead.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}else{
				return;
			}
			if(introPage.isLegalFormat(data)){
			ArrayList<Member> mm= getMembers(data);
			new Center(mm,getRels(mm), getLines(mm) );
			}else{
				JOptionPane.showMessageDialog(f, "Not the correct file format");
			}
		}

	}
	public ArrayList<Member[]> getLines(ArrayList<Member> mm) {
		ArrayList<Member[]> ret = new ArrayList<Member[]>();
		for (int i = 0; i < mm.size(); i++) {
	for (int j = 0; j < mm.get(i).Parents.size(); j++) {
		Member[] temp = {mm.get(i),mm.get(i).Parents.get(j)};
		ret.add(temp);
	}
	for (int j = 0; j < mm.get(i).Married.size(); j++) {
		Member[] temp = {mm.get(i),mm.get(i).Married.get(j)};
		ret.add(temp);
	}
}
		return ret;
	}

	public ArrayList<Integer> getRels(ArrayList<Member> mm) {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for (int i = 0; i < mm.size(); i++) {
	for (int j = 0; j < mm.get(i).Parents.size(); j++) {
		ret.add(RelationMenu.DESCENDANT);
	}
	for (int j = 0; j < mm.get(i).Married.size(); j++) {
		ret.add(RelationMenu.MARRIED);
	}
	
}
		return ret;
	}
	public  int inMyGen(ArrayList<Member> mems, int row) {
		int ret = 0;
			for (int i = 0; i < mems.size(); i++) {
			if(mems.get(i).row==row){
				ret++;
			}
		}
			return ret;
		}

		public int totalGens(ArrayList<Member> mems) {
			ArrayList<Integer> used = new ArrayList<Integer>();
			used.add(0);
			for (int i = 0; i < mems.size(); i++) {
				if(!used.contains(mems.get(i).row)){
					used.add(mems.get(i).row);
				}
			}
			return used.size();
		}

		public Member getMemberByRowColumn(ArrayList<Member> members,int row, int column){
			for (int i = 0; i < members.size(); i++) {
				if(members.get(i).column==column&&members.get(i).row==row){
					return members.get(i);
				}
			}
			return null;
		}
		public static ArrayList<Integer> intListToArrayList(String data){
			ArrayList<Integer> lines = new ArrayList<Integer>();
			String cur = "";
			for (int i = 0; i < data.length(); i++) {
				if(data.charAt(i)!='\n'&&data.charAt(i)!='\r'){
					cur+=data.charAt(i);
				}else if(!cur.equals("")){
					lines.add(Integer.parseInt(cur));
					cur = "";
				}
			}
			return lines;
		}
	public ArrayList<Member> getMembers(String data){
		ArrayList<Integer> lines = intListToArrayList(data);
		lines.remove(0);
		ArrayList<Member> ret = new ArrayList<Member>();
		int startIndex = 0;
		while(true){
		Member temp = new Member(0,false,0,0);
		temp.Carrier = lines.get(startIndex)==0?false:true;
		temp.row = lines.get(startIndex+3);
		temp.column = lines.get(startIndex+4);
		if(lines.get(startIndex+2)==1){
			Integer[] tempArray1 = {lines.get(startIndex+5),lines.get(startIndex+6)};
			temp.ParentCoordinates.add(tempArray1);
			Integer[] tempArray2 = {lines.get(startIndex+7),lines.get(startIndex+8)};
			temp.ParentCoordinates.add(tempArray2);
			
		}
			startIndex+=lines.get(startIndex+2)==1?9:5;
			ret.add(temp);
			if(startIndex>=lines.size()){
				break;
			}
		}
		for (int i = 0; i < ret.size(); i++) {
			ret.get(i).X = ret.get(i).column*(1000/inMyGen(ret,ret.get(i).row));
			ret.get(i).Y = ret.get(i).row*(1000/totalGens(ret));
			Member mm = ret.get(i);
			for (int j = 0; j < mm.ParentCoordinates.size(); j++) {
				mm.Parents.add(getMemberByRowColumn(ret,mm.ParentCoordinates.get(j)[0],mm.ParentCoordinates.get(j)[1]));
			}
		}
		for (int i = 0; i < ret.size(); i++) {
			Member mm = ret.get(i);
			for (int j = 0; j < mm.Parents.size(); j++) {
				mm.Parents.get(j).Children.add(mm);
				for (int k = 0; k < mm.Parents.size(); k++) {
					if(!mm.Parents.get(j).equals(mm.Parents.get(k))){
						mm.Parents.get(j).Married.add(mm.Parents.get(k));
					}
				}
			}
		}
		return ret;
		
	}
	public String buildStringFromTree() {
		if (s.members.size() < 2 || s.lines.size() < 1) {
			JOptionPane.showMessageDialog(f, "Please add at least two members with at least one connection");
			return null;
		}
		String ret = "";
		//ret+=getAllInts(searchBar.getText())+"\r\n";
		while(!introPage.isLegalFormat(ret)){
			Collections.shuffle(s.members);
			ret="";
			ret += s.members.size() + "\r\n";
		for (int i = 0; i < s.lines.size(); i++) {
			if (s.Relations.get(i) == RelationMenu.MARRIED) {
				if(!s.lines.get(i)[0].Married.contains(s.lines.get(i)[1])){
				s.lines.get(i)[0].Married.add(s.lines.get(i)[1]);
				}
				if(!s.lines.get(i)[1].Married.contains(s.lines.get(i)[0])){
				s.lines.get(i)[1].Married.add(s.lines.get(i)[0]);
				}
			} else {
				Member P1 = s.lines.get(i)[0];
				Member P2 = s.lines.get(i)[1];
				if (P1.Y > P2.Y) {
					if(!P1.Parents.contains(P2)){
					P1.Parents.add(P2);
					}
					if(!P2.Children.contains(P1)){
					P2.Children.add(P1);
					}
				} else {
					if(!P2.Parents.contains(P1)){
					P2.Parents.add(P1);
					}
					if(!P1.Children.contains(P2)){
					P1.Children.add(P2);
					}
				}
			}
		}
		setColumns();
		if (s.Parents[0] == null || s.Parents[1] == null) {
			JOptionPane.showMessageDialog(f, "Please select two parents by double clicking");
			return null;
		}
		for (int i = 0; i < s.members.size(); i++) {
			ret += s.members.get(i).Carrier ? "1\r\n" : "0\r\n";
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
		ret = ret.substring(0, ret.length()-2);
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
		int xx = s.getWidth()/2;
		int yy = s.getHeight()/2;
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
					m.X += (int) Math.round((-rolls * zoomSpeed) * ((m.X + (int) Math.round(s.zoom * Member.IMAGE_SIZE / 2)) - xx));
					m.Y += (int) Math.round((-rolls * zoomSpeed) * ((m.Y + (int) Math.round(s.zoom * Member.IMAGE_SIZE / 2)) - yy));
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
