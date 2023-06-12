package player.ai;

import game.BoardFunctions;

import java.awt.*;

public class Minimax {

    static int Num_NodesExplored = 0;

    //returns max score move
    public static Point SolveBoard(int[][] board, int player,int depth,Evaluator e){
        Point Best_Move;
        int HighestScore, childScore;
        Num_NodesExplored = 0;

        //Best Score is: 0x80000000;
        HighestScore = Integer.MIN_VALUE;
        Best_Move = null;

        //Loop on all the possible moves
        for(Point move : BoardFunctions.getAllPossibleMoves(board,player)){
            //create new node
            int[][] newNode = BoardFunctions.getNewBoardAfterMove(board,move,player);

            //recursive call
            childScore = MinMax_AlphaBetaPruning(newNode,player,depth-1,false,Integer.MIN_VALUE,Integer.MAX_VALUE,e);
            if(childScore > HighestScore)
            {
                HighestScore = childScore;
                Best_Move = move;
            }
        }

        System.out.println("Nodes Explored : " + Num_NodesExplored);
        return Best_Move;
    }


    //returns minimax value for a given node with A/B pruning
    private static int MinMax_AlphaBetaPruning(int[][] node,int player,int depth,boolean max,int alpha,int beta,Evaluator e){
        int opponent_player = (player==1) ? 2 : 1;
        int score;
        Num_NodesExplored++;

        //if terminal reached or depth limit reached: evaluate
        if(depth == 0 || BoardFunctions.BOOL_GameFinished(node))
        {
            return e.Evaluate_AI(node,player);
        }

        //if no moves available: forfeit turn
        if((max && !BoardFunctions.PlayerHasAnyMoves(node,player)) || (!max && !BoardFunctions.PlayerHasAnyMoves(node,opponent_player)))
        {
            return MinMax_AlphaBetaPruning(node,player,depth-1,!max,alpha,beta,e);
        }

        if(max){
            //maximizing
            score = Integer.MIN_VALUE;
            for(Point move : BoardFunctions.getAllPossibleMoves(node,player)){
                //my turn
                //create new node
                int[][] newNode = BoardFunctions.getNewBoardAfterMove(node,move,player);

                //recursive call
                int childScore = MinMax_AlphaBetaPruning(newNode,player,depth-1,false,alpha,beta,e);
                if(childScore > score)
                {
                    score = childScore;
                }

                //update alpha
                if(score > alpha)
                {
                    alpha = score;
                }
                if(beta <= alpha) {
                    break; //Beta Cutoff
                }
            }

        }

        else{
            //minimizing
            score = Integer.MAX_VALUE;
            for(Point move : BoardFunctions.getAllPossibleMoves(node,opponent_player)){
                //opponent turn
                //create new node
                int[][] newNode = BoardFunctions.getNewBoardAfterMove(node,move,opponent_player);

                //recursive call
                int childScore = MinMax_AlphaBetaPruning(newNode,player,depth-1,true,alpha,beta,e);
                if(childScore < score)
                {
                    score = childScore;
                }
                //update beta
                if(score < beta) {
                    beta = score;
                }
                if(beta <= alpha)
                {
                    break; //Alpha Cutoff
                }
            }
        }
        return score;
    }

}
