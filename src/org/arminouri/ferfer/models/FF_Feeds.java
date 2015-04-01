package org.arminouri.ferfer.models;

/**
 * Created by arminouri on 3/11/15.
 */

import static org.arminouri.ferfer.models.Constants.*;

public class FF_Feeds extends Table {
    public static final String table_name = "ff_feeds.tsv";
    public String get_table_name(){
        return this.table_name;
    }
    public static final String header = "feed_id\tsup_id\tdescription\ttype\tname\tprivate\tpicture_url\tpicture_address\tfeed_json";
    public String feed_id;
    public String sup_id;
    public String description;
    public String type;
    public String name;
    public boolean priv;
    public String picture_url;
    public String picture_address;
    public String feed_json;

    public FF_Feeds(String sup_id, String description, String type, String feed_id, String name, boolean priv, String picture_url, String feed_json) {
        this.feed_id = feed_id;
        this.sup_id = sup_id;
        this.description = description == null ? "" : description.replaceAll(RESERVED, MASK);
        this.type = type;
        this.name = name.replaceAll(RESERVED, MASK);
        this.priv = priv;
        this.picture_url = picture_url;
        this.picture_address = super.config.HOME + PICTURE_REPO + feed_id + PICTURE_EXTENSION;
        this.feed_json = feed_json.replaceAll(RESERVED, MASK);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(feed_id).append(DELIMITER);
        sb.append(sup_id).append(DELIMITER);
        sb.append(description).append(DELIMITER);
        sb.append(type).append(DELIMITER);
        sb.append(name).append(DELIMITER);
        sb.append(priv).append(DELIMITER);
        sb.append(picture_url).append(DELIMITER);
        sb.append(picture_address).append(DELIMITER);
        sb.append(feed_json);
        return sb.toString();
    }
}
