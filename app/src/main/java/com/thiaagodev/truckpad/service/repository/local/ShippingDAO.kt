package com.thiaagodev.truckpad.service.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.thiaagodev.truckpad.service.model.ShippingModel

@Dao
interface ShippingDAO {

    @Insert
    suspend fun insert(shippingModel: ShippingModel): Long

    @Query("SELECT * FROM Shipping ORDER BY id DESC")
    suspend fun list(): List<ShippingModel>

    @Query("SELECT * FROM Shipping WHERE id = :id")
    suspend fun get(id: Int): ShippingModel?

}