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
    public List<Account> getAllAccounts(){
        Connection connection = Util.ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try{
        String sql = " Select * from account;";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Account account = new Account (rs.getInt("account_id"), 
                                            rs.getString("username"),
                                            rs.getString("password"));
            accounts.add(account);
        }
    }
    catch(SQLException e){
        System.out.println(e.getMessage());
    }
    return accounts;
    }

    public Account registerAccount(Account account){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "Insert into account (username,password) values(?,?);";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,account.getUsername());
            stmt.setString(2,account.getPassword());
            stmt.executeUpdate();
            return account;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return account;
    }

    public Account loginAccount(Account account){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "Select * from account where username = ? and password =? ;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,account.getUsername());
            ps.setString(2, account.getPassword());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Account accounts= new Account(rs.getInt("account_id"),
                                rs.getString("username"),
                                rs.getString("password"));
                return accounts;
            }
        
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
