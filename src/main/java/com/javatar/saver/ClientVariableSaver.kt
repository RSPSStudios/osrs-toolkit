package com.javatar.saver

import com.javatar.osrs.definitions.definition.SerializableDefinition
import com.javatar.osrs.definitions.impl.ClientVarDefinition
import com.javatar.output.OutputStream

class ClientVariableSaver : SerializableDefinition<ClientVarDefinition> {
    override fun serialize(def: ClientVarDefinition): ByteArray {
        val out = OutputStream()

        if(def.persisent) {
            out.writeByte(2)
        }

        out.writeByte(0)

        return out.flip()
    }
}