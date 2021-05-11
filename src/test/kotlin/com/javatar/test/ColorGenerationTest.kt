package com.javatar.test

import com.displee.cache.CacheLibrary
import com.javatar.osrs.definitions.loaders.ItemLoader
import com.javatar.osrs.definitions.loaders.ModelLoader
import com.javatar.osrs.tools.ItemColorGeneration

object ColorGenerationTest {

    @JvmStatic
    fun main(args: Array<String>) {

        val cache = CacheLibrary.create("/home/javatar/Downloads/latest/cache")
        val loader = ItemLoader()

        val data = cache.data(2, 10, 1050)
        if(data != null) {

            val santa = loader.load(1050, data)

            println(santa.colorFind)
            println(santa.colorReplace)

            val mdata = cache.data(7, santa.inventoryModel)
            val mloader = ModelLoader()

            val model = mloader.load(santa.inventoryModel, mdata)

            val colors = ItemColorGeneration.generateOriginalColors(model)

            println(colors.toTypedArray().contentDeepToString())

        }

    }

}