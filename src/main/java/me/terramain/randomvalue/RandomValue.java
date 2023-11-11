package me.terramain.randomvalue;

import java.util.ArrayList;
import java.util.List;

public class RandomValue {
    private List<Option> options = new ArrayList<>();

    public RandomValue(){}
    public RandomValue addOption(Object option,int chance){
        options.add(new Option(option,chance));
        return this;
    }
    public RandomValue addOptions(Object... options){
        for (Object option:options) {
            this.options.add(new Option(option,1));
        }
        return this;
    }
    public Object getResult(){
        List<Integer> idValueList = new ArrayList<>();
        for (int i = 0; i < options.size(); i++) {
            for (int j = 0; j < options.get(i).chance; j++) {
                idValueList.add(i);
            }
        }
        int random = (int) (Math.random()*idValueList.size());
        return options.get( idValueList.get(random) ).option;
    }
}