package org.arminouri.ferfer.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by arminouri on 3/15/15.
 */
public class Config {
    private static Logger logger = LoggerFactory.getLogger(Config.class);
    public String username;
    public String pwd;
    public String HOME;
    public float maxsize;
    public Config() {
        try {
            this.getPropValues();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    public void getPropValues() throws IOException {
        Properties prop = new Properties();
        String propFileName = "config.properties";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }

        // load property values
        this.username = prop.getProperty("username");
        this.pwd = prop.getProperty("remotekey");
        this.HOME = prop.getProperty("HOME");
        this.maxsize = Float.parseFloat(prop.getProperty("maxsize"));
    }
}
