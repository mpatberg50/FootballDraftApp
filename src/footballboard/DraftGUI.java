/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package footballboard;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.table.*;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

/**
 *
 * @author Patberg
 */
public class DraftGUI extends javax.swing.JFrame {
    PlayerModifications playerMods = new PlayerModifications();
    TeamModifications teamMods = new TeamModifications();
    GeneralSettingsModifications genMods = new GeneralSettingsModifications();
    final ArrayList<String> teams;
    int rounds = genMods.getSettingTotal("Rounds");
    int currentPick = 1;
    String currentTeamPicking= "";
    ArrayList<String> players = playerMods.getPlayerList(false);
    
    
    /**
     * Creates new form DraftGUI
     */
    public DraftGUI() {
        currentTeamPicking = teamMods.getTeamAtPick(1);
        final ArrayList<String> playersDrafted = playerMods.getPlayerList(true);
        currentPick = playersDrafted.size()+1;
        teams= teamMods.getTeams();
        
        
        initComponents();        
        
        
        String fanTeam;
        String firstName,lastName,pos,realTeam;
        int curRound= 1;
        for(int x=1; x<currentPick; x++)
        {
            curRound = (x-1)/teams.size() +1;
            fanTeam = currentTeamPicking;
            firstName = this.getFirstName(playersDrafted.get(x-1));
            lastName = this.getLastName(playersDrafted.get(x-1));
            pos = this.getPosition(playersDrafted.get(x-1));
            realTeam = this.getTeam(playersDrafted.get(x-1));
            
            this.addToRoster(firstName,lastName, pos, fanTeam, realTeam);
            
            JPanel playerPanel = playerMods.getPlayerPanel(firstName, lastName, pos, realTeam);
            
            
            this.setTeamOnTheClock(curRound, currentTeamPicking);
            
            
            if(curRound%2==1)
            {
                DraftBoardTable.setValueAt(playerPanel, curRound-1, (x-1)%teams.size()+1);
            }
            else
            {                
                DraftBoardTable.setValueAt(playerPanel, curRound-1, teams.size()-(x-1)%teams.size());
            }  
            
            
        }
        
        this.setForeground(new Color(100,100,10));

    }
    
    //gets the team that is on the clock
    public void setTeamOnTheClock(int round, String lastTeamThatPicked)
    { 
        
       if(round%2==1)
       {
           if(lastTeamThatPicked.equals(teams.get(teams.size()-1)))
           {
               currentTeamPicking = lastTeamThatPicked; 
           }
           else
           {
               currentTeamPicking = teams.get(teams.indexOf(lastTeamThatPicked)+1);
           }
       }
       else
       {
           if(lastTeamThatPicked.equals(teams.get(0)))
           {
               currentTeamPicking = lastTeamThatPicked;
           }
           else
           {
               currentTeamPicking = teams.get(teams.indexOf(lastTeamThatPicked)-1);
           }
       }
    }
    
    public void resetList (boolean qb, boolean rb, boolean wr, boolean te, boolean k, boolean def)
    {
        DefaultListModel dlm = new DefaultListModel();
        PlayerList.setModel(dlm);
        
        String pos = "";
        
        for(int x =0; x<players.size();x++)
        {
            pos=this.getPosition(players.get(x));
            
            if(  (pos.equals("QB")&&qb) || (pos.equals("RB")&&rb) || (pos.equals("WR")&&wr) || (pos.equals("TE")&&te)
                    || (pos.equals("K")&&k) || (pos.equals("DEF")&&def) )
                dlm.addElement(players.get(x));
        }
     
        PlayerList.setSelectedIndex(0);
    }
    
       
    
    //adds player to the table
    private void addToRoster(String first, String last, String pos, String fanTeam,String realTeam)
    {
        int pickNum = teamMods.getPickNumber(fanTeam);
        boolean isFlex = false;
        if(pos.equals("RB") || pos.equals("WR") || pos.equals("TE"))
            isFlex=true;
        
        for(int x =0; x<rounds; x++)
        {
            String tablePos = (String)RosterTable.getModel().getValueAt(x, 0);
            
             //try statement checks for empty cell    
            try
            {
                

                if((tablePos.equals(pos) || tablePos.equals("BENCH")   || (isFlex && tablePos.equals("RB/WR/TE"))) 
                        && RosterTable.getModel().getValueAt(x, pickNum).equals(""));
            }
            catch(NullPointerException e)
            {
                JPanel playerPanel = playerMods.getPlayerPanel(first, last, pos, realTeam);
                RosterTable.setValueAt(playerPanel, x, pickNum);
                x=rounds;
            }


        }
        
    }
    
    private int getDashesInString(String s)
    {
        int x =0;
        for(int y =0; y<s.length();y++)
            if(s.charAt(y)=='-')
                x++;
        
        return x;
        
    }
    
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
                    pos=x+2;
                else
                    dashes--;
            }
            
        
        for(int x = pos; x<player.length();x++)
            len++;
        
        name = player.substring(pos, pos+ len);
        
        return name; 
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        DraftTabsPanel = new javax.swing.JTabbedPane();
        DraftBoardPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        DraftBoardTable = new javax.swing.JTable();
        TeamRosterPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        RosterTable = new javax.swing.JTable();
        SortPlayerPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        RBCheckBox = new javax.swing.JCheckBox();
        WRCheckBox = new javax.swing.JCheckBox();
        TECheckBox = new javax.swing.JCheckBox();
        KCheckBox = new javax.swing.JCheckBox();
        DEFCheckBox = new javax.swing.JCheckBox();
        QBCheckBox = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        PlayerList = new javax.swing.JList();
        DraftPickButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        TeamLabel = new javax.swing.JLabel();
        RestartDraft = new javax.swing.JButton();
        AddPlayerButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 255, 204));

        DraftBoardTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10"
            }
        ));
        DraftBoardTable.setRowHeight(32);
        jScrollPane2.setViewportView(DraftBoardTable);
        PlayerPanelModel dtm = new PlayerPanelModel();
        DraftBoardTable.setModel(dtm);
        dtm.addColumn("Round Number");
        ArrayList<String> teams = teamMods.getTeams();

        DraftBoardTable.setDefaultRenderer(JPanel.class, new PanelRenderer());

        for(int x =0; x<teams.size();x++)
        dtm.addColumn(teams.get(x));

        for(int x=1;x<=genMods.getSettingTotal("Rounds");x++)
        dtm.addRow(new Object[]{x});

        javax.swing.GroupLayout DraftBoardPanelLayout = new javax.swing.GroupLayout(DraftBoardPanel);
        DraftBoardPanel.setLayout(DraftBoardPanelLayout);
        DraftBoardPanelLayout.setHorizontalGroup(
            DraftBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DraftBoardPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 982, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        DraftBoardPanelLayout.setVerticalGroup(
            DraftBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
        );

        DraftTabsPanel.addTab("Draft Board", DraftBoardPanel);

        RosterTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        RosterTable.setRowHeight(32);
        jScrollPane3.setViewportView(RosterTable);
        PlayerPanelModel dtm2 = new PlayerPanelModel();
        RosterTable.setModel(dtm2);

        dtm2.addColumn("Position");

        for(int x =0; x<teams.size();x++)
        dtm2.addColumn(teams.get(x));

        int qb = genMods.getSettingTotal("QB");
        int rb = genMods.getSettingTotal("RB");
        int wr = genMods.getSettingTotal("WR");
        int te = genMods.getSettingTotal("TE");
        int k = genMods.getSettingTotal("K");
        int def = genMods.getSettingTotal("DEF");
        int RbWrTe = genMods.getSettingTotal("RB/WR/TE");
        int bench = rounds -qb -rb -wr -te -k -def -RbWrTe;

        for(int x =0; x<qb;x++)
        dtm2.addRow(new Object[]{"QB"});

        for(int x =0; x<rb;x++)
        dtm2.addRow(new Object[]{"RB"});

        for(int x =0; x<wr;x++)
        dtm2.addRow(new Object[]{"WR"});

        for(int x =0; x<te; x++)
        dtm2.addRow(new Object[]{"TE"});

        for(int x =0; x<RbWrTe;x++)
        dtm2.addRow(new Object[]{"RB/WR/TE"});

        for(int x =0; x<k; x++)
        dtm2.addRow(new Object[]{"K"});

        for(int x =0; x<def;x++)
        dtm2.addRow(new Object[]{"DEF"});

        for(int x =0; x<bench;x++)
        dtm2.addRow(new Object[]{"BENCH"});

        RosterTable.setDefaultRenderer(JPanel.class, new PanelRenderer());

        javax.swing.GroupLayout TeamRosterPanelLayout = new javax.swing.GroupLayout(TeamRosterPanel);
        TeamRosterPanel.setLayout(TeamRosterPanelLayout);
        TeamRosterPanelLayout.setHorizontalGroup(
            TeamRosterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 982, Short.MAX_VALUE)
        );
        TeamRosterPanelLayout.setVerticalGroup(
            TeamRosterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TeamRosterPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        DraftTabsPanel.addTab("Team Rosters", TeamRosterPanel);

        jLabel2.setText("Sort by:");

        RBCheckBox.setText("RB");
        RBCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RBCheckBoxActionPerformed(evt);
            }
        });

        WRCheckBox.setText("WR");
        WRCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WRCheckBoxActionPerformed(evt);
            }
        });

        TECheckBox.setText("TE");
        TECheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TECheckBoxActionPerformed(evt);
            }
        });

        KCheckBox.setText("K");
        KCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KCheckBoxActionPerformed(evt);
            }
        });

        DEFCheckBox.setText("DEF");
        DEFCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DEFCheckBoxActionPerformed(evt);
            }
        });

        QBCheckBox.setText("QB");
        QBCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QBCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SortPlayerPanelLayout = new javax.swing.GroupLayout(SortPlayerPanel);
        SortPlayerPanel.setLayout(SortPlayerPanelLayout);
        SortPlayerPanelLayout.setHorizontalGroup(
            SortPlayerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SortPlayerPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(SortPlayerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(RBCheckBox)
                    .addComponent(TECheckBox)
                    .addComponent(KCheckBox)
                    .addComponent(DEFCheckBox)
                    .addGroup(SortPlayerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(QBCheckBox)
                        .addComponent(WRCheckBox))))
        );
        SortPlayerPanelLayout.setVerticalGroup(
            SortPlayerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SortPlayerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(7, 7, 7)
                .addComponent(QBCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(RBCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(WRCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TECheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(KCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(DEFCheckBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        RBCheckBox.setSelected(true);
        WRCheckBox.setSelected(true);
        TECheckBox.setSelected(true);
        KCheckBox.setSelected(true);
        DEFCheckBox.setSelected(true);
        QBCheckBox.setSelected(true);

        PlayerList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        PlayerList.setToolTipText("");
        jScrollPane1.setViewportView(PlayerList);
        DefaultListModel dlm = new DefaultListModel();

        PlayerList.setModel(dlm);

        for(int x =0; x<players.size();x++)
        dlm.addElement(players.get(x));

        PlayerList.setSelectedIndex(0);

        DraftPickButton.setText("Draft Player");
        DraftPickButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DraftPickButtonActionPerformed(evt);
            }
        });

        TeamLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        TeamLabel.setText("Team.");

        RestartDraft.setText("Restart Draft");
        RestartDraft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RestartDraftActionPerformed(evt);
            }
        });

        AddPlayerButton.setText("Add New Player");
        AddPlayerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddPlayerButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(DraftTabsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(TeamLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(RestartDraft)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(DraftPickButton)
                                        .addGap(45, 45, 45)
                                        .addComponent(AddPlayerButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel3))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(SortPlayerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(440, Short.MAX_VALUE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(SortPlayerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DraftPickButton)
                            .addComponent(AddPlayerButton))
                        .addGap(46, 46, 46)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TeamLabel)
                        .addGap(25, 25, 25)
                        .addComponent(RestartDraft))
                    .addComponent(DraftTabsPanel))
                .addContainerGap())
        );

        TeamLabel.setText(currentTeamPicking + " is on the board");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void QBCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QBCheckBoxActionPerformed
        this.resetList(QBCheckBox.isSelected(), RBCheckBox.isSelected(), WRCheckBox.isSelected(), 
                TECheckBox.isSelected(), KCheckBox.isSelected(), DEFCheckBox.isSelected());
    }//GEN-LAST:event_QBCheckBoxActionPerformed

    private void RBCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RBCheckBoxActionPerformed
                this.resetList(QBCheckBox.isSelected(), RBCheckBox.isSelected(), WRCheckBox.isSelected(), 
                TECheckBox.isSelected(), KCheckBox.isSelected(), DEFCheckBox.isSelected());
    }//GEN-LAST:event_RBCheckBoxActionPerformed

    private void WRCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WRCheckBoxActionPerformed
            this.resetList(QBCheckBox.isSelected(), RBCheckBox.isSelected(), WRCheckBox.isSelected(), 
                TECheckBox.isSelected(), KCheckBox.isSelected(), DEFCheckBox.isSelected());
    }//GEN-LAST:event_WRCheckBoxActionPerformed

    private void TECheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TECheckBoxActionPerformed
            this.resetList(QBCheckBox.isSelected(), RBCheckBox.isSelected(), WRCheckBox.isSelected(), 
                TECheckBox.isSelected(), KCheckBox.isSelected(), DEFCheckBox.isSelected());
    }//GEN-LAST:event_TECheckBoxActionPerformed

    private void KCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KCheckBoxActionPerformed
            this.resetList(QBCheckBox.isSelected(), RBCheckBox.isSelected(), WRCheckBox.isSelected(), 
                TECheckBox.isSelected(), KCheckBox.isSelected(), DEFCheckBox.isSelected());
    }//GEN-LAST:event_KCheckBoxActionPerformed

    private void DEFCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DEFCheckBoxActionPerformed
        this.resetList(QBCheckBox.isSelected(), RBCheckBox.isSelected(), WRCheckBox.isSelected(), 
                TECheckBox.isSelected(), KCheckBox.isSelected(), DEFCheckBox.isSelected());
    }//GEN-LAST:event_DEFCheckBoxActionPerformed

    private void DraftPickButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DraftPickButtonActionPerformed
           
        String playerSelected = (String)PlayerList.getSelectedValue();
        String firstName = this.getFirstName(playerSelected);
        String lastName = this.getLastName(playerSelected);
        String realTeam=this.getTeam(playerSelected);
        String position=this.getPosition(playerSelected);
        int currentRound = (currentPick-1)/ (teams.size())+1;

        //pick in rounds go from 0 to team size-1
        int currentPickInRound = (currentPick-1)%teams.size();


        //modifies the player list
        DefaultListModel dlm = (DefaultListModel) PlayerList.getModel();
        int index = PlayerList.getSelectedIndex();
        if(index!=-1)
            dlm.remove(index);

        PlayerList.setSelectedIndex(0);

        JPanel playerPanel = playerMods.getPlayerPanel(lastName, firstName, position, realTeam);
        
        //modify draft board table and get team picking
        if(currentRound%2==1)
        {
            currentTeamPicking = teams.get(currentPickInRound);
            DraftBoardTable.setValueAt(playerPanel, currentRound-1, currentPickInRound+1);
        }
        else
        {                
            currentTeamPicking = teams.get(teams.size()-currentPickInRound-1);
            DraftBoardTable.setValueAt(playerPanel, currentRound-1, teams.size()-currentPickInRound);
        }

        //modify team on board label


        teamMods.addPickedPlayer(currentPick, lastName, firstName, position, currentTeamPicking, realTeam);
        this.addToRoster(firstName,lastName, position, currentTeamPicking, realTeam);

        this.setTeamOnTheClock(currentRound, currentTeamPicking);
        
        TeamLabel.setText(currentTeamPicking + " is on the clock");
        currentPick++;   
        players.remove(playerSelected);
        
        
          
    }//GEN-LAST:event_DraftPickButtonActionPerformed

    private void RestartDraftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RestartDraftActionPerformed
        Object[] options = {"I am sure","Go back to the draft"};
        String path = "WarningSign.jpg";
        
        ImageIcon image = new ImageIcon(path,"NFL logo");
        
        
        
        int choice = JOptionPane.showOptionDialog(null,
                "Are you sure that you want to reset the draft? \n The draft cannot be recovered if it is reset.",
                "Restarting the draft", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                image,
                options,
                options[1]);
                
                
        if(choice ==0)
        {
            FirstFrame fram = new FirstFrame();
            fram.setVisible(true);
            fram.setDefaultCloseOperation(EXIT_ON_CLOSE);
            playerMods.resetPlayers();
            this.dispose();
        }
    }//GEN-LAST:event_RestartDraftActionPerformed

    private void AddPlayerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddPlayerButtonActionPerformed
        AddPlayerGUI playerGUI = new AddPlayerGUI();
        playerGUI.setVisible(true);
        playerGUI.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_AddPlayerButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DraftGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DraftGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DraftGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DraftGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DraftGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddPlayerButton;
    private javax.swing.JCheckBox DEFCheckBox;
    private javax.swing.JPanel DraftBoardPanel;
    private javax.swing.JTable DraftBoardTable;
    private javax.swing.JButton DraftPickButton;
    private javax.swing.JTabbedPane DraftTabsPanel;
    private javax.swing.JCheckBox KCheckBox;
    private javax.swing.JList PlayerList;
    private javax.swing.JCheckBox QBCheckBox;
    private javax.swing.JCheckBox RBCheckBox;
    private javax.swing.JButton RestartDraft;
    private javax.swing.JTable RosterTable;
    private javax.swing.JPanel SortPlayerPanel;
    private javax.swing.JCheckBox TECheckBox;
    private javax.swing.JLabel TeamLabel;
    private javax.swing.JPanel TeamRosterPanel;
    private javax.swing.JCheckBox WRCheckBox;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
