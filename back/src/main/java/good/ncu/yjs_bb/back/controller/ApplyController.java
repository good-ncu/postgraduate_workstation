package good.ncu.yjs_bb.back.controller;

import com.alibaba.fastjson.JSON;
import good.ncu.yjs_bb.back.entity.Apply;
import good.ncu.yjs_bb.back.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class ApplyController {
    @Autowired
    ApplyService applyService;


    @RequestMapping("/getUserWork")
    public String getUserWork(Integer user_id) {
        if (user_id == null) {
            return "null_error";
        }
        Apply apply = applyService.getUserWork(user_id);
        List<Apply> applies = new ArrayList<>();
        applies.add(apply);
        String string = JSON.toJSONString(applies);//注意这里也可以直接返回对象，springboot会把对象直接转换成字符串
        System.out.println("/getUserWork接口调用成功");
//        System.out.println(string);
        if (string.equals("[null]")) {
            System.out.println(1);
            return "error";
        }
        return string;
    }


    // 添加申请
    @RequestMapping("/addApply")
    public String addApply(@RequestBody Apply apply) {
        if (apply == null) {
            return "error";
        }

        int i = applyService.addApply(apply);
        return i > 0 ? "success" : "error";
    }

    // 更新申请
    @RequestMapping("/rev_Apply")
    public String rev_Apply(@RequestBody Apply apply) {
        if (apply == null) {
            return "error";
        }

        int i = applyService.rev_Apply(apply);
        return i > 0 ? "success" : "error";
    }


    @RequestMapping("/getApply_gx")
    public String getApply_gx(@RequestParam("query_lhpy") String query_lhpy, @RequestParam("query_city") String query_city, @RequestParam("query_lx") String query_lx, @RequestParam("query_status") String query_status, @RequestParam("univ_code") String univ_code, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        // 获取最大列表数和当前编号
        int number = applyService.getApply_gx_count("%" + query_lhpy + "%", "%" + query_city + "%", query_lx, query_status, univ_code);
        int pageState = (pageNum - 1) * pageSize;

        List<Apply> apply = applyService.getAllApply_gx("%" + query_lhpy + "%", "%" + query_city + "%", query_lx, query_status, univ_code, pageState, pageSize);
        HashMap<String, Object> res = new HashMap<>();

        res.put("number", number);
        res.put("data", apply);
        String res_string = JSON.toJSONString(res);

        System.out.println("获取审核列表成功");

        return res_string;
    }

    @RequestMapping("/getApply_gx_and_year")
    public String getApply_gx_and_year(@RequestParam("query_lhpy") String query_lhpy, @RequestParam("query_city") String query_city, @RequestParam("query_lx") String query_lx, @RequestParam("query_status") String query_status, @RequestParam("univ_code") String univ_code, @RequestParam("query_year") String query_year, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        // 获取最大列表数和当前编号
        int number = applyService.getApply_gx_count_year("%" + query_lhpy + "%", "%" + query_city + "%", query_lx, query_status, univ_code, query_year);
        int pageState = (pageNum - 1) * pageSize;

        List<Apply> apply = applyService.getAllApply_gx_and_year("%" + query_lhpy + "%", "%" + query_city + "%", query_lx, query_status, univ_code, query_year, pageState, pageSize);
        HashMap<String, Object> res = new HashMap<>();

        res.put("number", number);
        res.put("data", apply);
        String res_string = JSON.toJSONString(res);

        System.out.println("获取审核列表成功");

        return res_string;
    }

    @RequestMapping("/getApply_st_and_year")
    public String getApply_st_and_year(@RequestParam("query_lhpy") String query_lhpy, @RequestParam("query_city") String query_city, @RequestParam("query_lx") String query_lx, @RequestParam("query_status") String query_status, @RequestParam("univ_code") String univ_code, @RequestParam("query_year") String query_year, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        // 获取最大列表数和当前编号
        int number = applyService.getApply_st_count_year("%" + query_lhpy + "%", "%" + query_city + "%", query_lx, query_status, univ_code, query_year);
        int pageState = (pageNum - 1) * pageSize;

        List<Apply> apply = applyService.getAllApply_st_and_year("%" + query_lhpy + "%", "%" + query_city + "%", query_lx, query_status, univ_code, query_year, pageState, pageSize);
        HashMap<String, Object> res = new HashMap<>();

        res.put("number", number);
        res.put("data", apply);
        String res_string = JSON.toJSONString(res);

        System.out.println("获取审核列表成功");

        return res_string;
    }


    @RequestMapping("/getApplyByID")
    public String getApplyByID(@RequestParam("apply_id") Integer apply_id) {
        if (apply_id == null) {
            return "null_error";
        }
        Apply apply = applyService.getApplyByID(apply_id);
        String string = JSON.toJSONString(apply);//注意这里也可以直接返回对象，springboot会把对象直接转换成字符串
        System.out.println("/getUpdate接口调用成功");
        return string;
    }

    @RequestMapping("/SH_Apply")
    public String SH_Apply(@RequestBody Apply apply) {
        if (apply == null) {
            return "error";
        }

        int i = applyService.SH_Apply(apply);
        System.out.println("/SH_Apply接口调用成功");
        return i > 0 ? "success" : "error";
    }

    @RequestMapping("/getApplyByUserID")
    public String getApplyByUserID(@RequestParam("user_id") Integer user_id) {
        if (user_id == null) {
            return "null_error";
        }
        Apply apply = applyService.getApplyByUserID(user_id);
        String string = JSON.toJSONString(apply);//注意这里也可以直接返回对象，springboot会把对象直接转换成字符串
        System.out.println("/getApplyByUserID接口调用成功");
        return string;
    }

}
