/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lzw;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Kamila
 */
class MyPanel extends JPanel {

    public MyPanel() {
		setPreferredSize(new Dimension(400, 400));
                    JButton LZWC = new JButton();
                    LZWC.setBounds(100 , 50 , 250 , 50);
   /* LZWC.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Integer> compressed = LZW.compress(text);
            LZWList = compressed;
            comp.setText(compressed.toString());
        }
    });*/
    this.add(LZWC);
    
	}

    
    
}
