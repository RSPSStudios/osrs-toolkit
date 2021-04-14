package com.javatar.saver;

import com.javatar.definition.SerializableDefinition;
import com.javatar.osrs.definitions.impl.VarpDefinition;
import com.javatar.output.OutputStream;

public class VarpSaver implements SerializableDefinition<VarpDefinition> {
    @Override
    public byte[] serialize(VarpDefinition def) {
        OutputStream out = new OutputStream();

        out.writeByte(5);
        out.writeShort(def.getType());
        out.writeByte(0);

        return out.flip();
    }
}
