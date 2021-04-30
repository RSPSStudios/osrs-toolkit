package com.javatar.saver

import com.javatar.definition.SerializableDefinition
import com.javatar.osrs.definitions.impl.HealthBarDefinition
import com.javatar.output.OutputStream

class HealthBarSaver : SerializableDefinition<HealthBarDefinition> {
    override fun serialize(def: HealthBarDefinition): ByteArray {
        val out = OutputStream()

        if(def.field3277 != 255) {
            out.writeByte(2)
            out.writeByte(def.field3277)
        }
        if(def.field3278 != 255) {
            out.writeByte(3)
            out.writeByte(def.field3278)
        }
        if(def.field3283 != -1) {
            out.writeByte(4)
        }
        if(def.field3275 != 70) {
            out.writeByte(5)
            out.writeShort(def.field3275)
        }
        if(def.healthBarFrontSpriteId != -1) {
            out.writeByte(7)
            out.writeBigSmart(def.healthBarFrontSpriteId)
        }
        if(def.healthBarBackSpriteId != -1) {
            out.writeByte(8)
            out.writeBigSmart(def.healthBarBackSpriteId)
        }
        if(def.field3283 != -1) {
            out.writeByte(11)
            out.writeShort(def.field3283)
        }
        if(def.healthScale != 30) {
            out.writeByte(14)
            out.writeByte(def.healthScale)
        }
        if(def.healthBarPadding != 0) {
            out.writeByte(15)
            out.writeByte(def.healthBarPadding)
        }

        out.writeByte(0)
        return out.flip()
    }
}