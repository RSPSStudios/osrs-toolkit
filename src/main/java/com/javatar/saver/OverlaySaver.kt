package com.javatar.saver

import com.javatar.definition.SerializableDefinition
import com.javatar.osrs.definitions.impl.OverlayDefinition
import com.javatar.output.OutputStream

class OverlaySaver : SerializableDefinition<OverlayDefinition> {
    override fun serialize(def: OverlayDefinition): ByteArray {
        val out = OutputStream()
        if (def.rgbColor != 0) {
            out.writeByte(1)
            out.write24BitInt(def.rgbColor)
        }
        if (def.texture != -1) {
            out.writeByte(2)
            out.writeByte(def.texture)
        }
        if (!def.isHideUnderlay) {
            out.writeByte(5)
        }
        if (def.secondaryRgbColor != -1) {
            out.writeByte(7)
            out.write24BitInt(def.secondaryRgbColor)
        }
        out.writeByte(0)
        return out.flip()
    }
}