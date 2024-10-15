/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author phuct
 */
public class Post {
    private int postID;
    private int userID;
    private String imgURL;
    private String heading;
    private String content;
    private Date createdDate;
    private String userFullName;
    private String avtUserImg;
    private boolean status;

    public Post() {
    }

    public Post(int postID, int userID, String imgURL, String heading, String content, Date createdDate) {
        this.postID = postID;
        this.userID = userID;
        this.imgURL = imgURL;
        this.heading = heading;
        this.content = content;
        this.createdDate = createdDate;
        this.status = true; 
    }

    public Post(int postID, int userID, String imgURL, String heading, String content, Date createdDate, boolean status) {
        this.postID = postID;
        this.userID = userID;
        this.imgURL = imgURL;
        this.heading = heading;
        this.content = content;
        this.createdDate = createdDate;
        this.status = status; 
    }

    public Post(int postID, int userID, String imgURL, String heading, String content, Date createdDate, String userFullName, boolean status) {
        this.postID = postID;
        this.userID = userID;
        this.imgURL = imgURL;
        this.heading = heading;
        this.content = content;
        this.createdDate = createdDate;
        this.userFullName = userFullName;
        this.status = status;
    }

    // Getters and Setters
    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getAvtUserImg() {
        return avtUserImg;
    }

    public void setAvtUserImg(String avtUserImg) {
        this.avtUserImg = avtUserImg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
