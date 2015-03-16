package org.arminouri.ferfer.models;

/**
 * Created by arminouri on 3/11/15.
 */
import static org.arminouri.ferfer.models.Constants.*;
public class FF_Post_Likes extends Table {
    public static final String table_name = "ff_post_likes.tsv";
    public String get_table_name(){
        return this.table_name;
    }
    public static final String header = "post_id\tdate\tfrom_id";
    public String post_id;
    public String date;
    public String from_id;
    public FF_Post_Likes(String post_id, String date, String from_id){
        this.post_id = post_id;
        this.date = date;
        this.from_id = from_id;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(post_id).append(DELIMITER);
        sb.append(date).append(DELIMITER);
        sb.append(from_id);
        return sb.toString();
    }
}
