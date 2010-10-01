///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package FrameWork;
//
//import java.awt.Color;
//import java.util.Random;
//
///**
// *
// * @author Stefan
// */
//public class RandomAIPlayer implements Player{
//    private String name;
//    private Color color;
//    private Position[] goalPositions;
//    private Board board;
//    private Position[] currentPositions;
//
//    public RandomAIPlayer(Board theBoard){
//        board = theBoard;
//    }
//
//    public RandomAIPlayer(Board theBoard, String aName, Color aColor, Position[] goal){
//        board = theBoard;
//        color = aColor;
//        name = aName;
//        goalPositions = goal;
//    }
//
//    public void makeMove(){
//        boolean moved = false;
//        Random r = new Random();
//        int random = -1;
//        Position p = null;
//
//        while(!moved){
//            // take a random piece of your own color
//            random = r.nextInt(10);
//            p = currentPositions[random];
//            board.determineValidMoves(p);
//            int numberValidMoves = board.getValidMoves().size();
//            // if the piece has valid moves
//            if(numberValidMoves > 0){
//                // take a random valid move
//                int random2 = r.nextInt(numberValidMoves);
//                // move the piece
//                Position[] beginAndEnd = new Position[2];
//                // begin position
//                beginAndEnd[0] = p;
//                // end position
//                beginAndEnd[1] = board.getValidMoves().get(random2);
//                board.movePiece(beginAndEnd);
//                moved = true;
//            }
//        }
//    }
//
//    public void setColor(Color aColor){
//        color = aColor;
//    }
//    public Color getColor(){
//        return color;
//    }
//
//    public void setName(String aName){
//        name = aName;
//    }
//    public String getName(){
//        return name;
//    }
//
//    public void setGoal(Position[] goal){
//        goalPositions = goal;
//    }
//    public Position[] getGoal(){
//        return goalPositions;
//    }
//
//}
