import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // private Percolation[] m_Percolations;
    private double[] m_OpenNums;
    private int m_Size;
    private int m_Trials;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        // The autograder forbids member variables www
        Percolation[] m_Percolations;
        m_Percolations = new Percolation[trials];
        for (int i = 0; i < trials; i++) {
            m_Percolations[i] = new Percolation(n);
        }
        // initialize the metadata
        m_OpenNums = new double[trials];
        m_Size = n;
        m_Trials = trials;

        // Monto Carlo simulation
        for (int i = 0; i < m_Trials; i++) {
            while (!m_Percolations[i].percolates()) {
                // Open one random site
                while (true) {
                    int row = StdRandom.uniformInt(1, m_Size + 1);
                    int col = StdRandom.uniformInt(1, m_Size + 1);
                    if (!m_Percolations[i].isOpen(row, col)) {
                        m_Percolations[i].open(row, col);
                        break;
                    }
                }
            }
            m_OpenNums[i] = m_Percolations[i].numberOfOpenSites() / (double) (m_Size * m_Size);
        }
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
        PercolationStats mySimulation = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        // Print the results
        System.out.println("mean                    = " + mySimulation.mean());
        System.out.println("stddev                  = " + mySimulation.stddev());
        System.out.println("95% confidence interval = [" + mySimulation.confidenceLo() + ", " + mySimulation.confidenceHi() + "]");
    }
}
