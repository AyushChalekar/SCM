package components;

import javax.swing.JButton;

/**
 *
 * @author ayu-senpai
 */

public class item extends JButton{

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
    
    public item(String name, int index, boolean subItem){
        super(name);
        this.index=index;
        this.length = 0;
    }
    
    // Variables declaration - do not modify                     
    private int index;
    private int length;    
    // End of variables declaration                   
}