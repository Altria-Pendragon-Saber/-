package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.LoveDao;
import dao.MusicDao;
import entity.Music;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/FindLoveServlet")
public class FindLoveServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        String musicName = req.getParameter("loveMusicName");
        System.out.println("loveMusicName:"+musicName);
        User user = (User)req.getSession().getAttribute("user");
        int user_id = user.getId();
        LoveDao loveDao = new LoveDao();
        List<Music> musicList = new ArrayList<>();

        if(musicName != null){
            musicList = loveDao.findLoveByKeyAndUID(musicName,user_id);
        }else{
            musicList = loveDao.findLove(user_id);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(resp.getWriter(),musicList);

    }
}
