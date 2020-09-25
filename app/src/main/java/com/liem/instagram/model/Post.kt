package com.liem.instagram.model

class Post {
    private var postId : String = ""
    private var postImage : String = ""
    private var publisher : String = ""
    private var description : String = ""


    constructor()
    constructor(postId: String, postImage: String, publisher: String, description: String) {
        this.postId = postId
        this.postImage = postImage
        this.publisher = publisher
        this.description = description
    }
    fun getPostId(): String {
        return postId
    }
    fun getPostImage(): String {
        return postImage
    }
    fun getPublisher(): String {
        return publisher
    }
    fun getDesc(): String {
        return description
    }
    fun setPostId(postId: String) {
        this.postId = postId
    }
    fun setPostImage(postImage: String) {
        this.postImage = postImage
    }
    fun setPublisher(publisher: String) {
        this.publisher = publisher
    }
    fun setDesc(description: String) {
        this.description = description
    }

}