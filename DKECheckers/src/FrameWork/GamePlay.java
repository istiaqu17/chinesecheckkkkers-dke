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
    public Board board;
    public Player turn;
    public Player[] players;
    

    public GamePlay(Board b, Player[] p){
        //the gui has the info about the players (human or AI, what AI)
        //so the gui must give the array of players
        //first player will begin
        board = b;
        players = p;
        turn = players[0];
    }

    public void changeTurn(){
        for(int i = 0; i<players.length; i++){
            if (turn == players[i]){
                if(i+1 < players.length)
                    turn = players[i+1];
                else
                    turn = players[0];
            }
        }
    }

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
}
