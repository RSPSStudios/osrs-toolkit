/*
 * Copyright (c) 2017, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.javatar.saver

import com.javatar.definition.SerializableDefinition
import com.javatar.osrs.definitions.impl.ObjectDefinition
import com.javatar.output.OutputStream

class ObjectSaver : SerializableDefinition<ObjectDefinition> {
    fun save(obj: ObjectDefinition): ByteArray {
        val out = OutputStream()
        if (obj.objectTypes != null && obj.objectModels != null) {
            out.writeByte(1)
            out.writeByte(obj.objectTypes.size)
            for (i in obj.objectTypes.indices) {
                out.writeShort(obj.objectModels[i])
                out.writeByte(obj.objectTypes[i])
            }
        }
        if (obj.name != null) {
            out.writeByte(2)
            out.writeString(obj.name)
        }
        if (obj.objectTypes == null && obj.objectModels != null) {
            out.writeByte(5)
            out.writeByte(obj.objectModels.size)
            for (i in obj.objectModels.indices) {
                out.writeShort(obj.objectModels[i])
            }
        }
        if (obj.sizeX != 1) {
            out.writeByte(14)
            out.writeByte(obj.sizeX)
        }
        if (obj.sizeY != 1) {
            out.writeByte(15)
            out.writeByte(obj.sizeY)
        }
        if (obj.interactType == 0 && !obj.isBlocksProjectile) {
            out.writeByte(17)
        } else if (!obj.isBlocksProjectile) {
            out.writeByte(18)
        }
        if (obj.wallOrDoor != -1) {
            out.writeByte(19)
            out.writeByte(obj.wallOrDoor)
        }
        if (obj.contouredGround == 0) {
            out.writeByte(21)
        }
        if (!obj.isMergeNormals) {
            out.writeByte(22)
        }
        if (obj.isABool2111) {
            out.writeByte(23)
        }
        if (obj.animationID != -1) {
            out.writeByte(24)
            out.writeShort(obj.animationID)
        }
        if (obj.interactType == 1) {
            out.writeByte(27)
        }
        if (obj.decorDisplacement != 16) {
            out.writeByte(28)
            out.writeByte(obj.decorDisplacement)
        }
        if (obj.ambient != 0) {
            out.writeByte(29)
            out.writeByte(obj.ambient)
        }
        if (obj.contrast != 0) {
            out.writeByte(39)
            out.writeByte(obj.contrast / 25)
        }
        for (i in 0..4) {
            out.writeByte(30 + i)
            val action = obj.actions[i]
            out.writeString(action ?: "Hidden")
        }
        if (obj.recolorToFind != null && obj.recolorToReplace != null) {
            out.writeByte(40)
            out.writeByte(obj.recolorToFind.size)
            for (i in obj.recolorToFind.indices) {
                out.writeShort(obj.recolorToFind[i].toInt())
                out.writeShort(obj.recolorToReplace[i].toInt())
            }
        }
        if (obj.retextureToFind != null && obj.textureToReplace != null) {
            out.writeByte(41)
            out.writeByte(obj.retextureToFind.size)
            for (i in obj.retextureToFind.indices) {
                out.writeShort(obj.retextureToFind[i].toInt())
                out.writeShort(obj.textureToReplace[i].toInt())
            }
        }
        if (obj.isRotated) {
            out.writeByte(62)
        }
        if (!obj.isShadow) {
            out.writeByte(64)
        }
        if (obj.modelSizeX != 128) {
            out.writeByte(65)
            out.writeShort(obj.modelSizeX)
        }
        if (obj.modelSizeHeight != 128) {
            out.writeByte(66)
            out.writeShort(obj.modelSizeHeight)
        }
        if (obj.modelSizeY != 128) {
            out.writeByte(67)
            out.writeShort(obj.modelSizeY)
        }
        if (obj.mapSceneID != -1) {
            out.writeByte(68)
            out.writeShort(obj.mapSceneID)
        }
        if (obj.blockingMask != 0) {
            out.writeByte(69)
            out.writeByte(obj.blockingMask)
        }
        if (obj.offsetX != 0) {
            out.writeByte(70)
            out.writeShort(obj.offsetX)
        }
        if (obj.offsetHeight != 0) {
            out.writeByte(71)
            out.writeShort(obj.offsetHeight)
        }
        if (obj.offsetY != 0) {
            out.writeByte(72)
            out.writeShort(obj.offsetY)
        }
        if (obj.isObstructsGround) {
            out.writeByte(73)
        }
        if (obj.isHollow) {
            out.writeByte(74)
        }
        if (obj.supportsItems != -1) {
            out.writeByte(75)
            out.writeByte(obj.supportsItems)
        }
        if (obj.ambientSoundId != -1) {
            out.writeByte(78)
            out.writeShort(obj.ambientSoundId)
            out.writeByte(obj.soundEffectRadius)
        }
        if (obj.soundEffectIds != null) {
            out.writeByte(79)
            out.writeShort(obj.anInt2112)
            out.writeShort(obj.anInt2113)
            out.writeByte(obj.soundEffectRadius)
            out.writeByte(obj.soundEffectIds.size)
            for (i in obj.soundEffectIds) {
                out.writeShort(i)
            }
        }
        if (obj.contouredGround != -1) {
            out.writeByte(81)
            out.writeByte(obj.contouredGround / 256)
        }
        if (obj.mapAreaId != -1) {
            out.writeByte(82)
            out.writeShort(obj.mapAreaId)
        }
        if (obj.isRandomizeAnimStart) {
            out.writeByte(89)
        }
        if (obj.configChangeDest != null) {
            out.writeByte(92)
            out.writeShort(obj.varbitID)
            out.writeShort(obj.varpID)
            val c = obj.configChangeDest
            out.writeShort(c[c.size - 1])
            out.writeByte(c.size - 2)
            for (i in 0..c.size - 2) {
                out.writeShort(c[i])
            }
        }
        if (obj.params != null) {
            out.writeByte(249)
            out.writeByte(obj.params.size)
            for ((key, value) in obj.params) {
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

    override fun serialize(def: ObjectDefinition): ByteArray {
        return save(def)
    }
}