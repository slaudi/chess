package chess.gui;

import chess.cli.HelperClass;
import chess.engine.Engine;
import chess.game.*;
import chess.network.NetworkGame;
import chess.pieces.Pawn;
import chess.pieces.Piece;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * HelperClass for Game class for Methods only needed when Playing in GUI.
 */
public class GuiGame { //NOPMD - this is the main class for handling gui - all fields needed

    /**
     * The GuiGame class can access the actual Game class to make moves.
     */
    Game game;
    private Square squareStart = null;
    private Square squareFinal = null;

    String white = "-fx-background-color: rgb(180,80,0)";
    String black = "-fx-background-color: rgb(255,228,196)";

    boolean isRotatingBoard = true;
    boolean highlightPossibleMoves = true;
    boolean allowedToChangeSelectedPiece = false;
    boolean hintInCheck = true;

    boolean turnAI = false;

    Socket connectionSocket = null;

    /**
     * Constructor for GuiGame-Objects
     */
    public GuiGame(){
        this.game = new Game();
    }

    Square getSquareStart(){
        return this.squareStart;
    }

    void setSquareStartNull(){
        this.squareStart = null;
    }

    Square getSquareFinal(){
        return this.squareFinal;
    }


    void setBothSquares(Square square){
        if(this.squareStart == null){
            this.squareStart = square;
        } else {
            this.squareFinal = square;
        }
    }


    // computes a list a possible Squares to move to for a Piece to highlight in GUI
    List<Square> computePossibleSquares() {
        List<Square> possibleSquares = new ArrayList<>();
        for (int y = 0; y < game.chessBoard.getHeight(); y++) {
            for (int x = 0; x < game.chessBoard.getWidth(); x++) {
                if (game.isMoveAllowed(getSquareStart().getOccupiedBy(), game.chessBoard.getSquareAt(x,y))){
                    if(this.squareStart.getOccupiedBy().getType() != Type.KING){
                        possibleSquares.add(game.chessBoard.getSquareAt(x,y));
                    } else {
                        if(game.isSafeSquare(game.chessBoard.getSquareAt(x,y))){
                            possibleSquares.add(game.chessBoard.getSquareAt(x,y));
                        }
                    }
                }
            }
        }
        return possibleSquares;
    }


    void setButtonAction(ChessBoardView chessBoardView) {
        if (this.squareStart != null && this.squareFinal == null) {
            if (game.getLanguage() == Language.English) {
                chessBoardView.englishGame.changeSelected.setDisable(true);
            } else {
                chessBoardView.germanGame.changeSelected.setDisable(true);
            }
        }
        if (this.squareStart != null && this.squareFinal != null) {
            int result = processingMovement(chessBoardView);
            if (result == 0) {
                // Move is allowed
                makeMove(result, chessBoardView);
            } else if (result == 3){
                // you're allowed to change your selected Piece
                this.squareStart = this.squareFinal;
                this.squareFinal = null;
                chessBoardView.generatePane();
            } else {
                // not an allowed Move
                notAllowedMove(result,chessBoardView);
            }
        } else {
            chessBoardView.generatePane();
        }
    }


    private void makeMove(int result, ChessBoardView chessBoardView) {
        if(game.isNetworkGame()){
            NetworkGame.sendMove(HelperClass.generateMoveString(this.squareStart, this.squareFinal), connectionSocket);
        }
        this.squareStart = null;
        this.squareFinal = null;
        game.isInCheck();
        game.isCheckMate();
        turnAI = true;
        chessBoardView.generatePane();
        game.isInCheck();
        game.isCheckMate();
        if (game.getLanguage() == Language.German){
            chessBoardView.germanGame.generateAnswer(result);
            chessBoardView.germanGame.changeSelected.setDisable(false);
        } else {
            chessBoardView.englishGame.generateAnswer(result);
            chessBoardView.englishGame.changeSelected.setDisable(false);
        }
    }


    private void notAllowedMove(int result, ChessBoardView chessBoardView){
        // show why it's not allowed
        if (game.getLanguage() == Language.German) {
            chessBoardView.germanGame.generateAnswer(result);
        } else {
            chessBoardView.englishGame.generateAnswer(result);
        }
        if (allowedToChangeSelectedPiece || game.isInCheck()) {
            // allowed to change Piece after having selected it
            this.squareStart = null;
        }
        this.squareFinal = null;
        chessBoardView.generatePane();
    }


    void makeAIMove(ChessBoardView chessBoardView){
        if (!game.isCheckMate() && !game.isADraw()) {
            // generate move of AI
            int AI_result;
            do {
                Move AIMove = Engine.nextBestMove(game);
                // no piece can move
                if(AIMove == null){
                    game.setDrawn(true);
                    break;
                }
                this.squareStart = AIMove.getStartSquare();
                this.squareFinal = AIMove.getFinalSquare();
                AI_result = processingMovement(chessBoardView);
                this.squareStart = null;
                this.squareFinal = null;
            } while (AI_result != 0);
            turnAI = false;
            chessBoardView.generatePane();
        }
    }

    void doNetworkMove(){
        NetworkGame.makeNetworkMove(this.game, this.connectionSocket);
    }


    private int processingMovement(ChessBoardView chessBoardView) {
        if(!(game.currentPlayer.isLoser() || game.isADraw() || game.isCheckMate() )
                && this.squareStart != null && this.squareFinal != null) {
            return isMoveAllowed(chessBoardView);
        }
        if(game.isCheckMate()){
            // player is check mate
            return 5;
        }
        if(game.isADraw()){
            return 6;
        }
        if (game.currentPlayer.isLoser()){
            // player gave up
            return 7;
        }
        return 8;
    }


    private int isMoveAllowed(ChessBoardView chessBoardView) {
        Piece selectedPiece = this.squareStart.getOccupiedBy();
        if (!allowedToChangeSelectedPiece && this.squareFinal.getOccupiedBy() != null
                && selectedPiece.getColour() == this.squareFinal.getOccupiedBy().getColour() && this.squareFinal != this.squareStart){
            // you can't change selected Piece to another one
            return 1;
        }
        if (game.isMoveAllowed(selectedPiece, this.squareFinal)) {
            char key = checkPromotion(selectedPiece,chessBoardView);
            if (!game.processMove(this.squareStart, this.squareFinal, key)) {
                // wouldn't free King from check
                return 2;
            }
        } else if (this.squareFinal.getOccupiedBy() != null && selectedPiece.getColour() == this.squareFinal.getOccupiedBy().getColour()){
            // you're allowed to change selected Piece to another one
            return 3;
        } else {
            // move is not allowed
            return 4;
        }
        // move is allowed
        return 0;
    }


    private char checkPromotion(Piece selectedPiece, ChessBoardView chessBoardView){
        char key = 'Q';
        if(selectedPiece.getType() == Type.PAWN && ((Pawn)selectedPiece).promotionPossible(this.squareFinal)){
            if (game.getLanguage() == Language.German) {
                key = chessBoardView.germanGame.promotionSelection();
            } else {
                key = chessBoardView.englishGame.promotionSelection();
            }
        }
        return key;
    }
}
