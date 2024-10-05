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
    
    public List<Message>getAllMessages() throws SQLException{
        List<Message> messages = new ArrayList<>();
         String sql = "Select * from message;";
        try( Connection con = ConnectionUtil.getConnection();
       
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Message message = new Message (rs.getInt("message_id"),
                                               rs.getInt("posted_by"),
                                               rs.getString("message_text"),
                                               rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }
            return messages;
        }
        
        

    

    public Message getMessageById(int message_id) throws SQLException{
        
            Message message = null;
            String sql = "Select * from message where message_id = ?;";

            try( Connection con = ConnectionUtil.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setInt(1, message_id);

           try( ResultSet rs = stmt.executeQuery()){
            if(rs.next()){
                 message = new Message(rs.getInt("message_id"),
                                              rs.getInt("posted_by"),
                                              rs.getString("message_text"),
                                              rs.getLong("time_posted_epoch"));
            }
        }
        }
            return message;
        }
        
       
    

    public boolean deleteMessageById(int message_id) throws SQLException{
         String sql ="Delete from message where message_id = ?;";

            try(Connection con = ConnectionUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){ ps.setInt(1, message_id);
                return ps.executeUpdate()> 0; 
            }
           
             
           // while(rs.next()){
             //   Message message = new Message(rs.getInt("message_id"),
             //   rs.getInt("posted_by"),
              //  rs.getString("message_text"),
             //   rs.getLong("time_posted_epoch")
              //  );
               // return message;
          //  }
        
        
    }

    public boolean updateMessage(Message messages) throws SQLException{
        
        String sql = "Update message Set message_text = ? where message_id =?;";
        try(Connection con = ConnectionUtil.getConnection();
            PreparedStatement p = con.prepareStatement(sql)){
                p.setString(1,messages.getMessage_text());
                p.setInt(2, messages.getMessage_id());
                return p.executeUpdate()>0;
            }
            
             
           // while(rs.next()){
             //   Message message = new Message(rs.getInt("message_id"),
                  //                            rs.getInt("posted_by"),
                  //                            rs.getString("message_text"),
                   //                           rs.getInt("time_posted_epoch"));
               // return message;
            
       // }
       
    }

    public List<Message> getAllMessagesByAccountId(int posted_by) throws SQLException{
        
        List<Message> messages = new ArrayList<>();
        String sql = "Select * from message where posted_by = ?;";

        try(
            Connection connect = ConnectionUtil.getConnection();
            PreparedStatement st = connect.prepareStatement(sql)){
            st.setInt(1, posted_by);
            try(
            ResultSet rs = st.executeQuery()){
            if(rs.next()){
                Message mess = new Message(rs.getInt("message_id"),
                                rs.getInt("posted_by"),
                                rs.getString("message_text"),
                                rs.getLong("time_posted_epoch"));
                messages.add(mess);
            }
        }
    }
        
        
        return messages;
    }

    public Message createMessage(Message message)throws SQLException{
        Connection con = ConnectionUtil.getConnection();
        
            String sql = "Insert into message (message_text,posted_by) values(?,?);";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,message.getMessage_text());
            stmt.setInt(2,message.getPosted_by());
            stmt.executeUpdate();
            return message;
    }}

