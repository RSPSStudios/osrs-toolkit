package com.javatar.saver

import com.javatar.definition.SerializableDefinition
import com.javatar.osrs.definitions.impl.StructDefinition
import com.javatar.output.OutputStream

class StructSaver : SerializableDefinition<StructDefinition> {
    override fun serialize(def: StructDefinition): ByteArray {
        val out = OutputStream()
        if (def.params != null) {
            out.writeByte(249)
            out.writeByte(def.params.size)
            for ((key, value) in def.params) {
                out.writeByte(if (value is String) 1 else 0)
                out.write24BitInt(key!!)
                if (value is String) {
                    out.writeString(value)
                } else {
                    out.writeInt((value as Int))
                }
            }
        }
        out.writeByte(0)
        return out.flip()
    }
}