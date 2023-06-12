package game;

import player.ai.*;
import player.human.Human_Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel implements GameEngine {

    int[][] board;

    //Turn = 1 : black
    //Turn = 2 : white
    int turn = 1;

    //swing elements
    BoardIndex[][] cells;
    JLabel PlayerOneScore;
    JLabel PlayerTwoScore;

    int t_score1 = 0;
    int t_score2 = 0;

    JLabel PlayerOneTotalScore;
    JLabel PlayerTwoTotalScore;

    JLabel Winner;

    GamePlayer PlayerOne;

    GamePlayer PlayerTwo;

    Timer PlayerOneHandlerTimer;
    Timer PlayerTwoHandlerTimer;

    @Override
    public int getCellValue(int i,int j)
    {
        return board[i][j];
    }

    @Override
    public void setCellValue(int i,int j,int value)
    {
        board[i][j] = value;
    }

    public GamePanel(int x, int y, int z){

        switch (x) {
            case 0:
                // Code to execute if the variable is "Option 1"
                PlayerOne = new Human_Player(1);
                PlayerTwo = new Human_Player(2);
                break;
            case 1:
                // Code to execute if the variable is "Option 2"
                PlayerOne = new Human_Player(1);
                PlayerTwo = new AI_Player(2,y);
                break;
            case 2:
                // Code to execute if the variable is "Option 3"
                PlayerOne = new AI_Player(1,y);
                PlayerTwo = new AI_Player(2,z);
                break;
            default:
                // Code to execute if the variable does not match any of the cases
                PlayerOne = new Human_Player(5);
                PlayerTwo = new Human_Player(6);
                break;
        }

        //Start Page Design
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());


        //Board Display
        JPanel OthelloBoard = new JPanel();
        OthelloBoard.setLayout(new GridLayout(8,8));
        OthelloBoard.setPreferredSize(new Dimension(500,500));
        OthelloBoard.setBackground(new Color(41,100, 59));

        //Init board
        Reset_Board();

        //Board Display
        cells = new BoardIndex[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j] = new BoardIndex(this,OthelloBoard,i,j);
                OthelloBoard.add(cells[i][j]);
            }
        }

        //Score sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar,BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(250,0));

        //Current game score
        PlayerOneScore = new JLabel("Score 1: ");
        PlayerTwoScore = new JLabel("Score 2: ");

        //Players total/accumulative score
        PlayerOneTotalScore = new JLabel("Total Score of player 1: ");
        PlayerTwoTotalScore = new JLabel("Total Score of player 2: ");

        //Last game Winner
        Winner = new JLabel("");

        sidebar.add(PlayerOneScore);
        sidebar.add(PlayerTwoScore);

        sidebar.add(new JLabel("-------------------------------------------------------"));

        sidebar.add(PlayerOneTotalScore);
        sidebar.add(PlayerTwoTotalScore);

        sidebar.add(new JLabel("-------------------------------------------------------"));

        sidebar.add(Winner);


        this.add(sidebar,BorderLayout.WEST);
        this.add(OthelloBoard);

        Update_BoardInfo();


        //AI Handler Timer (to unfreeze gui)
        PlayerOneHandlerTimer = new Timer(1000,(ActionEvent e) -> {
            handleAI(PlayerOne);
            PlayerOneHandlerTimer.stop();
            manageTurn();
        });

        PlayerTwoHandlerTimer = new Timer(1000,(ActionEvent e) -> {
            handleAI(PlayerTwo);
            PlayerTwoHandlerTimer.stop();
            manageTurn();
        });

        manageTurn();
    }

    private boolean awaitForClick = false;

    public void manageTurn(){
        if(BoardFunctions.PlayerHasAnyMoves(board,1) || BoardFunctions.PlayerHasAnyMoves(board,2)) {
            Update_BoardInfo();
            //Black Turn
            if (turn == 1)
            {
                if(BoardFunctions.PlayerHasAnyMoves(board,1))
                {
                    if (PlayerOne.BOOL_User_Player())
                    {
                        awaitForClick = true;
                    }
                    else
                    {
                        PlayerOneHandlerTimer.start();
                    }
                }
                else
                {
                    //forfeit this move and pass the turn
                    System.out.println("Player 1 has no legal moves !");
                    turn = 2;
                    manageTurn();
                }

            }

            //White Turn
            else {
                if(BoardFunctions.PlayerHasAnyMoves(board,2))
                {
                    if (PlayerTwo.BOOL_User_Player())
                    {
                        awaitForClick = true;
                    }
                    else
                    {
                        PlayerTwoHandlerTimer.start();
                    }
                }else{
                    //forfeit this move and pass the turn
                    System.out.println("Player 2 has no legal moves !");
                    turn = 1;
                    manageTurn();
                }
            }
        }
        else
        {
            //Game finished
            System.out.println("Game Finished !");

            int winner = BoardFunctions.getWinner(board);
            if(winner==1)
            {
                Winner.setText("Last Game Winner is\n" + PlayerOne.Player_Name() + " 1");
                t_score1++;
            }

            else if(winner==2)
            {
                Winner.setText("Last Game Winner is " + PlayerTwo.Player_Name() + " 2");
                t_score2++;
            }

            Update_TotalScore();

            //restart
            Reset_Board();

            turn=1;
            manageTurn();
        }
    }

    public void Reset_Board(){
        board = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j]=0;
            }
        }
        //initial board state
        setCellValue(3,3,2);
        setCellValue(3,4,1);
        setCellValue(4,3,1);
        setCellValue(4,4,2);
    }

    //update highlights on possible moves and scores
    public void Update_BoardInfo(){

        int Player1Score = 0;
        int Player2Score = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board[i][j] == 1) Player1Score++;
                if(board[i][j] == 2) Player2Score++;

                if(BoardFunctions.ValidPlay(board,turn,i,j))
                {
                    cells[i][j].PossibleCellHighlight = 1;
                }
                else
                {
                    cells[i][j].PossibleCellHighlight = 0;
                }
            }
        }

        PlayerOneScore.setText(PlayerOne.Player_Name() + "1 :  " + Player1Score);
        PlayerTwoScore.setText(PlayerTwo.Player_Name() + "2 :  " + Player2Score);
        Update_TotalScore();
    }

    public void Update_TotalScore(){
        PlayerOneTotalScore.setText("Total Score of " + PlayerOne.Player_Name() + "1 :  " + t_score1);
        PlayerTwoTotalScore.setText("Total Score of " + PlayerTwo.Player_Name() + "2 :  " + t_score2);
    }

    @Override
    public void handleClick(int i,int j){
        if(awaitForClick && BoardFunctions.ValidPlay(board,turn,i,j)){
            System.out.println("User Played in : "+ i + " , " + j);

            //update board
            board = BoardFunctions.getNewBoardAfterMove(board,new Point(i,j),turn);

            //advance turn
            turn = (turn == 1) ? 2 : 1;

            repaint();

            awaitForClick = false;

            //callback
            manageTurn();
        }
    }

    public void handleAI(GamePlayer ai){
        Point aiPlayPoint = ai.Play(board);
        int i = aiPlayPoint.x;
        int j = aiPlayPoint.y;
        if(!BoardFunctions.ValidPlay(board,ai.Player_mark,i,j)) System.err.println("FATAL : AI Invalid Move !");
        System.out.println(ai.Player_Name() + " Played in : "+ i + " , " + j);

        //update board
        board = BoardFunctions.getNewBoardAfterMove(board,aiPlayPoint,turn);

        //advance turn
        turn = (turn == 1) ? 2 : 1;

        repaint();
    }

}
