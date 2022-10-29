package com.thiaagodev.truckpad.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Shipping")
class ShippingModel {

    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo
    var originName: String = ""

    @ColumnInfo
    var originLatitude: Double = 0.0

    @ColumnInfo
    var originLongitude: Double = 0.0

    @ColumnInfo
    var destinyName: String = ""

    @ColumnInfo
    var destinyLatitude: Double = 0.0

    @ColumnInfo
    var destinyLongitude: Double = 0.0

    @ColumnInfo
    var axes: Int = 0

    @ColumnInfo
    var fuelConsumption: Double = 0.0

    @ColumnInfo
    var fuelPrice: Double = 0.0

}