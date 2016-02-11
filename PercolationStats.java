
    import edu.princeton.cs.algs4.StdRandom;
    import edu.princeton.cs.algs4.StdStats;
     
    public class PercolationStats {
       
        private double[] arrayMean;
        private int testNum;
        private int index = 0;
        private int openCount = 0;
        private int firstRand, secondRand;
        
        public PercolationStats(int N, int T)  {
            // perform T independent experiments on an N-by-N grid
            if (N <= 0 || T <= 0) {
                throw new IllegalArgumentException("N & T should be > 0");
            }
            arrayMean = new double[T];
            testNum = T;  
             while (T-- > 0) {
               openCount = 0;
               
               
               Percolation gridObj  = new Percolation(N);
               while (!gridObj.percolates()) {
                   
                   firstRand = StdRandom.uniform(N+1);
                   while (firstRand == 0)
                       firstRand = StdRandom.uniform(N+1);
                   
                   secondRand = StdRandom.uniform(N+1);
                   while (secondRand == 0)
                       secondRand = StdRandom.uniform(N+1);
                   
                   //  System.out.print(firstRand+" "+secondRand + "\n");
                   
                   if (!gridObj.isOpen(firstRand, secondRand)) {
                       gridObj.open(firstRand, secondRand);
                       openCount++;
                   }                    
               }
               arrayMean[index++] = openCount/ (double) (N*N);
           }
        }
       
        public double mean() {
            // sample mean of percolation threshold
           return StdStats.mean(arrayMean);
     
        }
        public double stddev() {
            // sample standard deviation of percolation threshold
            return StdStats.stddev(arrayMean);
        }
        public double confidenceLo() {
            // low  endpoint of 95% confidence interval
            double resultLo;
            resultLo = this.mean() - ((1.96*(this.stddev()))/Math.sqrt(testNum));
           
            return resultLo;
        }
        public double confidenceHi() {
            // high endpoint of 95% confidence interval
            double resultHi;
            resultHi = this.mean() + ((1.96*(this.stddev()))/Math.sqrt(testNum));
           
            return resultHi;
        }
     
       public static void main(String[] args)  {
    // test client (described below)
           int T, N;
           N = Integer.parseInt(args[0]);
           T = Integer.parseInt(args[1]);
          
           int index = 0;
           int openCount = 0;
         
           if (N <= 0 || T <= 0) {
                    throw new IllegalArgumentException("N & T should be > 0");
           }
           
           PercolationStats stat_obj = new PercolationStats(N, T);
           
          
           System.out.print("mean                    = "+ stat_obj.mean()+ "\n");
           System.out.print("stddev                  = "+ stat_obj.stddev()+ "\n");
           System.out.print("95% confidence interval = "+ stat_obj.confidenceLo()
           +", "+stat_obj.confidenceHi()+"\n"); 
       }
       
    }
