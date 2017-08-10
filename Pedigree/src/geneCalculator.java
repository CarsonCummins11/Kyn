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
		
		for (int i = 0; i < members.size(); i++) {
			boolean allCarrier = true;
				for (int j = 0; j < members.get(i).Parents.size(); j++) {
					if(!members.get(i).Parents.get(j).Carrier){
						allCarrier = false;
					}
				}
				if(allCarrier&&members.get(i).Parents.size()>0){
					members.get(i).Carrier=true;
				}
		}
		Member[] pars = new Member[XofSelected.length];
		for (int i = 0; i < XofSelected.length; i++) {
			int x = XofSelected[i];
			int y = YofSelected[i];
			System.out.println(x+","+y);
			for (int j = 0; j < members.size(); j++) {
				if(x==members.get(j).X&&y==members.get(j).Y){
					pars[i]=members.get(j);
				}
			}
		}
		double[] ret = {notAffectedChance(pars[0],pars[1]),procCarrierChance(pars[0],pars[1]),affectedChance(pars[0],pars[1])};
		return ret;
	}
	private static double procCarrierChance(Member m1, Member m2) {
		double carChance1 = carrierChance(m1);
		double carChance2 = carrierChance(m2);
		if(carChance1==0&&carChance2==0){
			return 0;
		}else if(carChance1 ==0){
			return .5*carChance2;
		}else if(carChance2 ==0){
			return .5*carChance1;
		}else{
			return .75*carChance1*carChance2;
		}
	}
	private static double carrierChance(Member m) {
		if(m.Carrier==true){
			return 0.0;
		}
		if(m.Parents.size()==2){
			return procCarrierChance(m.Parents.get(0), m.Parents.get(1));
		}else{
			double carrierChance = 1.0;
			for (int i = 0; i < m.Children.size(); i++) {
				if(m.Children.get(i).Carrier){
					return 1.0;
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
		}else if(m1.Carrier&&!m2.Carrier){
			return 0.5*carrierChance(m2);
		}else if(!m1.Carrier&&m2.Carrier){
			return 0.5*carrierChance(m2);
		}else{
			return .25*carrierChance(m1)*carrierChance(m2);
		}
	}
	public static int getRow(Member mm, int jumpsToTake) {
		int ret = mm.relations(0, 0, jumpsToTake);
		return ret;
	}

	
	
}
