package com.xzakota.wordpress.route

import com.xzakota.wordpress.WPClient
import com.xzakota.wordpress.model.CustomPost

open class CustomPostRouter<T : CustomPost>(
    client : WPClient, mainRoute : String, typeRef : Class<T>
) : StatusRouter<T>(client, mainRoute, typeRef)
