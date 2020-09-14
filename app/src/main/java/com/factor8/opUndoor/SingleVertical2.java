package com.factor8.opUndoor;

public class SingleVertical2 {
    private String boardName, boardDP, boardCover;


    public SingleVertical2(String companyName, String boardDP, String boardCover) {
        this.boardName = companyName;
        this.boardDP = boardDP;
        this.boardCover = boardCover;
    }

    public String getCompanyName() {
        return boardName;
    }

    public void setCompanyName(String companyName) {
        this.boardName = companyName;
    }

    public String getBoardDP() {
        return boardDP;
    }

    public void setBoardDP(String boardDP) {
        this.boardDP = boardDP;
    }

    public String getBoardCover() {
        return boardCover;
    }

    public void setBoardCover(String boardCover) {
        this.boardCover = boardCover;
    }
}
