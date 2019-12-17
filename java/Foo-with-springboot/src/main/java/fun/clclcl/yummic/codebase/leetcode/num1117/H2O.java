package fun.clclcl.yummic.codebase.leetcode.num1117;

/**
 * url:
 * https://leetcode-cn.com/problems/building-h2o/submissions/
 */
class H2O {

    private int ooo = 0;

    private int hhh = 0;

    private Object lock = new Object();

    public H2O() {

    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {

        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        synchronized(lock) {
            while (hhh == 2 && ooo == 0) {
                lock.wait();
            }
            if (ooo == 1 && hhh == 2) {
                ooo = 0;
                hhh = 0;
                releaseHydrogen.run();
                hhh++;
            } else if (hhh < 2) {
                // releaseOxygen.run() outputs "H". Do not change or remove this line.
                releaseHydrogen.run();
                hhh++;
            }


            lock.notifyAll();
        }

    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {

        synchronized(lock) {
            while(ooo == 1 && hhh < 2) {
                lock.wait();
            }
            if (ooo == 1 && hhh == 2) {
                ooo = 0;
                hhh = 0;
                releaseOxygen.run();
                ooo++;
            } else  if (ooo == 0) {
                releaseOxygen.run();
                ooo++;
            }
            lock.notifyAll();
        }
    }
}
