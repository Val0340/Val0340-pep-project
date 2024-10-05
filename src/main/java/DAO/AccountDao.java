package DAO;

import Util.ConnectionUtil;
import Model.Account;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;





public class AccountDao {
    public List<Account> getAllAccounts() throws SQLException{
        
        List<Account> accounts = new ArrayList<>();
    
        String sql = " Select * from account;";

        try(Connection connection = Util.ConnectionUtil.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()){
        while (rs.next()) {
            Account account = new Account (rs.getInt("account_id"), 
                                            rs.getString("username"),
                                            rs.getString("password"));
            accounts.add(account);
        }
    }catch (SQLException e) {
        e.printStackTrace(); 
        throw e; 
    }
    
    return accounts;
    }

    public Account registerAccount(Account account)throws SQLException{
        
        
            String sql = "Insert into account (username,password) values(?,?);";

            try( Connection con = ConnectionUtil.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){

                stmt.setString(1,account.getUsername());
                stmt.setString(2,account.getPassword());
                stmt.executeUpdate();
               // try(ResultSet rs = stmt.executeQuery()){
                   // if(rs.next()){
                       // return new Account(rs.getInt("account_id"),
                         //                rs.getString("username"),
                           //              rs.getString("password"));
                    // }
               // }
            }catch (SQLException e) {
                e.printStackTrace(); 
                throw e; 
            }
            
            return account;
    }

    public Account loginAccount(Account account)throws SQLException{
        
    
            String sql = "Select * from account where username = ? and password =? ;";
            try(
            Connection con = ConnectionUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1,account.getUsername());
            ps.setString(2, account.getPassword());
           try( ResultSet rs = ps.executeQuery()){
            if(rs.next()){
               return new Account(rs.getInt("account_id"),
                                rs.getString("username"),
                                rs.getString("password"));
            }
        }
    }catch (SQLException e) {
        e.printStackTrace();
        throw e; 
    }
        return null;
    }
}

