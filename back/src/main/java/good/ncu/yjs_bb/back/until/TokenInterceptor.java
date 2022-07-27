package good.ncu.yjs_bb.back.until;
 
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/**
 * @version 1.0
 * @author:**
 * @date:2020/3/20
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler)throws Exception{
    if(request.getMethod().equals("OPTIONS")){
      response.setStatus(HttpServletResponse.SC_OK);
      return true;
    }
    response.setCharacterEncoding("utf-8");
    // 获取存放在请求头Authorization字段的token
    String token = request.getHeader("Authorization");
    if(token != null){
      boolean result = TokenUtil.verify(token);
      if(result){
        System.out.println("通过拦截器");
        return true;
      }
    }
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json; charset=utf-8");
    try{
      JSONObject json = new JSONObject();
      json.put("msg","token verify fail");
      json.put("code","50000");
      response.getWriter().append(json.toJSONString());
      System.out.println("认证失败，未通过拦截器");
    }catch (Exception e){
      e.printStackTrace();
      response.sendError(500);
      return false;
    }
    return false;
  }
}