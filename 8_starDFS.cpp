#include <iostream>
#include <stack>
#include <cstring>
using namespace std;

#define N 3

struct Node {
    Node* parent;
    int mat[N][N];
    int x, y;
    int level;
};

void printMatrix(int mat[N][N]) {
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++)
            cout << mat[i][j] << " ";
        cout << endl;
    }
}

Node* newNode(int mat[N][N], int x, int y, int newX, int newY, int level, Node* parent) {
    Node* node = new Node;
    node->parent = parent;

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            node->mat[i][j] = mat[i][j];
        }
    }
    
    swap(node->mat[x][y], node->mat[newX][newY]);
    node->level = level;
    node->x = newX;
    node->y = newY;
    return node;
}

bool isSafe(int x, int y) {
    return (x >= 0 && x < N && y >= 0 && y < N);
}

bool isEqual(int mat1[N][N], int mat2[N][N]) {
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            if (mat1[i][j] != mat2[i][j])
                return false;
    return true;
}

void printPath(Node* root) {
    if (root == nullptr)
        return;
    printPath(root->parent);
    printMatrix(root->mat);
    cout << endl;
}

int row[] = {-1, 0, 1, 0};
int col[] = {0, -1, 0, 1};

bool solveDFS(Node* node, int final[N][N], int depth) {
    if (isEqual(node->mat, final)) {
        printPath(node);
        return true;
    }
    if (depth <= 0)
        return false;

    for (int i = 0; i < 4; i++) {
        int newX = node->x + row[i];
        int newY = node->y + col[i];

        if (isSafe(newX, newY)) {
            Node* child = newNode(node->mat, node->x, node->y, newX, newY, node->level + 1, node);
            if (solveDFS(child, final, depth - 1))
                return true;
        }
    }
    return false;
}
int main() {
    int initial[N][N] = {
        {1, 2, 3},
        {5, 6, 0},
        {7, 8, 4}
    };

    int final[N][N] = {
        {1, 2, 3},
        {7, 5, 6},
        {8, 4, 0}
    };

    int x = 1, y = 2;
    int depth = 10;
    Node* root = newNode(initial, x, y, x, y, 0, nullptr);

    if (!solveDFS(root, final, depth))
        cout << "Solution not found within depth limit" << endl;

    return 0;
}
