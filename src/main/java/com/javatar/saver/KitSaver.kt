package com.javatar.saver

import com.javatar.definition.SerializableDefinition
import com.javatar.osrs.definitions.impl.KitDefinition
import com.javatar.output.OutputStream

class KitSaver : SerializableDefinition<KitDefinition> {
    override fun serialize(def: KitDefinition): ByteArray {
        val out = OutputStream()
        if (def.getBodyPartId() != -1) {
            out.writeByte(1)
            out.writeByte(def.getBodyPartId())
        }
        if (def.getModels() != null) {
            out.writeByte(2)
            out.writeByte(def.getModels().size)
            for (i in def.getModels().indices) {
                out.writeShort(def.getModels()[i])
            }
        }
        if (def.isNonSelectable) {
            out.writeByte(3)
        }
        if (def.getRecolorToFind() != null && def.getRecolorToReplace() != null) {
            out.writeByte(40)
            out.writeByte(def.getRecolorToFind().size)
            for (i in def.getRecolorToFind().indices) {
                out.writeShort(def.getRecolorToFind()[i].toInt())
                out.writeShort(def.getRecolorToReplace()[i].toInt())
            }
        }
        if (def.getRetextureToFind() != null && def.getRetextureToReplace() != null) {
            out.writeByte(41)
            out.writeByte(def.getRetextureToFind().size)
            for (i in def.getRetextureToFind().indices) {
                out.writeShort(def.getRetextureToFind()[i].toInt())
                out.writeShort(def.getRetextureToReplace()[i].toInt())
            }
        }
        if (def.getChatheadModels() != null) {
            for (i in def.getChatheadModels().indices) {
                if (def.getChatheadModels()[i] != -1) {
                    out.writeByte(i + 60)
                    out.writeShort(def.getChatheadModels()[i])
                }
            }
        }
        out.writeByte(0)
        return out.flip()
    }
}