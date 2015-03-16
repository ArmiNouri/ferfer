package org.arminouri.ferfer.models;

/**
 * Created by arminouri on 3/11/15.
 */
import static org.arminouri.ferfer.models.Constants.*;
public class FF_Post_Hyperlinks extends Table {
    public static final String table_name = "ff_post_hyperlinks.tsv";
    public String get_table_name(){
        return this.table_name;
    }
    public static final String header = "post_id\thyperlink";
    public String post_id;
    public String hyperlink;
    public FF_Post_Hyperlinks(String post_id, String hyperlink){
        this.post_id = post_id;
        this.hyperlink = hyperlink;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(post_id).append(DELIMITER);
        sb.append(hyperlink);
        return sb.toString();
    }
}
