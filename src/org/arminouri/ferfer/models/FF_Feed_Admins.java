package org.arminouri.ferfer.models;

/**
 * Created by arminouri on 3/11/15.
 */
import static org.arminouri.ferfer.models.Constants.*;
public class FF_Feed_Admins extends Table {
    public static final String table_name = "ff_feed_admins.tsv";
    public String get_table_name(){
        return this.table_name;
    }
    public static final String header = "feed_id\tadmin_id";
    public String feed_id;
    public String admin_id;
    public FF_Feed_Admins(String feed_id, String admin_id){
        this.feed_id = feed_id;
        this.admin_id = admin_id;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(feed_id).append(DELIMITER);
        sb.append(admin_id);
        return sb.toString();
    }
}
