package org.arminouri.ferfer.models;

/**
 * Created by arminouri on 3/11/15.
 */

import static org.arminouri.ferfer.models.Constants.*;
public class FF_Post_Thumbnails extends Table {
    public static final String table_name = "ff_post_thumbnails.tsv";
    public String get_table_name(){
        return this.table_name;
    }
    public static final String header = "post_id\turl\tlink\tplayer\twidth\theight\taddress";
    public String post_id;
    public String url;
    public String link;
    public String player;
    public int width;
    public int height;
    public String address;
    public FF_Post_Thumbnails(String post_id, String url, String link, String player, int width, int height){
        this.post_id = post_id;
        this.url = url;
        this.link = link;
        this.player = player;
        this.width = width;
        this.height = height;
        this.address = super.config.HOME + THUMBNAIL_REPO + post_id + THUMBNAIL_EXTENSION;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(post_id).append(DELIMITER);
        sb.append(url).append(DELIMITER);
        sb.append(link).append(DELIMITER);
        sb.append(player).append(DELIMITER);
        sb.append(width).append(DELIMITER);
        sb.append(height).append(DELIMITER);
        sb.append(address);
        return sb.toString();
    }
}
