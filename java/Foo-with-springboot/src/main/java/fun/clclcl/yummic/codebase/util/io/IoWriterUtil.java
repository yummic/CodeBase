package fun.clclcl.yummic.codebase.util.io;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.Random;

public class IoWriterUtil {

    RestTemplate tt;

    RestTemplateBuilder dd;


    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        String filepath = "D:\\iotest.io";
        int size = 10;
        stopWatch.start("write io");
        try {
            for (int i = 0; i < 1; i++) {
                IoWriterUtil.randomWrite(filepath + i, size, IOUnit.GB, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopWatch.stop();
        System.out.println("write " + size + " MB to file : " + filepath + ", cost time (seconds):" + stopWatch.getLastTaskInfo().getTimeSeconds());
    }

    public static void randomWrite(String filepath, int size, IOUnit unit, boolean delete) throws IOException {
        printProcess("Start...");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("create file.");
        File file = new File(filepath);
        if (file.exists()) {
            throw new FileAlreadyExistsException("overwrite is dangerous!");
        }
        if (!file.createNewFile()) {
            throw new FileNotFoundException("Create File failed.");
        }
        stopWatch.stop();
        printProcess(stopWatch.getLastTaskName() + ", cost time : " + stopWatch.getLastTaskTimeMillis());
        stopWatch.start("write io");
        Random ran = new Random();
        try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file), 8192 * 1024)) {
            byte[] bytes = new byte[1024];
            for (int i = 0; i < unit.toKB(size); i++) {
                ran.nextBytes(bytes);
                bos.write(bytes);
            }
        } catch (IOException e) {
            throw e;
        }
        stopWatch.stop();
        printProcess(stopWatch.getLastTaskName() + ", cost time : " + stopWatch.getLastTaskTimeMillis());
        if (delete) {
            stopWatch.start("delete file.");
            file.deleteOnExit();
            stopWatch.stop();
            printProcess(stopWatch.getLastTaskName() + ", cost time : " + stopWatch.getLastTaskTimeMillis());
        }
        printProcess("finish. write size:" + unit.toMB(size) + " MB, cost time : " + stopWatch.getTotalTimeMillis());
    }

    private static void printProcess(String s) {
        System.out.println("[IOWriter] " + s);
    }
}
