#include "DGoingUp.h"
#include <iostream>
#include <string>
using namespace std;

struct person {
    int generation;
    int childNo;
    int parent1Gen;
    int parent1No;
    int parent2Gen;
    int parent2No;
    bool follow = true;
    string genotype = "nothere";
    bool affected = false;
};
person p1, p2, c1, c2;
void setPeople () {
    p1.affected = false;
    c1.affected = false;
    p2.affected = false;
    c2.affected = false;
}
struct probability {
    int RR=1;
    int Rr=1;
    int rr=1;
};
probability DgoingUpProbability (int childNo, person childrenArray [], string KnownGenotype, bool parent1affected, bool parent2affected) {
    bool OK = false;
    bool disease = false;
    probability parent1;
    parent1.RR = 0;
    parent1.Rr = 0;
    parent1.rr = 0;
    for (int i = 0; i<childNo; i++) {
        //cout << "childArray" << childrenArray [i].genotype<<endl;
        if (childrenArray [i].affected == false) {
            OK = true;
        //  cout << "hi" << endl;
        }

        if (childrenArray [i].affected == true) {
            disease = true;
           // cout << "HI2 " << endl;
        }
    }

    if (OK == true && disease == false) {

        //rrrr
        if (KnownGenotype == "rr" && parent1affected == false && parent2affected == false) {
            parent1.rr += 1;
            cout << "rrrr"<<endl;
        }
        //RrRr
        if (parent1affected == true && parent2affected == true){
            cout << "RrRr" << endl;
            parent1.Rr += 1;
        }

        //Rrrr
        if (KnownGenotype != "RR") {
            cout << "Rrrr" << endl;
            parent1.Rr += 1;
        }

        //rrRr
        if (KnownGenotype != "RR" && parent1affected == false && parent2affected == true) {
            cout << "rrRr" << endl;
            parent1.rr += 1;
        }
    }
    else if (OK == true && disease == true) {

        //RRRR
        if (KnownGenotype != "Rr" && parent1affected == true && parent2affected == true) {
            parent1.RR += 1;
            cout << "RRRR" << endl;
        }
        //RRRr
        if (parent1affected == true && parent2affected == true) {
            parent1.RR += 1;
            cout << "RRRr"<<endl;
        }
        //RRrr
        if (KnownGenotype =="Rr" && parent1affected == true && parent2affected == false) {
            cout << "RRrr" << endl;
            parent1.RR += 1;
        }
        //RrRR
        if (parent1affected == true && parent2affected == true) {
            cout << "RrRR" << endl;
            parent1.Rr += 1;
        }
        //RrRr
        if (parent1affected == true && parent2affected == true) {
            cout << "RrRr" <<endl;
            parent1.Rr += 1;
        }

        //Rrrr
        if (KnownGenotype != "RR" && parent1affected == true && parent2affected == false) {
            cout << "Rrrr" << endl;
            parent1.Rr += 1;
        }
        //rrRR
        if (KnownGenotype != "RR" && parent1affected == false && parent2affected == true) {
            cout << "rrRR" << endl;
            parent1.rr += 1;
        }
        //rrRr
        if (KnownGenotype != "RR" && parent1affected == false && parent2affected == true) {
            cout << "rrRr" << endl;
            parent1.rr += 1;
        }

    }
    else if (OK == false && disease == true) {

        //RRRR
        if (KnownGenotype != "Rr" && parent1affected == true && parent2affected == true) {
            parent1.RR += 1;
            cout << "RRRR" << endl;
        }

        //RRRr
        if (parent1affected == true && parent2affected == true) {
            parent1.RR += 1;
            cout << "RRRr"<<endl;
        }

        //RrRr
        if (parent1affected == true && parent2affected == true) {
            parent1.Rr += 1;
            cout << "RrRr" << endl;
        }
        //RrRR
        if (parent1affected == true && parent2affected == true) {
            cout << "RrRR" << endl;
            parent1.Rr += 1;
        }
    }
    return parent1;
}

int main () {
    setPeople();
    person childArray [2] = {c1, c2};
     probability test = goingUpProbability (2, childArray, "Rr", p1.affected, p2.affected);
     cout << test.RR << endl;
     cout << test.Rr << endl;
     cout << test.rr << endl;
}
