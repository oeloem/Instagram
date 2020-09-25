package com.liem.instagram.model

class User {
    private var username : String = ""
    private var fullname : String = ""
    private var bio : String = ""
    private var image : String = ""
    private var uid : String = ""

    constructor()
    constructor(username: String, fullname: String, bio: String, image: String, uid: String) {
        this.username = username
        this.fullname = fullname
        this.bio = bio
        this.image = image
        this.uid = uid
    }
    fun getUsername(): String {
        return username
    }
    fun getFullName(): String {
        return fullname
    }
    fun getBio(): String {
        return bio
    }
    fun getImage(): String {
        return image
    }
    fun getUid(): String {
        return uid
    }
    fun setUsername(username: String) {
        this.username = username
    }
    fun setFullName(fullname: String) {
        this.fullname = fullname
    }
    fun setBio(bio: String) {
        this.bio = bio
    }
    fun setImage(image: String) {
        this.image = image
    }
    fun setUid(uid: String) {
        this.uid = uid
    }

}
