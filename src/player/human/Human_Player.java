//Human player
package player.human;

import game.GamePlayer;

import java.awt.*;

public class Human_Player extends GamePlayer {

    public Human_Player(int mark)
    {
        super(mark);
    }

    @Override
    public boolean BOOL_User_Player()
    {
        return true;
    }

    @Override
    public String Player_Name()
    {
        return "User";
    }

    @Override
    public Point Play(int[][] board)
    {
        return null;
    }

    @Override
    public String toString()
    {
        return Player_Name();
    }
}