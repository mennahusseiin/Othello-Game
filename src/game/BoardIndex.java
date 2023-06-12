//BoardIndex
package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class BoardIndex extends JLabel {

    //Row Index
    int i;
    //Column Index
    int j;

    GameEngine Game_Engine;
    JPanel Parent_Panel;

    public int PossibleCellHighlight = 0;


    public BoardIndex(GameEngine Game_Engine, JPanel Parent_Panel, int i, int j){
        this.Game_Engine = Game_Engine;
        this.Parent_Panel = Parent_Panel;
        this.i = i;
        this.j = j;
        this.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                handleClick(e);
            }
        });
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        int Left_Margin = this.getWidth()  / 10;
        int Top_Margin  = this.getHeight() / 10;

        //Highlight Drawing
        if(PossibleCellHighlight == 1)
        {
            graphics.setColor(new Color(138, 177, 62));
            graphics.fillRect(0,0,this.getWidth(),this.getHeight());
            graphics.setColor(Parent_Panel.getBackground());
            graphics.fillRect(4,4,this.getWidth()-8,this.getHeight()-8);
        }

        //Border Drawing
        graphics.setColor(Color.BLACK);
        graphics.drawRect(0,0,this.getWidth()-1,this.getHeight()-1);

        //Piece Drawing
        int value = Game_Engine.getCellValue(i,j);

        //Black Coin
        if(value == 1)
        {
            graphics.setColor(Color.BLACK);
            graphics.fillOval(Left_Margin,Top_Margin,this.getWidth()-2*Left_Margin,this.getHeight()-2*Top_Margin);
        }

        //White Coin
        else if(value == 2)
        {
            graphics.setColor(Color.WHITE);
            graphics.fillOval(Left_Margin,Top_Margin,this.getWidth()-2*Left_Margin,this.getHeight()-2*Top_Margin);
        }
    }

    private void handleClick(MouseEvent e)
    {
        Game_Engine.handleClick(i,j);
    }

}
