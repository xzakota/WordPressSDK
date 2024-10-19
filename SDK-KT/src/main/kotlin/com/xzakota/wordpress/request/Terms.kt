package com.xzakota.wordpress.request

import com.xzakota.wordpress.WPClient
import com.xzakota.wordpress.model.Term
import com.xzakota.wordpress.route.WPV2Router

open class Terms(
    client : WPClient,
    mainRoute : String
) : WPV2Router<Term>(client, mainRoute, Term::class.java)