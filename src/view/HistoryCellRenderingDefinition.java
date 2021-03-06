package view;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

// Cell Rendering definition for the move-history list.
public class HistoryCellRenderingDefinition extends JLabel implements
	ListCellRenderer {

		private static final long serialVersionUID = 1L;

		private final Color HIGHLIGHTING = Color.LIGHT_GRAY;

		public HistoryCellRenderingDefinition() {
			super ();
			setOpaque ( true );
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {	

				assert( list != null ):"List is null!";
				assert( value != null ):"Empty value";
				assert( index >= 0):"Index out of bounds";
				assert( isSelected == true || isSelected == false):"Must be selected before";
				assert( cellHasFocus == true || cellHasFocus == false):"Cell focus must be seted before";

				// Assumes the stuff in the list has a pretty toString
				setText (value.toString ());
				// here the highlighting of every second row is defined
				if ( ( index + 2 ) % 4 == 0 ) { 
					setBackground ( HIGHLIGHTING );
				}
				else if ( ( index + 1 ) % 4 == 0 ) {
					setBackground ( HIGHLIGHTING );
				}
				else{
					setBackground ( Color.WHITE );
				}
				
				if ( cellHasFocus ) {
					setBackground ( Color.BLUE );
				}
				return this;
  		}
}
