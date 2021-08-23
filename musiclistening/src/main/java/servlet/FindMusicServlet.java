package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.MusicDao;
import entity.Music;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/FindMusicServlet")
public class FindMusicServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            req.setCharacterEncoding("utf-8");
            resp.setContentType("text/html;charset=utf-8");

            String musicName = req.getParameter("musicName");
            MusicDao musicDao = new MusicDao();
            List<Music> musicList = null;
            if(musicName!=null){
                musicList = musicDao.findMusicByKey(musicName);
            }else{
                musicList = musicDao.findMusic();
            }
            for (Music music:musicList) {
                System.out.println(music.getUrl());
            }
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(resp.getWriter(),musicList);
        }
    }
