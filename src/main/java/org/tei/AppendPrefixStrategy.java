package org.tei;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class AppendPrefixStrategy extends PropertyNamingStrategies.NamingBase {
    @Override
    public String translate(String input){
        return '_' + input;
    }
}
