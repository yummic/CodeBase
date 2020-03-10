package fun.clclcl.yummic.codebase.leetcode;

/**
 * Jump Game V
 * <br/>
 * https://leetcode.com/problems/jump-game-v/submissions/
 *
 */
public class LeetCode1340 {

    public int maxJumps(int[] arr, int d) {
        int max[] = new int[arr.length];

        int maxnum = 0;
        for (int i = 0; i < arr.length; i++) {
            maxnum = max(maxnum, getMax(i, arr, d, max));
        }
        return maxnum;
    }

    private int getMax(int index, int[] arr, int d, int[] max) {
        if (max[index] > 0) {
            return max[index];
        }
        if (isBotom(index, arr)) {
            max[index] = 1;
            return 1;
        }

        int maxnum = 0;
        for (int i = 1; i <= d; i++) {
            if (index + i < arr.length && arr[index + i] < arr[index]) {
                int maxtmp = getMax(index + i, arr, d, max);
                maxnum = max(maxnum, maxtmp + 1);
            } else {
                break;
            }
        }
        for (int i = 1; i <= d; i++) {
            if (index - i >= 0  && arr[index - i] < arr[index]) {
                int maxtmp = getMax(index - i, arr, d, max);
                maxnum = max(maxnum, maxtmp + 1);
            } else {
                break;
            }
        }
        max[index] = maxnum;
        return maxnum;
    }

    private boolean isBotom(int index, int[] arr) {
        int left;
        if (index == 0) {
            left = arr[0];
        } else {
            left = arr[index - 1];
        }
        int right;
        if (index == arr.length - 1) {
            right = arr[index];
        } else {
            right = arr[index + 1];
        }
        int min = min(left, arr[index]);
        min = min(min, right);
        if (min == arr[index]) {
            return true;
        }
        return false;
    }

    int min(int i, int j) {
        return i > j ? j : i;
    }

    int max(int i, int j) {
        return i > j ? i : j;
    }
}
