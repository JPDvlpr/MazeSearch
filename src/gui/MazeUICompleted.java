package gui;

import generation.AlgorithmType;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.paint.Color;
import structures.DisjointSets;

/**
 * MazeUICompleted Overrides the MazeUI parent methods user selects an ENUM type and the appropriate
 * method is executed
 *
 * @author Jhakon Pappoe
 * @version 0.1
 */
public class MazeUICompleted extends MazeUI {

    private int[] graphSize = {getCols() * getRows() - 1};

    /**
     * This method can be passed one of three values: GENERATE_MAZE, DFS, or BFS Once you have a
     * graph representation of your maze, you are ready to begin drawing the maze.
     *
     * @param type the algorithm to execute
     */
    @Override
    public void runAlgorithm(AlgorithmType type) {
        switch (type) {
            case GENERATE_MAZE:
                generateMaze();
            case BFS:
                bfs();
                break;
            case DFS:
                dfs();
                break;
            default:
        }
    }

    //Generate a random maze using the union-find algorithm and a graph structure.
    private void generateMaze() {
        clearScreen();
        drawBoundary();
    }

    //traversal algorithms
    private void bfs() {
        //highlights the solution path of the maze using the BFS algorithm

    }

    //traversal algorithms
    private void dfs() {
        //highlights the solution path of the maze using the DFS algorithm

    }

    private void drawBoundary() {
        boolean[] walls = {true, true, true, true};

        setStrokeWidth(10);
        setStrokeColor(Color.BLACK);
        setFillColor(Color.RED);

        for (int i = 0; i < getRows() * getCols(); i++) {
//            if (i < 1){
//                walls[3] =
//            }
            drawCell(i, walls);
        }
        adjacentIndices();
    }

    private int adjacentIndices() {
        DisjointSets disjointSets = new DisjointSets(getCols() * getRows() - 1);
        ArrayList neighbors = new ArrayList();
        int index = randomIndex(graphSize) / getCols();
        int row = index / getCols();
        int col = index % getCols();
        int west = index - 1;
        int east = index + 1;
        int north = index - getCols();
        int south = index + getCols();
        int vertexSize;
        disjointSets.union(index, randomIndex(graphSize));

//        Your application should then track the vertices 0, 1, 2, ... , (rows * cols) - 1
//        by adding them to a DisjointSets object. Afterwards, you can randomly choose two cells
//        that are neighbors and union the sets containing the cells. This should continue until
//        all elements belong in the same set.
        //while number of edges is |V| - 1
        while (disjointSets.size() > 0) {
            switch (neighbors.size()){
                case(1):
                    break;
                case(1):
                    break;
                case(1):
                    break;
            }
            if (col < 1) {
                //index is at top left
                if (row < 1) {
                    neighbors[0] = east;
                    neighbors[1] = south;
                    disjointSets.union(index, randomIndex(neighbors));

//                    return randomIndex(neighbors);

                } else if (row > getRows() - 1) {
                    //index is at bottom left
                    neighbors[0] = east;
                    neighbors[1] = north;
                    disjointSets.union(index, randomIndex(neighbors));

//                    return randomIndex(neighbors);
                } else {
                    //index is in the middle on the left
                    neighbors[0] = north;
                    neighbors[1] = east;
                    neighbors[2] = south;
                    disjointSets.union(index, randomIndex(neighbors));

//                    return randomIndex(neighbors);
                }
            } else if (col > getCols() - 1) {
                //index is at top right
                if (row < 1) {
                    neighbors[0] = west;
                    neighbors[1] = south;
                    disjointSets.union(index, randomIndex(neighbors));

//                    return randomIndex(neighbors);
                    //index is at bottom right
                } else if (row > getRows() - 1) {
                    //index is at bottom left
                    neighbors[0] = west;
                    neighbors[1] = north;
                    disjointSets.union(index, randomIndex(neighbors));

//                    return randomIndex(neighbors);
                } else {
                    //index is in the middle on the right
                    neighbors[0] = north;
                    neighbors[1] = west;
                    neighbors[2] = south;
                    disjointSets.union(index, randomIndex(neighbors));
//                    return randomIndex(neighbors);
                }
            } else {
                //index is in the middle
                neighbors[0] = north;
                neighbors[1] = west;
                neighbors[2] = south;
                neighbors[3] = east;
                disjointSets.union(index, randomIndex(neighbors));

            }
        }
        return -1;
    }

    private int randomIndex(int[] graph) {
        int random = (int) (Math.random() * graph.length);
        return graph[random];
    }

    @Override
    public String toString() {
        return "MazeUICompleted{" +
            "graphSize=" + Arrays.toString(graphSize) +
            '}';
    }
}