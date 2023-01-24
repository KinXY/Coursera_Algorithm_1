import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int m_n;
    private boolean[][] m_grid;
    // Quick union set for the grid
    // head is the top virtual node and end is the bottom virtual node
    // index of a node is (row - 1) * m_n + column
    private WeightedQuickUnionUF m_nodes;

    private int m_openNum;

    // get the index of the node in the UF
    private int GetUFIndex(int row, int column) {
        return (row - 1) * m_n + column;
    }

    public int GetN() {
        return m_n;
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        
        m_n = n;
        m_grid = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m_grid[i][j] = false;
            }
        }
        m_nodes = new WeightedQuickUnionUF(n * n + 2);
        // Union for virtual nodes
        for (int i = 1; i < n; i++) {
            m_nodes.union(0, GetUFIndex(1, i));
            m_nodes.union(n * n + 1, GetUFIndex(n, i));
        }

        m_openNum = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > m_n || col < 1 || col > m_n) {
            throw new IllegalArgumentException();
        }

        m_grid[row - 1][col - 1] = true;
        // union the site with open sites next to it
        if (row > 1 && isOpen(row - 1, col)) {
            m_nodes.union(GetUFIndex(row - 1, col), GetUFIndex(row, col));
        }
        if (row < m_n && isOpen(row + 1, col)) {
            m_nodes.union(GetUFIndex(row + 1, col), GetUFIndex(row, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            m_nodes.union(GetUFIndex(row, col - 1), GetUFIndex(row, col));
        }
        if (col < m_n && isOpen(row, col + 1)) {
            m_nodes.union(GetUFIndex(row, col + 1), GetUFIndex(row, col));
        }

        // update other metadata
        m_openNum++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > m_n || col < 1 || col > m_n) {
            throw new IllegalArgumentException();
        }

        return m_grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > m_n || col < 1 || col > m_n) {
            throw new IllegalArgumentException();
        }
        
        return !m_grid[row - 1][col - 1];
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return m_openNum;
    }

    // does the system percolate?
    public boolean percolates() {
        return m_nodes.connected(0, m_n * m_n + 1);
    }
}
