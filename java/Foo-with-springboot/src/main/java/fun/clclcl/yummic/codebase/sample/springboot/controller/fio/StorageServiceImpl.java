package fun.clclcl.yummic.codebase.sample.springboot.controller.fio;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
public class StorageServiceImpl implements StorageService {

    String rootPath = "D:/tmp/mvcupload/";

    @Override
    public void init() throws IOException {
        Path path = Paths.get(rootPath);
        if (!Files.exists(path))
            Files.createDirectory(path);
    }

    public static void main(String[] args) {
        Path path = Paths.get("/mvcupload/", "aa.txt");
        System.out.println(path.toFile().getAbsoluteFile());
    }

    @Override
    public void store(MultipartFile file) {
        try {
            System.out.println("*************************************** store : " + file.getOriginalFilename());
            //new File("D:/mvcupload/" + "aa.txt").createNewFile();
            Files.copy(file.getInputStream(), Paths.get(rootPath, file.getOriginalFilename()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Stream<Path> loadAll() throws IOException {
        return Files.list(Paths.get(rootPath));
    }

    @Override
    public Path load(String filename) {
        return Paths.get(rootPath, filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        return new FileSystemResource(Paths.get(rootPath, filename));
    }

    @Override
    public void deleteAll() throws IOException {

    }
}
