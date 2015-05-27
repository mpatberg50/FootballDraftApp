/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package footballboard;

/**
 *
 * @author Patberg
 */
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;


public class PlayerPanelModel extends DefaultTableModel {
  
 
  public PlayerPanelModel() {
    super();
  }
 @Override
  public Class getColumnClass(int columnIndex) 
  {
      if(columnIndex!=0) 
          return JPanel.class; 
      else
          return Object.class;
  }

 
}