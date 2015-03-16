package org.arminouri.ferfer.models;

/**
 * Created by arminouri on 3/11/15.
 */
import static org.arminouri.ferfer.models.Constants.*;
public class FF_Services extends Table {
    public static final String table_name = "ff_services.tsv";
    public String get_table_name(){
        return this.table_name;
    }
    public static final String header = "service_id\tname\turl\ticon";
    public String service_id;
    public String name;
    public String url;
    public String icon;
    public FF_Services(String service_id, String name, String url, String icon){
        this.service_id = service_id;
        this.name = name;
        this.url = url;
        this.icon = icon;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(service_id).append(DELIMITER);
        sb.append(name).append(DELIMITER);
        sb.append(url).append(DELIMITER);
        sb.append(icon);
        return sb.toString();
    }
}
