// Assignment By Muhammad Yassa - 8 Integers in a Cross Pattern Using backtracking.
#include <iostream>
using namespace std;
// boolean check function to check if the current integer placed in the box has not been repeated AND is not consecutive to the numbers adjacent.
bool check(int helper[][5], int i, int q[]) {
	int x = 0;
	// check to see if number is not being repeated.
	for (int z = 0; z < i; z++) {
		if (q[z] == q[i]) {
			return false;
		}
	}
	// check to see if integer being placed isnt consecutive to the numbers adjacent to it, using helper array.
	while (helper[i][x] !=  -1) {
		if (abs((q[helper[i][x]] - q[i])) == 1) {
			return false;
		}
		x++;
	}
	return true;
}
// print function to print all solutions with a solution number, and in a cross pattern.
void print(int q[]) {
	static int Sol_Num = 0;
	cout << "Solution #" << ++Sol_Num << ": " << endl;
	cout << " ";
	for (int i = 0; i < 2; i++) {
		cout << q[i];
	}
	cout << endl;
	for (int i = 2; i < 6; i++) {
		cout << q[i];
	}
	cout << endl;
	cout << " ";
	for (int i = 6; i < 8; i++) {
		cout << q[i];
	}
	cout << endl;
}
// main function.
int main() {
	// initialization of 8 grid boxes in a cross represented by an integer array called q. and integer c representing the grid box we are currently on,
	int q[8] = {0}, c = 0;
	// we place the number 1 in the first grid box.
	q[0] = 1;
	// helper array to help the program know which array column to check, the array columbns to be checked are the ones adjacent to each grid box. -1 is used to indicate that there are no more columns to check for the current grid box.
	int helper[8][5] = { {-1,-1,-1,-1,-1},{0,-1,-1,-1,-1},{0,-1,-1,-1,-1},{0,1,2,-1,-1},{0,1,3,-1,-1},{1,4,-1,-1,-1},{2,3,4,-1,-1},{3,4,5,6,-1} };
	while (c >= 0){
		// outer while loop increments the column number every loop.
		c++;
		// check to see if each array column has been filled, if so a solution is found and we call print function and backtrack.
		if (c == 8) {
			print(q);
			c--;
		}
		// if not, then we set value of current column to 0.
		else {
			q[c] = 0;
		}
		while (c >= 0) {
			// inner while loop increments the value of the current column by 1.
			q[c]++;
			// if value reaches 9, meaning integers 1-8 did not pass check function, we back track.
			if (q[c] == 9) {
				c--;
			}
			// if value passes check function, inner while loop is broken and we increment to the next column.
			else if (check(helper, c, q)) {
				break;
			}
		}
	}
	// exit with code 0 once all solutions have been found and column number is backtracked below 0.
	return 0;
}
