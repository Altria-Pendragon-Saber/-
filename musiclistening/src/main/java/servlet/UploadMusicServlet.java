package servlet;

import dao.MusicDao;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/upload")
@MultipartConfig
public class UploadMusicServlet extends HttpServlet {
    private static final String SAVEPATH = "E:\\Idea\\musiclistening\\src\\main\\webapp\\music\\";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        User user = (User) req.getSession().getAttribute("user");
        if(user == null){
            System.out.println("您还未登录，不能上传音乐");
        }else{
            Part part = req.getPart("filename");
            String header = part.getHeader("Content-Disposition");
            int start = header.lastIndexOf("=");
            String fileName = header.substring(start+1).replace("\"","");
            System.out.println("fileName:"+ fileName);

           /* int index = fileName.indexOf("%");
            fileName = fileName.substring(index);
            fileName = URLDecoder.decode(fileName,"utf-8");
            System.out.println("fileName2:"+fileName);*/

            part.write(SAVEPATH+"/"+fileName);
            System.out.println("fileName2:"+fileName);


            String singer = req.getParameter("singer");
            System.out.println("歌手:"+ singer);

            String[] titles = fileName.split("\\.");
            String title = titles[0];
            System.out.println("title:"+title);

            String url = "music/"+title;
            System.out.println("url:"+url);

            SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("yyyy-MM-dd");
            String time = simpleDateFormat.format(new Date());

            int userId = user.getId();
            MusicDao musicDao = new MusicDao();
            int ret = musicDao.insert(title,singer,time,url,userId);
            if(ret != 0){
                resp.sendRedirect("list.html");
            }else{
                System.out.println("上传失败");
                part.delete();
            }

        }
    }
}
