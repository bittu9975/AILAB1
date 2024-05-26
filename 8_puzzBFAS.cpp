#include <iostream>
#include <queue>
#include <cstring>
using namespace std;

#define N 3

struct Node
{
    Node* parent;
    int mat[N][N];
    int x, y;
    int level;
};

void printMatrix(int mat[N][N])
{
    for (int i = 0; i < N; i++)
    {
        for (int j = 0; j < N; j++)
            cout << mat[i][j] << " ";
        cout << endl;
    }
}

Node* newNode(int mat[N][N], int x, int y, int newX,int newY, int level, Node* parent)
{
    Node* node = new Node;
    node->parent = parent;
    memcpy(node->mat, mat, sizeof node->mat);

    swap(node->mat[x][y], node->mat[newX][newY]);

    node->level = level;

    node->x = newX;
    node->y = newY;
    return node;
}

bool isSafe(int x, int y)
{
    return (x >= 0 && x < N && y >= 0 && y < N);
}

bool isEqual(int mat1[N][N], int mat2[N][N])
{
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            if (mat1[i][j] != mat2[i][j])
                return false;
    return true;
}

void printPath(Node* root)
{
    if (root == NULL)
        return;
    printPath(root->parent);
    printMatrix(root->mat);
    cout << endl;
}

int row[] = { 1, 0, -1, 0 };
int col[] = { 0, -1, 0, 1 };

void solve(int initial[N][N], int x,int y, int final[N][N]){
    queue<Node*>q;
    Node* root = newNode(initial,x,y,x,y,0,NULL);
    q.push(root);
    while(!q.empty()){
        Node* curr = q.front();
        q.pop();

        if(isEqual(curr->mat,final)){
            printPath(curr);
            return;
        }
        for(int i=0;i<4;i++){
            if(isSafe(curr->x+row[i], curr->y+col[i])){
                Node* child = newNode(curr->mat,curr->x,curr->y, curr->x+row[i], curr->y+col[i],curr->level+1,curr);
                q.push(child);
            }
        }
    }

}

int main()
{
    int initial[N][N] =
    {
        {1, 2, 3},
        {5, 6, 0},
        {7, 8, 4}
    };
    int final[N][N] =
    {
        {1, 2, 3},
        {7, 5, 6},
        {8, 4, 0}
    };
    int x = 1, y = 2;
    solve(initial, x, y, final);
    return 0;
}


