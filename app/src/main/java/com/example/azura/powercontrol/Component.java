package com.example.azura.powercontrol;


public class Component {

    private int mType;
    private String mLocation;
    private boolean mState;

    public Component(int type, String location, boolean state) {
        mType = type;
        mLocation = location;
        mState = state;
    }

    public int getType() { return mType; }

    public String getLocation() { return mLocation; }

    public boolean getState() { return mState; }

}
