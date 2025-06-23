package com.testtech.nutech.utils;


import java.util.logging.Logger;

public class LoggingFile {

    private static final StringBuilder sbuilds = new StringBuilder();
    private static final Logger logger = Logger.getLogger(String.valueOf(LoggingFile.class));
    public static void exceptionStringz(String[] datax,Exception e, String flag) {
        if(flag.equals("y"))
        {
            sbuilds.setLength(0);
            logger.info(sbuilds.append(System.lineSeparator()).
                    append("ERROR IN CLASS =>").append(datax[0]).append(System.lineSeparator()).
                    append("METHOD            =>").append(datax[1]).append(System.lineSeparator()).
                    append("ERROR IS          =>").append(e.getMessage()).
                    append(System.lineSeparator()).toString());
            sbuilds.setLength(0);
        }
    }

}
