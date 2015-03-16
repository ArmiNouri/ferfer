package org.arminouri.ferfer.models;

/**
 * Created by arminouri on 3/11/15.
 */
import static org.arminouri.ferfer.models.Constants.*;
public class FF_Post_To extends Table {
    public static final String table_name = "ff_post_to.tsv";
    public String get_table_name(){
        return this.table_name;
    }
    public static final String header = "post_id\tto_id\tto_direct";
    public String post_id;
    public String  to_id;
    public boolean to_direct;
    public FF_Post_To(String post_id, String to_id, boolean to_direct){
        this.post_id = post_id;
        this.to_id = to_id;
        this.to_direct = to_direct;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(post_id).append(DELIMITER);
        sb.append(to_id).append(DELIMITER);
        sb.append(to_direct);
        return sb.toString();
    }
}
