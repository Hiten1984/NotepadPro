package au.com.pro.note.pad.notepad.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "notepad")
data class Notepad (
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "notepadId")
        var id: Int = 0,

        @ColumnInfo(name = "notepadText")
        var text: String = "",

        @ColumnInfo(name = "notepadDate")
        var date: Long = System.currentTimeMillis()
)

