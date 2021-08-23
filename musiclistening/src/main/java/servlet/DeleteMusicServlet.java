package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.MusicDao;
import entity.Music;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/DeleteMusicServlet")
public class DeleteMusicServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        String sid = req.getParameter("id");
        int id = Integer.parseInt(sid);

        MusicDao musicDao = new MusicDao();
        Music music = musicDao.findMusicById(id);

        Map<String,Object> return_map = new HashMap<>();

        if(music == null) return;
        int ret = musicDao.deleteMusicById(id);
        if(ret == 1){
            //删除服务器目录下对应的文件
            File file = new File("E:\\Idea\\musiclistening\\src\\main\\webapp\\"+music.getUrl()+".mp3");
            if(file.delete()){
                return_map.put("msg",true);
            }else{
                return_map.put("msg",false);
            }
        }else{
            return_map.put("msg",false);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(resp.getWriter(),return_map);
    }
}
