package com.xzakota.wordpress.route

import com.xzakota.net.ResponseTarget
import com.xzakota.net.route.IRouter

interface IWPRouter<T : ResponseTarget> : IRouter<WPRouter<T>>