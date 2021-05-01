package com.javatar.test

import com.displee.cache.CacheLibrary
import com.javatar.osrs.definitions.loaders.ItemLoader
import com.javatar.saver.ItemSaver

class ItemEncodingTest {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val cache = CacheLibrary.create("/home/javatar/Downloads/latest/cache")

            val itemData = cache.data(2, 10, 3)

            val loader = ItemLoader()
            val encoder = ItemSaver()

            var def = loader.load(3, itemData)

            val data = encoder.save(def)

            println(itemData.contentEquals(data))
            println("${data.size} - ${itemData?.size}")

            println(def.name)
            println(def.colorFind.toTypedArray().contentDeepToString())
            println(def.colorReplace.toTypedArray().contentDeepToString())

            def = loader.load(3, data)

            println(def.name)
            println(def.colorFind.toTypedArray().contentDeepToString())
            println(def.colorReplace.toTypedArray().contentDeepToString())


            /*
Opcode: 35
Opcode: 39
Opcode: 40
Opcode: 148
             */
            /*
Opcode: 32
Opcode: 35
Opcode: 39
Opcode: 40
Opcode: 148
             */

        }

    }

}