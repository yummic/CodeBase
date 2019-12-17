package fun.clclcl.yummic.codebase.leetcode.num432;

/**
 * url:
 * https://leetcode-cn.com/problems/all-oone-data-structure/
 */
class AllOne {

    int capacity = 1024;

    int factor = 1;

    int max = 0;

    int maxIndex = -1;

    Node[] arr = null;

    /** Initialize your data structure here. */
    public AllOne() {
        arr = new Node[capacity];
    }

    /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
    public void inc(String key) {
        int hash = key.hashCode();
        int index = hash % capacity;

        if (arr[index] == null)
            arr[index] = new Node(key, 1);
        else {
            Node list = arr[index];
            while (list != null) {
                if (list.key.equals(key)) {
                    list.val++;
                    return;
                }
                list = list.next;
            }
            Node node = new Node(key, 1);
            node.next = list;
            arr[index] = node;
        }
    }

    private void markMax(int val, int index) {
        if (maxIndex < 0 || max < val) {
            maxIndex = index;
            max = val;
        }
    }

    /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
    public void dec(String key) {
        int hash = key.hashCode();
        int index = hash % capacity;

        if (arr[index] == null)
            return;

        Node list = arr[index];
        while (list != null) {
            if (list.key.equals(key)) {
                if (list.val == 1) {
                    Node tmp = list.next;
                    list.next = null;
                    arr[index] = tmp;
                } else {
                    list.val--;
                }
            }
            list = list.next;
        }
    }

    /** Returns one of the keys with maximal value. */
    public String getMaxKey() {
        Node max = null;
        for (Node node : arr) {
            if (node == null)
                continue;
            while (node != null) {
                if (max == null || max.val < node.val)
                    max = node;
                node = node.next;
            }
        }
        if (max == null)
            return "";
        else
            return max.key;
    }

    /** Returns one of the keys with Minimal value. */
    public String getMinKey() {
        Node max = null;
        for (Node node : arr) {
            if (node == null)
                continue;
            while (node != null) {
                if (max == null || max.val > node.val)
                    max = node;
                node = node.next;
            }
        }
        if (max == null)
            return "";
        else
            return max.key;
    }

    static class Node {
        String key;
        int val;
        Node next;

        public Node(String key, int val) {
            this.key = key;
            this.val = val;
        }
    }
}

/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne obj = new AllOne();
 * obj.inc(key);
 * obj.dec(key);
 * String param_3 = obj.getMaxKey();
 * String param_4 = obj.getMinKey();
 */