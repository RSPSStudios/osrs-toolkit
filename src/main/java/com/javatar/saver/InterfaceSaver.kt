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
import com.javatar.osrs.definitions.impl.InterfaceDefinition
import com.javatar.output.OutputStream

class InterfaceSaver : SerializableDefinition<InterfaceDefinition> {
    fun save(def: InterfaceDefinition): ByteArray {
        return if (def.isIf3) {
            saveIf3(def)
        } else {
            saveIf1(def)
        }
    }

    private fun saveIf3(def: InterfaceDefinition): ByteArray {
        throw UnsupportedOperationException("Not supported yet.") //To change body of generated methods, choose Tools | Templates.
    }

    private fun saveIf1(def: InterfaceDefinition): ByteArray {
        val out = OutputStream()
        out.writeByte(def.type)
        out.writeByte(def.menuType)
        out.writeShort(def.contentType)
        out.writeShort(def.originalX)
        out.writeShort(def.originalY)
        out.writeShort(def.originalWidth)
        out.writeShort(def.originalHeight)
        out.writeByte(def.opacity)
        out.writeShort(def.parentId)
        out.writeShort(def.hoveredSiblingId)
        if (def.alternateOperators != null) {
            out.writeByte(def.alternateOperators.size)
            for (i in def.alternateOperators.indices) {
                out.writeByte(def.alternateOperators[i])
                out.writeShort(def.alternateRhs[i])
            }
        } else {
            out.writeByte(0)
        }
        if (def.clientScripts != null) {
            out.writeByte(def.clientScripts.size)
            for (i in def.clientScripts.indices) {
                var len = 0
                for (j in 0 until def.clientScripts[i].size) {
                    val ins = def.clientScripts[i][j]
                    len++
                    if (ins.operands != null) {
                        len += ins.operands.size
                    }
                }
                out.writeShort(len)
                for (j in 0 until def.clientScripts[i].size) {
                    val ins = def.clientScripts[i][j]
                    out.writeShort(ins.opcode.ordinal)
                    if (ins.operands != null) {
                        for (op in ins.operands) {
                            out.writeShort(op)
                        }
                    }
                }
            }
        } else {
            out.writeByte(0)
        }
        if (def.type == 0) {
            out.writeShort(def.scrollHeight)
            out.writeByte(if (def.isHidden) 1 else 0)
        }
        if (def.type == 1) {
            out.writeShort(0)
            out.writeByte(0)
        }
        if (def.type == 2) {
            out.writeByte(if (def.clickMask and 268435456 != 0) 1 else 0)
            out.writeByte(if (def.clickMask and 1073741824 != 0) 1 else 0)
            out.writeByte(if (def.clickMask and Int.MIN_VALUE != 0) 1 else 0)
            out.writeByte(if (def.clickMask and 536870912 != 0) 1 else 0)
            out.writeByte(def.xPitch)
            out.writeByte(def.yPitch)
            for (i in 0..19) {
                if (def.sprites[i] != -1) {
                    out.writeByte(1)
                    out.writeShort(def.xOffsets[i])
                    out.writeShort(def.yOffsets[i])
                    out.writeShort(def.sprites[i])
                } else {
                    out.writeByte(0)
                }
            }
            for (i in 0..4) {
                if (def.configActions[i] != null) {
                    out.writeString(def.configActions[i])
                } else {
                    out.writeString("")
                }
            }
        }
        if (def.type == 3) {
            out.writeByte(if (def.filled) 1 else 0)
        }
        if (def.type == 4 || def.type == 1) {
            out.writeByte(def.xTextAlignment)
            out.writeByte(def.yTextAlignment)
            out.writeByte(def.lineHeight)
            out.writeShort(def.fontId)
            out.writeByte(if (def.textShadowed) 1 else 0)
        }
        if (def.type == 4) {
            out.writeString(def.text)
            out.writeString(def.alternateText)
        }
        if (def.type == 1 || def.type == 3 || def.type == 4) {
            out.writeInt(def.textColor)
        }
        if (def.type == 3 || def.type == 4) {
            out.writeInt(def.alternateTextColor)
            out.writeInt(def.hoveredTextColor)
            out.writeInt(def.alternateHoveredTextColor)
        }
        if (def.type == 5) {
            out.writeInt(def.spriteId)
            out.writeInt(def.alternateSpriteId)
        }
        if (def.type == 6) {
            out.writeShort(def.modelId)
            out.writeShort(def.alternateModelId)
            out.writeShort(def.animation)
            out.writeShort(def.alternateAnimation)
            out.writeShort(def.modelZoom)
            out.writeShort(def.rotationX)
            out.writeShort(def.rotationZ)
        }
        if (def.type == 7) {
            out.writeByte(def.xTextAlignment)
            out.writeShort(def.fontId)
            out.writeByte(if (def.textShadowed) 1 else 0)
            out.writeInt(def.textColor)
            out.writeShort(def.xPitch)
            out.writeShort(def.yPitch)
            out.writeByte(if (def.clickMask and 1073741824 != 0) 1 else 0)
            for (i in 0..4) {
                out.writeString(def.configActions[i])
            }
        }
        if (def.type == 8) {
            out.writeString(def.text)
        }
        if (def.menuType == 2 || def.type == 2) {
            out.writeString(def.targetVerb)
            out.writeString(def.spellName)
            out.writeShort(def.clickMask ushr 11 and 63)
        }
        if (def.menuType == 1 || def.menuType == 4 || def.menuType == 5 || def.menuType == 6) {
            out.writeString(def.tooltip)
        }
        return out.flip()
    }

    override fun serialize(def: InterfaceDefinition): ByteArray {
        return save(def)
    }
}