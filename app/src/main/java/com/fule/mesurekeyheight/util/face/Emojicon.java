package com.fule.mesurekeyheight.util.face;

/**
 * Created by Administrator on 2017/7/27/027.
 */

public class Emojicon {

    public Emojicon(int icon, String emoji) {
        this.icon = icon;
        this.emoji = emoji;
    }

    private int icon;

    public String getEmoji() {
        return emoji;
    }


    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    private String emoji;

}
