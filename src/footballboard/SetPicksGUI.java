/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package footballboard;

import java.util.ArrayList;
import javax.swing.table.*;
import java.util.Random;
import javax.swing.JOptionPane;
/**
 *
 * @author Patberg
 */
public class SetPicksGUI extends javax.swing.JFrame {

    TeamModifications teamMods = new TeamModifications();
    GeneralSettingsModifications genMods = new GeneralSettingsModifications();
    /**
     * Creates new form SetPicksGUI
     */
    public SetPicksGUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        DraftTable = new javax.swing.JTable();
        BackToSettingsButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        ManualDraftPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        TeamLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        PickNumberComboBox = new javax.swing.JComboBox();
        TeamComboBox = new javax.swing.JComboBox();
        AddPick = new javax.swing.JButton();
        RandomDraftOrder = new javax.swing.JPanel();
        RandomPicksButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        DraftPickSetupLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        ResetPicksButton = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        DraftTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null}
            },
            new String [] {
                "Pick Number", "Team"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        DraftTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(DraftTable);
        DefaultTableModel dtm = new DefaultTableModel();
        DraftTable.setModel(dtm);
        dtm.addColumn("Pick Number");
        dtm.addColumn("Team Name");

        for(int x =1; x<=teamMods.getTeams().size();x++)
        dtm.addRow(new Object[] {x});

        ArrayList<String> teamList = new ArrayList();

        teamList = teamMods.getTeams();

        for(int x = 0; x<teamList.size();x++)
        {
            if(teamMods.getPickNumber(teamList.get(x))!=-1)
            DraftTable.setValueAt(teamList.get(x),teamMods.getPickNumber(teamList.get(x))-1, 1);
        }

        BackToSettingsButton.setText("Back To Settings");
        BackToSettingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackToSettingsButtonActionPerformed(evt);
            }
        });

        ManualDraftPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setText("For Manual Draft Order:");

        TeamLabel.setText("Team:");

        jLabel4.setText("Pick Number:");

        AddPick.setText("Add Pick");
        AddPick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddPickActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ManualDraftPanelLayout = new javax.swing.GroupLayout(ManualDraftPanel);
        ManualDraftPanel.setLayout(ManualDraftPanelLayout);
        ManualDraftPanelLayout.setHorizontalGroup(
            ManualDraftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManualDraftPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ManualDraftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(ManualDraftPanelLayout.createSequentialGroup()
                        .addComponent(TeamLabel)
                        .addGap(50, 50, 50)
                        .addComponent(TeamComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ManualDraftPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(PickNumberComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(AddPick))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        ManualDraftPanelLayout.setVerticalGroup(
            ManualDraftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManualDraftPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(ManualDraftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TeamLabel)
                    .addComponent(TeamComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ManualDraftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(PickNumberComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(AddPick)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        int size = teamMods.getTeams().size();

        for(int x = 1; x<=size;x++)
        PickNumberComboBox.addItem(x);
        ArrayList<String> teams = teamMods.getTeams();

        for(int x = 0; x<teams.size(); x++)
        TeamComboBox.addItem(teams.get(x));

        RandomPicksButton.setText("Next Random Pick");
        RandomPicksButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RandomPicksButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("For Random Draft Order:");

        javax.swing.GroupLayout RandomDraftOrderLayout = new javax.swing.GroupLayout(RandomDraftOrder);
        RandomDraftOrder.setLayout(RandomDraftOrderLayout);
        RandomDraftOrderLayout.setHorizontalGroup(
            RandomDraftOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RandomDraftOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(RandomDraftOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(RandomPicksButton, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        RandomDraftOrderLayout.setVerticalGroup(
            RandomDraftOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RandomDraftOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(RandomPicksButton)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        DraftPickSetupLabel.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        DraftPickSetupLabel.setText("Draft Pick Setup");

        jButton1.setText("Proceed To Draft");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        ResetPicksButton.setText("Reset Picks");
        ResetPicksButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetPicksButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ManualDraftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(RandomDraftOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(ResetPicksButton)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BackToSettingsButton)
                        .addGap(45, 45, 45)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(DraftPickSetupLabel)
                .addGap(133, 133, 133))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DraftPickSetupLabel)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(RandomDraftOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(19, 19, 19)
                                        .addComponent(ResetPicksButton))
                                    .addComponent(ManualDraftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton1)
                                    .addComponent(BackToSettingsButton)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(40, 40, 40))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ArrayList<String> teamsWithoutPicks = teamMods.getTeamsWithOutPick();
        
        if(teamsWithoutPicks.size()==0)
        {
            DraftGUI draftGui = new DraftGUI();
            draftGui.setDefaultCloseOperation(EXIT_ON_CLOSE);
            draftGui.setVisible(true);
            this.dispose();
        }    
        else
        {
            JOptionPane.showMessageDialog(this, "Every team needs a draft pick to proceed to the draft.");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void AddPickActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddPickActionPerformed
            teamMods.addPick((String)TeamComboBox.getSelectedItem(),(Integer)PickNumberComboBox.getSelectedItem());
            DraftTable.setValueAt((String)TeamComboBox.getSelectedItem(), (Integer)PickNumberComboBox.getSelectedItem()-1,1 );
    }//GEN-LAST:event_AddPickActionPerformed

    private void BackToSettingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToSettingsButtonActionPerformed
        FirstFrame fram = new FirstFrame();
        fram.setDefaultCloseOperation(EXIT_ON_CLOSE);
        fram.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BackToSettingsButtonActionPerformed

    private void RandomPicksButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RandomPicksButtonActionPerformed
        ArrayList<String> teams = teamMods.getTeamsWithOutPick();
        int pick = teamMods.getLastAvailablePick();
        int randomTeam = (int)(Math.random()*teams.size());
        
        if(pick!=0)
        {
            teamMods.addPick(teams.get(randomTeam), pick);
            DraftTable.setValueAt(teams.get(randomTeam), pick-1, 1);
        }
    }//GEN-LAST:event_RandomPicksButtonActionPerformed

    private void ResetPicksButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetPicksButtonActionPerformed
        genMods.resetPicks();
        
        for(int x =0; x<teamMods.getTeams().size();x++)
            DraftTable.setValueAt("", x, 1);
    }//GEN-LAST:event_ResetPicksButtonActionPerformed

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
            java.util.logging.Logger.getLogger(SetPicksGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SetPicksGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SetPicksGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SetPicksGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SetPicksGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddPick;
    private javax.swing.JButton BackToSettingsButton;
    private javax.swing.JLabel DraftPickSetupLabel;
    private javax.swing.JTable DraftTable;
    private javax.swing.JPanel ManualDraftPanel;
    private javax.swing.JComboBox PickNumberComboBox;
    private javax.swing.JPanel RandomDraftOrder;
    private javax.swing.JButton RandomPicksButton;
    private javax.swing.JButton ResetPicksButton;
    private javax.swing.JComboBox TeamComboBox;
    private javax.swing.JLabel TeamLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
