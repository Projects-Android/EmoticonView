package com.ev.library.strategy.configs;

import java.io.IOException;
import java.io.InputStream;

/**
 * strategy to get config file
 * eg: config.xml
 * Created by EV on 2018/4/23.
 */
public interface IConfigFileStrategy {

    /**
     * get config.xml file
     * @return
     * @throws IOException
     */
    InputStream getConfig() throws IOException;
}
