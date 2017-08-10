#include "ancestor.h" // header file


void ancestors::load_tree(person& mystery){

    ifstream ancestry("ancestors.tree");
    vector<int> anAncestor;

    if (!ancestry.is_open())
    {
        cout << "File not working like it's purpose is!" << endl;
    }

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

	if (has_parents == true)
    {
        ancestry >> parent1_child_number;
        anAncestor.push_back(parent1_child_number);

        ancestry >> parent1_generation;
        anAncestor.push_back(parent1_generation);

        ancestry >> parent2_child_number;
        anAncestor.push_back(parent2_child_number);

        ancestry >> parent2_generation;
        anAncestor.push_back(parent2_generation);
    }

    // If we do not have parents, we will use -1 as a flag.
    // This way, we ensure data alignment (that is, ALL ancestors
    // have the same amount of data).
    else
    {
        anAncestor.push_back(-1);
        anAncestor.push_back(-1);
        anAncestor.push_back(-1);
        anAncestor.push_back(-1);
    }
}


void ancestors::vect_to_persons(person& mystery)
{
    bool up = true;
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

    // Insertion Sort

    for (int ii = 0; ii < people.size() - 1; ++ii)
    {
        if (up == true)
        {
            if (people[ii].generation < people[ii + 1].generation)
            {
                temp_person = people[ii];
                people[ii] = people[ii + 1];
                people[ii + 1] = temp_person;
                up = false;
            }
            else if (people[ii].generation == people[ii + 1].generation && people[ii].childNo < people[ii + 1].childNo)
            {
                temp_person = people[ii];
                people[ii] = people[ii + 1];
                people[ii + 1] = temp_person;
                up = false;
            }
        }
        else
        {
            for (int gg = ii - 1; gg > -1; --gg)
            {
                if (people[gg].generation < people[gg + 1].generation)
                {
                    temp_person = people[gg];
                    people[gg] = people[gg + 1];
                    people[gg + 1] = temp_person;
                }
                else if (people[gg].generation == people[gg + 1].generation && people[gg].childNo < people[gg + 1].childNo)
                {
                    temp_person = people[gg];
                    people[gg] = people[gg + 1];
                    people[gg + 1] = temp_person;
                }
                else
                {
                    up = true;
                    break;
                }
            }
        }
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
                mystery.parent1No = people[ii].childNo;
            }
            else
            {
                mystery.parent2Gen = people[ii].generation;
                mystery.parent2No = people[ii].childNo;
            }
        }
    }

    mystery.generation = mystery.parent1Gen + 1;
    mystery.childNo = -1;
    //cout << mystery.generation << endl;
    //cout << mystery.parent1Gen << endl;
    //cout << mystery.parent1No << endl;
    //cout << mystery.parent2Gen << endl;
    //cout << mystery.parent2No << endl;
}


probability ancestors::goingDownProbability (person childrenArray [], int arrayLength, string p1Genotype, bool p2Affected)
{
//    for (int i = 0; i <arrayLength; i++) //cout << childrenArray[i].genotype<< " ";
    //cout << endl;
    probability child1;
    child1.RR = 0;
    child1.Rr = 0;
    child1.rr = 0;
    bool OK = false;
    bool disease = false;
    for (int i = 0; i<arrayLength; i++) {
//        //cout << "GOINGTHROUGH CHILDARRAY " << childrenArray [i].genotype << endl;
        if (childrenArray [i].affected == false) {
            OK = true;
        }
        else {
            //cout << "why me shouldn't work" << endl;
            disease = true;
        }
    }
    //cout << "OK: " << OK << " disease: " << disease << endl;
    bool p2RR = (disease == false) & (OK == true);
    if (p2Affected == true) {
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
                if (OK == true) {
                child1.RR += 4;
                //cout << "RRRR2" << endl;
                }
                else {
                //RRRr
                child1.RR += 2;
                //cout << "RRRr2" << endl;
                }
            }

        }
        else if (p1Genotype == "Rr") {
            if (p2RR) {
                //RrRR
                child1.RR += 2;
                child1.Rr += 2;
                //cout << "RrRR2" << endl;
            }
            else {
            //RrRr
            child1.RR += 1;
            child1.Rr += 2;
            child1.rr += 1;
                //cout << "RrRr2" << endl;
            }
            //cout << child1.RR<< " here39 " << child1.Rr << endl;

        }
        else if (p1Genotype == "rr") {
            if (p2RR){
            //rrRR
            child1.Rr += 4;
                //cout << "rrRR2" << endl;
            }
            else {
            //rrRr
                child1.Rr += 2;
                child1.rr += 2;
                //cout << "rrRr2" << endl;
            }
        }
    }
    //cout << "GOINGDoWNRESULTS " << child1.RR << endl;
    //cout << "GOINGDoWNRESULTS " << child1.Rr << endl;
    //cout << "GOINGDoWNRESULTS " << child1.rr << endl;
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
//        //cout << "childArray" << childrenArray [i].genotype<<endl;
        if (childrenArray [i].affected == false) {
            OK = true;
          //cout << "hi" << endl;
        }

        if (childrenArray [i].affected == true) {
            disease = true;
            //cout << "HI2 " << endl;
        }
    }

    if (OK == true && disease == false) {

        //RRRR
        if (KnownGenotype != "Rr" && parent1affected == false && parent2affected == false) {
            parent1.RR += 1;
            //cout << "RRRR" << endl;
        }
        //RRRr
        if (parent1affected == false && parent2affected == false) {
            parent1.RR += 1;
            //cout << "RRRr"<<endl;
        }

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
          //  //cout << "RrRr" << endl;
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
            //cout << "WE ARE HERE rrrr"<<endl;
        }
    }
    return parent1;
}

person ancestors::findCommonPoint (person disease, person mystery, person* diseaseUp, person* mysteryUp, int&mysteryUpCount, int& mysteryCommonIndex)
{
    //do if both need to go up
    int diseaseUpCount = 1;
    diseaseUp [0] = disease;
    mysteryUp [0] = mystery;
//    //cout << "Mysterygenotype" <<  mystery.genotype << endl;
 //   //cout << diseaseUp[0].genotype << endl;

    bool stop = false;
     mysteryUpCount = 1;


    //now find diseaseUp
    for (int i = 0; i < num_ancestor; i++) {
        if (stop == true) {
            break;
        }
        for (int j = 0; j < num_ancestor; j++) {
            if (people [i].generation == 0 && diseaseUp [diseaseUpCount - 1].generation != 0) {
                stop = true;
                diseaseUp [diseaseUpCount] = people[i];
                diseaseUpCount ++;
                break;
            }
            if (people [j].follow == true && people [j].generation == diseaseUp[diseaseUpCount - 1].parent1Gen && people [j].childNo == diseaseUp[diseaseUpCount - 1].parent1No) {
                diseaseUp [diseaseUpCount] = people [j];
                disease = people [j];
                diseaseUpCount ++;
                break;
            }
            if (people [j].follow == true && people [j].generation == diseaseUp[diseaseUpCount - 1].parent2Gen && people [j].childNo == diseaseUp[diseaseUpCount - 1].parent2No) {
                diseaseUp [diseaseUpCount] = people [j];
                disease = people[j];
                diseaseUpCount ++;
                break;
            }


        }

    }
    stop = false;
    //cout << num_ancestor << endl;
    for (int i = 0; i < num_ancestor; i++) {
//        //cout << "GOING THROUGH people " << people[i].genotype << endl;
//        //cout << people[i].genotype << endl;
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
            if (people [j].follow == true && people [j].generation == mysteryUp[mysteryUpCount-1].parent1Gen && people [j].childNo == mysteryUp[mysteryUpCount-1].parent1No) {
//                //cout << people[j].genotype << " 2937 " << mysteryUp[mysteryUpCount-1].genotype << endl;
                mysteryUp [mysteryUpCount] = people [j];
                mystery = people [j];
                mysteryUpCount ++;
                break;
            }
            if (people [j].follow == true && people [j].generation == mysteryUp[mysteryUpCount-1].parent2Gen && people [j].childNo == mysteryUp[mysteryUpCount-1].parent2No) {
                mysteryUp [mysteryUpCount] = people [j];
                mystery = people[j];
                mysteryUpCount ++;
                break;
            }

        }
      //  //cout << "GOINGTHROUGHMYSTERYARRAY " << mysteryUp [mysteryUpCount].genotype << endl;


    }

    int mysteryLeastCommon = 0;
//    for (int i = 0; i<mysteryUpCount; i++ ) //cout << "mysteryUp " << mysteryUp [i].genotype << endl;
    //cout << "mysteryupend" << endl;
//    for (int i = 0; i<diseaseUpCount; i++ ) //cout << "diseaseUp " << diseaseUp [i].genotype << endl;
    //cout <<"diseaseUpEnd" <<  endl;

    for (int i = 0; i < mysteryUpCount && mysteryLeastCommon == 0; i++) {
                for (int j = 0; j<diseaseUpCount && mysteryLeastCommon == 0; j++ ) {
            if (mysteryUp [i].childNo == diseaseUp [j].childNo && mysteryUp[i].generation == diseaseUp [j].generation) {
                mysteryLeastCommon = i;
            }
        }
    }

    int diseaseLeastCommon = 0;
    for (int i = 0; i < diseaseUpCount && diseaseLeastCommon == 0; i++) {

        for (int j = 0; j<mysteryUpCount && diseaseLeastCommon == 0; j++) {
                         if (mysteryUp [i].childNo == diseaseUp [j].childNo && mysteryUp[i].generation == diseaseUp [j].generation) {
                diseaseLeastCommon = i;
            }
        }
    }

    ////cout << "commonpointindex" << mysteryCommonIndex << endl;
    mysteryCommonIndex = mysteryLeastCommon;
    return mysteryUp [mysteryLeastCommon];
}


probability ancestors::goingDownSpecial (string p1Genotype, bool p2Affected) {
   probability mysteryChild;
    mysteryChild.RR = 0;
    mysteryChild.Rr = 0;
    mysteryChild.rr = 0;
    if (p2Affected == false) {
        if (p1Genotype == "RR") {
            //RRRR
            mysteryChild.RR = 4;
            //cout << "special RRRR" << endl;

            //RRRr
           // mysteryChild.RR += 2;
            //mysteryChild.Rr += 2;
        }
        else if (p1Genotype == "Rr") {
            //cout << "special RrRR" << endl;
            //RrRR
            mysteryChild.RR = 2;
            mysteryChild.Rr = 2;
            //cout << "doesn't print out " << mysteryChild.RR << endl;
            //RrRr
           // mysteryChild.RR += 1;
           // mysteryChild.Rr += 2;
           // mysteryChild.rr += 1;
        }
        else {
            //rrRR
            mysteryChild.Rr = 4;

            //rrRr
           // mysteryChild.Rr += 2;
        }

    }
    else if (p2Affected == true) {
        if (p1Genotype == "RR") {
            //RRrr
            mysteryChild.Rr = 4;
        }
        else if (p1Genotype == "Rr") {
            //Rrrr
            mysteryChild.Rr = 2;
            mysteryChild.rr = 2;
        }
        else {
            //rrrr
            mysteryChild.rr = 4;
        }
    }
    return mysteryChild;
}


probability ancestors::thecontrolpanel (person mystery) {
  probability average;
    int pCount = 0;
    average.RR = 1;
    average.Rr = 1;
    average.rr = 1;
    //find disease person
    for (int i = 0; i<num_ancestor; i++) {
        ////cout << people [i].genotype << " " << people[i].affected << endl;
        person diseaseUp[num_ancestor];
        person mysteryUp[num_ancestor];
        int mysteryUpCount;
        //if (people [i].affected == true) {
        if (people [i].affected == true && (people[i].follow || people[i].generation == 0)) {
            //find common point
//            //cout << "FOUND PERSON WITH Disease" << people[i].genotype <<endl;
            int mysteryCommonIndex;
            person commonPoint = findCommonPoint (people[i], mystery, diseaseUp, mysteryUp, mysteryUpCount, mysteryCommonIndex);

            //commonPoint = people [4];
            //cout << "COMMON POINT " << commonPoint.generation << endl;
            //FIX THIS
            probability child;
            child.rr = 4;

            bool stop = false;

            //cout << "HERE X0 " << endl;
            stop = (diseaseUp [0].generation == 0);
            //go up to common point using disease array
            for (int j = 0; j<num_ancestor && stop == false && diseaseUp[j].generation != 0; j++) {
                pCount ++;
              //  //cout << "pCount = " << pCount << endl;
                ////cout << "looking at: " << diseaseUp[j].genotype << endl;

                //cout << "HERE X1 " << endl;

                person childArray [num_ancestor];
                int childArrayCount = 0;
                //prepare childNumber + array + parentsAffectedOrNot
                bool parent2Affected = false;
                bool parent1Affected = false;

                for (int k = 0; k<num_ancestor; k++) {  // Child array

                    if (diseaseUp[j].parent1No == people [k].parent1No && diseaseUp[j].parent1Gen == people [k].parent1Gen) {

                        if (people[k].follow == true) {
                            childArray [childArrayCount] = people [k];
                             childArrayCount ++;
//                            //cout <<"here3 " << childArray [childArrayCount - 1].genotype << endl;
                        }
                       // //cout << "HERE3 " << people [k].genotype <<endl;
                        ////cout << "HERE3 " << << endl;

                    }
                    //problems

                    if (diseaseUp[k].generation == 0 && diseaseUp[j].generation == 1) {
                        parent1Affected = people[num_ancestor - 2].affected;
                        parent2Affected = people[num_ancestor - 1].affected;
                        //stop = true;
//                        //cout << " this is generation 0 " <<people[i].genotype << " " << diseaseUp[k].affected << endl;
                        //cout << "HERE X1 " << endl;
                    }
                    else {
                        if (diseaseUp[j].follow == true && diseaseUp[j].parent1No == people[k].childNo && diseaseUp[j].parent1Gen == people[k].generation) {
                            parent1Affected = diseaseUp[k].affected;

//                            //cout << people[k].genotype << " this is affected " << people[k].affected << endl;
                        }

                        if (diseaseUp[j].follow == false && diseaseUp[j].parent2No == people[k].childNo && diseaseUp[j].parent2Gen == people[k].generation) {
                            parent2Affected = diseaseUp[k].affected;

//                            //cout << people[k].genotype << " this is affected " << people[k].affected << endl;
                        }
                    }

                }   // end child arrary loop



                //cout << endl;
                //get probability of parents
                //if (people[j].childNo == mystery.childNo && people[j].generation == mystery.generation)
                if (child.RR == 0) child.RR = 1;
                if (child.Rr == 0) child.Rr = 1;
                if (child.rr == 0) child.rr = 1;

                if (diseaseUp[j].affected == false) {
                    //cout << "WE GO THROUGH HERE" << endl;
                    child.RR = child.RR * goingUpProbability (childArrayCount, childArray, "RR", parent1Affected, parent2Affected).RR;
                    child.Rr = child.RR * goingUpProbability (childArrayCount, childArray, "RR", parent1Affected, parent2Affected).Rr;
                    child.rr = child.RR * goingUpProbability (childArrayCount, childArray, "RR", parent1Affected, parent2Affected).rr;
                    child.RR += child.Rr * goingUpProbability (childArrayCount, childArray, "Rr", parent1Affected, parent2Affected).RR;
                    child.Rr += child.Rr * goingUpProbability (childArrayCount, childArray, "Rr", parent1Affected, parent2Affected).Rr;
                    child.rr += child.Rr * goingUpProbability (childArrayCount, childArray, "Rr", parent1Affected, parent2Affected).rr;
                }
                else {
                   //cout <<"should go through here" << endl;
                    child.RR = child.rr * goingUpProbability (childArrayCount, childArray, "rr", parent1Affected, parent2Affected).RR;
                    child.Rr = child.rr * goingUpProbability (childArrayCount, childArray, "rr", parent1Affected, parent2Affected).Rr;
                    child.rr = child.rr * goingUpProbability (childArrayCount, childArray, "rr", parent1Affected, parent2Affected).rr;
                }
                //cout << "RR goingUp " << child.RR << endl;
                //cout << "Rr goingUp " << child.Rr << endl;
                //cout << "rr goingUp " << child.rr << endl;

              //  //cout << "diseaseGEN " << diseaseUp[j].generation<< " GenerationOfCP " << commonPoint.generation << endl;
                if (diseaseUp [j].generation - 1 == commonPoint.generation || diseaseUp [j].generation == commonPoint.generation
                    ) {
                    stop = true;
//                    //cout << "Stop is set " << diseaseUp[j].genotype << endl;
                    //cout << "HERE XSTOP " << endl;
                }


            }   // end of going up loop

            //go down to the mystery using mystery array
            stop = false;
             bool mysteryIsOnlyChild = false;
            //find upper bound problem
            for (int j = mysteryCommonIndex - 1; j >= 0 && stop == false; j--) {
//                //cout << "WHAT MYSTERUP GOES through "<<mysteryUp[j].genotype << " " << j << endl;
                //cout << j << endl;
                person childArray [num_ancestor];
                int childArrayCount = 0;
                //prepare childNumber + array + parentsAffectedOrNot
                bool parent2Affected = false;
                bool parent1Affected = true;

                for (int k = 0; k<num_ancestor; k++) {
                    if (mysteryUp[j].parent1No == people [k].parent1No && mysteryUp[j].parent1Gen == people [k].parent1Gen) {
//                       //cout << "HERE4 " << people[k].genotype << " " << people[k].childNo << " " << people[k].follow << endl;
                        if (people[k].follow == true) {
                            childArray [childArrayCount] = people [k];
//                            //cout << j << " HERE7 " << people [k].genotype << " ";
                            childArrayCount ++;
                        }

                        //
                        ////cout << "HERE3 " << << endl;

                        ////cout << childArray [k[ << endl]];
                    }

                    mysteryIsOnlyChild = (childArrayCount == 0);
                  //  //cout << people[j].genotype << " " people[k].genotype << endl;
                    if (people[k].follow == false && mysteryUp[j].parent1No == people[k].childNo && mysteryUp[j].parent1Gen == people[k].generation) {
                        parent2Affected = people[k].affected;
                        //cout << "parent1affected "<<people[k].affected << endl;
                    }
                    if (people[k].follow == true && mysteryUp[j].parent1No == people[k].childNo && mysteryUp[j].parent1Gen == people[k].generation) {
                        parent1Affected = people[k].affected;
                        //cout << "parent1affected "<<people[k].affected << endl;
                    }

                }
                //cout << endl;
                //now for the long trek down
               //cout << "RR of child before: " << child.RR << endl;
               //cout << "Rr of child before " << child.Rr << endl;
                //cout << "rr of child before: " << child.rr << endl;
                if (child.RR == 0) child.RR = 1;
                if (child.Rr == 0) child.Rr = 1;
                if (child.rr == 0) child.rr = 1;
//                //cout <<"TEST9" << mysteryUp[j].genotype << endl;

                if (parent1Affected == false && mysteryIsOnlyChild == false) {
                    //cout << "I AM HERE"<<endl;
                    int placeHolderRR;
                    int placeHolderRr;
                    if  (child.RR == 0)  placeHolderRR = 1;
                    else  placeHolderRR = child.RR;
                    if  (child.RR == 0)  placeHolderRr = 1;
                    else placeHolderRr = child.Rr;
                    child.RR = placeHolderRR * goingDownProbability(childArray, childArrayCount, "RR", parent2Affected).RR;
                    child.Rr = placeHolderRR * goingDownProbability(childArray, childArrayCount, "RR", parent2Affected).Rr;
                    //cout << "midway check " << child.RR << endl;
                    //cout << "midway check " << child.Rr << endl;
                  child.RR += placeHolderRr * goingDownProbability (childArray, childArrayCount, "Rr", parent2Affected).RR;
                    child.Rr += placeHolderRr * goingDownProbability (childArray, childArrayCount, "Rr", parent2Affected).Rr;
                    if (j==0) child.rr  = placeHolderRr * goingDownProbability (childArray, childArrayCount, "Rr", parent2Affected).rr;

                }
                else if (parent1Affected == true && mysteryIsOnlyChild == false){
                    child.rr = goingDownProbability(childArray, childArrayCount, "rr", parent2Affected).rr;
                    child.Rr = goingDownProbability(childArray, childArrayCount, "rr", parent2Affected).Rr;

                }
                //cout << "RR: " << child.RR << endl;
                //cout << "Rr: " << child.Rr << endl;
                //cout << "rr: " << child.rr << endl;
                //cout << "parent2Affected" << parent2Affected << endl;
                // Handle the 1st geneneration where the mystery case is
                if (mysteryIsOnlyChild) {
                    int placeHolderRR;
                    int placeHolderRr;
                    if  (child.RR == 0)  placeHolderRR = 1;
                    else  placeHolderRR = child.RR;
                    if  (child.RR == 0)  placeHolderRr = 1;
                    else placeHolderRr = child.Rr;
                    if (parent1Affected == false) {
                        //cout << "parent2Affected" << parent2Affected << endl;
                        child.RR = placeHolderRR * goingDownSpecial("RR", parent2Affected).RR;
                        child.Rr = placeHolderRR * goingDownSpecial("RR", parent2Affected).Rr;
                      //  //cout << "midway point " << "RR " <<goingDownSpecial("RR", parent2Affected).RR<<endl;
                        child.RR += placeHolderRr * goingDownSpecial("Rr", parent2Affected).RR;
                        //cout << "??? " << placeHolderRr << endl;
                        child.Rr += placeHolderRr * goingDownSpecial("Rr", parent2Affected).Rr;
                        child.rr = placeHolderRr * goingDownSpecial("Rr", parent2Affected).rr;
                        //cout << "final check 1 " << goingDownSpecial("Rr", parent2Affected).RR << endl;
                        //cout<<"Final check point " << child.RR << " Rr: " << child.Rr << " rr " << child.rr << endl;

                    }
                    else {
                        int placeHolderrr;
                        if (child.rr == 0) placeHolderrr = 1;
                        else placeHolderrr = child.rr;
                        child.Rr = placeHolderrr * goingDownSpecial("rr", parent2Affected).Rr;
                        child.rr = placeHolderrr * goingDownSpecial("rr", parent2Affected).rr;

                    }

                }

                if (mysteryUp [j].generation -1  == mystery.generation) {
                    stop = true;
                }
               // //cout << "RR of child: " << child.RR << endl;
               // //cout << "Rr of child " << child.Rr << endl;
            //    //cout << "rr of child: " << child.rr << endl;
            }



            if (child.RR != 1 && child.RR != 0)average.RR = average.RR * child.RR;
            if (child.Rr != 1 && child.Rr != 0)average.Rr = average.Rr *  child.Rr;
            if (child.rr != 1 && child.rr != 0)average.rr = average.rr * child.rr;
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
            //cout << family_tree[ii][jj];
        }
        //cout << endl;
    }
    //cout << endl;
    // displays people array
    for (int ii = 0; ii < people.size(); ++ii)
    {
        //cout << people[ii].affected;
        //cout << people[ii].is_parent;
        //cout << people[ii].follow;
        //cout << people[ii].generation;
        //cout << people[ii].childNo;
        if (people[ii].follow == 1)
        {
            //cout << people[ii].parent1No;
            //cout << people[ii].parent1Gen;
            //cout << people[ii].parent2No;
            //cout << people[ii].parent2Gen;
        }
        //cout << endl;
    }
    //cout << endl;
}*/


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

    // //cout << test.RR / total * 100 << "%" << endl;
    // //cout << test.Rr / total * 100 << "%" << endl;
    // //cout << test.rr / total * 100 << "%" << endl;

     chances.close();
}

