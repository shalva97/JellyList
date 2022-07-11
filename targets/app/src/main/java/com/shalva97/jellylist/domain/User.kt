package com.shalva97.jellylist.domain

import java.util.*

data class User(val name: String, val id: UUID, val hasPassword: Boolean)