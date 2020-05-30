package es.enylrad.gamesgallery.core.db.converters

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import es.enylrad.gamesgallery.core.model.CoverEntity
import java.lang.reflect.Type

class CoverConverter {
    @TypeConverter
    fun fromCountryLangList(coverEntities: List<CoverEntity?>?): String? {
        if (coverEntities == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object :
            TypeToken<List<CoverEntity?>?>() {}.type
        return gson.toJson(coverEntities, type)
    }

    @TypeConverter
    fun toCountryLangList(countryLangString: String?): List<CoverEntity>? {
        if (countryLangString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object :
            TypeToken<List<CoverEntity?>?>() {}.type
        return gson.fromJson<List<CoverEntity>>(countryLangString, type)
    }
}