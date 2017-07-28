#include "ancestor.h" // header file


void ancestors::load_tree(){

    ifstream ancestry("Ancestors.txt");
    vector<int> anAncestor;
    int num_ancestor;

    // read in # of ancestors
    ancestry >> num_ancestor;

    // where I read ancestry from file
    for (int ii = 0; ii < num_ancestor; ++ii)
    {
        read_ancestry(ancestry, anAncestor);
        family_tree.push_back(anAncestor);
        anAncestor.clear();
    }

    vect_to_persons();

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


void ancestors::vect_to_persons()
{
    person temp_person;
    for (int ii = 0; ii < family_tree.size(); ++ii)
    {
        temp_person.is_carrier = family_tree[ii][0];
        temp_person.is_parent = family_tree[ii][1];
        temp_person.has_parent = family_tree[ii][2];
        temp_person.generation = family_tree[ii][3];
        temp_person.childNo = family_tree[ii][4];
        temp_person.parent1No = family_tree[ii][5];
        temp_person.parent1Gen = family_tree[ii][6];
        temp_person.parent2No = family_tree[ii][7];
        temp_person.parent2Gen = family_tree[ii][8];
        people.push_back(temp_person);
    }
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
    // displays people array
    for (int ii = 0; ii < people.size(); ++ii)
    {
        cout << people[ii].is_carrier;
        cout << people[ii].is_parent;
        cout << people[ii].has_parent;
        cout << people[ii].generation;
        cout << people[ii].childNo;
        cout << people[ii].parent1No;
        cout << people[ii].parent1Gen;
        cout << people[ii].parent2No;
        cout << people[ii].parent2Gen;
        cout << endl;
    }
}*/


int main ()
{
    ancestors pedigree;

    pedigree.load_tree();
}

