package org.arminouri.ferfer.models;

/**
 * Created by arminouri on 3/11/15.
 */
import static org.arminouri.ferfer.models.Constants.*;
public class FF_Feed_Subscriptions extends Table {
    public static final String table_name = "ff_feed_subscriptions.tsv";
    public String get_table_name(){
        return this.table_name;
    }
    public static final String header = "feed_id\tsubscription_id";
    public String feed_id;
    public String subscription_id;
    public FF_Feed_Subscriptions(String feed_id, String subscription_id){
        this.feed_id = feed_id;
        this.subscription_id = subscription_id;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(feed_id).append(DELIMITER);
        sb.append(subscription_id);
        return sb.toString();
    }
}