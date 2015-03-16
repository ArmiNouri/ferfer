package org.arminouri.ferfer;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by arminouri on 3/10/15.
 */
public class Feed {
    public static class FILE {
        public String url;
        public String icon;
        public String type;
        public String name;
        public long size;
    }
    public static class From {
        public String type;
        public String id;
        public String name;
        @JsonProperty(value = "private")
        public Boolean priv;
        public String[] commands;
    }
    public static class Thumbnail {
        public String url;
        public String link;
        public String player;
        public int width;
        public int height;
    }
    public static class Via {
        public String url;
        public String name;
    }
    public static class Comment {
        public String date;
        public String body;
        public From from;
        public Via via;
        public String id;
        public String[] commands;
    }
    public static class To {
        public String type;
        public String id;
        public String name;
        public Boolean direct;
        @JsonProperty(value = "private")
        public Boolean priv;
        public String[] commands;
    }
    public static class Like {
        public String date;
        public From from;
        public Via via;
    }
    public static class Geo{
        public long lat;
        @JsonProperty(value = "long")
        public long lng;
    }
    public static class Entry {
        public String body;
        public FILE[] files;
        public From from;
        public Via via;
        public Thumbnail[] thumbnails;
        public String url;
        public Comment[] comments;
        public To[] to;
        public Like[] likes;
        public String date;
        public String id;
        public Geo geo;
        public String[] commands;
    }

    public String sup_id;
    public String description;
    public String type;
    public String id;
    public String name;
    public String[] commands;
    public Entry[] entries;
    @JsonProperty(value = "private")
    public Boolean priv;
}
