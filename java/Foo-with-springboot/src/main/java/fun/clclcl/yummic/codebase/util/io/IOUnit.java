package fun.clclcl.yummic.codebase.util.io;

import java.util.concurrent.TimeUnit;

/**
 * Enum file size unit, referenced the implementtion of {@link TimeUnit};
 */
public enum IOUnit {
    /**
     * Size unit representing 8 bits.
     */
    BYTE {
        public long toByte(long s) { return s; }
        public long toKB(long s) { return div(s, sk/sb); }
        public long toMB(long s) { return div(s, sm/sb); }
        public long toGB(long s) { return div(s, sg/sb); }
        public long toTB(long s) { return div(s, st/sb); }
        public long toPB(long s) { return div(s, sp/sb); }
    },

    /**
     * Size unit representing 1024 Byte.
     */
    KB {
        public long toByte(long s) { return mul(s, sk/sb); }
        public long toKB(long s) { return s; }
        public long toMB(long s) { return div(s, sm /sk); }
        public long toGB(long s) { return div(s, sg /sk); }
        public long toTB(long s) { return div(s, st /sk); }
        public long toPB(long s) { return div(s, sp /sk); }
    },

    /**
     * Size unit representing 1024 KB.
     */
    MB {
        public long toByte(long s) { return mul(s, sm/sb); }
        public long toKB(long s) { return mul(s, sm/sk); }
        public long toMB(long s) { return s; }
        public long toGB(long s) { return div(s, sg /sm); }
        public long toTB(long s) { return div(s, st /sm); }
        public long toPB(long s) { return div(s, sp /sm); }
    },
    /**
     * Size unit representing 1024 MB.
     */
    GB {
        public long toByte(long s) { return mul(s, sg/sb); }
        public long toKB(long s) { return mul(s, sg/sk); }
        public long toMB(long s) { return mul(s, sg/sm); }
        public long toGB(long s) { return s; }
        public long toTB(long s) { return div(s, st /sg); }
        public long toPB(long s) { return div(s, sp /sg); }
    },

    /**
     * Size unit representing 1024 GB.
     */
    TB {
        public long toByte(long s) { return mul(s, st/sb); }
        public long toKB(long s) { return mul(s, st/sk); }
        public long toMB(long s) { return mul(s, st/sm); }
        public long toGB(long s) { return mul(s, st/sg);  }
        public long toTB(long s) { return s; }
        public long toPB(long s) { return div(s, sp /st); }
    },
    /**
     * Size unit representing 1024 TP.
     */
    PB {
        public long toByte(long s) { return mul(s, sp/sb); }
        public long toKB(long s) { return mul(s, sp/sk); }
        public long toMB(long s) { return mul(s, sp/sm); }
        public long toGB(long s) { return mul(s, sp/sg);  }
        public long toTB(long s) { return mul(s, sp /st); }
        public long toPB(long s) { return s; }
    };

    final long sb = 1L;
    final long sk = sb * 1024;
    final long sm = sk * 1024;
    final long sg = sm * 1024;
    final long st = sg * 1024;
    final long sp = st * 1024;

    final long MAX = Long.MAX_VALUE;

    long div(long d, long m) {
        checkNeg(d, m);
        return d / m;
    }

    long mul(long d, long m) {
        checkNeg(d, m);
        if (d > MAX / m) {
            return MAX;
        }
        return d * m;
    }

    private void checkNeg(long ... nums) {
        for (long num : nums) {
            if (num < 0)
                throw new NumberFormatException("Unsupport negtive number.");
        }
    }

    long toByte(long s) {
        throw new AbstractMethodError();
    }

    public long toKB(long s) {
        throw new AbstractMethodError();
    }

    public long toMB(long s) {
        throw new AbstractMethodError();
    }

    public long toGB(long s) {
        throw new AbstractMethodError();
    }

    public long toTB(long s) {
        throw new AbstractMethodError();
    }

    public long toPB(long s) {
        throw new AbstractMethodError();
    }

}
