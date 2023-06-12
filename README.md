# Othello-Game

Othello is a strategy board game played between two people on a 8x8 checkerboard with black and white disks. 
Game rules are described in the following link: 
https://www.youtube.com/watch?v=zFrlu3E18BA
                
Our program is implemented in JAVA and it is mainly about an AI playing Othello against a human using an AI program we implemented or against another AI.  
AI will determine the state of the board using evaluators, differentiate between different moves using algorithms and 
heuristics, choose the best move to make, and take action based on these heuristics.

# MANUAL
•	The program starts by giving the user different options to play as:
1. Human Vs Human.
2. Human Vs AI.
3. AI Vs AI.
  
•	If the user chooses any option that contains AI (choice 2 & 3), the GUI shows 3 different levels, which are:
1. Easy.
2. Medium.
3. Hard.

•	When the user chooses a game option and an AI level, he can press on “Open Game Panel” which starts the game with the options he chose.

•	Once the game starts, the program starts to calculate the score of each player each turn based on the number of disks of each player.

•	AI uses some algorithms as minimax and minimax with alpha-beta pruning and heuristics as mobility, coins parity and corners captured
to choose the best move to take.

•	When one player wins, the accumulative score of him is incremented and a new game with the same options start and the winner of the last game is displayed.

•	The user can start a new game by closing the current game panel and choosing the options again ana open a new panel with a whole new score.
