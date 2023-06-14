package player.ai;

import game.BoardFunctions;


public class AI_Evaluator implements Evaluator {

    //Evaluation Function Changes during Early-Game / Mid-Game / Late-Game
    enum GamePhase {
        EARLY_GAME,
        MID_GAME,
        LATE_GAME
    }

    private GamePhase getGamePhase(int[][] board){
        //Get total stone count
        int Stone_Count = BoardFunctions.getTotalBoardStoneCount(board);

        //Checks the phase of the game : Early-Game / Mid-Game / Late-Game
        if(Stone_Count<20)
        {
            return GamePhase.EARLY_GAME;
        }

        else if(Stone_Count<=58)
        {
            return GamePhase.MID_GAME;
        }

        else return GamePhase.LATE_GAME;
    }


    //Coin Parity Heuristic
    public static int CoinParity(int[][] board , int player){
        int opponent_player = (player==1) ? 2 : 1;

        //Get Maximum Player total coins
        int MaxPlayerCoins = BoardFunctions.getOnePlayerStoneCount(board,player);

        //Get Minimum Player total coins
        int MinPlayerCoins = BoardFunctions.getOnePlayerStoneCount(board,opponent_player);

        //Coin Parity heuristic Calculation
        return 100 * (MaxPlayerCoins - MinPlayerCoins) / (MaxPlayerCoins + MinPlayerCoins);
    }

    //Mobility Heuristic
    public static int Mobility(int[][] board , int player){
        int opponent_player = (player==1) ? 2 : 1;

        //Get Maximum Player available moves
        int MaxPlayerMoves = BoardFunctions.getAllPossibleMoves(board,player).size();

        //Get Minimum Player available moves
        int MinPlayerMoves = BoardFunctions.getAllPossibleMoves(board,opponent_player).size();

        //Mobility heuristic Calculation
        return 100 * (MaxPlayerMoves - MinPlayerMoves) / (MaxPlayerMoves + MinPlayerMoves+1);
    }

    //Corners Captured Heuristic
    public static int CornersCaptured(int[][] board , int player){
        int opponent_player = (player==1) ? 2 : 1;

        int MaxPlayerCorners = 0;
        int MinPlayerCorners = 0;

        //Calculating Maximum Player Corners
        if(board[0][0] == player) MaxPlayerCorners++;
        if(board[7][0] == player) MaxPlayerCorners++;
        if(board[0][7] == player) MaxPlayerCorners++;
        if(board[7][7] == player) MaxPlayerCorners++;

        //Calculating Minimum Player Corners
        if(board[0][0] == opponent_player) MinPlayerCorners++;
        if(board[7][0] == opponent_player) MinPlayerCorners++;
        if(board[0][7] == opponent_player) MinPlayerCorners++;
        if(board[7][7] == opponent_player) MinPlayerCorners++;

        //CornersCaptured heuristic Calculation
        return 100 * (MaxPlayerCorners - MinPlayerCorners) / (MaxPlayerCorners + MinPlayerCorners + 1);
    }

    /*Project description
    public static int evalParity(int[][] board){
        int remDiscs = 64 - BoardHelper.getTotalStoneCount(board);
        return remDiscs % 2 == 0 ? -1 : 1;
    }*/

    public int Evaluate_AI(int[][] board , int player){

        //Terminal
        if(BoardFunctions.BOOL_GameFinished(board))
        {
            return 1000* CoinParity(board, player);
        }

        //Semi-terminal
        switch (getGamePhase(board)){
            case EARLY_GAME:
                return 1000* CornersCaptured(board,player) + 50* Mobility(board,player);
            case MID_GAME:
                return 1000* CornersCaptured(board,player) + 20* Mobility(board,player) + 10* CoinParity(board, player) ;
            case LATE_GAME:
            default:
                return 1000* CornersCaptured(board,player) + 100* Mobility(board,player) + 500* CoinParity(board, player);
        }
    }

}
