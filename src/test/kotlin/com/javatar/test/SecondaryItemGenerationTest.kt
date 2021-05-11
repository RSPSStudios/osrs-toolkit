package com.javatar.test

import com.displee.cache.CacheLibrary
import com.javatar.osrs.definitions.loaders.ItemLoader
import com.javatar.osrs.tools.ItemGeneration

object SecondaryItemGenerationTest {

    @JvmStatic
    fun main(args: Array<String>) {

        val cache = CacheLibrary.create("/home/javatar/Downloads/latest/cache")
        val loader = ItemLoader()

        val data = cache.data(2, 10, 4151)
        val whipDef = loader.load(4151, data)
        val bankData = cache.data(2, 10, whipDef.notedID)
        val whipBankDef = loader.load(whipDef.notedID, bankData)
        val whipPlaceData = cache.data(2, 10, whipDef.placeholderId)
        val whipPlaceDef = loader.load(whipDef.placeholderId, whipPlaceData)

        println(whipBankDef.notedTemplate)
        val tempDef = loader.load(whipBankDef.notedTemplate, cache.data(2, 10, whipBankDef.notedTemplate))

        println(whipDef)

        //whipBankDef.updateNote(tempDef, whipDef)

        val placeTempDef = loader.load(14401, cache.data(2, 10, 14401))

        println(whipBankDef)
        println(whipPlaceDef)
        println(placeTempDef)

        val def = ItemGeneration.generateBankNoteItem(whipDef, 50000)

        println(def)
        println(whipDef)

    }

}