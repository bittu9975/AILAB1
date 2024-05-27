#include <bits/stdc++.h>
using namespace std;

struct Node {
    vector<vector<int>> state;
    int g;
    int h;
    Node* parent;

    Node(vector<vector<int>> state, int g, int h, Node* parent)
        : state(state), g(g), h(h), parent(parent) {}

    bool operator>(const Node& other) const {
        return (g + h) > (other.g + other.h);
    }
};

int countMisplaced(const vector<vector<int>>& curr, const vector<vector<int>>& goal) {
    int count = 0;
    for (int i = 0; i < 3; ++i) {
        for (int j = 0; j < 3; ++j) {
            if (curr[i][j] != 0 && curr[i][j] != goal[i][j]) {
                count++;
            }
        }
    }
    return count;
}

pair<int, int> findBlank(const vector<vector<int>>& state) {
    for (int i = 0; i < 3; ++i) {
        for (int j = 0; j < 3; ++j) {
            if (state[i][j] == 0) {
                return {i, j};
            }
        }
    }
    return {-1, -1};
}

void printState(const vector<vector<int>>& state) {
    for (const auto& row : state) {
        for (int el : row) {
            cout << el << " ";
        }
        cout << endl;
    }
    cout << endl;
}

vector<Node*> generateChildren(Node* node, const vector<vector<int>>& goal) {
    vector<Node*> children;
    vector<vector<int>> curr = node->state;
    pair<int, int> blank = findBlank(curr);
    int row = blank.first;
    int col = blank.second;

    int delrow[] = {0, 1, 0, -1};
    int delcol[] = {1, 0, -1, 0};

    for (int i = 0; i < 4; ++i) {
        int nrow = row + delrow[i];
        int ncol = col + delcol[i];
        if (nrow >= 0 && nrow < 3 && ncol >= 0 && ncol < 3) {
            swap(curr[row][col], curr[nrow][ncol]);
            int h = countMisplaced(curr, goal);
            Node* child = new Node(curr, node->g + 1, h, node);
            children.push_back(child);
            swap(curr[row][col], curr[nrow][ncol]);
        }
    }
    return children;
}

void reconstructPath(Node* node) {
    if (node == nullptr) return;
    reconstructPath(node->parent);
    printState(node->state);
}

void aoStarSearch(vector<vector<int>>& start, const vector<vector<int>>& goal) {
    priority_queue<Node*, vector<Node*>, greater<Node*>> pq;
    set<vector<vector<int>>> visited;

    int h = countMisplaced(start, goal);
    Node* root = new Node(start, 0, h, nullptr);
    pq.push(root);
    visited.insert(start);

    while (!pq.empty()) {
        Node* curr = pq.top();
        pq.pop();
        
        if (curr->h == 0) {
            cout << "Solution found!" << endl;
            reconstructPath(curr);
            return;
        }

        vector<Node*> children = generateChildren(curr, goal);
        for (Node* child : children) {
            if (visited.find(child->state) == visited.end()) {
                pq.push(child);
                visited.insert(child->state);
            }
        }
    }
    cout << "No Solution Found!" << endl;
}

int main() {
    vector<vector<int>> start = {
        {2, 8, 3},
        {1, 6, 4},
        {7, 0, 5}
    };
    vector<vector<int>> goal = {
        {1, 2, 3},
        {8, 0, 4},
        {7, 6, 5}
    };

    cout << "Initial State: " << endl;
    printState(start);
    cout << "Final State: " << endl;
    printState(goal);

    cout << "Solving Puzzle using AO* Search: " << endl;
    aoStarSearch(start, goal);
    return 0;
}