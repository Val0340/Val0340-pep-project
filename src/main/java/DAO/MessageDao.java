package DAO;
import Util.ConnectionUtil;
import Model.Message;

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
            PreparedStatement ps = con.prepareStatement(sql)){ 
                ps.setInt(1, message_id);
                int rowAffected = ps.executeUpdate(); 
           
            return rowAffected > 0;
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
            PreparedStatement p = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
                p.setString(1,messages.getMessage_text());
                p.setInt(2, messages.getMessage_id());
             
                try(ResultSet keys = p.getGeneratedKeys()){
                    if(keys.next()){
                        messages.setMessage_id(keys.getInt(1));
                    }
                }
                return p.executeUpdate()>0;
            }
            
             
            
     
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
            while(rs.next()){
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
            String sql = "Insert into message (message_text,posted_by,time_posted_epoch) values(?,?,?);";
            try(
            Connection con = ConnectionUtil.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                stmt.setString(1,message.getMessage_text());
                stmt.setInt(2,message.getPosted_by());
                stmt.setLong(3,message.getTime_posted_epoch());
                stmt.executeUpdate();

                try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        message.setMessage_id(generatedKeys.getInt(1));
                       // message.setTime_posted_epoch(generatedKeys.getLong(1));
                    }
                }
            }
            
            return message;
    }}

