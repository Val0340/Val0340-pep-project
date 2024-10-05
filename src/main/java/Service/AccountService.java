package Service;

import Model.Account;
import DAO.AccountDao;

import java.sql.SQLException;
import java.util.List;



public class AccountService {
    
    public  AccountDao accountDao;

    public AccountService (){
        accountDao = new AccountDao();
    }

   
    public AccountService(AccountDao accountDao){
        this.accountDao = accountDao;
    }

    public List<Account> getAllAccounts()throws SQLException{
        return accountDao.getAllAccounts();
    }
    
    public  Account registerAccount(String user, String pass, int acc_id) throws SQLException{
      
    
        if(user == null || pass.length() < 4 || user == ""){
            return null;
        }

        List<Account> existingAccount = accountDao.getAllAccounts();
        for (Account acc : existingAccount){
            if(acc.getUsername().equals(user)){
                return null;
            }
        }
    
        Account account = new Account();
        account.setUsername(user); 
        account.setPassword(pass);
        account.getAccount_id();
        //account.setAccount_id(acc_id);

        return accountDao.registerAccount(account);
    }

    public Account loginAccount(String user, String pass) throws SQLException{
        List<Account> existingAccount = accountDao.getAllAccounts();
        for ( Account acc : existingAccount){
            if (acc.getUsername().equals(user)&& acc.getPassword().equals(pass)){
                return acc;
            }
        }
       
        return null;
    }
}
