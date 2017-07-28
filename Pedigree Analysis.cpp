#include "ancestor.h" // header file


void ancestors::load_tree(){

    ifstream ancestry("Ancestors.txt");
    int num_ancestor;

    if(!ancestry.is_open()){
        cout << "error opening file";
        exit(1);
    }

    vector<int> anAncestor;

    // read in # of ancestors
    ancestry >> num_ancestor;
    ancestry.ignore(1000, '\n');

    //cout << "Number of ancestors: " << num_ancestor << endl; // test to make sure # of ancestors read in was correct

    //where I read ancestry from file
    for (int ii = 0; ii < num_ancestor; ++ii)
    {
        read_ancestry(ancestry, anAncestor);
        family_tree.push_back(anAncestor);
        anAncestor.clear();

        for(int i=0;i<anAncestor.size();++i)
            cout << anAncestor[i];
        cout << endl;
    }

    display_tree();
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


void ancestors::display_tree(){

    for (int ii = 0; ii < family_tree.size(); ++ii)
    {
        for (int jj = 0; jj < family_tree[ii].size(); ++jj)
        {
            cout << family_tree[ii][jj];
        }
        cout << endl;
    }
}


int main ()
{
    ancestors pedigree;

    pedigree.load_tree();
}

