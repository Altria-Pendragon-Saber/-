package dao;

import entity.Music;
import util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//插入音乐

public class MusicDao {
    public int insert(String title,String singer,String time,String url,int userid){
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            con = DBUtils.getConn();
            String sql = "insert into music(title,singer,time,url,userid) values(?,?,?,?,?)";
            preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1,title);
            preparedStatement.setString(2,singer);
            preparedStatement.setString(3,time);
            preparedStatement.setString(4,url);
            preparedStatement.setInt(5,userid);

            int ret = preparedStatement.executeUpdate();
            if(ret == 1){
                return ret;
            }

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DBUtils.getClose(con,preparedStatement,null);
        }
        return 0;
    }

    //查询全部歌单
    public List<Music> findMusic(){
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Music> musicList = new ArrayList<>();

        try {
            con = DBUtils.getConn();
            String sql = "select * from music";
            preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Music music = new Music();
                music.setId(resultSet.getInt("id"));
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

    //根据id查找音乐
    public Music findMusicById(int id){
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Music music = null;

        try {
            con = DBUtils.getConn();
            String sql = "select * from music where id=?";
            preparedStatement = con.prepareStatement(sql);

            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                music = new Music();
                music.setId(resultSet.getInt("id"));
                music.setTitle(resultSet.getString("title"));
                music.setSinger(resultSet.getString("singer"));
                music.setTime(resultSet.getString("time"));
                music.setUrl(resultSet.getString("url"));
                music.setUserId(resultSet.getInt("userid"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DBUtils.getClose(con,preparedStatement,resultSet);
        }
        return music;
    }

    //根据关键字查询歌单
    public  List<Music> findMusicByKey(String str){
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Music> musicList = new ArrayList<>();

        try {
            con = DBUtils.getConn();
            String sql = "select * from music where title like '%"+str+"%'";
            preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Music music = new Music();
                music.setId(resultSet.getInt("id"));
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

    //删除歌曲
    public int deleteMusicById(int musicId){
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            con = DBUtils.getConn();
            String sql = "delete from music where id=?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1,musicId);

            int ret = preparedStatement.executeUpdate();
            if(ret == 1){
                if(findLoveById(musicId)){
                    int ret2 = deleteLoveById(musicId);
                    if(ret2 == 1){
                        System.out.println("删除成功");
                        return 1;
                    }
                }
                return 1;
            }else{
                return 0;
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DBUtils.getClose(con,preparedStatement,null);
        }
        return 0;
    }


    //根据id查找在喜欢的列表中是都包含这样一首歌
    public boolean findLoveById(int musicId){
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            con = DBUtils.getConn();
            String sql = "select * from love where music_id=?";
            preparedStatement = con.prepareStatement(sql);

            preparedStatement.setInt(1,musicId);
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


    //根据id删除在喜欢的列表中的音乐
    public int deleteLoveById(int musicId){
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            con = DBUtils.getConn();
            String sql = "delete from love where music_id=?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1,musicId);

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
