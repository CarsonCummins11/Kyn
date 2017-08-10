#include <iostream>
#include <vector>
#include <fstream>
#include <cstdlib>
#include <string>

using namespace std;


struct person {
    bool affected;
    bool is_parent;
    bool follow;
    int generation;
    int childNo;
    int parent1No;
    int parent1Gen;
    int parent2No;
    int parent2Gen;
};


struct probability {
    int RR = 1;
    int Rr = 1;
    int rr = 1;
};


class ancestors // manager class
{

public:
    //int trait_type; // i.e: recessive / dominant allele
    int num_ancestor;
    bool is_carrier = false; // if true, the person is a carrier of the disease or trait in question
    bool is_parent = false; // if true, the person is the parent of the child in question
    bool has_parents = false; // if true, the person does have parents
    int generation; // based off offspring, not age
    int child_number; // If two people had a child, their child number is the same
    int parent1_child_number; // child number of first parent
    int parent1_generation; // generation of first parent
    int parent2_child_number; // child number of second parent
    int parent2_generation; // generation of second parent

    // first vector stores other vectors of type integer equal to the number of ancestors
    //Each vector of type <int> stores all of the data for one ancestor
    vector<vector<int> > family_tree;
    vector<person> people; // vector of persons. each index is a person struct that holds all of the variables in the struct

    // prototypes
    void load_tree(person& mystery); // main function that manages file I.O. Calls on read_in function to help transfer data
    void read_ancestry(ifstream & ancestry, vector<int> & anAncestor); // reads in data for each ancestor one at a time
    void vect_to_persons(person& mystery); // takes 2 dimensional vector of ancestors and transfers them into a vector of person structs
    void display_tree(); // Used for testing
    probability goingDownProbability (person childrenArray [], int arrayLength, string p1Genotype, bool p2Phenotype);
    probability goingUpProbability (int childNo, person childrenArray [], string KnownGenotype, bool parent1affected, bool parent2affected);
    //finds common point of disease and mystery person
    person findCommonPoint (person disease, person mystery, person* diseaseUp, person* mysteryUp, int&mysteryUpCount, int& mysteryCommonIndex);
    probability goingDownSpecial (string p1Genotype, bool p2Affected);
    probability thecontrolpanel (person mystery);
};
