package good.ncu.yjs_bb.back.controller;

import com.alibaba.fastjson.JSON;
import good.ncu.yjs_bb.back.service.ApplyService;
import good.ncu.yjs_bb.back.service.FileService;
import good.ncu.yjs_bb.back.service.UserService;
import org.apache.commons.io.FilenameUtils;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.soap.Addressing;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class FileController implements WebMvcConfigurer {

    @Autowired
    FileService fileService;

    @Autowired
    ApplyService applyService;

    // 查询用户提交过的文件
    @RequestMapping("/getFileByUserID")
    public Map<String, Object> getFileByUserID(@RequestParam("user_id") Integer user_id) {
        Map<String, Object> resultMap = new HashMap<>();

        //判空
        if(user_id == null){
            resultMap.put("msg", "用户ID为空");
            resultMap.put("code", "400");
        }

        //查询请求表是否存在这个用户id，如果没有，则直接返回
        int i = applyService.findUserID(user_id);
        if(i == 0){
            // 找不到该用户的记录，用户记录不在了或假删除了
            resultMap.put("msg", "用户请求不在了");
            resultMap.put("code", "400");
        }



        good.ncu.yjs_bb.back.entity.File file_pdf = fileService.getFileByUserID(user_id, "0");
        good.ncu.yjs_bb.back.entity.File file_zc = fileService.getFileByUserID(user_id, "1");

        if(file_pdf != null){
            resultMap.put("pdf_url", file_pdf.getFile_url());
            resultMap.put("pdf_name", file_pdf.getFile_name());
        }


        if(file_zc != null){
            resultMap.put("zc_url", file_zc.getFile_url());
            resultMap.put("zc_name", file_zc.getFile_name());
        }

        return resultMap;
    }


    // 将文件相关信息保存到数据库
    @RequestMapping("/addFile")
    public String addFile(@RequestBody good.ncu.yjs_bb.back.entity.File file) {
        if(file == null){
            return "error";
        }

        int i = fileService.addFile(file);
        System.out.println("添加文件接口调用成功");
        return i > 0 ? "success" : "error";
    }


    // 更新上传的文件
    @RequestMapping("/rev_File")
    public String rev_File(@RequestBody good.ncu.yjs_bb.back.entity.File file) {
        if(file == null){
            return "error";
        }

        int i = fileService.updateFile(file);
        System.out.println("更新文件接口调用成功");
        return i > 0 ? "success" : "error";
    }

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("/yyyy/MM/dd/");

    // 将 yml 中的自定义配置注入到这里
    @Value("${gorit.file.root.path}")
    private String filePath;

    // PDF文件上传
    @PostMapping("/upload_pdf")
    public Map<String, Object> fileUpload_pdf(MultipartFile file, HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<>();

        if(file == null){
            resultMap.put("msg","无上传内容！");
            resultMap.put("status", "error");
            return resultMap;
        }


        // 【1】得到上传时的文件名
        String originName = file.getOriginalFilename();
        if (!originName.endsWith(".pdf")) {
            resultMap.put("status", "error");
            resultMap.put("msg", "文件类型不对，必须为pdf");
            return resultMap;
        }


        String strFormat = simpleDateFormat.format(new Date());
        String realPath = filePath + "/PDF" + strFormat;

        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // 保存文件对象，加上uuid是为了防止文件重名
        String strNewFileName = UUID.randomUUID().toString().replaceAll("-", "") + ".pdf";

        try {
            file.transferTo(new File(folder, strNewFileName));
            //【3】生成浏览器路径
            String strUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/PDF" + strFormat + strNewFileName;
            System.out.println(strUrl);
            resultMap.put("status", "success");
            resultMap.put("review_url", strUrl);
            resultMap.put("real_Path", realPath);
            resultMap.put("file_name", originName);
        } catch (IOException e) {
            e.printStackTrace();
            resultMap.put("status", "error");
            resultMap.put("msg", e.getMessage());
        }


        return resultMap;
    }

    // 支持材料上传
    @PostMapping("/upload_file")
    public Map<String, Object> fileUpload_file(MultipartFile file, HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<>();

        if(file == null){
            resultMap.put("msg","无上传内容！");
            resultMap.put("status", "error");
            return resultMap;
        }

        //上传时文件名 【1】原始文件名
        String originName = file.getOriginalFilename();

        //上传时文件类型   【2】文件类型
        String originFileType = FilenameUtils.getExtension(originName);


        String strFormat = simpleDateFormat.format(new Date());

        System.out.println(new Date());

        // 设置上传路径
        // 【2】真实存储路径
        String realPath = filePath + "/ZC" + strFormat;

        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // 保存文件对象，加上uuid是为了防止文件重名
        String strNewFileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + originFileType;
        try {
            file.transferTo(new File(folder, strNewFileName));
            //【3】生成浏览器路径
            String strUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/ZC" + strFormat + strNewFileName;
            System.out.println(strUrl);
            resultMap.put("status", "success");
            resultMap.put("review_url", strUrl);
            resultMap.put("real_Path", realPath);
            resultMap.put("file_name", originName);
            resultMap.put("file_type", originFileType);
        } catch (IOException e) {
            e.printStackTrace();
            resultMap.put("status", "error");
            resultMap.put("msg", e.getMessage());
        }

        return resultMap;
    }


    // 文件服务器，虚拟路由，默认的会存放到tmp路径，定期被清理
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("file:E:/good_Project/JX_ShuangYiLiu/back/file/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }


}
