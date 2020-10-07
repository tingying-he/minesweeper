package model;

/**
 * Created by Tingying He on 2020/10/7.
 */
public class CellModel {

    //ImgURL
    public static final String blockImgURL = "img/block.png";
    public static final String flagImgURL = "img/flag.png";
    public static final String mineImgURL = "img/mine.png";
    public static final String starImgURL = "img/star.png";
    public static final String clockImgURL = "img/clock.png";


    public int x,y;
    private boolean mine;
    private boolean star;
    private boolean clock;
    public int cellSide = 32;

    private int N,M,mineNumber;

    public CellModel(int x, int y){
        this.x = x;
        this.y = y;
        mine = false;
        putMines();
    }

    public boolean isMine(){
        return mine;
    }

    private void putMines(){
        if(Math.random()<0.2)
            mine = true;
    }






}
