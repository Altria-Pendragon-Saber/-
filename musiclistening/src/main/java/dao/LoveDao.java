package dao;

import entity.Music;
import util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LoveDao {
    //添加音乐到喜欢列表中
    public boolean insertLove(int userId,int musicId){
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            con = DBUtils.getConn();
            String sql = "insert into love(user_id,music_id) values(?,?)";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,musicId);
            int ret = preparedStatement.executeUpdate();
            if(ret == 1){
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DBUtils.getClose(con,preparedStatement,null);
        }
        return false;
    }


    //查找用户喜欢的音乐
    public List<Music> findLove(int user_id){
        List<Music> musicList = new ArrayList<>();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            con = DBUtils.getConn();
            //两张表联合查询
            String sql = "select m.id as m_id,title,singer,time,url,userid from love l,music m where m.id=l.music_id and l.user_id=?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1,user_id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Music music = new Music();
                music.setId(resultSet.getInt("m_id"));
                music.setTitle(resultSet.getString("title"));
                music.setSinger(resultSet.getString("singer"));
                music.setTime(resultSet.getString("time"));
                music.setUrl(resultSet.getString("url"));
                music.setUserId(resultSet.getInt("userid"));
                musicList.add(music);
            }

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DBUtils.getClose(con,preparedStatement,resultSet);
        }
        return musicList;
    }


    //判断此时用户是否已经喜欢了这首歌,预防重复喜欢
    public boolean findLoveByMusicIdAndUserId(int user_id,int musicID){
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            con = DBUtils.getConn();
            String sql = "select * from love where user_id=? and music_id=?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1,user_id);
            preparedStatement.setInt(2,musicID);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DBUtils.getClose(con,preparedStatement,resultSet);
        }
        return false;
    }


    //根据关键字查询喜欢的歌单
    public List<Music> findLoveByKeyAndUID(String str,int user_id){
        List<Music> musicList = new ArrayList<>();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            con = DBUtils.getConn();
            String sql = "select m.id as m_id,title,singer,time,url,userid from love l,music m where m.id=l.music_id and l.user_id=? title like ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1,user_id);
            str = "%"+str+"%";
            preparedStatement.setString(2,str);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Music music = new Music();
                music.setId(resultSet.getInt("m_id"));
                music.setTitle(resultSet.getString("title"));
                music.setSinger(resultSet.getString("singer"));
                music.setTime(resultSet.getString("time"));
                music.setUrl(resultSet.getString("url"));
                music.setUserId(resultSet.getInt("userid"));
                musicList.add(music);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DBUtils.getClose(con,preparedStatement,resultSet);
        }
        return musicList;
    }


    //移除喜欢的音乐
    public int removeLove(int userId,int musicId){
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            con = DBUtils.getConn();
            String sql = "delete from love where user_id=? and music_id=?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,musicId);
            int ret = preparedStatement.executeUpdate();
            if(ret == 1){
                return 1;
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DBUtils.getClose(con,preparedStatement,null);
        }
        return 0;
    }
}
