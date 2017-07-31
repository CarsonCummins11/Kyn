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
		for (int i = 0; i < data.length(); i++) {
			if(!Character.isDigit(data.charAt(i))&&data.charAt(i)!='\n'){
				return false;
			}
		
		}
		ArrayList<Integer> lines = intListToArrayList(data);
		lines.remove(0);
		int startIndex = 0;
		while(true){
			if(lines.get(startIndex+2)>1||lines.get(startIndex+1)>1||lines.get(startIndex)>1){
				System.out.println(startIndex);
				return false;
			}
			startIndex+=lines.get(startIndex+2)==1?9:5;
			if(startIndex>=lines.size()){
				break;
			}
		}
		/*int linesInCurrentMember = 8;
		int memberStartingIndex = 0;
		for (int i = 0; i < lines.size(); i++) {
			if(i==0){
				linesInCurrentMember = lines.get(i+2)==1?8:4;
				if(lines.get(i)>1){
					return false;
				}
			}
			if(i-memberStartingIndex>=linesInCurrentMember){
				memberStartingIndex=i;
				linesInCurrentMember = lines.get(i+2)==1?8:4;
				if(lines.get(i)>1){
					return false;
				}
				}else if(i-memberStartingIndex<=2){
					if(lines.get(i)>1){
						System.out.println(i);
						return false;
					}
				}
			}
			*/
		
		return true;
		
	}

//Good
public ArrayList<Integer> intListToArrayList(String data){
	ArrayList<Integer> lines = new ArrayList<Integer>();
	String cur = "";
	for (int i = 0; i < data.length(); i++) {
		if(data.charAt(i)!='\n'){
			cur+=data.charAt(i);
		}else{
			lines.add(Integer.parseInt(cur));
			cur = "";
		}
	}
	return lines;
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
		System.out.println("yo");
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

}
