import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] m_grid;
    

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("negative n");
        }
        m_grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m_grid[i][j] = i * n + j;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

    }

    // returns the number of open sites
    public int numberOfOpenSites() {

    }

    // does the system percolate?
    public boolean percolates() {

    }

    public static void main(String[] args) {

    }
}
