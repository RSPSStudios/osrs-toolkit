package com.javatar.test

import com.displee.cache.CacheLibrary
import com.javatar.osrs.definitions.impl.VarbitDefinition
import com.javatar.osrs.definitions.loaders.VarbitLoader
import com.javatar.osrs.definitions.loaders.VarpLoader
import com.javatar.saver.VarbitSaver
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

class VarbitCreator {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val cache = CacheLibrary.create("/home/javatar/Downloads/F-16Cache/cache")

            val varbits = cache.index(2).archive(14)
            val loader = VarbitLoader()
            val saver = VarbitSaver()
            val varpLoader = VarpLoader()

            val def = VarbitDefinition(0)
            def.index = 2599
            def.leastSignificantBit = 0
            def.mostSignificantBit = 1
            val varbitList = mutableListOf<Int>()
            repeat(100) {
                val file = varbits?.add(saver.serialize(def))
                if (file != null) {
                    varbitList.add(file.id)
                }
            }

            Files.write(File("./varbits.txt").toPath(), varbitList.map { "Varbit ID: $it" }.joinToString("\n") { it }.toByteArray())

            cache.index(2).update()

            val list = mutableListOf<Int>()
            varbits?.fileIds()?.forEach {
                val def = loader.load(it, cache.data(2, 14, it))
                println("Index ${def.index} - $it")
                list.add(def.index)
            }
            println()
            println()
            println()

            list.sort()

            list.forEach {
                println("Index $it")
            }

        }

    }

}