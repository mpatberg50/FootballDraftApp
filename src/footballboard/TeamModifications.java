/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package footballboard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Patberg
 */
public class TeamModifications {
    final private String username = "a";
    final private String password = "a";
    final private String url = "jdbc:derby://localhost:1527/FantasyFootballDraftBoards";
    
    TeamModifications()
    {
        
    }
    
    //gets pick number for a specific team
    public int getPickNumber (String team)
    {
        String query = "SELECT PICKNUMBER FROM A.TEAMS WHERE NAME = ?";
        
        try(Connection conn = DriverManager.getConnection(url,username,password);
                PreparedStatement prepStat = conn.prepareStatement(query))
        {
            prepStat.setString(1, team);
            ResultSet resSet = prepStat.executeQuery();
            
            while(resSet.next())
            {
                return resSet.getInt("PICKNUMBER");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }
    
    
    //adds team to database
    public void addNewTeam(String teamName)
    {
        String query = "INSERT INTO A.TEAMS (NAME,NUMBER,PICKNUMBER)"
        + " VALUES(?,?,-1) ";
        try(Connection connection = DriverManager.getConnection(url,username,password);
                PreparedStatement addTeamStatement= connection.prepareStatement(query))
        {
            addTeamStatement.setString(1, teamName);
            addTeamStatement.setInt(2, this.getNextTeamNumber());
            
            addTeamStatement.executeUpdate();
            
        }
        catch(SQLException exception )
        {
            exception.printStackTrace();
        }
        
    }
    
    //gets a team id
    private int getNextTeamNumber()
    {
        String query = "SELECT NUMBER FROM A.TEAMS";
        int teamNumber = 0;
        
        
        try(Connection connection = DriverManager.getConnection(url,username,password);
                Statement getTeams = connection.createStatement();
                ResultSet teamSet = getTeams.executeQuery(query))
        {
                while(teamSet.next())
                {
                    teamNumber = teamSet.getInt("NUMBER") + 1;
                }
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
        return teamNumber;
    }
    
    //delete team from database
    public void removeTeam(String teamName)
    {
        String query = "DELETE FROM A.TEAMS WHERE NAME = ? ";
        
        try(Connection connection = DriverManager.getConnection(url,username,password);
                PreparedStatement removeTeamStatement= connection.prepareStatement(query))
        {
            removeTeamStatement.setString(1, teamName);
            removeTeamStatement.executeUpdate();
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
                
    }
    
    //gets list of all the teams
    public ArrayList getTeams()
    {
        ArrayList<String> teams = new ArrayList();
        String query = "SELECT NAME FROM A.TEAMS ORDER BY PICKNUMBER";
        
        try(Connection connection = DriverManager.getConnection(url,username,password);
                Statement stat = connection.createStatement();
                ResultSet teamSet = stat.executeQuery(query))
        {
            while(teamSet.next())
            {
                teams.add(teamSet.getString("NAME"));
            }
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
        
        return teams;
    }
    
    //gets list of teams that do not have a draft position
    public ArrayList getTeamsWithOutPick()
    {
        ArrayList<String> teams = new ArrayList();
        String query = "SELECT NAME FROM A.TEAMS WHERE PICKNUMBER = -1";
        
        try(Connection connection = DriverManager.getConnection(url,username,password);
                Statement stat = connection.createStatement();
                ResultSet teamSet = stat.executeQuery(query))
        {
            while(teamSet.next())
            {
                teams.add(teamSet.getString("NAME"));
            }
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
        
        return teams;
    }
    
    //updates the database to give a team the pick that they recieved
    public void addPick (String team, int pickNum)
    {
        final String query = "UPDATE A.TEAMS "
                + "SET PICKNUMBER = ? "
                + "WHERE NAME = ?";
        
        try (Connection connection = DriverManager.getConnection(url,username,password);
                PreparedStatement givePick = connection.prepareStatement(query))
        {
            givePick.setString(2, team);
            givePick.setInt(1, pickNum);
            givePick.executeUpdate();
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
    }
    
    //gets the team that has the pick given for the first round
    public String getTeamAtPick(int pickNum)
    {
        final String query = "SELECT NAME FROM A.TEAMS WHERE PICKNUMBER = ?";
        String team = "";
        try(Connection connection = DriverManager.getConnection(url,username,password);
                PreparedStatement getTeam = connection.prepareStatement(query))
        {
            getTeam.setInt(1,pickNum);
            ResultSet resSet = getTeam.executeQuery();
            
            while(resSet.next())
                team = resSet.getString("NAME");
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        return team;
    }
    
    //gets last pick that has not been given for the random picks
    public int getLastAvailablePick ()
    {
        int pick = this.getTeams().size();
        String query = "SELECT PICKNUMBER FROM A.TEAMS ORDER BY PICKNUMBER DESC";
        
        try(Connection connection = DriverManager.getConnection(url,username,password);
                Statement stat = connection.createStatement();
                ResultSet resSet = stat.executeQuery(query))
        {
            while(resSet.next())
            {
                if(resSet.getInt("Picknumber")<pick)
                    return pick;
                else
                    pick--;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return pick;
    }
    
    //update a player that has been picked in the player database
    public void addPickedPlayer(int pickNumber, String last, String first, String pos, String fanTeam, String realTeam)
    {
        String query = "UPDATE A.PLAYERS "
                + "SET FANTASYTEAM=?, PICKNUMBER=? "
                + "WHERE LASTNAME=? AND FIRSTNAME=? AND POSITION=? AND  REALTEAM=?";
        
        try(Connection connection = DriverManager.getConnection(url,username,password);
                PreparedStatement addPlayer = connection.prepareStatement(query))
        {
            addPlayer.setString(1, fanTeam);
            addPlayer.setInt(2, pickNumber);
            addPlayer.setString(3, last);
            addPlayer.setString(4, first);
            addPlayer.setString(5, pos);
            addPlayer.setString(6, realTeam);
            addPlayer.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}