#include <iostream>
using namespace std;
bool check(int a[8], int c) {
	// check for same row and check for upper and bottom diagonal.
	for (int i = 0; i < c; i++) {
		if (a[i] == a[c] || (c - i) == abs((a[c] - a[i]))) {
			return false;
		}
	}
	return true;
}
int nqueens(int n){
    // dynamically allocate memory using new function.
    int* q = new int[n];
    for (int i=0; i<n; i++){
        q[i]= 0;
    }
    // declare variables.
    int solutions= 0;
    int c=0;
    while (c >= 0) {
		// incrementation to next column.
		c++;
		// if column reaches n, meaning chess board is filled and a solution is found, decrements C to backtrack and find another solution.
		if (c == n) {
			solutions++;
			c--;
		} // else the row number starts from -1.
		else {
			q[c] = -1;
		}
		// inner while loop also continues until C goes out of bounds, meaning all possibe solutions have been found.
		while (c >= 0) {
			// increments row number to test next row.
			q[c]++;
			// if row number reaches n, goes out of chess board without placing a queen so code backtracks.
			if (q[c] == n) {
				c--;
			}
			// check function is called to test if queen can be placed on current row, if true inner while loop is broken and outer while loop goes to next column. if false, inner while loop loops again and row is incremented.
			else if (check(q, c)) {
				break;
			}
		}
	}
    //delete allocated memory.
    delete[] q;
    //return number of solutions.
    return solutions;
}
int main(){
    // declare int n and ask user to input an integer.
    int n;
    cout << "Enter an integer for the n queens problem: ";
    cin >> n;
    // loop from 1 - n and print out number of solutions using nqueens function.
    for (int i=1; i<=n ; i++){
        cout << "There are " << nqueens(i) << " solutions to the " << i << " queens problem.";
        cout << endl;
    }
    return 0;
}
