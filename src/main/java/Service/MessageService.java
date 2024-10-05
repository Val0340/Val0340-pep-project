package Service;
import Model.Message;
import DAO.MessageDao;

import java.sql.SQLException;
import java.util.List;

public class MessageService {
   private MessageDao messageDao;

   public MessageService (){
    messageDao = new MessageDao();
   }

   
   public MessageService(MessageDao messageDao){
    this.messageDao = messageDao;
   }

   public List<Message> getAllMessages() throws SQLException{
    return messageDao.getAllMessages();
   }

   public Message getMessageById(int message_id) throws SQLException{
    return messageDao.getMessageById(message_id);
   }

   public boolean deleteMessageById(int message_id) throws SQLException{
    return messageDao.deleteMessageById(message_id);
   }
   
   public boolean updateMessage(String messages ,int messageId) throws SQLException{
      
      
      if (messages == null || messages.length() > 255){
         return false;
      }

      Message newMessage = new Message();
      newMessage.setMessage_text(messages);
      newMessage.setMessage_id(messageId);

    return messageDao.updateMessage(newMessage);
   }

   public List<Message> getAllMessagesByAccountId(int posted_by) throws SQLException{
    return messageDao.getAllMessagesByAccountId(posted_by);
   }
  
   public Message createMessage(String message, int posted_by) throws SQLException{

      if(message == null || message.length()>255 || message == "" ){
         return null;
      }
      Message mes = new Message();
      mes.setMessage_text(message);
      mes.setPosted_by(posted_by);
      List<Message> existingMessage = messageDao.getAllMessagesByAccountId(posted_by);
      
      if(existingMessage.isEmpty()){
         return null;
      }
      
    return messageDao.createMessage(mes);
   }
}
