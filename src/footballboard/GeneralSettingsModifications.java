/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package footballboard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


/**
 *
 * @author Patberg
 */
public class GeneralSettingsModifications {
    final private String username = "a";
    final private String password = "a";
    final private String url = "jdbc:derby://localhost:1527/FantasyFootballDraftBoards";
    
    public GeneralSettingsModifications()
    {
        
    }
    
    //add setting total
    public void add(String setting, int total)
    {
        final String query = "UPDATE A.GENERALSETTINGS "
                + "SET TOTAL=? "
                + "WHERE NAME = ?";
        try(Connection connection = DriverManager.getConnection(url,username,password);
                PreparedStatement addTeam = connection.prepareStatement(query))
        {
            addTeam.setInt(1, total);
            addTeam.setString(2, setting);
            addTeam.executeUpdate();
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
        
    }
    
    //get setting total
    public int getSettingTotal(String setting)
    {
        int total = 0;
        final String query = "SELECT TOTAL FROM A.GENERALSETTINGS WHERE NAME = ?";
        try(Connection connection = DriverManager.getConnection(url,username,password);
                PreparedStatement getTeam = connection.prepareStatement(query))
        {
            getTeam.setString(1,setting);
            ResultSet resSet = getTeam.executeQuery();
            
            while(resSet.next())
                total = resSet.getInt("TOTAL");
            
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        return total;
    }
    
    //reset picks for teams
    public void resetPicks()
    {
        String query= "UPDATE A.TEAMS "
                + "SET PICKNUMBER = -1";
        
        try(Connection connection = DriverManager.getConnection(url,username,password);
                PreparedStatement stat = connection.prepareStatement(query))
        {
            stat.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        
    }
}
