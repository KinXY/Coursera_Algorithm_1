import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int m_n;
    private boolean[][] m_grid;
    // Quick union set for the grid
    // head is the top virtual node and end is the bottom virtual node
    // index of a node is (row - 1) * m_n + column
    private WeightedQuickUnionUF m_nodes;
    private WeightedQuickUnionUF m_nodesWithoutBottom;

    private int m_openNum;

    // get the index of the node in the UF
    private int GetUFIndex(int row, int column) {
        return (row - 1) * m_n + column;
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
        m_nodesWithoutBottom = new WeightedQuickUnionUF(n * n + 1);

        m_openNum = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > m_n || col < 1 || col > m_n) {
            throw new IllegalArgumentException();
        }
        if (isOpen(row, col)) {
            return;
        }

        m_grid[row - 1][col - 1] = true;
        // union the site with open sites next to it
        if (row > 1 && isOpen(row - 1, col)) {
            m_nodes.union(GetUFIndex(row - 1, col), GetUFIndex(row, col));
            m_nodesWithoutBottom.union(GetUFIndex(row - 1, col), GetUFIndex(row, col));
        }
        if (row < m_n && isOpen(row + 1, col)) {
            m_nodes.union(GetUFIndex(row + 1, col), GetUFIndex(row, col));
            m_nodesWithoutBottom.union(GetUFIndex(row + 1, col), GetUFIndex(row, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            m_nodes.union(GetUFIndex(row, col - 1), GetUFIndex(row, col));
            m_nodesWithoutBottom.union(GetUFIndex(row, col - 1), GetUFIndex(row, col));
        }
        if (col < m_n && isOpen(row, col + 1)) {
            m_nodes.union(GetUFIndex(row, col + 1), GetUFIndex(row, col));
            m_nodesWithoutBottom.union(GetUFIndex(row, col + 1), GetUFIndex(row, col));
        }
        // if the node is at the edge, union it to the virtual node
        if (row == 1) {
            m_nodes.union(0, GetUFIndex(row, col));
            m_nodesWithoutBottom.union(0, GetUFIndex(row, col));
        }
        if (row == m_n) {
            m_nodes.union(m_n * m_n + 1, GetUFIndex(row, col));
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
        
        if (!isOpen(row, col)) {
            return false;
        }
        int root1 = m_nodesWithoutBottom.find(0);
        int root2 = m_nodesWithoutBottom.find(GetUFIndex(row, col));
        return root1 == root2;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return m_openNum;
    }

    // does the system percolate?
    public boolean percolates() {
        int root1 = m_nodes.find(0);
        int root2 = m_nodes.find(m_n * m_n + 1);
        return root1 == root2;
    }
}
