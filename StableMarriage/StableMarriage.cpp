// Assignment By Muhammad Yassa- Stable Marriage.
#include <iostream>
using namespace std;
// boolean check function
bool check(int q[], int col, int mp[][3], int wp[][3]) {
	for (int i = 0; i < col; i++) {
		// check to see if new woman hasn't already been paired with another man.
		if (q[i] == q[col]) {
			return false;
		}
		// check to see if current man prefers new woman and new woman prefers current man.
		if (mp[i][q[col]] < mp[i][q[i]] && wp[q[col]][i] < wp[q[col]][col]) {
			return false;
		}
		// check to see if new man prefers current woman and current woman prefers new man.
		if (mp[col][q[i]] < mp[col][q[col]] && wp[q[i]][col] < wp[q[i]][i]) {
			return false;
		}
	}
	return true;
}
// print function to print all solutions and their solution number.
void print(int q[]) {
	static int Sol_Num;
	cout << "Solution #" << ++Sol_Num << ": " << endl;
	cout << "MAN 0 :" << "WOMAN " << q[0] << endl;
	cout << "MAN 1 :" << "WOMAN " << q[1] << endl;
	cout << "MAN 2 :" << "WOMAN " << q[2] << endl;
}
// main function.
int main() {
	// initialization of solution array, column number and 2 3x3 arrays for men preferences and womens preferences.
	int q[3], c=0;
	int mp[3][3] = { {0,2,1},{0,2,1},{1,2,0} };
	int wp[3][3] = { {2,1,0}, {0,1,2}, {2,0,1} };
	// pair first woman with first man.
	q[0] = 0;
	while (c >= 0) {
		// outer while loop increments column number.
		c++;
		// if column number reaches 3, a solution has been found and the print function is called and we backtrack.
		if (c == 3) {
			print(q);
			c--;
		}
		// else we set column value to equal -1.
		else {
			q[c] = -1;
		}
		while (c >= 0) {
			// inner while loop increments the number of the women.
			q[c]++;
			// if all woman have been tested and column value reaches 3, we backtrack.
			if (q[c] == 3) {
				c--;
			}
			// if new woman passes all checks, we break out of inner while loop and increment to next man (next column).
			else if (check(q, c, mp, wp)) {
				break;
			}
		}
	}
	// once program backtracks below c=0, all solutions have been found and the code exits with 0.
	return 0;
}
