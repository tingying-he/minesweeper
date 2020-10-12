package model;

import controller.CellController;

/**
 * Created by Tingying He on 2020/10/7.
 */
public class CellModel {
    private CellController cellController;

    //ImgURL
    public static final String coverImgURL = "img/cover.png";
    public static final String flagImgURL = "img/flag.png";
    public static final String mineImgURL = "img/mine.png";
    public static final String starImgURL = "img/star.png";
    public static final String clockImgURL = "img/clock.png";
    public static String numberImgURL(int num){
        return "img/" + num + ".png";
    }

    //Data
    public int neighborMinesNum;
    private boolean mine;
    private boolean star;
    private boolean clock;

    private boolean open;
    private boolean flag;
    public boolean hasPutStar = false;

    public int cellSide = 32;
    private double mineRate = 0.12;



    public CellModel(CellController cellController){
        this.cellController = cellController;

        mine = false;
        star = false;
        clock = false;

        open = false;
        flag = false;

        putMine();
//        putStar();
//        putClock();
    }

    public int getNeighborMinesNum() {
        return neighborMinesNum;
    }

    public boolean isMine(){
        return mine;
    }
    public boolean isStar() {
        return star;
    }
    public boolean isClock() {
        return clock;
    }
    public boolean isOpen(){return open;}
    public boolean isFlag(){return flag;}

    private void putMine(){
        if(Math.random()<mineRate)
            mine = true;
    }

//    private void putStar(){
//        if(Math.random()<0.03 && getNeighborMinesNum()==0)
//            star = true;
//    }
//
//    private void putClock(){
//        if(Math.random()<0.03 && getNeighborMinesNum()==0)
//            clock = true;
//    }

    public void setOpen(boolean open){
        this.open =open;
    }
    public void setFlag(){
        flag = !flag;
    }
    public void setStar(boolean star){this.star = star;}
    public void setClock(boolean clock){this.clock = clock;}

    public void removeClock(){
        clock = false;
    }
    public void removeStar(){
        star = false;
    }
}
