package com.example.user.jsetestapp;

/*
 * DataObject class holds data for displaying in RecyclerView
 * */
public class DataObject {
    private String mText1;
    private String mText2;
    private String mText3;
    private String mText4;
    private String mText5;
    private String mText6;

    DataObject(String text1, String text2, String text3, String text4, String text5, String text6) {
        mText1 = text1;
        mText2 = text2;
        mText3 = text3;
        mText4 = text4;
        mText5 = text5;
        mText6 = text6;
    }

    public String getmText1() {

        return mText1;
    }

    public String getmText2() {

        return mText2;
    }

    public String getmText3() {

        return mText3;
    }

   public String getmText4() {

        return mText4;
    }

    public String getmText5() {

        return mText5;
    }

    public String getmText6() {

        return mText6;
    }

}
