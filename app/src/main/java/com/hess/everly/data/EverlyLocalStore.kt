package com.hess.everly.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EverlyLocalStore private constructor(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    fun getMemories(): List<OfflineMemory> =
        readList(KEY_MEMORIES, memoryType, seedMemories())

    fun getMemory(memoryId: Int): OfflineMemory? =
        getMemories().firstOrNull { it.id == memoryId }

    fun addMemory(title: String, description: String, date: String) {
        val memories = getMemories().toMutableList()
        val nextId = (memories.maxOfOrNull { it.id } ?: 0) + 1
        memories.add(
            0,
            OfflineMemory(
                id = nextId,
                title = title,
                description = description.ifBlank { "A new chapter saved on your device." },
                date = date,
                accentColor = memoryPalette[nextId % memoryPalette.size],
                isPublic = false,
                photos = listOf(
                    MemoryPhoto(nextId * 10 + 1, "Cover", "#F4E8FF"),
                    MemoryPhoto(nextId * 10 + 2, "Moment", "#E8F5FF"),
                    MemoryPhoto(nextId * 10 + 3, "Detail", "#FFF1E8")
                )
            )
        )
        writeList(KEY_MEMORIES, memories)
    }

    fun getJournals(): List<OfflineJournal> =
        readList(KEY_JOURNALS, journalType, seedJournals())

    fun getJournal(journalId: Int): OfflineJournal? =
        getJournals().firstOrNull { it.id == journalId }

    fun getAutographs(): List<OfflineAutograph> =
        readList(KEY_AUTOGRAPHS, autographType, seedAutographs())

    fun getAutograph(autographId: Int): OfflineAutograph? =
        getAutographs().firstOrNull { it.id == autographId }

    fun getEvents(): List<OfflineEvent> =
        readList(KEY_EVENTS, eventType, seedEvents())

    fun getEvent(eventId: Int): OfflineEvent? =
        getEvents().firstOrNull { it.id == eventId }

    private fun <T> readList(key: String, typeToken: java.lang.reflect.Type, seeds: List<T>): List<T> {
        val raw = prefs.getString(key, null)
        if (raw.isNullOrBlank()) {
            writeList(key, seeds)
            return seeds
        }
        return gson.fromJson(raw, typeToken)
    }

    private fun <T> writeList(key: String, items: List<T>) {
        prefs.edit().putString(key, gson.toJson(items)).apply()
    }

    private fun seedMemories() = listOf(
        OfflineMemory(
            id = 1,
            title = "Summer Trip 2026",
            description = "A sun-soaked week of beaches, train rides, and late-night food stops.",
            date = "2026-07-15",
            accentColor = "#EADFFF",
            isPublic = false,
            photos = listOf(
                MemoryPhoto(1, "Arrival", "#DDEBFF"),
                MemoryPhoto(2, "Ocean", "#FDE8E8"),
                MemoryPhoto(3, "Night Walk", "#FFF2D8")
            )
        ),
        OfflineMemory(
            id = 2,
            title = "Graduation Day",
            description = "Caps in the air, family photos, and the quiet relief of making it through.",
            date = "2026-12-05",
            accentColor = "#DDEBFF",
            isPublic = true,
            photos = listOf(
                MemoryPhoto(4, "Ceremony", "#E6F6EC"),
                MemoryPhoto(5, "Portrait", "#FCE7F3"),
                MemoryPhoto(6, "Celebration", "#EEF2FF")
            )
        ),
        OfflineMemory(
            id = 3,
            title = "Dog's Birthday",
            description = "Three years of chaos, treats, and couch-stealing loyalty.",
            date = "2026-02-14",
            accentColor = "#FFF0D8",
            isPublic = false,
            photos = listOf(
                MemoryPhoto(7, "Cake", "#FFE4E6"),
                MemoryPhoto(8, "Park", "#DCFCE7"),
                MemoryPhoto(9, "Nap", "#E0F2FE")
            )
        )
    )

    private fun seedJournals() = listOf(
        OfflineJournal(
            id = 1,
            title = "A quiet win",
            content = "Today felt small from the outside, but inside it mattered. I finished the pieces I kept postponing and finally felt the project click into place.",
            createdAt = "2026-04-11",
            accentStartColor = "#6F42C1",
            accentEndColor = "#9A74D8",
            wordCount = 148
        ),
        OfflineJournal(
            id = 2,
            title = "Family dinner notes",
            content = "Everyone spoke over each other, laughed too loudly, and somehow that made the night feel even warmer. I want to remember the way the table looked.",
            createdAt = "2026-04-08",
            accentStartColor = "#667EEA",
            accentEndColor = "#764BA2",
            wordCount = 121
        ),
        OfflineJournal(
            id = 3,
            title = "Building Everly",
            content = "The app is starting to feel less like a set of screens and more like a place someone might actually keep their life in. That changes how I design it.",
            createdAt = "2026-04-03",
            accentStartColor = "#F093FB",
            accentEndColor = "#F5576C",
            wordCount = 172
        )
    )

    private fun seedAutographs() = listOf(
        OfflineAutograph(
            id = 1,
            title = "Graduation 2026",
            targetName = "Hesanda",
            description = "Messages collected for the end of one chapter and the start of the next.",
            createdAt = "2026-03-28",
            coverColor = "#FEFCF3",
            entries = listOf(
                AutographEntry(1, "Amaya", "Keep building things that feel honest. You have a gift for it."),
                AutographEntry(2, "Kavin", "You made hard seasons lighter for everyone around you."),
                AutographEntry(3, "Mila", "Don't lose the softness. It's part of the strength.")
            )
        ),
        OfflineAutograph(
            id = 2,
            title = "Family Reunion Book",
            targetName = "Nana",
            description = "A warm collection of handwritten notes, tiny stories, and gratitude.",
            createdAt = "2026-01-16",
            coverColor = "#FFF7ED",
            entries = listOf(
                AutographEntry(4, "Lena", "Thank you for keeping all of us stitched together."),
                AutographEntry(5, "Noah", "You make ordinary meals feel like celebrations.")
            )
        )
    )

    private fun seedEvents() = listOf(
        OfflineEvent(
            id = 1,
            title = "Summer Picnic",
            description = "A relaxed family picnic with photo corners, old songs, and memory prompts.",
            date = "2026-08-21",
            location = "Victoria Park",
            accentColor = "#C4B5FD"
        ),
        OfflineEvent(
            id = 2,
            title = "Anniversary Dinner",
            description = "An intimate evening built around a shared timeline of milestones and keepsakes.",
            date = "2026-06-03",
            location = "The Harbour Table",
            accentColor = "#F9A8D4"
        ),
        OfflineEvent(
            id = 3,
            title = "New Year Gathering",
            description = "Photos, message cards, and a gentle recap of the year that just closed.",
            date = "2025-12-31",
            location = "Home Garden",
            accentColor = "#93C5FD"
        )
    )

    companion object {
        private const val PREFS_NAME = "everly_local_store"
        private const val KEY_MEMORIES = "memories"
        private const val KEY_JOURNALS = "journals"
        private const val KEY_AUTOGRAPHS = "autographs"
        private const val KEY_EVENTS = "events"

        private val memoryType = object : TypeToken<List<OfflineMemory>>() {}.type
        private val journalType = object : TypeToken<List<OfflineJournal>>() {}.type
        private val autographType = object : TypeToken<List<OfflineAutograph>>() {}.type
        private val eventType = object : TypeToken<List<OfflineEvent>>() {}.type
        private val memoryPalette = listOf("#EADFFF", "#DDEBFF", "#FFF0D8", "#E6F6EC")

        @Volatile
        private var instance: EverlyLocalStore? = null

        fun get(context: Context): EverlyLocalStore =
            instance ?: synchronized(this) {
                instance ?: EverlyLocalStore(context.applicationContext).also { instance = it }
            }
    }
}

