package com.gayan.mvvmnewsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gayan.mvvmnewsapp.models.Article

@Database(
    entities = [Article::class],
    version = 1
)
//Room can only store primitive types (int, string, etc.)
//here we are using TypeConverter to get "Source".
@TypeConverters(Converters:: class)
abstract class ArticleDatabase : RoomDatabase(){

    abstract fun getArticleDao(): ArticleDao

    //The companion object makes the members inside it act like "static" variables in Java.
    companion object{
        @Volatile /**This ensures that changes made to the instance variable by one thread are
        immediately visible to all other threads. It prevents different threads from seeing
        a "stale" version of the database. */
        private var instance: ArticleDatabase? = null

        /**This is a physical object used for synchronization.
         * It ensures that only one thread can enter the database creation block at a time.*/
        private val LOCK = Any()

        /**It allows you to "call" the class like a function.
         * In your code, you can just type ArticleDatabase(context) to get your instance.*/

        /**This prevents a race condition where two threads might try to create the
         * database at the exact same millisecond.*/
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also{
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()
    }
}