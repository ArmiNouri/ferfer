package org.arminouri.ferfer.models;

/**
 * Created by arminouri on 3/15/15.
 */
import static org.arminouri.ferfer.models.Constants.*;
public class FF_Post_Comment_Hyperlinks extends Table {
    public static final String table_name = "ff_post_comment_hyperlinks.tsv";
    public String get_table_name(){
        return this.table_name;
    }
    public static final String header = "comment_id\thyperlink";
    public String comment_id;
    public String hyperlink;
    public FF_Post_Comment_Hyperlinks(String comment_id, String hyperlink){
        this.comment_id = comment_id;
        this.hyperlink = hyperlink;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(comment_id).append(DELIMITER);
        sb.append(hyperlink);
        return sb.toString();
    }
}
