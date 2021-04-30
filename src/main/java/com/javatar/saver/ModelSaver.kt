package com.javatar.saver

import com.javatar.definition.SerializableDefinition
import com.javatar.osrs.definitions.impl.ModelDefinition
import com.javatar.output.OutputStream

/**
 * @author David Schlachter <davidschlachter96></davidschlachter96>@gmail.com>
 * @created March 13 2021
 * This model saver only patches the model if the face colors or face textures change.
 */
class ModelSaver : SerializableDefinition<ModelDefinition> {
    override fun serialize(def: ModelDefinition): ByteArray {
        val out = OutputStream()
        out.writeBytes(def.rawModelData)
        for (patch in def.colorModelPatches) {
            out.offset = patch.offset
            out.writeShort(def.faceColors[patch.index].toInt())
        }
        for (patch in def.textureModelPatches) {
            out.offset = patch.offset
            out.writeShort(def.faceTextures[patch.index].toInt())
        }
        return out.array
    }
}