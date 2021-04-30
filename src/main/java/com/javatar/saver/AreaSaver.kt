package com.javatar.saver

import com.javatar.definition.SerializableDefinition
import com.javatar.osrs.definitions.impl.AreaDefinition
import com.javatar.output.OutputStream

/**
 * Not writing opcodes that are ignore by runescape in this revision.
 */
class AreaSaver : SerializableDefinition<AreaDefinition> {
    override fun serialize(def: AreaDefinition): ByteArray {
        val out = OutputStream()
        if (def.spriteId != -1) {
            out.writeByte(1)
            out.writeBigSmart(def.spriteId)
        }
        if (def.field3294 != -1) {
            out.writeByte(2)
            out.writeBigSmart(def.field3294)
        }
        if (def.name != null) {
            out.writeByte(3)
            out.writeString(def.name)
        }
        if (def.field3296 != 0) {
            out.writeByte(4)
            out.write24BitInt(def.field3296)
        }
        if (def.field3310 != 0) {
            out.writeByte(6)
            out.writeByte(def.field3310)
        }
        for (i in def.field3298.indices) {
            if (def.field3298[i] != null) {
                out.writeByte(10 + i)
                out.writeString(def.field3298[i])
            }
        }
        if (def.field3300 != null && def.field3300.size > 0) {
            out.writeByte(15)
            out.writeByte(def.field3309.size)
            for (i in def.field3300.indices) {
                out.writeShort(def.field3300[i])
            }
            out.writeInt(0)
            out.writeByte(def.field3292.size)
            for (i in def.field3292.indices) {
                out.writeInt(def.field3292[i])
            }
            for (i in def.field3309.indices) {
                out.writeByte(def.field3309[i].toInt())
            }
        }
        if (def.field3308 != null) {
            out.writeByte(17)
            out.writeString(def.field3308)
        }
        if (def.worldmapCategoryId != -1) {
            out.writeByte(19)
            out.writeShort(def.worldmapCategoryId)
        }
        if (def.opcode29 != 0) {
            out.writeByte(29)
            out.writeByte(def.opcode29)
        }
        if (def.opcode30 != 0) {
            out.writeByte(30)
            out.writeByte(def.opcode30)
        }
        out.writeByte(0)
        return out.flip()
    }
}