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

   public Message getMessageById(int message_id){
    return messageDao.getMessageById(message_id);
   }

   public Message deleteMessageById(int message_id){
    return messageDao.deleteMessageById(message_id);
   }
   public Message updateMessage(Message messages){
    return messageDao.updateMessage(messages);
   }

   public List<Message> getAllMessagesByAccountId(int posted_by){
    return messageDao.getAllMessagesByAccountId(posted_by);
   }
   @SuppressWarnings("unlikely-arg-type")
   public static Message createMessage(Message message){
      if(message.message_text == null || message.message_text.length()>255){
         return null;
      }
      List<Message> existingMessage = messageDao.getAllMessagesByAccountId(message.getPosted_by());
      
      if(!existingMessage.contains(message.posted_by)){
         return null;
      }
    return messageDao.createMessage(message);
   }
}
