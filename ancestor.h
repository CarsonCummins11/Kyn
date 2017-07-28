// Takes ancestors from file and stores them for later use
#include <iostream>
#include <vector>
#include <fstream>
#include <cstdlib>
using namespace std;


class ancestors // manager class
{

public:
    int trait_type; // i.e: recessive / dominant allele
    int generation; // based off offspring, not age
    int child_number; // If two people had a child, their child number is the same
    int parent1_child_number; // child number of first parent
    int parent2_child_number; // child number of second parent
    bool is_carrier = false; // if true, the person is a carrier of the disease or trait in question
    bool is_parent = false; // if true, the person is the parent of the child in question

    // first vector stores other vectors of type integer equal to the number of ancestors.
    //Each vector of type <int> stores all of the data for one ancestor
    vector<vector<int> > family_tree;

    // prototypes
    void read_ancestry(ifstream & ancestry, vector<int> & anAncestor); // reads in data for each ancestor one at a time
    void load_tree(); // main function that manages file I.O. Calls on read_in function to help transfer data.
    void display_tree(); // displays all data passed in from G.U.I. Used for testing
};
