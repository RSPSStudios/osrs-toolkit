package com.javatar.saver

import com.javatar.definition.SerializableDefinition
import com.javatar.osrs.definitions.impl.InventoryDefinition
import com.javatar.output.OutputStream

class InventorySaver : SerializableDefinition<InventoryDefinition> {
    override fun serialize(def: InventoryDefinition): ByteArray {
        val out = OutputStream()
        if (def.getSize() != 0) {
            out.writeByte(2)
            out.writeShort(def.getSize())
        }
        out.writeByte(0)
        return out.flip()
    }
}