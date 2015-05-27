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

import javax.swing.table.TableCellRenderer;
import javax.swing.JTable;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class PanelRenderer implements TableCellRenderer
{
    
    
    @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
  {
      JPanel panel= new JPanel();
      if(value instanceof JPanel)
      {
            panel = (JPanel)value;
            return panel;
      }
      else if(value instanceof Component)
      {
          return (Component)value;
      }
      return panel;
  }
}