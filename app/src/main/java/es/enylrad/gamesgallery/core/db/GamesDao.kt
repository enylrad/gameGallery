package es.enylrad.gamesgallery.core.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.enylrad.gamesgallery.core.model.GameEntity

@Dao
interface GamesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(fileEntity: List<GameEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gameEntity: GameEntity)

    @Query("SELECT * FROM games ORDER BY popularity DESC")
    fun getGames(): LiveData<List<GameEntity>>

    @Query("SELECT * FROM games ORDER BY popularity DESC")
    fun getGamesPagedList(): DataSource.Factory<Int, GameEntity>

    @Query("SELECT * FROM games WHERE id == :id")
    fun getGame(id: Int): LiveData<GameEntity>

}