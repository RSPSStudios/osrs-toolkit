package com.javatar.saver

import com.javatar.definition.SerializableDefinition
import com.javatar.osrs.definitions.impl.SpotAnimDefinition
import com.javatar.output.OutputStream

class SpotAnimSaver : SerializableDefinition<SpotAnimDefinition> {
    override fun serialize(def: SpotAnimDefinition): ByteArray {
        val out = OutputStream()
        if (def.modelId != 0) {
            out.writeByte(1)
            out.writeShort(def.modelId)
        }
        if (def.animationId != -1) {
            out.writeByte(2)
            out.writeShort(def.animationId)
        }
        if (def.resizeX != 128) {
            out.writeByte(4)
            out.writeShort(def.resizeX)
        }
        if (def.resizeY != 128) {
            out.writeByte(5)
            out.writeShort(def.resizeY)
        }
        if (def.rotaton != 0) {
            out.writeByte(6)
            out.writeShort(def.rotaton)
        }
        if (def.ambient != 0) {
            out.writeByte(7)
            out.writeByte(def.ambient)
        }
        if (def.contrast != 0) {
            out.writeByte(8)
            out.writeByte(def.contrast)
        }
        if (def.recolorToFind != null && def.recolorToReplace != null) {
            out.writeByte(40)
            out.writeByte(def.recolorToFind.size)
            for (i in def.recolorToFind.indices) {
                out.writeShort(def.recolorToFind[i].toInt())
                out.writeShort(def.recolorToReplace[i].toInt())
            }
        }
        if (def.textureToFind != null && def.textureToReplace != null) {
            out.writeByte(41)
            out.writeByte(def.textureToFind.size)
            for (i in def.textureToFind.indices) {
                out.writeShort(def.textureToFind[i].toInt())
                out.writeShort(def.textureToReplace[i].toInt())
            }
        }
        out.writeByte(0)
        return out.flip()
    }
}