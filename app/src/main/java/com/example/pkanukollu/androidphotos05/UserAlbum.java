package com.example.pkanukollu.androidphotos05;

/**
 * Created by pkanukollu on 4/25/2017.
 */

import android.net.Uri;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class is used as a large data structure to store all contents of users, along with their albums and photos.
 * @author Pranav Kanukollu, pvk9
 * @author Nishanth Athreya, nsa48
 */
public class UserAlbum implements Serializable{
    private static final long serialVersionUID = 1L;
    //private HashMap<String, ArrayList<String>> albums;
    //private HashMap<String, HashMap<String, ArrayList<Picture>>> albums;
    private HashMap<String, ArrayList<Picture>> albums;
    //private HashMap<String, String> pics;
    //private HashMap<String, HashMap<String, ArrayList<Picture>>> albums;
    /**
     * Constructor.
     */
    public UserAlbum(){
        //albums = new HashMap<String, HashMap<String, ArrayList<String>>>();
        //albums = new HashMap<String, HashMap<String, ArrayList<Picture>>>();
        albums = new HashMap<String, ArrayList<Picture>>();
        //albums.keySet().ad
        //albums.put("stock", value)
    }
    /**
     * This method is used to add user to the hashmap.
     * @param user String variable
     */
    /*public void addUser(String user){
        albums.put(user, null);//adding user
    }*/
    /**
     * This method is used to add an album to the data structure given a user and the album name.
     * @param album_name String variable
     */
    public void addAlbum(String album_name){
        if(album_name.length() > 0){
            albums.put(album_name, new ArrayList<Picture>());
        }
        /*if(albums.get(user) == null){//no albums exist for users
                //HashMap<String, ArrayList<String>> album_list = new HashMap<String, ArrayList<String>>();
                HashMap<String, ArrayList<Picture>> album_list = new HashMap<String, ArrayList<Picture>>();
                //album_list.put(album_name, new ArrayList<String>());
                album_list.put(album_name, new ArrayList<Picture>());
                albums.put(user, album_list);
            }else{//at least one album already present for user
                //albums.get(user).put(album_name, new ArrayList<String>());
                albums.get(user).put(album_name, new ArrayList<Picture>());*/
    }
    public void renameAlbum(String oldAlbum, String newAlbum){
        ArrayList<Picture> pics = albums.get(oldAlbum);
        albums.put(newAlbum, pics);
        albums.remove(oldAlbum);
    }
    /**
     * This method is used to add a photo to the data structure.
     * @param album String variable
     * @param pic String variable
     */
    public void addPic(String album, String pic, String name){
        long lastModif = new File(pic).lastModified();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String modif = sdf.format(lastModif);
        //Picture p = new Picture(pic, "");

        Picture picture = new Picture(pic, "");
        picture.setDateAndTime(modif);
        picture.calendar(modif);
        picture.setName(name);
        if(albums.containsKey(album)){
            albums.get(album).add(picture);
        }
        /*if(albums.get(user) != null){//user exists
            if(albums.get(user).containsKey(album)){//album exists
                if(albums.get(user).get(album) == null){//no pics in album
                    ArrayList<Picture> pics = new ArrayList<Picture>();
                    pics.add(picture);
                }else{//at least one pic in
                    albums.get(user).get(album).add(picture);
                }
            }
        }*/
    }
    /**
     * This method is used to delete a user from the data structure.
     * @param user String variable
     */
    /*public void deleteUser(String user){
        albums.remove(user);
    }*/
    /**
     * This method is used to delete an album from the data structure.
     * @param album String variable
     */
    public void deleteAlbum(String album){
        if(albums.containsKey(album)){
            albums.remove(album);
        }
        /*if(albums.containsKey(user)){
            if(albums.get(user).containsKey(album)){
                albums.get(user).remove(album);
            }
        }*/
    }
    /**
     * This method is used to delete a photo from the data structure.
     * @param album String variable
     * @param pic String variable
     */
    public void deletePic(String album, String pic){
        if(albums.containsKey(album)){
            albums.get(album).remove(new Picture(pic, ""));
        }
        /*if(albums.containsKey(user)){
            if(albums.get(user).containsKey(album) && albums.get(user).get(album) != null){
                albums.get(user).get(album).remove(new Picture(pic, ""));
            }
        }*/
    }
    /**
     * This method is used to return all the albums of a given user.
     * @return Iterator<String>
     */
    public String[] getAlbums(){
        //ArrayList<String> allAlbums = new ArrayList<String>();
        //allAlbums.addAll(albums.keySet());//adds set of keys (album names) to ArrayList of album names
        return albums.keySet().toArray(new String[albums.size()]);
    }
    public HashMap<String, ArrayList<Picture>> getAlbumMap(){
        //ArrayList<String> allAlbums = new ArrayList<String>();
        //allAlbums.addAll(albums.keySet());//adds set of keys (album names) to ArrayList of album names
        return albums;
    }
    /*public Iterator<String> getAlbums(){
        //if(albums.containsKey(user)){
          //  if(albums.get(user) != null){
            //    return albums.get(user).keySet().iterator();
            //}
        //
        return albums.keySet().iterator();
    }*/
    /**
     * This method is used to get all the photos of a given user in an album.
     * @param album String variable
     * @return ArrayList<Picture>
     */
    public ArrayList<Picture> getPics(String album){
        if(albums.containsKey(album)){
            return albums.get(album);
        }
        /*if(albums.containsKey(user)){
            if(albums.get(user) != null){
                return albums.get(user).get(album);
            }
        }*/
        return null;
    }
    public ArrayList<String> getPaths(String album){
        ArrayList<String> paths = new ArrayList<String>();
        if(albums.containsKey(album)){
            for(int i = 0;i < albums.get(album).size();i++){
                Picture p = albums.get(album).get(i);
                paths.add(p.getPath());
            }
        }
        return paths;
    }
    /*public ArrayList<Uri> getUris(String album){
        ArrayList<Uri> uris = new ArrayList<Uri>();
        if(albums.containsKey(album)){
            for(int i = 0;i < albums.get(album).size();i++){
                Picture p = albums.get(album).get(i);
                uris.add(p.getPath());
            }
        }
        return uris;
    }*/
}
