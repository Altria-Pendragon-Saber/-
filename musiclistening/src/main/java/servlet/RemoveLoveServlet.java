package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.LoveDao;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/RemoveLoveServlet")
public class RemoveLoveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        String strId = req.getParameter("id");
        int musicid = Integer.parseInt(strId);

        User user = (User)req.getSession().getAttribute("user");
        int userId = user.getId();

        Map<String,Object> return_map = new HashMap<>();

        LoveDao loveDao = new LoveDao();
        int ret = loveDao.removeLove(userId,musicid);
        if(ret == 1){
            return_map.put("msg",true);
        }else{
            return_map.put("msg",false);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(resp.getWriter(),return_map);
    }
}
