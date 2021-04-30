package com.javatar.saver

import com.javatar.definition.SerializableDefinition
import com.javatar.osrs.definitions.impl.ParamDefinition
import com.javatar.output.OutputStream

class ParamSaver : SerializableDefinition<ParamDefinition> {
    override fun serialize(def: ParamDefinition): ByteArray {
        val out = OutputStream()
        if (def.type != null) {
            out.writeByte(1)
            out.writeByte(def.type.keyChar.toInt())
        }
        if (def.defaultInt != 0) {
            out.writeByte(2)
            out.writeInt(def.defaultInt)
        }
        if (!def.isMembers) {
            out.writeByte(4)
        }
        if (def.defaultString != null) {
            out.writeByte(5)
            out.writeString(def.defaultString)
        }
        out.writeByte(0)
        return out.flip()
    }
}