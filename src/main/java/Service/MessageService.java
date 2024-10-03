package Service;
import Model.Message;
import DAO.MessageDao;
import java.util.List;

public class MessageService {
   private static MessageDao messageDao;

   public MessageService (){
    messageDao = new MessageDao();
   }

   @SuppressWarnings("static-access")
   public MessageService(MessageDao messageDao){
    this.messageDao = messageDao;
   }

   public static List<Message> getAllMessages(){
    return messageDao.getAllMessages();
   }

   public static List<Message> getMessageById(int message_id){
    return messageDao.getMessageById(message_id);
   }

   public static Message deleteMessageById(int message_id){
    return messageDao.deleteMessageById(message_id);
   }
   
   public static Message updateMessage(String messages ,int messageId){
      Message newMessage = new Message();
      newMessage.message_text = messages;
      if (newMessage.message_text == null && newMessage.message_text.length() > 255){
         return null;
      }
    return messageDao.updateMessage(newMessage);
   }

   public static List<Message> getAllMessagesByAccountId(int posted_by){
    return messageDao.getAllMessagesByAccountId(posted_by);
   }
   @SuppressWarnings("unlikely-arg-type")
   public static Message createMessage(String message){
      Message mes = new Message();
      mes.message_text = message;
      if(mes.message_text == null || mes.message_text.length()>255){
         return null;
      }
      List<Message> existingMessage = messageDao.getAllMessagesByAccountId(mes.getPosted_by());
      
      if(!existingMessage.contains(mes.posted_by)){
         return null;
      }
    return messageDao.createMessage(mes);
   }
}
