package com.javatar.saver

import com.javatar.definition.SerializableDefinition
import com.javatar.osrs.definitions.impl.UnderlayDefinition
import com.javatar.output.OutputStream

class UnderlaySaver : SerializableDefinition<UnderlayDefinition> {
    override fun serialize(def: UnderlayDefinition): ByteArray {
        val out = OutputStream()
        if (def.color != 0) {
            out.writeByte(1)
            out.write24BitInt(def.color)
        }
        out.writeByte(0)
        return out.flip()
    }
}