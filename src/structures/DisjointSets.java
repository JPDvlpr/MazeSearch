package structures;

import java.util.Arrays;

/**
 * @author Jhakon Pappoe
 * @version 0.1
 * Structure that tracks the element sets
 * aka: union-find algorithm
 */
public class DisjointSets {

    private int[] disjointSet;

    /**
     * @param size size of the disjoint sets array
     * Sets the value of each index in the array to -1
     */
    public DisjointSets(int size) {
        this.disjointSet = new int[size];
        for (int i = 0; i < disjointSet.length; i++) {
            disjointSet[i] = -1;
        }
    }

    /**
     * @param element element to be found in the array
     * @return the root element
     * determines the subset an element belongs to
     * checks if two elements are in the same subset
     */
    public int find(int element) {
        if (disjointSet[element] < 0) {
            return element;
        } else {
            return disjointSet[element] = find(disjointSet[element]);
        }
    }

    /**
     * @param first first index
     * @param second second index
     */
    public void union(int first, int second) {
        int firstRoot = find(first);
        int secondRoot = find(second);

        if (firstRoot != secondRoot) {
            if (disjointSet[firstRoot] < disjointSet[secondRoot]) {
                disjointSet[secondRoot] = firstRoot;
            } else if (disjointSet[secondRoot] < disjointSet[firstRoot]) {
                disjointSet[firstRoot] = secondRoot;
            } else if (disjointSet[firstRoot] == disjointSet[secondRoot]) {
                disjointSet[secondRoot] = firstRoot;
                disjointSet[firstRoot]--;
            }
        }
    }

    private int size(int[] disjointSet) {
        return disjointSet.length;
    }

    @Override
    public String toString() {
        return "DisjointSets{" +
            "disjointSet=" + Arrays.toString(disjointSet) +
            '}';
    }
}