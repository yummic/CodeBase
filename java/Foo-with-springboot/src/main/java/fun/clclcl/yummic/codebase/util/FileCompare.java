package fun.clclcl.yummic.codebase.util;

import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

public class FileCompare {

    /**
     * compare file name and content with MD5sum.
     * @param f1
     * @param f2
     * @return
     * @throws IOException
     */
    public boolean isEqual(File f1, File f2) throws IOException {
        if (f1.getName().equals(f2.getName()) && f1.exists() && f2.exists()) {
            try (FileInputStream in1 = new FileInputStream(f1); FileInputStream in2 = new FileInputStream(f2)) {
                byte[] md51 = DigestUtils.md5Digest(in1);
                byte[] md52 = DigestUtils.md5Digest(in2);
                //compare content equal with md5?
                return MessageDigest.isEqual(md51, md52);
            }
        }
        return false;
    }

}
