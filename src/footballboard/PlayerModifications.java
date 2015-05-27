/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package footballboard;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
/**
 *
 * @author Patberg
 */
public class PlayerModifications {
    
    final private String username = "a";
    final private String password = "a";
    final private String url = "jdbc:derby://localhost:1527/FantasyFootballDraftBoards";
    public PlayerModifications()
    {
        
    }
    
    //add player to database
    public void addPlayer(String first, String last, String team, String position)
    {
        String query = "INSERT INTO A.PLAYERS (LASTNAME,FIRSTNAME,REALTEAM,POSITION,FANTASYTEAM,PICKNUMBER,ID)"
                + "VALUES(?,?,?,?,'',-1,?)";
        try(Connection connection = DriverManager.getConnection(url, username,password);
                PreparedStatement addTeamStat = connection.prepareStatement(query))
        {
            addTeamStat.setString(1, last);
            addTeamStat.setString(2,first);
            addTeamStat.setString(3, team);
            addTeamStat.setString(4, position);
            addTeamStat.setInt(5, this.getNextPlayerID());
            addTeamStat.executeUpdate();
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
    }
    
    //reset players to not be drafted
    public void resetPlayers()
    {
        String query = "update A.Players set picknumber=-1, fantasyteam=''";
        try(Connection conn = DriverManager.getConnection(url,username,password);
                Statement stat = conn.createStatement())
        {
            stat.executeUpdate(query);       
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
    }
    
    
    //sees if player is already in database
    public boolean isInDatabase(String first, String last, String team, String position)
    {
        String query = "SELECT FIRSTNAME, LASTNAME, REALTEAM, POSITION FROM A.PLAYERS" + 
          " WHERE FIRSTNAME = ? AND LASTNAME = ? AND REALTEAM = ? AND POSITION = ?";
        
        try(Connection conn = DriverManager.getConnection(url,username,password);
                PreparedStatement stat = conn.prepareStatement(query))
        {
            stat.setString(1, first);
            stat.setString(2, last);
            stat.setString(3, team);
            stat.setString(4, position);
            
            ResultSet resSet = stat.executeQuery();
            
            while(resSet.next())
                return true;
            
            return false;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return false;
    }
    
    //gets player rank
    private int getNextPlayerID()
    {
        final String query = "SELECT ID FROM A.PLAYERS ORDER BY ID ASC";
        int id = 0;
        try(Connection connection = DriverManager.getConnection(url,username,password);
        Statement getID = connection.createStatement();
                ResultSet resSet = getID.executeQuery(query))
        {
            
            while(resSet.next())
            {
                id = resSet.getInt("ID") +1;
            }
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        return id;
    }
    
    //gets list of all players in format  #. last,first (TEAM) - POS
    public ArrayList getPlayerList(boolean onlyDrafted)
    {
        ArrayList<String> players = new ArrayList();
        String individualPlayer= "";
        String query = "SELECT LASTNAME,FIRSTNAME,REALTEAM,ID,POSITION FROM A.PLAYERS "
                + "WHERE PICKNUMBER ";
        
        if(onlyDrafted)
        {
            query += " <> -1 ORDER BY PICKNUMBER";
        }
        else
        {
            query += " = -1 ORDER BY ID";
        }
        
        
        try(Connection connection = DriverManager.getConnection(url,username,password);
                Statement getPlayers = connection.createStatement();
                ResultSet resSet = getPlayers.executeQuery(query))
        {
            while(resSet.next())
            {
                individualPlayer = resSet.getInt("ID")+1 +". "+ resSet.getString("LASTNAME") 
                        + ", " + resSet.getString("FIRSTNAME") + " ("+ resSet.getString("REALTEAM") 
                        +") - " + resSet.getString("POSITION");
                players.add(individualPlayer);                
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        
        return players;
    }
    
    
    //gets player display for draft board
    public JPanel getPlayerPanel(String lastName, String firstName, String pos, String team)
    {
        JPanel playerPanel = new JPanel();
        JLabel top = new JLabel();
        JLabel bottom = new JLabel();
        JLabel right = new JLabel();
        
        
        top.setText(lastName);
        bottom.setText(firstName);
        right.setText(team);
        
        playerPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        
        if(pos.equals("QB"))
            playerPanel.setBackground(Color.yellow);
        else if(pos.equals( "RB"))
            playerPanel.setBackground(Color.cyan);
        else if(pos.equals("WR"))
            playerPanel.setBackground(Color.green);
        else if(pos.equals("TE"))
            playerPanel.setBackground(Color.orange);
        else if(pos.equals("K"))
            playerPanel.setBackground(Color.MAGENTA);
        else if(pos.equals("DEF"))
            playerPanel.setBackground(Color.GRAY);
        
        
        
       c.gridx=0;
       c.gridy=0;
       c.gridwidth=4;
       playerPanel.add(top,c);
       
       c.gridy=1;
       playerPanel.add(bottom,c);
       
       c.gridx=6;
       c.gridwidth=2;
       c.gridheight=2;
       c.gridy=0;
       
    //   playerPanel.add(right,c);               
        
       playerPanel.setForeground(Color.yellow);
        return playerPanel;
    }
    
    
    //get first name from player list string
    private String getFirstName(String player)
    {            
        String name = "";
        try
        {

            int pos =0;
            int len = 0;
            for(int x = 0; x<player.length() && pos==0;x++)
                if(player.charAt(x)==',')
                    pos = x +2;

            for(int x = pos; player.charAt(x)!=' ';x++)
                len++;

            name = player.substring(pos, pos+ len);
        }
        catch(NullPointerException e)
        {
            
        }
            return name;
    }

    //get last name from player list string
    private String getLastName(String player)
    {
        String name = "";
        int pos =0;
        int len = 0;
        for(int x = 0; x<player.length() && pos==0;x++)
            if(player.charAt(x)=='.')
                pos = x +2;
        
        for(int x = pos; player.charAt(x)!=',';x++)
            len++;
        
        name = player.substring(pos, pos+ len);
        
        return name;        
    }
    
    //get real team from player list string
    private String getTeam (String player)
    {
        String name = "";
        int pos =0;
        int len = 0;
        for(int x = 0; x<player.length() && pos==0;x++)
            if(player.charAt(x)=='(')
                pos = x +1;
        
        for(int x = pos; player.charAt(x)!=')';x++)
            len++;
        
        name = player.substring(pos, pos+ len);
        
        return name; 
    }
    
    //get position from player list string
    private String getPosition(String player)
    {
            String name = "";
        int pos =0;
        int len = 0;
        int dashes = this.getDashesInString(player);
        for(int x = 0; x<player.length() && pos==0;x++)
            if(player.charAt(x)=='-')
            {
                if(dashes==1)
                    pos = x +2;
                else
                    dashes--;
            }
        for(int x = pos; x<player.length();x++)
            len++;
        
        name = player.substring(pos, pos+ len);
        
        return name; 
    }
    
        private int getDashesInString(String s)
    {
        int x =0;
        for(int y =0; y<s.length();y++)
            if(s.charAt(y)=='-')
                x++;
        
        return x;
        
    }
    
    
}
