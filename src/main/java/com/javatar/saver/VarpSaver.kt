package com.javatar.saver

import com.javatar.definition.SerializableDefinition
import com.javatar.osrs.definitions.impl.VarpDefinition
import com.javatar.osrs.definitions.output.OutputStream

class VarpSaver : SerializableDefinition<VarpDefinition> {
    override fun serialize(def: VarpDefinition): ByteArray {
        val out = OutputStream()

        out.writeByte(5)
        out.writeShort(def.type)
        out.writeByte(0)

        return out.flip()
    }
}