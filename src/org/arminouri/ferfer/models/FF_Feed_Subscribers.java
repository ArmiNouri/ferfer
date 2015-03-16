package org.arminouri.ferfer.models;

/**
 * Created by arminouri on 3/11/15.
 */
import static org.arminouri.ferfer.models.Constants.*;
public class FF_Feed_Subscribers extends Table {
    public static final String table_name = "ff_feed_subscribers.tsv";
    public String get_table_name(){
        return this.table_name;
    }
    public static final String header = "feed_id\tsubscriber_id";
    public String feed_id;
    public String subscriber_id;
    public FF_Feed_Subscribers(String feed_id, String subscriber_id){
        this.feed_id = feed_id;
        this.subscriber_id = subscriber_id;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(feed_id).append(DELIMITER);
        sb.append(subscriber_id);
        return sb.toString();
    }
}
