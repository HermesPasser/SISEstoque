import javax.swing.JOptionPane;

import gui.AlternativeGUI;
import gui.GUI;

public class Main {

	public static void main(String[] args) {
            
		int txt = JOptionPane.showConfirmDialog(null,"Este programa e um prototipo em desenvolvimento.\n"
                        + "Ao usar o software voce concorda que quaisquer danos\n "
                        + "causados sao de total responsabilidade do usuario. \n\n"
                        + "Deseja iniciar no modo de seguranca?");
                
                
		if(txt == JOptionPane.YES_OPTION){
			AlternativeGUI gui = new AlternativeGUI();
			gui.setVisible(true);
		}else if(txt == JOptionPane.NO_OPTION){
			GUI gui = new GUI();
			gui.setVisible(true);
		}
	}
}