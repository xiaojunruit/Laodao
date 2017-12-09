package com.laoodao.smartagri.event;

/**
 * Created by WORK on 2017/4/25.
 */

public class ChoiceOperationEvent {
    public int type;
    public String typeName;
    public ChoiceOperationEvent(int type,String typeName){
        this.type=type;
        this.typeName=typeName;
    }
}
