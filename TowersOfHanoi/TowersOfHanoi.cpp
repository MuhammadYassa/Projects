#include <iostream>
#include <vector>
using namespace std;
int main(){
    //Declare 3 Towers.
    vector<int>t[3];
    int n, candidate, to, from, move=0;
    cout << "Please enter the number of rings to move: ";
    cin >> n;
    cout << endl;
    //Initialize the 3 Towers.
    for (int i=n; i>=1; i--){
        t[0].push_back(i);
    }
    t[1].push_back(n);
    t[2].push_back(n);
    //Initialize from and to towers, if number of rings is odd to tower is initialized as tower B since we go to the right, and if n is even, to tower is tower C since we go to the left.
    from =0;
    if (n%2==1){
        to=1;
    } else {
        to=2;
    }
    //Initialize candidate.
    candidate=1;
    while (t[1].size()<n){ //There are still rings to transfer to Tower B.
        cout << "move number " << ++move << ": Transfer ring " << candidate << " from tower " << char(from+'A') << " to tower " << char(to+'A') << endl;
        //Push the top of the "from" tower to the "to" tower.
        t[to].push_back(t[from].back());    
        //Remove the ring from the "from" tower.
        t[from].pop_back();
        //Get next "from tower" It's not the most recent "to" so it'll be tower with the smallest ring.
        if (t[(to+1)%3].back() < t[(to+2)%3].back()){
            from = (to+1)%3;
       } else {
            from = (to+2)%3;
       }
       // next candidate is smallest ring on top of from tower.
       candidate = t[from].back();
       // if number of rings is odd then closest tower to the right is from+1.
       if (n%2==1){
        if (t[(from+1)%3].back() > candidate){
            to = (from+1)%3;
        } else {
            to = (from+2)%3;
        }
       }
       // if number of rings is even then closest tower to the left is from+2.
       if (n%2==0){
        if (t[(from+2)%3].back() > candidate){
            to= (from+2)%3;
        } else {
            to= (from+1)%3;
        }
       }
    }
    return 0;
}
