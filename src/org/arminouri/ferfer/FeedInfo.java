package org.arminouri.ferfer;

/**
 * Created by arminouri on 3/11/15.
 */
import org.codehaus.jackson.annotate.JsonProperty;

public class FeedInfo {
    public static class User {
        public String type;
        public String id;
        @JsonProperty(value = "private")
        public boolean priv;
        public String name;
        public String[] commands;
    }
    public static class Service {
        public String id;
        public String icon;
        public String name;
        public String url;
        public String profile;
        public String username;
    }
    public String sup_id;
    public String description;
    public String type;
    public String id;
    public String name;
    public User[] admins;
    public User[] subscribers;
    public User[] subscriptions;
    public Service[] services;
    public String[] commands;
    @JsonProperty(value = "private")
    public boolean priv;
}
