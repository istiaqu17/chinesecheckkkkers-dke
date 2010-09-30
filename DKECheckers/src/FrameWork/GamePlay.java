/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FrameWork;

/**
 *
 * @author Stefan
 */
public class GamePlay {

    // variables should be private
    public Board board;
    public Player turn;
    public Player[] players;
    public Player winner;


    public GamePlay(Board b, Player[] p){
        //the gui has the info about the players (human or AI, what AI)
        //so the gui must give the array of players
        //first player will begin
        board = b;
        players = p;
        // make turn an integer
        turn = players[0];
    }

    public void changeTurn(){
        // Way too complicated
        /*
         * if (turn + 1 < players.length){
         *    turn++} else {
         * turn = 0;}
         */
        // I would write this
        for(int i = 0; i<players.length; i++){
            if (turn == players[i]){
                if(i+1 < players.length)
                    turn = players[i+1];
                else
                    turn = players[0];
            }
        }
    }

    /*
     * Useless, since player.getGoal does not return the positions which are really on the board
     */
    public boolean winner(Player player){
        //checks for the player if the goal positions are filled and if they are filled with the right color
        for(Position pos: player.getGoal()){
            if(pos.getPiece() != null){
                if (pos.getPiece().getColor() != player.getColor())
                    return false;
            }
            else
                return false;
        }
        return true;
    }

    public Player runGame(){
        while(winner == null){
            // it's a players turn, so wait until a turn is made
            // the board has to notify() to "wake this up" again
            if(turn instanceof HumanPlayer){
                try{
                    this.wait();
                }catch(InterruptedException e){ System.out.println("Some error"); }
            }
            else{
                //AI player
                turn.makeMove();
            }
           

            // now the player has made his move
            // check if this player wins, else change turn
            if(winner(turn))
                winner = turn;
            else
                changeTurn();
        }
        return winner;
    }
}