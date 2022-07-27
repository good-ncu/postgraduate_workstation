package good.ncu.yjs_bb.back.controller;

import com.alibaba.fastjson.JSON;
import good.ncu.yjs_bb.back.entity.Univ;
import good.ncu.yjs_bb.back.service.UnivService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UnivController {
    @Autowired
    UnivService univService;

    @RequestMapping("/getAllUniv")
    public String getAllUniv() {
        List<Univ> ls = univService.getAllUniv();
        return JSON.toJSONString(ls);
    }

    @RequestMapping("/getUnivByCode")
    public String getUnivByCode(@RequestParam("univ_code") String univ_code) {
        if (univ_code == null) {
            return "error";
        }
        Univ univ = univService.getUnivByCode(univ_code);
        return JSON.toJSONString(univ);
    }

    @RequestMapping("/getUnivIndex")
    public String getUnivIndex(@RequestParam("univ_code") String univ_code) {
        if (univ_code == null) {
            return "error";
        }
        Map<String, Object> resultMap = new HashMap<>();
        Univ univ = univService.getUnivByCode(univ_code);
        resultMap.put("univ_index", univ.getUniv_index());
        return JSON.toJSONString(resultMap);
    }

    @RequestMapping("/addUnivIndex")
    public String addUnivIndex(@RequestParam("univ_code") String univ_code) {
        if (univ_code == null) {
            return "error";
        }
        int i = univService.UpdateIndex(univ_code);
        return i > 0 ? "success" : "error";
    }


}
