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

@WebServlet("/DeleteSelMusicServlet")
public class DeleteSelMusicServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        String[] sId = req.getParameterValues("id[]");
        MusicDao musicDao = new MusicDao();

        int sum = 0;
        Map<String,Object> return_map = new HashMap<>();

        for(int i = 0;i<sId.length;i++){
            int id = Integer.parseInt(sId[i]);
            Music music = musicDao.findMusicById(id);
            if(music == null) continue;
            int ret = musicDao.deleteMusicById(id);

            if(ret == 1){
                File file = new File("E:\\Idea\\musiclistening\\src\\main\\webapp\\"+music.getUrl()+".mp3");
                if(file.delete()){
                    sum += ret;
                }else{
                    return_map.put("msg",false);
                    System.out.println("删除失败");

                }
            }else{
                return_map.put("msg",false);
                System.out.println("删除失败");
            }
        }
        if(sum == sId.length){
            return_map.put("msg",true);
            System.out.println("删除成功");
        }else{
            return_map.put("msg",false);
            System.out.println("删除失败");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(resp.getWriter(),return_map);
    }
}
