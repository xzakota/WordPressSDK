package com.xzakota.wordpress.request

import com.xzakota.collect.KStrVObj
import com.xzakota.wordpress.WPClient
import com.xzakota.wordpress.model.ApplicationPassword
import com.xzakota.wordpress.model.DeletedItem
import com.xzakota.wordpress.model.User
import com.xzakota.wordpress.route.IDRouter
import com.xzakota.wordpress.route.WPRoute

class Users(
    client : WPClient
) : IDRouter<User>(client, WPRoute.USERS, User::class.java) {
    companion object {
        const val ID_ME = "me"
    }

    fun retrieveMe() : User? {
        return retrieveById(ID_ME)
    }

    fun updateMe(target : User) : User? {
        return updateById(ID_ME, target)
    }

    fun deleteMe() : DeletedItem<User>? {
        return deleteById(ID_ME)
    }

    override fun deleteById(id : Long, force : Boolean?) : DeletedItem<User>? {
        return super.deleteById(id, null)
    }

    override fun deleteById(identifier : String, requestBody : KStrVObj?) : DeletedItem<User>? {
        val requestBody = requestBody ?: KStrVObj.of()
        // force required to be true, as users do not support trashing.
        requestBody.push("force", true)
        // reassign the deleted user's posts and links to this user ID.
        requestBody.push("reassign", 1L)
        return delete(checkRoute(identifier), requestBody)
    }

    fun listAppPwdByUser(user : User) : List<ApplicationPassword>? {
        return user.id?.let { it ->
            listAppPwdByUserId(it)
        }
    }

    fun listAppPwdByUserId(userID : Long) : List<ApplicationPassword>? {
        return listAppPwdByUserId(userID.toString())
    }

    @JvmOverloads
    fun listAppPwdByUserId(
        identifier : String,
        queryParams : KStrVObj? = null
    ) : List<ApplicationPassword>? {
        return list(
            getRouter().uri + checkRoute("$identifier/application-passwords"),
            ApplicationPassword::class.java,
            queryParams
        )
    }
}
