package com.hess.everly.data

data class MemoryPhoto(
    val id: Int,
    val label: String,
    val accentColor: String
)

data class OfflineMemory(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val accentColor: String,
    val isPublic: Boolean,
    val photos: List<MemoryPhoto>
)

data class OfflineJournal(
    val id: Int,
    val title: String,
    val content: String,
    val createdAt: String,
    val accentStartColor: String,
    val accentEndColor: String,
    val wordCount: Int
)

data class AutographEntry(
    val id: Int,
    val authorName: String,
    val message: String
)

data class OfflineAutograph(
    val id: Int,
    val title: String,
    val targetName: String,
    val description: String,
    val createdAt: String,
    val coverColor: String,
    val entries: List<AutographEntry>
)

data class OfflineEvent(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val accentColor: String
)

