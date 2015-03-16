package org.arminouri.ferfer.models;

/**
 * Created by arminouri on 3/11/15.
 */
import static org.arminouri.ferfer.models.Constants.*;
public class FF_Feed_Services extends Table {
    public static final String table_name = "ff_feed_services.tsv";
    public String get_table_name(){
        return this.table_name;
    }
    public static final String header = "feed_id\tservice_id\tusername\tprofile";
    public String feed_id;
    public String service_id;
    public String username;
    public String profile;
    public FF_Feed_Services(String feed_id, String service_id, String username, String profile){
        this.feed_id = feed_id;
        this.service_id = service_id;
        this.username = username;
        this.profile = profile;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(feed_id).append(DELIMITER);
        sb.append(service_id).append(DELIMITER);
        sb.append(username).append(DELIMITER);
        sb.append(profile);
        return sb.toString();
    }
}
