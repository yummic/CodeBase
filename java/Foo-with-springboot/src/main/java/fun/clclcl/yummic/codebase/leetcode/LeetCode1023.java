package fun.clclcl.yummic.codebase.leetcode;

import java.util.ArrayList;
import java.util.List;

public class LeetCode1023 {

    public List<Boolean> camelMatch(String[] queries, String pattern) {

        List<Boolean> result = new ArrayList<>();
        for (String query : queries) {
            boolean match = match(query, pattern);
            result.add(match);
        }

        return result;
    }

    private boolean match(String query, String pattern) {
        int i = 0, j =0;
        while (i < query.length() && j < pattern.length()) {
            char q = query.charAt(i);
            char p = pattern.charAt(j);
            if (!isUpperLetter(q)) {
                if (p == q){
                    i++;
                    j++;
                    continue;
                } else {
                    i++;
                    continue;
                }
            } else {
                if (q == p) {
                    i++;
                    j++;
                    continue;
                } else {
                    break;// not match.
                }
            }
        }

        if (i == query.length() -1 && j == pattern.length() -1) {
            return true;
        } else if (j == pattern.length()){
            while (i < query.length()) {
                if (isUpperLetter(query.charAt(i))) {
                    return false;
                }
                i++;
            }
            return true;
        }
        return false;
    }

    private boolean isUpperLetter(char charAt) {
        return charAt >= 'A' && charAt <= 'Z';
    }
}
