import java.util.ArrayList;

public class geneCalculator {

	public static double[] calculateLikelihoods(ArrayList<Member[]> liness, ArrayList<Integer> Relations, int[] XofSelected, int[] YofSelected){
	ArrayList<Member[]> lines = liness;
	ArrayList<Member> members = new ArrayList<Member>();
		for (int i = 0; i < lines.size(); i++) {
		if (Relations.get(i) == RelationMenu.MARRIED) {
			if(!lines.get(i)[0].Married.contains(lines.get(i)[1])){
			lines.get(i)[0].Married.add(lines.get(i)[1]);
			}
			if(!lines.get(i)[1].Married.contains(lines.get(i)[0])){
			lines.get(i)[1].Married.add(lines.get(i)[0]);
			}
		} else {
			Member P1 = lines.get(i)[0];
			Member P2 = lines.get(i)[1];
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
		for (int i = 0; i < lines.size(); i++) {
			for (int j = 0; j < lines.get(i).length; j++) {
				if(!members.contains(lines.get(i)[j])){
					members.add(lines.get(i)[j]);
				}
			}
		}
		System.out.println(members.size());
		members = setColumns(members);
		for (int i = 0; i < members.size(); i++) {
			members.get(i).row = getRow(members.get(i),members.size());
		}
		for (int k = 0; k < members.size(); k++) {
		for (int i = 0; i < members.size(); i++) {
			for (int j = 0; j < members.get(i).Married.size(); j++) {
				members.get(i).affectedChance =  affectedChance(members.get(i).Married.get(j),members.get(i));
				members.get(i).notAffectedChance =  notAffectedChance(members.get(i).Married.get(j),members.get(i));
				members.get(i).carrierChance =  carrierChance(members.get(i).Married.get(j),members.get(i));
				
			}
		}
		}
		Member[] pars = new Member[XofSelected.length];
		for (int i = 0; i < XofSelected.length; i++) {
			int x = XofSelected[i];
			int y = YofSelected[i];
			for (int j = 0; j < members.size(); j++) {
				if(x==members.get(j).X&&y==members.get(j).Y){
					pars[i]=members.get(i);
				}
			}
		}
		double[] ret = {notAffectedChance(pars[0],pars[1]),carrierChance(pars[0],pars[1]),affectedChance(pars[0],pars[1])};
		return ret;
	}
	private static double carrierChance(Member m1, Member m2) {
		if(m1.Parents.size()>0&&m2.Parents.size()>0){
			double carrierChance = 1.0;
				carrierChance = carrierChance*m1.Parents.get(0).carrierChance;
				for (int j = 1; j < m1.Parents.size(); j++) {
				double carrierChance22 = (m1.Parents.get(j).carrierChance);
				carrierChance= carrierChance22*carrierChance;
				}
				carrierChance = carrierChance*m2.Parents.get(0).carrierChance;
				for (int j = 1; j < m2.Parents.size(); j++) {
				double carrierChance22 = (m2.Parents.get(j).carrierChance);
				carrierChance= carrierChance22*carrierChance;
				}
			return carrierChance;
		}else if(m1.Parents.size()>0){
			double carrierChance = 1.0;
			carrierChance = carrierChance*m1.Parents.get(0).carrierChance;
			for (int j = 1; j < m1.Parents.size(); j++) {
			double carrierChance22 = (m1.Parents.get(j).carrierChance);
			carrierChance= carrierChance22*carrierChance;
			}

			boolean carrierChild = false;
			for (int i = 0; i < m2.Children.size(); i++) {
				if(m2.Children.get(i).Carrier){
					m2.carrierChance=1;
					carrierChild = true;
				}
			}
			if(carrierChild){
				return carrierChance;
			}else{
				return .5*carrierChance;
			}
		}else if(m2.Parents.size()>0){
			double carrierChance = 1.0;
			carrierChance = carrierChance*m2.Parents.get(0).carrierChance;
			for (int j = 0; j < m2.Parents.size(); j++) {
			double carrierChance22 = (m2.Parents.get(j).carrierChance);
			carrierChance= carrierChance22*carrierChance;
		}

			boolean carrierChild = false;
			for (int i = 0; i < m1.Children.size(); i++) {
				if(m1.Children.get(i).Carrier){
					m1.carrierChance=1;
					carrierChild = true;
				}
			}
			if(carrierChild){
				return carrierChance;
			}else{
				return .5*carrierChance;
			}
		}else{
			double carrierChance = 1.0;
			for (int i = 0; i < m1.Children.size(); i++) {
				if(m1.Children.get(i).Carrier){
					m1.carrierChance=1;
					break;
				}else{
					carrierChance= carrierChance*.75;
				}
			}
			return carrierChance;
			}
	}
	private static double notAffectedChance(Member m1, Member m2) {
		return 1-affectedChance(m1,m2);
	}
	private static double affectedChance(Member m1, Member m2) {
		if(m1.Carrier&&m2.Carrier){
			return 1.0;
		}else if(m1.Carrier&&!m2.Carrier||!m1.Carrier&&m2.Carrier){
			return 0.5;
		}else{
			return .25*carrierChance(m1,m2);
		}
	}
	public static int getRow(Member mm, int jumpsToTake) {
		int ret = mm.relations(0, 0, jumpsToTake);
		return ret;
	}

	public static ArrayList<Member> setColumns(ArrayList<Member> memberss) {
		ArrayList<Member> members = memberss;
		ArrayList<ArrayList<Member>> generations = new ArrayList<ArrayList<Member>>();
		int totalGenerations = getYoungest(members).countAncestors(0);
		for (int i = 0; i < totalGenerations; i++) {
			generations.add(new ArrayList<Member>());
		}
		for (int i = 0; i < members.size(); i++) {
			int b = members.get(i).relations(0, 0, members.size());
			generations.get(b).add(members.get(i));
		}
		for (int i = 0; i < generations.size(); i++) {
			for (int j = 0; j < generations.get(i).size(); j++) {
				generations.get(i).get(j).column = j;
			}

		}
return members;
	}
	private static Member getYoungest(ArrayList<Member> mm) {
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
}
