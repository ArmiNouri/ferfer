package org.arminouri.ferfer;

/**
 * Created by arminouri on 3/10/15.
 */

import org.arminouri.ferfer.models.*;
import org.arminouri.ferfer.io.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.apache.commons.io.FileUtils;
import java.net.MalformedURLException;
import java.net.*;
import java.io.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.arminouri.ferfer.models.Constants.*;

public class FerFer {
    private static Logger logger = LoggerFactory.getLogger(FerFer.class);
    private IO io;
    private Config config;
    private Stack<String> unvisited;
    private Set<String> visited;
    private String authStr;
    ObjectMapper mapper;
    public FerFer(){
        this.io = new IO();
        this.config = new Config();
        mapper = new ObjectMapper();
        unvisited = new Stack<String>();
        visited = new HashSet<String>();
        authStr = authenticate();
    }

    private String authenticate(){
        String authString = config.username + ":" + config.pwd;
        logger.debug("auth string: " + authString);
        byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
        String authStringEnc = new String(authEncBytes);
        logger.debug("Base64 encoded auth string: " + authStringEnc);
        return authStringEnc;
    }

    private String getResponse(HttpURLConnection connection){
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();
            return sb.toString();
        }catch(IOException e) {
            logger.warn("Failed to get proper response from " + connection.getURL().toString() + "\nwith error message: " + e);
            return null;
        }
    }

    private String getPictureUrl(String feedName){
        return FF_API_PICTURE + feedName;
    }

    private List<String> findHyperlinks(String post) {
        List<String> hyperlinks = new ArrayList<String>();
        String body = post;
        while(body.indexOf("href=\"") > -1){
            body = body.substring(body.indexOf("href=\""));
            int start = 6;
            int end = start + body.substring(start).indexOf('\"');
            String hyperlink = body.substring(start, end);
            if(!hyperlink.contains("friendfeed.com/search")) {
                hyperlinks.add(hyperlink);
            }
            body = body.substring(end);
        }
        return hyperlinks;
    }

    private void downloadPicture(String picture_url, String picture_address){
        try {
            URL pictureUrl = new URL(picture_url);
            HttpURLConnection connection = (HttpURLConnection)pictureUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Basic " + this.authStr);
            connection.connect();
            try {
                FileUtils.copyURLToFile(new URL(picture_url), new File(picture_address));
            } catch (MalformedURLException e) {
                logger.warn("Malformed URL: " + picture_url);
            }
        }catch(MalformedURLException e){
            logger.warn("Malformed URL: " + picture_url);
        }catch(IOException e){
            logger.warn("Failed to download picture: " + e);
        }
    }

    public void loadStack() {
        this.visited = io.loadVisited(config.HOME + VISITED);
        this.unvisited = io.loadUnvisited(config.HOME + UNVISITED);
    }
    public void persistStack() {
        io.writeStack(config.HOME + VISITED, visited, config.HOME + UNVISITED, unvisited);
        this.visited = io.loadVisited(config.HOME + VISITED);
        this.unvisited = io.loadUnvisited(config.HOME + UNVISITED);
    }

    public void crawlOnce(String feedName) {
        getFeedInfo(feedName);
        getFeed(feedName);
        visited.add(feedName);
        persistStack();
        if(io.checkFileSize(config.HOME + FF_Posts.table_name) > config.maxsize) {
            logger.warn("ff_posts table exceeded 1 GBs of data. Terminating app...");
            return;
        }
    }

    public void crawlOnce(){
        loadStack();
        String feedName = unvisited.pop();
        if(!visited.contains(feedName)) {
            crawlOnce(feedName);
        }
    }

    public void crawlAll() {
        //load stack params
        loadStack();
        unvisited.push(config.username);
        //iterate through unvisited pages
        while(unvisited.size() > 0) {
            String feedName = unvisited.pop();
            if(!visited.contains(feedName)) {
                crawlOnce(feedName);
            }
        }
    }

    public void getFeedInfo(String feedName) {
        logger.debug("Now downloading the feed for " + feedName + "... ");
        String feed_url = FF_API_FEEDINFO + feedName;
        try {
            URL feedInfoUrl = new URL(feed_url);
            HttpURLConnection connection = (HttpURLConnection) feedInfoUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Basic " + this.authStr);
            connection.connect();
            int status = connection.getResponseCode();
            if (status == 200 || status == 201) {
                String sb = getResponse(connection);
                FeedInfo feedInfo = mapper.readValue(sb, FeedInfo.class);
                FF_Feeds feed = new FF_Feeds(feedInfo.sup_id, feedInfo.description, feedInfo.type, feedInfo.id, feedInfo.name, feedInfo.priv, getPictureUrl(feedName), sb);
                downloadPicture(getPictureUrl(feedName), feed.picture_address);
                io.writeToFile(feed.table_path, feed.header, feed.toString());
                if (feedInfo.admins != null)
                    for (FeedInfo.User adm : feedInfo.admins) {
                        FF_Feed_Admins admin = new FF_Feed_Admins(feedInfo.id, adm.id);
                        io.writeToFile(admin.table_path, admin.header, admin.toString());
                        unvisited.push(admin.admin_id);
                    }
                if (feedInfo.subscribers != null)
                    for (FeedInfo.User sub : feedInfo.subscribers) {
                        FF_Feed_Subscribers subscriber = new FF_Feed_Subscribers(feedInfo.id, sub.id);
                        io.writeToFile(subscriber.table_path, subscriber.header, subscriber.toString());
                        unvisited.push(subscriber.subscriber_id);
                    }
                if (feedInfo.subscriptions != null)
                    for (FeedInfo.User sup : feedInfo.subscriptions) {
                        FF_Feed_Subscriptions subscription = new FF_Feed_Subscriptions(feedInfo.id, sup.id);
                        io.writeToFile(subscription.table_path, subscription.header, subscription.toString());
                        unvisited.push(subscription.subscription_id);
                    }
                if (feedInfo.services != null)
                    for (FeedInfo.Service serv : feedInfo.services) {
                        FF_Feed_Services service = new FF_Feed_Services(feedInfo.id, serv.id, serv.username, serv.profile);
                        io.writeToFile(service.table_path, service.header, service.toString());
                        FF_Services services = new FF_Services(serv.id, serv.name, serv.url, serv.icon);
                        io.writeToFile(services.table_path, services.header, services.toString());
                    }
            }
            else{
                return;
            }
        } catch (MalformedURLException e) {
            logger.warn("Malformed URL: " + feed_url);
        } catch (IOException e) {
            logger.error("Failed to write to output files: " + e);
        } catch(Exception e) {
            logger.warn("Received an error message " + e + ". The app will stop downloading " + feedName + "...");
            return;
        }
    }

    public void getFeed(String feedName) {
        int offset = 0;
        boolean cont = true;
        Feed oldfeed = null;
        while(cont) {
            String feed_url = FF_API_FEED + feedName + "?start=" + offset;
            try {
                URL feedurl = new URL(feed_url);
                HttpURLConnection connection = (HttpURLConnection)feedurl.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", "Basic " + this.authStr);
                connection.connect();
                int status = connection.getResponseCode();
                if(status == 200 || status == 201) {
                    String sb = getResponse(connection);
                    Feed feed = mapper.readValue(sb, Feed.class);
                    if(feed.entries == null || feed.entries.length == 0) {
                        cont = false;
                        return;
                    }
                    if(oldfeed != null && oldfeed.entries != null && oldfeed.entries.length > 0 && oldfeed.entries[0].id.equals(feed.entries[0].id)) {
                        cont = false;
                        return;
                    }
                    for(Feed.Entry  entry : feed.entries) {
                        FF_Posts post = new FF_Posts(entry.id, entry.date, entry.from.id, entry.url, entry.body, entry.via == null? "" : entry.via.url == null? "" : entry.via.url, entry.via == null ? "" : entry.via.name == null ? "" : entry.via.name, mapper.writeValueAsString(entry));
                        io.writeToFile(post.table_path, post.header, post.toString());
                        if(entry.to != null && entry.to.length > 0){
                            for(Feed.To to : entry.to){
                                FF_Post_To post_to = new FF_Post_To(entry.id, to.id, to.direct == null ? false : to.direct);
                                io.writeToFile(post_to.table_path, post_to.header, post_to.toString());
                            }
                        }
                        if(entry.thumbnails != null && entry.thumbnails.length > 0){
                            for(Feed.Thumbnail thumbnail : entry.thumbnails){
                                FF_Post_Thumbnails post_thumbnail = new FF_Post_Thumbnails(entry.id, thumbnail.url, thumbnail.link, thumbnail.player, thumbnail.width, thumbnail.height);
                                io.writeToFile(post_thumbnail.table_path, post_thumbnail.header, post_thumbnail.toString());
                                String thumbnail_url = thumbnail.url;
                                try {
                                    FileUtils.copyURLToFile(new URL(thumbnail_url), new File(post_thumbnail.address));
                                } catch (MalformedURLException e) {
                                    logger.warn("Malformed URL: " + thumbnail_url);
                                }
                            }
                        }
                        if(entry.likes != null && entry.likes.length > 0){
                            for(Feed.Like like : entry.likes){
                                FF_Post_Likes post_like = new FF_Post_Likes(entry.id, like.date, like.from.id);
                                io.writeToFile(post_like.table_path, post_like.header, post_like.toString());
                                unvisited.push(post_like.from_id);
                            }
                        }
                        if(entry.comments != null && entry.comments.length > 0){
                            for(Feed.Comment comment :  entry.comments){
                                FF_Post_Comments post_comment = new FF_Post_Comments(entry.id, comment.date, comment.id, comment.body, comment.from.id, comment.via == null? "" : comment.via.url == null? "" : comment.via.url, comment.via == null ? "" : comment.via.name == null ? "" : comment.via.name);
                                io.writeToFile(post_comment.table_path, post_comment.header, post_comment.toString());
                                for(String hyperlink : findHyperlinks(post_comment.body)){
                                    FF_Post_Comment_Hyperlinks post_comment_hyperlink = new FF_Post_Comment_Hyperlinks(comment.id, hyperlink);
                                    io.writeToFile(post_comment_hyperlink.table_path, post_comment_hyperlink.header, post_comment_hyperlink.toString());
                                }
                                unvisited.push(post_comment.from_id);
                            }
                        }
                        if(entry.files != null && entry.files.length > 0) {
                            for (Feed.FILE file : entry.files) {
                                FF_Post_Files post_file = new FF_Post_Files(entry.id, file.url, file.icon, file.type, file.name, file.size);
                                io.writeToFile(post_file.table_path, post_file.header, post_file.toString());
                                String file_url = file.url;
                                try {
                                    FileUtils.copyURLToFile(new URL(file_url), new File(post_file.address));
                                } catch (MalformedURLException e) {
                                    logger.warn("Malformed URL: " + file_url);
                                }
                            }
                        }
                        for(String hyperlink : findHyperlinks(entry.body)) {
                            FF_Post_Hyperlinks post_hyperlink = new FF_Post_Hyperlinks(entry.id, hyperlink);
                            io.writeToFile(post_hyperlink.table_path, post_hyperlink.header, post_hyperlink.toString());
                        }
                    }
                    oldfeed = feed;
                }
                else {
                    cont = false;
                    return;
                }
            }catch(MalformedURLException e){
                logger.warn("Malformed URL: " + feed_url);
            }catch(IOException e){
                logger.error("Failed to write to output files: " + e);
            }catch(Exception e) {
                logger.warn("Received an error message " + e + ". The app will stop downloading " + feedName + "...");
                return;
            }
            offset += 30;
        }
    }

    public static void main(String[] args) {
        String choice1 = "";
        String choice2 = "";
        FerFer ff = new FerFer();
        while (!choice1.equals("1") && !choice1.equals("2")) {
            System.out.println("What mode would you like to run the program in?");
            System.out.println("1 - Full mode (begins crawling from your page and runs a depth-first search traversal that collects the feeds of your followers, your followers' followers, and so on).");
            System.out.println("2 - Single-user mode (only downloads a single user's feed).");
            BufferedReader choice1Input = new BufferedReader(new InputStreamReader(System.in));
            try {
                choice1 = choice1Input.readLine();
                if(choice1.equals("1"))
                    ff.crawlAll();
                else if (choice1.equals("2")) {
                    System.out.println("Enter the username of the person whose feed you'd like to download");
                    BufferedReader choice2Input = new BufferedReader(new InputStreamReader(System.in));
                    try {
                        choice2 = choice2Input.readLine();
                        ff.loadStack();
                        ff.crawlOnce(choice2);
                    } catch(IOException ioe) {
                        System.out.println("IO error trying to read your choice!");
                        System.exit(1);
                    }
                }
                else {
                    System.out.println("Please enter 1 to choose the first option, and 2 to choose the second option.");
                }
            } catch (IOException ioe) {
                System.out.println("IO error trying to read your choice!");
                System.exit(1);
            }
        }
    }
}
