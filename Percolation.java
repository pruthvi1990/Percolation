import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation
{
    private int[][] grid;
    private int gridSize;
    private WeightedQuickUnionUF obj;

    public Percolation(int N)
    {
        // create N-by-N grid, with all sites blocked
        if (N <= 0)
        {
            throw new IllegalArgumentException("N should be >= 0");
        }
        grid = new int [N][N];
        gridSize = N;
        for (int i = 0;
        i < N;
        i++)
        {
            for (int j = 0;
            j < N;
            j++)
            {
                grid[i][j] = -1;
                //all the grids are blocked
            }
        }
        obj = new WeightedQuickUnionUF((N*N)+2);
    }
    
    private int xyto1d(int x, int y)
    {
        int index = 0;
        if (x == 0)
        {
            //for first rows, index is just row+col
            index = x+y+1;
        }
        if (x > 0)
        {
            index = x*gridSize+1+y;
        }
        return index;
    }
    
    public void open(int i, int j)
    {
        // open site (row i, column j) if it is not open already
        if ((i <= 0 || j <= 0) || i > gridSize || j > gridSize)
        {
            throw new ArrayIndexOutOfBoundsException(String.valueOf(i) 
                                             + " "+ String.valueOf(j));
        }
        //open the site
        grid[i-1][j-1] = 0;
        int x = i-1;
        int y = j-1;
        if (x*gridSize+y < gridSize)
        {
            obj.union(0, this.xyto1d(x, y));
        }
        if (((x*gridSize+1) >= (gridSize*gridSize-gridSize)+1) 
                   && ((x*gridSize+1) <= (gridSize*gridSize)))
        {
            obj.union(gridSize*gridSize+1, this.xyto1d(x, y));
        }
        if ((x >= 0 && y > 0))
        {
            if (grid[x][y-1] == 0)
            {
                obj.union(this.xyto1d(x, y), this.xyto1d(x, y-1));
            }
        }
        if ((x >= 1 && y < gridSize-1 && x < gridSize-1))
        {
            if (grid[x][y+1] == 0)
            {
                obj.union(this.xyto1d(x, y), this.xyto1d(x, y+1));
            }
        }
        if ((x < gridSize-1))
        {
            if (grid[x+1][y] == 0)
            {
                obj.union(this.xyto1d(x, y), this.xyto1d(x+1, y));
            }
        }
        if (x > 0)
        {
            if (grid[x-1][y] == 0)
            {
                obj.union(this.xyto1d(x, y), this.xyto1d(x-1, y));
            }
        }
    }
    public boolean isOpen(int i, int j)
    {
        // is site (row i, column j) open?
        if ((i <= 0 || j <= 0) || i > gridSize || j > gridSize)
        {
            throw new ArrayIndexOutOfBoundsException(String.valueOf(i) 
                                             + " "+ String.valueOf(j));
        }
        return (grid[i-1][j-1] == 0);

    }
    public boolean isFull(int i, int j)
    {
        // is site (row i, column j) full?
        if ((i <= 0 || j <= 0) || i > gridSize || j > gridSize)
        {
            throw new ArrayIndexOutOfBoundsException(String.valueOf(i) 
                                             + " "+ String.valueOf(j));
        }
        return (obj.connected(0, this.xyto1d(i-1, j-1)));

    }
    public boolean percolates()
    {
        // does the system percolate?
        return (obj.connected(0, (gridSize*gridSize+1)));

    }
    public static void main(String[] args)
    {
        // test client (optional)
    }
}