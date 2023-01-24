import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private Percolation m_Percolation;
    private double[] m_OpenNums;
    private int m_Size;
    private int m_Trials;

    private void OpenRandomSite() {
        while (true) {
            int row = StdRandom.uniformInt(1, m_Size + 1);
            int col = StdRandom.uniformInt(1, m_Size + 1);
            if (m_Percolation.isFull(row, col)) {
                m_Percolation.open(row, col);
                break;
            }
        }
    }

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        m_Percolation = new Percolation(n);
        m_OpenNums = new double[trials];
        m_Size = n;
        m_Trials = trials;
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(m_OpenNums);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(m_OpenNums);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(m_Trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(m_Trials);
    }

    public static void main(String args[]) {
        // Set the stats
        // Command line arguments: n and T
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        
        // Monto Carlo simulation
        for (int i = 0; i < ps.m_Trials; i++) {
            ps.m_Percolation = new Percolation(ps.m_Size);
            while (!ps.m_Percolation.percolates()) {
                ps.OpenRandomSite();
            }
            ps.m_OpenNums[i] = ps.m_Percolation.numberOfOpenSites() / (double) (ps.m_Size * ps.m_Size);
        }
        
        // Print the results
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
