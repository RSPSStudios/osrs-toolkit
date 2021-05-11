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

            val notedWhipData = cache.data(2, 10, 4152)
            val whip = cache.data(2, 10, 4151)

            val notedWhipDef = loader.load(4152, notedWhipData)
            val whipDef = loader.load(4151, whip)

            println(notedWhipDef.notedID)
            println(notedWhipDef.notedTemplate)
            println()
            println(whipDef.notedID)
            println(whipDef.notedTemplate)
            println("Placeholder data")
            println(notedWhipDef.placeholderId)
            println(notedWhipDef.placeholderTemplateId)
            println()
            val placeWhipData = cache.data(2, 10, 14032)
            val placeWhipDef = loader.load(14032, placeWhipData)
            println(whipDef.placeholderId)
            println(whipDef.placeholderTemplateId)
            println(placeWhipDef.placeholderId)
            println(placeWhipDef.placeholderTemplateId)


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