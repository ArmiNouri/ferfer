package org.arminouri.ferfer.models;

/**
 * Created by arminouri on 3/11/15.
 */

import static org.arminouri.ferfer.models.Constants.*;
public class FF_Post_Files extends Table {
    public static final String table_name = "ff_post_files.tsv";
    public String get_table_name(){
        return this.table_name;
    }
    public static final String header = "post_id\turl\ticon\ttype\tname\tsize\taddress";
    public String post_id;
    public String url;
    public String icon;
    public String type;
    public String name;
    public long size;
    public String address;
    public FF_Post_Files(String post_id, String url, String icon, String type, String name, long size){
        this.post_id = post_id;
        this.url = url;
        this.icon = icon;
        this.type = type;
        this.name = name;
        this.size = size;
        this.address = super.config.HOME + FILE_REPO + name;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(post_id).append(DELIMITER);
        sb.append(url).append(DELIMITER);
        sb.append(icon).append(DELIMITER);
        sb.append(type).append(DELIMITER);
        sb.append(name).append(DELIMITER);
        sb.append(size).append(DELIMITER);
        sb.append(address);
        return sb.toString();
    }
}
