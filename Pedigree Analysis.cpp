#include "ancestor.h" // header file


void ancestors::load_tree(person& mystery){

    ifstream ancestry("Ancestors.txt");
    vector<int> anAncestor;

    // read in # of ancestors
    ancestry >> num_ancestor;

    // where I read ancestry from file
    for (int ii = 0; ii < num_ancestor; ++ii)
    {
        read_ancestry(ancestry, anAncestor);
        family_tree.push_back(anAncestor);
        anAncestor.clear();
    }

    vect_to_persons(mystery);

    //display_tree();
}


void ancestors::read_ancestry(ifstream & ancestry, vector<int>  & anAncestor)
{
    /*ancestry >> trait_type;
    anAncestor.push_back(trait_type);*/

	ancestry >> is_carrier;
	anAncestor.push_back(is_carrier);

	ancestry >> is_parent;
	anAncestor.push_back(is_parent);

	ancestry >> has_parents;
	anAncestor.push_back(has_parents);

	ancestry >> generation;
	anAncestor.push_back(generation);

	ancestry >> child_number;
	anAncestor.push_back(child_number);

	ancestry >> parent1_child_number;
	anAncestor.push_back(parent1_child_number);

	ancestry >> parent1_generation;
	anAncestor.push_back(parent1_generation);

	ancestry >> parent2_child_number;
	anAncestor.push_back(parent2_child_number);

	ancestry >> parent2_generation;
	anAncestor.push_back(parent2_generation);
}


void ancestors::vect_to_persons(person& mystery)
{
    person temp_person;
    for (int ii = 0; ii < family_tree.size(); ++ii)
    {
        temp_person.affected = family_tree[ii][0];
        temp_person.is_parent = family_tree[ii][1];
        temp_person.follow = family_tree[ii][2];
        temp_person.generation = family_tree[ii][3];
        temp_person.childNo = family_tree[ii][4];
        temp_person.parent1No = family_tree[ii][5];
        temp_person.parent1Gen = family_tree[ii][6];
        temp_person.parent2No = family_tree[ii][7];
        temp_person.parent2Gen = family_tree[ii][8];
        people.push_back(temp_person);
    }
    mystery.parent1Gen = -1;

    // calculates the mystery child's generation and child number, parent's generations and parent's child numbers
    for (int ii = 0; ii < people.size(); ++ii)
    {
        if (people[ii].is_parent == true)
        {
            if (mystery.parent1Gen == -1)
            {
                mystery.parent1Gen = people[ii].generation;
                mystery.parent1No = people[ii].parent1No;
            }
            else
            {
                mystery.parent2Gen = people[ii].generation;
                mystery.parent2No = people[ii].parent2No;
            }
        }
    }

    mystery.generation = mystery.parent1Gen - 1;
    mystery.childNo = -1;
}

probability ancestors::goingDownProbability (person childrenArray [], int arrayLength, string p1Genotype, bool p2Phenotype) {
    probability child1;
    child1.RR = 0;
    child1.Rr = 0;
    child1.rr = 0;
    bool OK = false;
    bool disease = false;
    for (int i = 0; i<arrayLength; i++) {
       // cout << "GOINGTHROUGH CHILDARRAY " << childrenArray [i].genotype << endl;
        if (childrenArray [i].affected == false) {
            OK = true;

        }

        if (childrenArray [i].affected == true) {
            disease = true;
        }
    }
  //  cout << "OK: " << OK << " disease: " << disease << endl;
    if (p2Phenotype == true) {
        if (p1Genotype == "RR" && disease == false && OK == true){
            //RRrr
            child1.Rr += 4;
        }
        else if (p1Genotype == "Rr") {
            //Rrrr
            child1.Rr += 2;
            child1.rr += 2;
        }
        else if (p1Genotype == "rr" && disease == true && OK == false) {
            //rrrr
            child1.rr += 4;
        }
    }
    else {
        //do it assuming adult is RR/Rr
        if (p1Genotype == "RR") {


            if (OK == true && disease == false) {
                //RRRR
                child1.RR += 4;

                //RrRR
                child1.RR += 2;
            }
        }
        else if (p1Genotype == "Rr") {
            if (OK == true && disease == false) {
                //RrRR
                child1.RR += 2;
                child1.Rr += 2;
            }

            //RrRr
            child1.RR += 1;
            child1.Rr += 2;
            child1.rr += 1;

        }
        else if (p1Genotype == "rr") {
            if (OK == true && disease == false){
            //rrRR
            child1.Rr += 4;
            }

            //rrRr
            child1.Rr += 2;
        }
    }
    // cout << "GOINGDoWNRESULTS " << child1.RR << endl;
    // cout << "GOINGDoWNRESULTS " << child1.Rr << endl;
    // cout << "GOINGDoWNRESULTS " << child1.rr << endl;
    return child1;
}

//going Up Probability
probability ancestors::goingUpProbability (int childNo, person childrenArray [], string KnownGenotype, bool parent1affected, bool parent2affected) {
    bool OK = false;
    bool disease = false;
    probability parent1;
    parent1.RR =0;
    parent1.Rr =0;
    parent1.rr =0;
    for (int i = 0; i<childNo; i++) {

        if (childrenArray [i].affected == false) {
            OK = true;
         //  cout << "hi" <<childrenArray[i].genotype << endl;
        }

        if (childrenArray [i].affected == true) {
            disease = true;
        }
    }
    if (OK == true && disease == false) {
        //RRRR
        if (KnownGenotype != "Rr" && parent1affected == false && parent2affected == false) {
            parent1.RR += 1;
        }
        //RRRr
        if (parent1affected == false && parent2affected == false) parent1.RR += 1;

        //RRrr
        if (KnownGenotype == "Rr" && parent1affected == false && parent2affected == true) parent1.RR += 1;

        //RrRR
        if (parent1affected == false && parent2affected == false) parent1.Rr += 1;

        //RrRr
        if (parent1affected == false && parent2affected == false) parent1.Rr += 1;

        //Rrrr
        if (KnownGenotype != "RR") parent1.Rr += 1;

        //rrRR
        if (KnownGenotype != "RR" && parent1affected == true && parent2affected == false) parent1.rr += 1;

        //rrRr
        if (KnownGenotype != "RR" && parent1affected == true && parent2affected == false) parent1.rr += 1;
    }
    else if (OK == true && disease == true) {
        //RrRr
        if (parent1affected == false && parent2affected == false) {
            parent1.Rr += 1;
        }
        //Rrrr
        if (KnownGenotype != "RR" && parent1affected == false && parent2affected == true) parent1.Rr += 1;
        //rrRr
        if (KnownGenotype != "RR" && parent1affected == true && parent2affected == false) parent1.rr += 1;
    }
    else if (OK == false && disease == true) {
        //RrRr
        if (parent1affected == false && parent2affected == false) {
            parent1.Rr += 1;
          //  cout << "RrRr" << endl;
        }

        //Rrrr
        if (KnownGenotype != "RR" && parent1affected == false && parent2affected == true){
            parent1.Rr += 1;
            //cout << "Rrrr"<<endl;
        }

        //rrRR
        if (KnownGenotype != "RR" && parent1affected == true && parent2affected == false){
            //cout << "rrRR"<<endl;
            parent1.rr += 1;
        }

        //rrRr
        if (parent1affected == true && parent2affected == false) {
           parent1.rr += 1;
           //cout<<"rrRr" << endl;
        }
        //rrrr
        if (KnownGenotype == "rr" && parent1affected == true && parent2affected == true) {
            parent1.rr += 1;
            //cout << "rrrr"<<endl;
        }
    }
    return parent1;
}

person ancestors::findCommonPoint (person disease, person mystery, person* diseaseUp, person* mysteryUp, int&mysteryUpCount) {
    //do if both need to go up
    int diseaseUpCount = 1;
    diseaseUp [0] = disease;
    mysteryUp [0] = mystery;
 //   cout << diseaseUp[0].genotype << endl;

    bool stop = false;
     mysteryUpCount = 1;


    //now find diseaseUp
    for (int i = 1; i < num_ancestor; i++) {
        if (stop == true) {
            break;
        }
        for (int j = 0; j < num_ancestor; j++) {
            if (people [i].generation == 0) {
                stop = true;
                diseaseUp [diseaseUpCount] = people[i];
                diseaseUpCount ++;
                break;
            }
            if (people [j].follow == true && people [j].generation == disease.parent1Gen && people [j].childNo == disease.parent1No) {
                diseaseUp [i] = people [j];
                disease = people [j];
                diseaseUpCount ++;
                break;
            }
            if (people [j].follow == true && people [j].generation == disease.parent2Gen && people [j].childNo == disease.parent2No) {
                diseaseUp [i] = people [j];
                disease = people[j];
                diseaseUpCount ++;
                break;
            }

        }

    }
    stop = false;
    //now find mysteryUp
    for (int i = 1; i < num_ancestor; i++) {
        if (stop == true) {
            break;
        }
        for (int j = 0; j < num_ancestor; j++) {
            if (people [i].generation == 0) {
                stop = true;
                mysteryUp [mysteryUpCount] = people[i];
                mysteryUpCount ++;
                break;
            }
            if (people [j].follow == true && people [j].generation == mystery.parent1Gen && people [j].childNo == mystery.parent1No) {
                mysteryUp [i] = people [j];
                mystery = people [j];
                mysteryUpCount ++;
                break;
            }
            if (people [j].follow == true && people [j].generation == mystery.parent2Gen && people [j].childNo == mystery.parent2No) {
                mysteryUp [i] = people [j];
                mystery = people[j];
                mysteryUpCount ++;
                break;
            }

        }

    }

    int mysteryLeastCommon = 0;
    //for (int i = 0; i<diseaseUpCount; i++ ) cout << "COMMON POINT " << diseaseUp [i].genotype << endl;
    for (int i = 0; i < mysteryUpCount && mysteryLeastCommon == 0; i++) {

     //   cout << diseaseUp [i].genotype << endl;
        for (int j = 0; j<diseaseUpCount && mysteryLeastCommon == 0; j++ ) {
            if (mysteryUp [i].childNo == diseaseUp [j].childNo && mysteryUp[i].generation == diseaseUp [j].generation) {
                mysteryLeastCommon = i;
            }
        }
    }

    int diseaseLeastCommon =0;
    for (int i = 0; i < diseaseUpCount && diseaseLeastCommon == 0; i++) {

        for (int j = 0; j<mysteryUpCount && diseaseLeastCommon == 0; j++) {
                         if (mysteryUp [i].childNo == diseaseUp [j].childNo && mysteryUp[i].generation == diseaseUp [j].generation) {
                diseaseLeastCommon = i;
            }
        }
    }


    if (mysteryUp [mysteryLeastCommon].generation < diseaseUp [diseaseLeastCommon].generation) return mysteryUp [mysteryLeastCommon];
    else return diseaseUp [diseaseLeastCommon];
}

probability ancestors::thecontrolpanel (person mystery) {
    probability average;
    int pCount = 0;
    average.RR = 0;
    average.Rr = 0;
    average.rr = 0;
    //find disease person
    for (int i = 0; i<num_ancestor; i++) {
        person diseaseUp[num_ancestor];
        person mysteryUp[num_ancestor];
        int mysteryUpCount;
        if (people [i].affected == true) {
            // find common point
            // cout << "here1 " << i <<endl;

            person commonPoint = findCommonPoint (people[i], mystery, diseaseUp, mysteryUp, mysteryUpCount);

           // commonPoint = people [4];
            // cout << "HERE2 " << commonPoint.genotype << endl;
            probability child;
            child.rr = 4;

            bool stop = false;
            //go up to common point using disease array
            for (int j = 0; j<num_ancestor && stop == false; j++) {
                pCount ++;
              //  cout << "pCount = " << pCount << endl;
                //cout << "looking at: " << diseaseUp[j].genotype << endl;
                person childArray [num_ancestor];
                int childArrayCount = 0;
                //prepare childNumber + array + parentsAffectedOrNot
                bool parent1Affected = false;
                bool parent2Affected = false;
                for (int k = 0; k<num_ancestor; k++) {

                    if (diseaseUp[j].parent1No == people [k].parent1No && diseaseUp[j].parent1Gen == people [k].parent1Gen) {
                        if (people[k].childNo != mystery.childNo && people[k].follow == true) {
                            childArray [childArrayCount] = people [k];
                             childArrayCount ++;
                        }
                       // cout << "HERE3 " << people [k].genotype <<endl;
                        //cout << "HERE3 " << << endl;

                    }

                    if (diseaseUp[j].parent1No == diseaseUp[k].childNo && diseaseUp[j].parent1Gen == diseaseUp[k].childNo) parent1Affected = diseaseUp[k].affected;
                    if (diseaseUp[j].parent2No == diseaseUp[k].childNo && diseaseUp[j].parent2Gen == diseaseUp[k].childNo) parent2Affected = diseaseUp[k].affected;
                }

                //get probability of parents
                //if (people[j].childNo == mystery.childNo && people[j].generation == mystery.generation)
                if (child.RR == 0) child.RR = 1;
                if (child.Rr == 0) child.Rr = 1;
                if (child.rr == 0) child.rr = 1;
                if (diseaseUp[j].affected == false) {
                    child.RR = child.RR * goingUpProbability (childArrayCount, childArray, "RR", parent1Affected, parent2Affected).RR;
                    child.Rr = child.RR * goingUpProbability (childArrayCount, childArray, "RR", parent1Affected, parent2Affected).Rr;
                    child.rr = child.RR * goingUpProbability (childArrayCount, childArray, "RR", parent1Affected, parent2Affected).rr;
                    child.RR += child.Rr * goingUpProbability (childArrayCount, childArray, "Rr", parent1Affected, parent2Affected).RR;
                    child.Rr += child.Rr * goingUpProbability (childArrayCount, childArray, "Rr", parent1Affected, parent2Affected).Rr;
                    child.rr += child.Rr * goingUpProbability (childArrayCount, childArray, "Rr", parent1Affected, parent2Affected).rr;
                }
                else {
                    child.RR = child.rr * goingUpProbability (childArrayCount, childArray, "rr", parent1Affected, parent2Affected).RR;
                    child.Rr = child.rr * goingUpProbability (childArrayCount, childArray, "rr", parent1Affected, parent2Affected).Rr;
                    child.rr = child.rr * goingUpProbability (childArrayCount, childArray, "rr", parent1Affected, parent2Affected).rr;
                }
                //cout << "RR " << child.RR << endl;
                //cout << "Rr " << child.Rr << endl;
                //cout << "rr " << child.rr << endl;

              //  cout << "diseaseGEN " << diseaseUp[j].generation<< " GenerationOfCP " << commonPoint.generation << endl;
                if (diseaseUp [j].generation - 1 == commonPoint.generation && diseaseUp[j].generation == commonPoint.generation) {
                    stop = true;
                    //cout << "Stop is set " << diseaseUp[j].genotype << endl;
                }


            }

            //go down to the mystery using mystery array
            stop = false;

            //find upper bound problem
            for (int j = commonPoint.generation; j >= 0 && stop == false; j--) {
                //cout << "WHAT MYSTERUP GOES THROUGHT "<<mysteryUp[j].genotype << endl;
                person childArray [num_ancestor];
                int childArrayCount = 0;
                //prepare childNumber + array + parentsAffectedOrNot
                bool parent1Affected = false;
                bool parent2Affected = false;

                for (int k = 0; k<num_ancestor; k++) {

                    if (mysteryUp[j].childNo == people [k].parent1No && mysteryUp[j].generation == people [k].parent1Gen) {
                       // cout << "HERE4 " << people[k].genotype << " " << people[k].childNo << " " << people[k].follow << endl;
                        if (people[k].childNo != mystery.childNo && people[k].follow == true) {
                            childArray [childArrayCount] = people [k];
                           // cout << "HERE3 " << people [k].genotype <<endl;
                        }

                        //
                        //cout << "HERE3 " << << endl;
                        childArrayCount ++;
                        //cout << childArray [k[ << endl]];
                    }


                    if (people[j].parent1No == people[k].childNo && people[j].parent1Gen == people[k].childNo) parent1Affected = people[k].affected;
                    if (people[j].parent2No == people[k].childNo && people[j].parent2Gen == people[k].childNo) parent2Affected = people[k].affected;


                }
               // now for the long trek down
               // cout << "RR of child before: " << child.RR << endl;
               // cout << "Rr of child before " << child.Rr << endl;
                //cout << "rr of child before: " << child.rr << endl;
                if (child.RR == 0) child.RR = 1;
                if (child.Rr == 0) child.Rr = 1;
                if (child.rr == 0) child.rr = 1;
                if (mysteryUp[j].affected == false) {
                    int placeHolderRR;
                    int placeHolderRr;
                    if  (child.RR == 0)  placeHolderRR = 1;
                    else  placeHolderRR = child.RR;

                    if  (child.RR == 0)  placeHolderRr = 1;
                    else placeHolderRr = child.Rr;
                    child.RR = placeHolderRR * goingDownProbability(childArray, childArrayCount, "RR", false).RR;
                    child.Rr = placeHolderRR * goingDownProbability(childArray, childArrayCount, "RR", false).Rr;
                    child.RR += placeHolderRr * goingDownProbability (childArray, childArrayCount, "Rr", false).RR;
                    child.Rr += placeHolderRr * goingDownProbability (childArray, childArrayCount, "Rr", false).Rr;
                    child.rr  = placeHolderRr * goingDownProbability (childArray, childArrayCount, "Rr", false).rr;

                }
                else {
                    child.rr = 4;
                    child.RR = 1;
                    child.Rr = 1;
                }

                if (mysteryUp [j].generation + 1 == mystery.generation) stop = true;
                //cout << "RR of child: " << child.RR << endl;
                //cout << "Rr of child " << child.Rr << endl;
                //cout << "rr of child: " << child.rr << endl;
            }

            average.RR += child.RR;
            average.Rr += child.Rr;
            average.rr += child.rr;
        }
    }
    return average;
}

/*
void ancestors::display_tree(){

    // displays family_tree vector
    for (int ii = 0; ii < family_tree.size(); ++ii)
    {
        for (int jj = 0; jj < family_tree[ii].size(); ++jj)
        {
            cout << family_tree[ii][jj];
        }
        cout << endl;
    }
    cout << endl;
    // displays people array
    for (int ii = 0; ii < people.size(); ++ii)
    {
        cout << people[ii].affected;
        cout << people[ii].is_parent;
        cout << people[ii].follow;
        cout << people[ii].generation;
        cout << people[ii].childNo;
        cout << people[ii].parent1No;
        cout << people[ii].parent1Gen;
        cout << people[ii].parent2No;
        cout << people[ii].parent2Gen;
        cout << endl;
    }
    cout << endl;
}
*/

int main ()
{
    ofstream chances;
    ancestors pedigree;
    double total;

    person mystery;

    pedigree.load_tree(mystery);

    probability test = pedigree.thecontrolpanel (mystery);

    chances.open("chances.txt");

    total = test.RR + test.Rr + test.rr;

    chances << test.RR / total << endl;
    chances << test.Rr / total << endl;
    chances << test.rr / total << endl;

    // cout << test.RR / total * 100 << "%" << endl;
    // cout << test.Rr / total * 100 << "%" << endl;
    // cout << test.rr / total * 100 << "%" << endl;

    chances.close();
}

