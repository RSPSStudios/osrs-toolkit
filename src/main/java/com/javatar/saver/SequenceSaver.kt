package com.javatar.saver

import com.javatar.definition.SerializableDefinition
import com.javatar.osrs.definitions.impl.SequenceDefinition
import com.javatar.output.OutputStream

class SequenceSaver : SerializableDefinition<SequenceDefinition> {
    override fun serialize(def: SequenceDefinition): ByteArray {
        val out = OutputStream()
        if (def.frameLengths != null && def.frameIDs != null) {
            out.writeByte(1)
            out.writeShort(def.frameLengths.size)
            for (length in def.getFrameLengths()) {
                out.writeShort(length)
            }
            for (frameID in def.frameIDs) {
                out.writeShort(frameID)
            }
            for (frameID in def.frameIDs) {
                out.writeShort(frameID shr 16)
            }
        }
        if (def.frameStep != -1) {
            out.writeByte(2)
            out.writeShort(def.frameStep)
        }
        if (def.interleaveLeave != null) {
            out.writeByte(3)
            out.writeByte(def.interleaveLeave.size - 1)
            for (i in 0 until def.interleaveLeave.size - 1) {
                out.writeByte(def.interleaveLeave[i])
            }
        }
        if (def.stretches) {
            out.writeByte(4)
        }
        if (def.forcedPriority != 5) {
            out.writeByte(5)
            out.writeByte(def.forcedPriority)
        }
        if (def.leftHandItem != -1) {
            out.writeByte(6)
            out.writeShort(def.leftHandItem)
        }
        if (def.rightHandItem != -1) {
            out.writeByte(7)
            out.writeShort(def.rightHandItem)
        }
        if (def.maxLoops != 99) {
            out.writeByte(8)
            out.writeByte(def.maxLoops)
        }
        if (def.precedenceAnimating != -1) {
            out.writeByte(9)
            out.writeByte(def.precedenceAnimating)
        }
        if (def.priority != -1) {
            out.writeByte(10)
            out.writeByte(def.priority)
        }
        if (def.replyMode != 2) {
            out.writeByte(11)
            out.writeByte(def.replyMode)
        }
        if (def.chatFrameIds != null) {
            out.writeByte(12)
            out.writeByte(def.chatFrameIds.size)
            for (chatFrameId in def.chatFrameIds) {
                out.writeShort(chatFrameId)
            }
            for (chatFrameId in def.chatFrameIds) {
                out.writeShort(chatFrameId shr 16)
            }
        }
        if (def.frameSounds != null) {
            out.writeByte(13)
            out.writeByte(def.frameSounds.size)
            for (frameSound in def.frameSounds) {
                out.write24BitInt(frameSound)
            }
        }
        out.writeByte(0)
        return out.flip()
    }
}