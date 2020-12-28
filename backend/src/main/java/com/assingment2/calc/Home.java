package com.assingment2.calc;
import java.lang.*;
public class Home{
    private double first,second;
    public void setFirst(String f){
        first= Double.parseDouble(f);
    }
    public void setSecond(String s){

        second= Double.parseDouble(s);
    }
    public String getFirst(){
        return String.valueOf(first);
    }
    public String sum(){
        double v = first + second;
        return String.valueOf(v);
    }
    public String sub(){
        double v = first - second;
        return String.valueOf(v);
    }
    public String multi(){
        double v = first * second;
        return String.valueOf(v);
    }
    public String division(){
        if(second==0){
            return "E";
        }
        double v = first / second;
        return String.valueOf(v);
    }
    public String inverse(){
        if(first==0){
            return "E";
        }
        double v = 1 / first;
        return String.valueOf(v);
    }
    public String square(){
        double v = first * first;

        return String.valueOf(v);
    }
    public String sqr(){
        if(first<0){
            return "E";
        }
        return String.valueOf(Math.sqrt(first));
    }
    public String percent(){
        return String.valueOf((first*second)/100);
    }


}