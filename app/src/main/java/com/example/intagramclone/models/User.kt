package com.example.intagramclone.models

class User {
    var uid:String?=null
    var image:String? =null
    var name:String? =null
    var username:String?=null
    var email:String? =null
    var password:String? =null
    var bio:String?=null
    var gender:String?=null
    var follow:String?=null
    constructor()
    constructor(uid:String?,image: String?, name: String?,username:String?, email: String?, password: String?,bio:String?,gender:String?,follow:String?) {
        this.uid=uid
        this.image = image
        this.name = name
        this.username = username
        this.email = email
        this.password = password
        this.bio=bio
        this.gender=gender
        this.follow=follow
    }

    constructor(name: String?, email: String?, password: String?) {
        this.name = name
        this.email = email
        this.password = password
    }

    constructor(email: String?, password: String?) {
        this.email = email
        this.password = password
    }


}