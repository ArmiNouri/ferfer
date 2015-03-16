package org.arminouri.ferfer.models;

/**
 * Created by arminouri on 3/11/15.
 */

import static org.arminouri.ferfer.models.Constants.*;
public class FF_Post_Comments extends Table {
    public static final String table_name = "ff_post_comments.tsv";
    public String get_table_name(){
        return this.table_name;
    }
    public static final String header = "post_id\tdate\tcomment_id\tbody\tfrom_id\tvia_url\tvia_name";
    public String post_id;
    public String date;
    public String comment_id;
    public String body;
    public String from_id;
    public String via_url;
    public String via_name;
    public FF_Post_Comments(String post_id, String date, String comment_id, String body, String from_id, String via_url, String via_name){
        this.post_id = post_id;
        this.date = date;
        this.comment_id = comment_id;
        this.body = body.replaceAll(RESERVED, MASK);
        this.from_id = from_id;
        this.via_url = via_url;
        this.via_name = via_name;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(post_id).append(DELIMITER);
        sb.append(date).append(DELIMITER);
        sb.append(comment_id).append(DELIMITER);
        sb.append(body).append(DELIMITER);
        sb.append(from_id).append(DELIMITER);
        sb.append(via_url).append(DELIMITER);
        sb.append(via_name);
        return sb.toString();
    }
}
