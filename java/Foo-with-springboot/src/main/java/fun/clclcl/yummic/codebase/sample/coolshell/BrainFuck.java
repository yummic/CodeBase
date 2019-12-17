package fun.clclcl.yummic.codebase.sample.coolshell;

import java.io.PrintStream;
import java.util.Objects;

public class BrainFuck {

    int printNum = 64;

    int printIndex = 0;

    char[] prints = new char[printNum];

    int[] val = null;

    int initlength = 10;

    public static BrainFuck parse(String string) {
        BrainFuck brainFuck = new BrainFuck();
        brainFuck.realParse(string);
        return brainFuck;
    }

    private void realParse (String string) {
        Objects.nonNull(string);
        char[] chars = string.toCharArray();
        LinkedNode current = new LinkedNode();

        for (int index = 0; index < chars.length; index++) {
            char ch = chars[index];
            switch (ch) {
                case '+':
                    current.increament();
                    break;
                case '-' :
                    current.decrement();
                    break;
                case '>':
                    current = current.nextNode();
                    break;
                case '<':
                    current = current.preNode();
                    break;
                case '.' :
                    prePrint(current.getValue());
                    break;
                case '[':
                    if (current.isZero()) {
                        // jump to loop end.
                        int end = findEndPair(chars, index + 1);
                        if (end == -1) {
                            error("No pair end found.");
                        }
                        index = end-1;
                    }
                    break;
                case ']':
                    if (!current.isZero()) {
                        // jump to loop start.
                        int start = findStartPair(chars, index - 1);
                        if (start == -1) {
                            error("No pair start found.");
                        }
                        index = start-1;
                    }
                    break;
                default:
                    //invalid char. just ignore.
                    break;
            }
        }
    }

    public void print(PrintStream stream) {
        stream.print(prints);
    }

    private void error(String err) {
        throw new RuntimeException(err);
    }

    private int findEndPair(char[] chars, int start) {
        int child = 0;
        for (int i = start; i < chars.length; i++) {
            if (chars[i] == '[') {
                child++;
            } else if (chars[i] == ']') {
                if (child == 0)
                    return i;
                else
                    child--;
            }
        }
        return -1;
    }

    private int findStartPair(char[] chars, int start) {
        int child = 0;
        for (int i = start; i >= 0; i--) {
            if (chars[i] == ']') {
                child++;
            } else if (chars[i] == '[') {
                if (child == 0)
                    return i;
                else
                    child--;
            }
        }
        return -1;
    }

    private void prePrint(int c) {
        prints[printIndex] = (char)c;
        printIndex++;
        if (printIndex == printNum) {
            //expend char array. or print ??

        }
    }


    private BrainFuck() {

    }

}

class LinkedNode {

    int v = 0;

    private LinkedNode pre;

    private LinkedNode next;

    public int getValue() {
        return v;
    }

    public boolean isZero() {
        return v == 0;
    }

    public void increament() {
        v++;
    }

    public void decrement() {
        v--;
    }

    public LinkedNode nextNode() {
        if (next == null) {
            next = new LinkedNode();
            next.pre = this;
        }
        return next;
    }

    public LinkedNode preNode() {
        if (pre == null) {
            pre = new LinkedNode();
            pre.next = this;
        }
        return pre;
    }

    public static void main(String[] args) {
        String str = "++++++++[>+>++>+++>++++>+++++>++++++>+++++++>++++++++>+++++++++>++++++++++>+++++++++++>++++++++++++>+++++++++++++>++++++++++++++>+++++++++++++++>++++++++++++++++<<<<<<<<<<<<<<<<-]>>>>>>>>>>>>>>>-.+<<<<<<<<<<<<<<<>>>>>>>>>>>>>---.+++<<<<<<<<<<<<<>>>>>>>>>>>>>>----.++++<<<<<<<<<<<<<<>>>>>>>>>>>>+++.---<<<<<<<<<<<<>>>>>>>>>>>>>>-.+<<<<<<<<<<<<<<>>>>>>>>>>>>>>---.+++<<<<<<<<<<<<<<>>>>>>>>>>>>>---.+++<<<<<<<<<<<<<>>>>>>--.++<<<<<<>>>>>>>>>>>>>.<<<<<<<<<<<<<>>>>>>>>>>>>>>>----.++++<<<<<<<<<<<<<<<>>>>>>>>>>>>>>---.+++<<<<<<<<<<<<<<>>>>>>>>>>>>>>----.++++<<<<<<<<<<<<<<.";
        BrainFuck brainFuck = BrainFuck.parse(str);
        brainFuck.print(System.out);
    }
}
