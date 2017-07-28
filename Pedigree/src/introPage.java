import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class introPage implements ActionListener{
JFrame f = new JFrame("Pedigree");
JButton newTree = new JButton("New Tree");
JButton loadTree = new JButton("Load Tree");
Container menu = new Container();
JLabel header = new JLabel("<html><span style='font-size:50px'>"+"Pedigree"+"</span></html>", SwingConstants.CENTER);
	public introPage() {
		f.setSize(500, 500);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setLayout(new GridLayout(2,1));
		header.setBackground(Color.GREEN);
		header.setOpaque(true);
		menu.setLayout(new GridLayout());
		menu.add(loadTree);
		menu.add(newTree);
		newTree.addActionListener(this);
		loadTree.addActionListener(this);
		f.add(header);
		f.add(menu);
	}

	public static void main(String[] args) {
		new introPage();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(newTree)){
			new Center();
		}else{
			String data = "";
			JFileChooser choose = new JFileChooser();
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
			if(isLegalFormat(data)){
			ArrayList<Member> mm= getMembers(data);
			new Center(mm,getRels(mm), getLines(mm) );
			}else{
				JOptionPane.showMessageDialog(f, "Not the correct file format");
			}
		}
		
	}
	public boolean isLegalFormat(String data) {
		ArrayList<String> lines = new ArrayList<String>();
		ArrayList<ArrayList<String>> memStrings = new ArrayList<ArrayList<String>>();
		memStrings.add(new ArrayList<String>());
		String curLine = "";
		for (int i = 0; i < data.length(); i++) {
			if(data.charAt(i)!='\n'){
				curLine+=data.charAt(i);
			}else{
				lines.add(curLine);
				curLine = "";
			}
		}
		lines.remove(0);
		int prevlineNum = 0;
		int numJumps=9;
		for (int i = 0; i <lines.size(); i++) {
		if(i-prevlineNum==2){
			if (!(Integer.parseInt(lines.get(i))==0||Integer.parseInt(lines.get(i))==1)){
			return false;
			}
			numJumps=Integer.parseInt(lines.get(i))==0?5:9;
		}
			if(i-prevlineNum==numJumps){
			memStrings.get(memStrings.size()-1).add(lines.get(i));
			memStrings.add(new ArrayList<String>());
		}else{
			memStrings.get(memStrings.size()-1).add(lines.get(i));
		}
		}
		for (int i = 0; i < memStrings.size(); i++) {
			int[] vals = new int[memStrings.get(i).size()];
			for (int j = 0; j < memStrings.get(i).size(); j++) {
				if(isInteger(memStrings.get(i).get(j))){
				vals[j] = Integer.parseInt(memStrings.get(i).get(j));
				System.out.println(vals[j]);
				}else{
					return false;
				}
			}
			if((vals[0]==1||vals[0]==0)&&
			(vals[1]==1||vals[1]==0)&&
			(vals[2]==1||vals[2]==0)&&
			(vals[3]>=0)&&(vals[4]>=0)){
				
				//you're almost definitely good
			}else{
				return false;
			}
		}
		return true;
	}

	public boolean isInteger(String s) {
		for (int i = 0; i < s.length(); i++) {
			if(!Character.isDigit(s.charAt(i))){
				return false;
			}
		}
		return true;
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
	for (int j = 0; j < mm.get(i).Children.size(); j++) {
		Member[] temp = {mm.get(i),mm.get(i).Children.get(j)};
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

	public ArrayList<Member> getMembers(String data){
		ArrayList<Member> ret = new ArrayList<Member>();
		ArrayList<String> lines = new ArrayList<String>();
		ArrayList<ArrayList<String>> memStrings = new ArrayList<ArrayList<String>>();
		memStrings.add(new ArrayList<String>());
		String curLine = "";
		for (int i = 0; i < data.length(); i++) {
			if(data.charAt(i)!='\n'){
				curLine+=data.charAt(i);
			}else{
				lines.add(curLine);
				curLine = "";
			}
		}
		lines.remove(0);
		int prevlineNum = 0;
		int numJumps=9;
		for (int i = 0; i <lines.size(); i++) {
			if(i-prevlineNum==2){
				numJumps=Integer.parseInt(lines.get(i))==0?5:9;
			}
				if(i-prevlineNum==numJumps){
			memStrings.get(memStrings.size()-1).add(lines.get(i));
			memStrings.add(new ArrayList<String>());
		}else{
			memStrings.get(memStrings.size()-1).add(lines.get(i));
		}
		}
		for (int i = 0; i < memStrings.size(); i++) {
			Member toAdd = new Member(0,false,0,0);
			toAdd.Carrier = (memStrings.get(i).get(0).equals(1)?true:false);
			toAdd.row = Integer.parseInt(memStrings.get(i).get(3));
			toAdd.column = Integer.parseInt(memStrings.get(i).get(4));
			ret.add(toAdd);
		}
		for (int i = 0; i < ret.size(); i++) {
			if(memStrings.get(i).size()>5){
			int p1Row = Integer.parseInt(memStrings.get(i).get(5));
			int p1Col = Integer.parseInt(memStrings.get(i).get(6));
			int p2Row = Integer.parseInt(memStrings.get(i).get(7));
			int p2Col = Integer.parseInt(memStrings.get(i).get(8));
		ret.get(i).Parents.add(getParentByRowColumn(ret,p1Row,p1Col));
		ret.get(i).Parents.add(getParentByRowColumn(ret,p2Row,p2Col));
		for (int j = 0; j < ret.get(i).Parents.size(); j++) {
			ret.get(i).Parents.get(j).Children.add(ret.get(i));
			ArrayList<Member> temp = ret.get(i).Parents;
			temp.remove(ret.get(i).Parents.get(j));
			ret.get(i).Parents.get(j).Married.addAll(temp);
		}
			}
		ret.get(i).X = (int) (ret.get(i).column*Toolkit.getDefaultToolkit().getScreenSize().getWidth()/inMyGen(ret,ret.get(i).row));
		ret.get(i).Y = (int)((int)ret.get(i).row*Toolkit.getDefaultToolkit().getScreenSize().getWidth()/totalGens(ret));
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

	public Member getParentByRowColumn(ArrayList<Member> members,int row, int column){
		for (int i = 0; i < members.size(); i++) {
			if(members.get(i).column==column&&members.get(i).row==row){
				return members.get(i);
			}
		}
		return null;
	}

}
