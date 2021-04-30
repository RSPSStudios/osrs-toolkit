package com.javatar.saver

import com.javatar.definition.SerializableDefinition
import com.javatar.osrs.definitions.impl.HitSplatDefinition
import com.javatar.output.OutputStream

class HitSplatSaver : SerializableDefinition<HitSplatDefinition> {
    override fun serialize(def: HitSplatDefinition): ByteArray {
        val out = OutputStream()
        if (def.fontType != -1) {
            out.writeByte(1)
            out.writeBigSmart(def.fontType)
        }
        if (def.textColor != 0xFFFFFF) {
            out.writeByte(2)
            out.write24BitInt(def.textColor)
        }
        if (def.leftSprite != -1) {
            out.writeByte(3)
            out.writeBigSmart(def.leftSprite)
        }
        if (def.leftSprite2 != -1) {
            out.writeByte(4)
            out.writeBigSmart(def.leftSprite2)
        }
        if (def.backgroundSprite != -1) {
            out.writeByte(5)
            out.writeBigSmart(def.backgroundSprite)
        }
        if (def.rightSpriteId != -1) {
            out.writeByte(6)
            out.writeBigSmart(def.rightSpriteId)
        }
        if (def.scrollToOffsetX != 0) {
            out.writeByte(7)
            out.writeShort(def.scrollToOffsetX)
        }
        if (def.stringFormat != null) {
            out.writeByte(8)
            out.writeString2(def.stringFormat)
        }
        if (def.displayCycles != 70) {
            out.writeByte(9)
            out.writeShort(def.displayCycles)
        }
        if (def.scrollToOffsetY != 0) {
            out.writeByte(10)
            out.writeShort(def.scrollToOffsetY)
        }
        if (def.fadeStartCycle == 0) {
            out.writeByte(11)
        }
        if (def.useDamage != -1) {
            out.writeByte(12)
            out.writeByte(def.useDamage)
        }
        if (def.textOffsetY != 0) {
            out.writeByte(13)
            out.writeShort(def.textOffsetY)
        }
        if (def.fadeStartCycle > 0) {
            out.writeByte(14)
            out.writeShort(def.fadeStartCycle)
        }
        if (def.varbitID != -1) {
            val defaultId = def.multihitsplats[def.multihitsplats.size - 1]
            out.writeByte(if (defaultId == -1) 17 else 18)
            out.writeShort(def.varbitID)
            out.writeShort(def.varpID)
            if (defaultId != -1) {
                out.writeShort(defaultId)
            }
            out.writeByte(def.multihitsplats.size - 2)
            for (i in 0 until def.multihitsplats.size - 1) {
                out.writeShort(def.multihitsplats[i])
            }
        }
        out.writeByte(0)
        return out.flip()
    }
}