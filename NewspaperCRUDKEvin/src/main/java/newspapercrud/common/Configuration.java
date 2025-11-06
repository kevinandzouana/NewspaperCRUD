/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newspapercrud.common;

import java.io.IOException;
import java.util.Properties;


public class Configuration {

    private static Configuration instance=null;
    private Properties p;

    private Configuration() {
        try {
            p = new Properties();
            p.loadFromXML(Configuration.class.getClassLoader().getResourceAsStream("config/properties.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Configuration getInstance() {

        if (instance==null) {
            instance=new Configuration();
        }
        return instance;
    }

    public String getProperty(String key) {
        return p.getProperty(key);
    }

}
