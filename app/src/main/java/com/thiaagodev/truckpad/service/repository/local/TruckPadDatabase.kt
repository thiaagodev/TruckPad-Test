package com.thiaagodev.truckpad.service.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thiaagodev.truckpad.service.model.ShippingModel

@Database(entities = [ShippingModel::class], version = 1)
abstract class TruckPadDatabase : RoomDatabase() {

    abstract fun shippingDAO(): ShippingDAO

    companion object {
        private lateinit var INSTANCE: TruckPadDatabase

        fun getDatabase(context: Context): TruckPadDatabase {
            if (!::INSTANCE.isInitialized) {
                synchronized(TruckPadDatabase::class) {
                    INSTANCE =
                        Room.databaseBuilder(context, TruckPadDatabase::class.java, "truckPadDB")
                            .build()
                }
            }

            return INSTANCE
        }

    }
}