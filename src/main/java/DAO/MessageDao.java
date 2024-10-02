package DAO;
import Util.ConnectionUtil;
import Model.Message;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {
    
    public List<Message>getAllMessages(){
        Connection con = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "Select * from message;";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message (rs.getInt("message_id"),
                                               rs.getInt("posted_by"),
                                               rs.getString("message_text"),
                                               rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
            
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;

    }

    public Message getMessageById(int message_id){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "Select message_text from message where message_id = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, message_id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                                              rs.getInt("posted_by"),
                                              rs.getString("message_text"),
                                              rs.getLong("time_posted_epoch")
                                              );
                return message;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessageById(int message_id){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql ="Delete * from message where message_id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, message_id);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message updateMessage(Message messages){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "Update message Set message_text = ? where message_id =?;";
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1,messages.getMessage_text());
            p.setInt(2, messages.getMessage_id());
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                                              rs.getInt("posted_by"),
                                              rs.getString("message_text"),
                                              rs.getInt("time_posted_epoch"));
                return message;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessagesByAccountId(int posted_by){
        Connection connect = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "Select * from message where account_id =?;";
            PreparedStatement st = connect.prepareStatement(sql);
            st.setInt(1, posted_by);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                                rs.getInt("posted_by"),
                                rs.getString("message_text"),
                                rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message createMessage(Message message){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "Insert into message (message_text) values(?);";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,message.getMessage_text());
            stmt.executeUpdate();
            return message;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return message;
    }
}
