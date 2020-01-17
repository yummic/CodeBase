package fun.clclcl.yummic.codebase.sample.springboot.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController()
@RequestMapping("")
public class PacController {

    @GetMapping("/proxy.pac")
    public ResponseEntity<InputStreamResource> getPac() throws FileNotFoundException {
        File file = new File("D:\\data\\proxy\\proxy.pac");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.valueOf("application/x-ns-proxy-autoconfig")).contentLength(file.length())
                .body(resource);
    }
}
