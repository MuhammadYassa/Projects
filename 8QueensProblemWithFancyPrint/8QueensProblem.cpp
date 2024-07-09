#include <iostream>
using namespace std;
bool check(int q[8], int c){
	// check for same row and check for upper and bottom diagonal.
	for (int i=0; i<c; i++){
		if (q[c] == q[i] || c-i == abs(q[c]- q[i])){
			return false;
		}
}
	return true;
}
//print function prints out the solution number and layout of board once called.
void print(int q[]){
	// initialization of sol_num.
	static int Sol_Num=0;
	//Output solution number and layout of queen placements.
	cout << "Solution #: " << ++Sol_Num << endl;
    for (int k = 0; k < 8; k++) {
		cout << q[k];
	 }
	cout << endl;
	//create a data type "box" which is an array of char 5x7.
	typedef char box[5][7];
	//initialize white box, black box, white queen, black queen, and the board which is an array of pointers to type box of 8x8.
	box wb, bb, wq, bq, *board[8][8];
	//fill wb,bb,wq, and bq with respective characters.
	for (int i=0; i<5; i++){
		for (int j=0; j<7; j++){
			wb[i][j]=' ';
			wq[i][j]=' ';
			bb[i][j]= char(219);
			bq[i][j]= char(219);
		}
	}
	//fill white queen and black queen boxes with opposite colors to create a queen pattern.
    bq[1][1]=' ';   
	bq[2][1]=' ';
	bq[3][1]=' ';
	bq[3][2]=' ';
	bq[1][3]=' ';
	bq[2][3]=' ';
	bq[3][3]=' ';
	bq[3][4]=' ';
	bq[1][5]=' ';
	bq[2][5]=' ';
	bq[3][5]=' ';
	wq[1][1]=char(219);
	wq[2][1]=char(219);
	wq[3][1]=char(219);
	wq[3][2]=char(219);
	wq[1][3]=char(219);
	wq[2][3]=char(219);
	wq[3][3]=char(219);
	wq[3][4]=char(219);
	wq[1][5]=char(219);
	wq[2][5]=char(219);
	wq[3][5]=char(219);
	//fill board with pointers to bb and wb in alternate positions.
	for (int i=0; i<8; i++){
		for (int j=0; j<8; j++){
			if ((i+j)%2 == 0){
				board[i][j]= &wb;
			} else {
				board [i][j]= &bb;
			}
		}
	}
	//fill board with pointers to white queen or black queen using q array from main function and depending on even or odd grid box.
	for (int i=0; i<8; i++){
		for (int j=0; j<8; j++){
			if (q[j]==i){
				if((i+j)%2==0){
					board [i][j] = &wq;
				} else{
					board [i][j]= &bq;
				}
			}
		}
	}
	// print the board via the pointers in board array.
	// print upper border.
    cout << " ";
    for (int i =0; i<7*8; i++){
        cout << '_';
    }
	cout << endl;
	// print board.
	for (int a=0; a<8; a++){
		for (int b=0; b<5; b++){
            cout << " ";
            cout << char(179);
			for (int c=0; c<8; c++){
				for (int d=0; d<7; d++){
					cout << (*board[a][c])[b][d];
				}
			}
			cout << char(179) << endl;
		}
	}
	// print lower border.
    cout << " ";
    for (int i=0; i<7*8; i++){
        cout <<char(196);
    }
    cout << endl << endl;
}
int main() {
	//initializtion of 1-D Array and column number, And placement of first Queen at 0,0.
	int q[8] = { 0 }, c = 0;
	q[0] = 0;
	//outer while loop continues looping till column backtracks out of bounds.
	while (c >= 0) {
		// incrementation to next column.
		c++;
		// if column reaches 8, meaning chess board is filled and a solution is found, print function is called and decrements C to backtrack and find another solution.
		if (c == 8) {
			print(q);
			c--;
		} // else the row number starts from -1.
		else {
			q[c] = -1;
		}
		// inner while loop also continues until C goes out of bounds, meaning all possibe solutions have been found.
		while (c >= 0) {
			// increments row number to test next ro		w.
			q[c]++;
			// if row number reaches 8, goes out of chess board without placing a queen so code backtracks.
			if (q[c] == 8) {
				c--;
			}
			// check function is called to test if queen can be placed on current row, if true inner while loop is broken and outer while loop goes to next column. if false, inner while loop loops again and row is incremented.
			else if (check(q, c)) {
				break;
			}
		}
	}
	// once all possible solutions have been found exit code with 0.
	return 0;
}
