/* *****************************************************************************
 *  Name:              Sohan Kolla
 *  Coursera User ID:  sohan.kolla03@gmail.com
 *  Last modified:     08/01/2023
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int VIRTUALTOP = 0;
    private WeightedQuickUnionUF uf;
    private final int size;
    private boolean[][] status;
    private int openSites = 0;
    private int[][] grid;
    private int virtualBottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        size = n;
        virtualBottom = n * n + 1;
        uf = new WeightedQuickUnionUF(
                size * size + 2); // n^2 objects + virtual top + virtual bottom
        grid = new int[n][n];
        status = new boolean[n][n];
        int name = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                status[i][j] = false;
                grid[i][j] = name;
                name++;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row > size || row < 1 || col > size || col < 1) throw new IllegalArgumentException();
        if (!status[row - 1][col - 1]) {
            status[row - 1][col - 1] = true;
            if (row == 1) {
                uf.union(grid[row - 1][col - 1],
                         VIRTUALTOP); // if opened site is on top row, connects to virtual
            }
            else if (row == size) {
                uf.union(grid[row - 1][col - 1],
                         virtualBottom); // if opened site is on bottom row, connects to virtual
            }
            if (row != 1 && isOpen(row - 1, col)) { // these sites can check above them
                uf.union(grid[row - 1][col - 1], grid[row - 2][col - 1]);
            }
            if (row != size && isOpen(row + 1, col)) { // these sites can check below them
                uf.union(grid[row - 1][col - 1], grid[row][col - 1]);
            }
            if (col != 1 && isOpen(row, col - 1)) { // these sites can check to the left of them
                uf.union(grid[row - 1][col - 1], grid[row - 1][col - 2]);
            }
            if (col != size && isOpen(row, col + 1)) { // these sites can check to the right of them
                uf.union(grid[row - 1][col - 1], grid[row - 1][col]);
            }
            openSites++; // increments open site counter
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > size || row < 1 || col > size || col < 1) throw new IllegalArgumentException();
        return status[row - 1][col - 1];
    }

    // is the site (row, col) full? - Does it connect to the top?
    public boolean isFull(int row, int col) {
        if (row > size || row < 1 || col > size || col < 1) throw new IllegalArgumentException();
        return uf.find(grid[row - 1][col - 1]) == uf.find(VIRTUALTOP);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(virtualBottom) == uf.find(VIRTUALTOP);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
