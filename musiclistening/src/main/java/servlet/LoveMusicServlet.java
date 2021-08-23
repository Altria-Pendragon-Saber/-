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

@WebServlet("/LoveMusicServlet")
public class LoveMusicServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        Map<String,Object> return_map = new HashMap<>();


        String musicIds = req.getParameter("id");
        int musicId = Integer.parseInt(musicIds);

        User user = (User)req.getSession().getAttribute("user");
        int userId = user.getId();

        LoveDao loveDao = new LoveDao();

        boolean effect2 = loveDao.findLoveByMusicIdAndUserId(userId,musicId);
        if(effect2){
            return_map.put("msg",false);
        }else{
            boolean effect = loveDao.insertLove(userId,musicId);
            if(effect){
                return_map.put("msg",true);
            }else{
                return_map.put("msg",false);
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(resp.getWriter(),return_map);
    }
}
