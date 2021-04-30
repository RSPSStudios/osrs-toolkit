package com.javatar.saver

import com.javatar.definition.SerializableDefinition
import com.javatar.osrs.definitions.impl.VarbitDefinition
import com.javatar.output.OutputStream

class VarbitSaver : SerializableDefinition<VarbitDefinition> {
    override fun serialize(def: VarbitDefinition): ByteArray {
        val out = OutputStream()
        if (def.index != 0) {
            out.writeByte(1)
            out.writeShort(def.index)
            out.writeByte(def.leastSignificantBit)
            out.writeByte(def.mostSignificantBit)
        }
        out.writeByte(0)
        return out.flip()
    }
}