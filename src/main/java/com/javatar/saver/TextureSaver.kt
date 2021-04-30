package com.javatar.saver

import com.javatar.definition.SerializableDefinition
import com.javatar.osrs.definitions.impl.TextureDefinition
import com.javatar.output.OutputStream

class TextureSaver : SerializableDefinition<TextureDefinition> {
    override fun serialize(def: TextureDefinition): ByteArray {
        val out = OutputStream()
        out.writeShort(def.field1777)
        out.writeBoolean(def.field1778)
        out.writeByte(def.fileIds.size)
        for (id in def.fileIds) {
            out.writeShort(id)
        }
        if (def.fileIds.size > 1) {
            for (value in def.field1780) {
                out.writeByte(value)
            }
        }
        if (def.fileIds.size > 1) {
            for (value in def.field1781) {
                out.writeByte(value)
            }
        }
        for (value in def.field1786) {
            out.writeInt(value)
        }
        out.writeByte(def.field1783)
        out.writeByte(def.field1782)
        return out.flip()
    }
}