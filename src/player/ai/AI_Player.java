package player.ai;

import game.GamePlayer;

import java.awt.*;

public class AI_Player extends GamePlayer {

    private int AI_SearchingDepth;
    private Evaluator AI_Evaluator;

    public AI_Player(int mark, int depth)
    {
        super(mark);
        AI_SearchingDepth = depth;
        AI_Evaluator = new AI_Evaluator();
    }

    @Override
    public boolean BOOL_User_Player()
    {
        return false;
    }

    @Override
    public String Player_Name()
    {
        return "AI (Depth : " + AI_SearchingDepth + ")  ";
    }

    @Override
    public Point Play(int[][] board)
    {
        return Minimax.SolveBoard(board,Player_mark,AI_SearchingDepth,AI_Evaluator);
    }
}
