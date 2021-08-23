package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.UserDao;
import entity.User;
import service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        System.out.println("username:"+username);
        System.out.println("password:"+password);

        Map<String,Object> return_map = new HashMap<>();

        User loginUser = new User();
        loginUser.setUsername(username);
        loginUser.setPassword(password);

        LoginService loginService = new LoginService();
        User user = loginService.login(loginUser);

        if(user == null){
            //登陆失败
            return_map.put("msg",false);
        }else{
            req.getSession().setAttribute("user",user);//绑定数据
            return_map.put("msg",true);
        }

        ObjectMapper mapper = new ObjectMapper();
        //利用Jackson将map转换为json对象
        //writer 将转换后的json字符串保存到字符输出流中，最后给客户端
        mapper.writeValue(resp.getWriter(),return_map);
    }
}
