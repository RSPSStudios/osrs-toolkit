package com.javatar.saver

import com.javatar.definition.SerializableDefinition
import com.javatar.osrs.definitions.impl.WorldMapDefinition
import com.javatar.osrs.definitions.impl.map.world.impl.WorldMapType0
import com.javatar.osrs.definitions.impl.map.world.impl.WorldMapType1
import com.javatar.osrs.definitions.impl.map.world.impl.WorldMapType2
import com.javatar.osrs.definitions.impl.map.world.impl.WorldMapType3
import com.javatar.output.OutputStream

class WorldMapSaver : SerializableDefinition<WorldMapDefinition> {
    override fun serialize(def: WorldMapDefinition): ByteArray {
        val out = OutputStream()

        out.writeString(def.safeName)
        out.writeString(def.name)

        val position = def.position
        val packed = position.y shl 28 or position.x shl 14 or position.z
        out.writeInt(packed)
        out.writeInt(def.field450)
        out.writeByte(0) // Skipped
        out.writeBoolean(def.isSurface)
        out.writeByte(def.defaultZoom)
        out.writeByte(def.regionList.size)

        def.regionList.forEach {
            when (it) {
                is WorldMapType0 -> {
                    out.writeByte(0)
                    it.serialize(out)
                }
                is WorldMapType1 -> {
                    out.writeByte(1)
                    it.serialize(out)
                }
                is WorldMapType2 -> {
                    out.writeByte(2)
                    it.serialize(out)
                }
                is WorldMapType3 -> {
                    out.writeByte(3)
                    it.serialize(out)
                }
            }
        }



        return byteArrayOf()
    }

    private fun WorldMapType0.serialize(out: OutputStream) {
        out.writeByte(plane)
        out.writeByte(numberOfPlanes)
        out.writeShort(xLow)
        out.writeByte(chunk_xLow)
        out.writeShort(yLow)
        out.writeByte(chunk_yLow)
        out.writeShort(xHigh)
        out.writeByte(chunk_xHigh)
        out.writeShort(yHigh)
        out.writeByte(chunk_yHigh)
    }

    private fun WorldMapType1.serialize(out: OutputStream) {
        out.writeByte(plane)
        out.writeByte(numberOfPlanes)
        out.writeShort(xLowerLeft)
        out.writeShort(yLowerLeft)
        out.writeShort(xLowerRight)
        out.writeShort(yUpperLeft)
        out.writeShort(xUpperLeft)
        out.writeShort(yLowerRight)
        out.writeShort(xUpperRight)
        out.writeShort(yUpperRight)
    }

    private fun WorldMapType2.serialize(out: OutputStream) {
        out.writeByte(plane)
        out.writeByte(numberOfPlanes)
        out.writeShort(xLow)
        out.writeShort(yLow)
        out.writeShort(xHigh)
        out.writeShort(yHigh)
    }

    private fun WorldMapType3.serialize(out: OutputStream) {
        out.writeByte(oldPlane)
        out.writeByte(numberOfPlanes)
        out.writeShort(oldX)
        out.writeByte(chunk_oldXLow)
        out.writeByte(chunk_oldXHigh)
        out.writeShort(oldY)
        out.writeByte(chunk_oldYLow)
        out.writeByte(chunk_oldYHigh)
        out.writeShort(newX)
        out.writeByte(chunk_newXLow)
        out.writeByte(chunk_newXHigh)
        out.writeShort(newY)
        out.writeByte(chunk_newYLow)
        out.writeByte(chunk_newYHigh)
    }

}