package com.zhauniarovich.fsquadra.optionsparser;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class CommandLineOptions {
    
    @Parameter(description = "The paths to files/folders to compare files in")
    public List<String> paths = new ArrayList<String>();

    @Parameter(names = { "-o", "--outputFile" }, description = "File to store the results of comparison", required=true)
    public String outputFile;
}
