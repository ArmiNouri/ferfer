package org.arminouri.ferfer.models;

/**
 * Created by arminouri on 3/11/15.
 */
import static org.arminouri.ferfer.models.Constants.*;
public class FF_Posts extends Table {
    public static final String table_name = "ff_posts.tsv";
    public String get_table_name(){
        return this.table_name;
    }
    public static final String header = "post_id\tdate\tfrom_id\turl\tbody\tvi_url\tvia_name\tpost_json";
    public String post_id;
    public String date;
    public String from_id;
    public String url;
    public String body;
    public String via_url;
    public String via_name;
    public String post_json;
    public FF_Posts(String post_id, String date, String from_id, String url, String body, String via_url, String via_name, String post_json){
        this.post_id = post_id;
        this.date = date;
        this.from_id = from_id;
        this.url = url;
        this.body = body.replaceAll(RESERVED, MASK);
        this.via_url = via_url;
        this.via_name = via_name;
        this.post_json = post_json.replaceAll(RESERVED, MASK);
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(post_id).append(DELIMITER);
        sb.append(date).append(DELIMITER);
        sb.append(from_id).append(DELIMITER);
        sb.append(url).append(DELIMITER);
        sb.append(body).append(DELIMITER);
        sb.append(via_url).append(DELIMITER);
        sb.append(via_name).append(DELIMITER);
        sb.append(post_json);
        return sb.toString();
    }
}
