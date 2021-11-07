package org.tei;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;

public class AppendPrefixStrategy extends PropertyNamingStrategies.NamingBase {
    @Override
    public String translate(String input){
        return '_' + input;
    }
}
