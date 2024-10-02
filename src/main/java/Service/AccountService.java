package Service;

import Model.Account;
import DAO.AccountDao;
import java.util.List;



public class AccountService {
    
    private static AccountDao accountDao;

    public AccountService (){
        accountDao = new AccountDao();
    }

    @SuppressWarnings("static-access")
    public AccountService(AccountDao accountDao){
        this.accountDao = accountDao;
    }

    public List<Account> getAllAccounts(){
        return accountDao.getAllAccounts();
    }
    @SuppressWarnings("unlikely-arg-type")
    public static Account registerAccount(Account account){
        if(account.username == null ||account.password.length() < 4)  ){
            return null;
        }
        List<Account> existingAccount = accountDao.getAllAccounts();
        if(existingAccount.contains(account.getUsername())){
            return null;
        }
        return accountDao.registerAccount(account);
    }

    public static Account loginAccount(Account account){
        List<Account> existingAccount = accountDao.getAllAccounts();
        if(!existingAccount.contains(account)){
            return null;
        }

        return accountDao.loginAccount(account);
    }
}
