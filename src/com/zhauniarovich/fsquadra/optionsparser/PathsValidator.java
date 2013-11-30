package com.zhauniarovich.fsquadra.optionsparser;

import java.io.File;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class PathsValidator implements IParameterValidator {
    @Override
    public void validate(String name, String value) throws ParameterException {
        if (!(new File(name)).exists()) { 
            throw new ParameterException("The path [" + name + "] does not exist!");
        }
    }
}
