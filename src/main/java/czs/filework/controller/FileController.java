package czs.filework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
public class FileController {
    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    @RequestMapping(value="/upload")
    public String upload(@RequestParam("file") MultipartFile file){
        try {
            if(file.isEmpty()){
                return "file is empty";
            }
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            log.info("上传文件名为" + fileName);
            String filePath = "G:\\";
            String path = filePath + fileName;
            File dest = new File(path);

            if(!dest.getParentFile().exists()){
                dest.getParentFile().mkdirs();
            }

            file.transferTo(dest);
            return "upload success";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "upload fail";
    }

    @GetMapping(value = "/download")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response){
        String fileName = "";
        if(fileName != null){
            File file = new File("G:\\MyProject\\aas.txt");
            if(file.exists()) {
                response.setContentType("application/force-download");
                response.addHeader("Content-Disposition","attachment;fileName=" + fileName);
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while(i != -1){
                        os.write(buffer,0,i);
                        i = bis.read(buffer);
                    }

                    bis.close();
                    fis.close();
                    return "download success";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "download failed";
    }

}
