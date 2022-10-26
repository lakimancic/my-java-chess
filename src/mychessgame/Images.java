package mychessgame;

import java.awt.*;

public class Images {
    public Image background;
    public Image bPawn, bRook, bKnight, bBishop, bKing, bQueen;
    public Image wPawn, wRook, wKnight, wBishop, wKing, wQueen;

    public Images() {
        background = Toolkit.getDefaultToolkit().getImage("assets/board.jpg");
        bPawn = Toolkit.getDefaultToolkit().getImage("assets/black_pawn.png");
        bRook = Toolkit.getDefaultToolkit().getImage("assets/black_rook.png");
        bKnight = Toolkit.getDefaultToolkit().getImage("assets/black_knight.png");
        bBishop = Toolkit.getDefaultToolkit().getImage("assets/black_bishop.png");
        bKing = Toolkit.getDefaultToolkit().getImage("assets/black_king.png");
        bQueen = Toolkit.getDefaultToolkit().getImage("assets/black_queen.png");
        wPawn = Toolkit.getDefaultToolkit().getImage("assets/white_pawn.png");
        wRook = Toolkit.getDefaultToolkit().getImage("assets/white_rook.png");
        wKnight = Toolkit.getDefaultToolkit().getImage("assets/white_knight.png");
        wBishop = Toolkit.getDefaultToolkit().getImage("assets/white_bishop.png");
        wKing = Toolkit.getDefaultToolkit().getImage("assets/white_king.png");
        wQueen = Toolkit.getDefaultToolkit().getImage("assets/white_queen.png");
    }
}
