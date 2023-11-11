package me.terramain.randomvalue;

import java.util.ArrayList;
import java.util.List;

public class RandomCode {
    private List<Option> options = new ArrayList<>();

    public RandomCode(){}
    public RandomCode addOption(Code code, int chance){
        options.add(new Option(code,chance));
        return this;
    }
    public void run(){
        List<Integer> idValueList = new ArrayList<>();
        for (int i = 0; i < options.size(); i++) {
            for (int j = 0; j < options.get(i).chance; j++) {
                idValueList.add(i);
            }
        }
        int random = (int) (Math.random()*idValueList.size());
        ((Code) options.get( idValueList.get(random) ).option).code();
    }

    public interface Code{
        public void code();
    }
}
