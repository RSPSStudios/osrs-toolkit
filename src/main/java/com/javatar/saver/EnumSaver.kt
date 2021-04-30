package com.javatar.saver

import com.javatar.definition.SerializableDefinition
import com.javatar.osrs.definitions.impl.EnumDefinition
import com.javatar.output.OutputStream

class EnumSaver : SerializableDefinition<EnumDefinition> {
    override fun serialize(def: EnumDefinition): ByteArray {
        val out = OutputStream()
        if (def.keyType != null) {
            out.writeByte(1)
            out.writeByte(def.keyType.keyChar.toInt())
        }
        if (def.valType != null) {
            out.writeByte(2)
            out.writeByte(def.valType.keyChar.toInt())
        }
        if (def.defaultString != null) {
            out.writeByte(3)
            out.writeString(def.defaultString)
        }
        if (def.defaultInt != 0) {
            out.writeByte(4)
            out.writeInt(def.defaultInt)
        }
        if (def.keys != null && def.stringVals != null) {
            out.writeByte(5)
            out.writeShort(def.keys.size)
            for (i in def.keys.indices) {
                out.writeInt(def.keys[i])
                out.writeString(def.stringVals[i])
            }
        }
        if (def.keys != null && def.intVals != null) {
            out.writeByte(6)
            out.writeShort(def.keys.size)
            for (i in def.keys.indices) {
                out.writeInt(def.keys[i])
                out.writeInt(def.intVals[i])
            }
        }
        out.writeByte(0)
        return out.flip()
    }
}