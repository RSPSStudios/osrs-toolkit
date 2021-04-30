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
import com.javatar.osrs.definitions.impl.ScriptDefinition
import com.javatar.osrs.definitions.impl.cs2.Opcodes
import com.javatar.output.OutputStream

class ScriptSaver : SerializableDefinition<ScriptDefinition> {
    fun save(script: ScriptDefinition): ByteArray {
        val instructions = script.instructions
        val intOperands = script.intOperands
        val stringOperands = script.stringOperands
        val switches = script.switches
        val out = OutputStream()
        out.writeByte(0) // null string
        for (i in instructions.indices) {
            val opcode = instructions[i]
            out.writeShort(opcode)
            if (Opcodes.SCONST == opcode) {
                out.writeString(stringOperands[i])
            } else if (opcode < 100 && opcode != Opcodes.RETURN && opcode != Opcodes.POP_INT && opcode != Opcodes.POP_STRING) {
                out.writeInt(intOperands[i])
            } else {
                out.writeByte(intOperands[i])
            }
        }
        out.writeInt(instructions.size)
        out.writeShort(script.localIntCount)
        out.writeShort(script.localStringCount)
        out.writeShort(script.intStackCount)
        out.writeShort(script.stringStackCount)
        val switchStart = out.offset
        if (switches == null) {
            out.writeByte(0)
        } else {
            out.writeByte(switches.size)
            for (s in switches) {
                out.writeShort(s.size)
                for ((key, value) in s) {
                    out.writeInt(key!!)
                    out.writeInt(value!!)
                }
            }
        }
        val switchLength = out.offset - switchStart
        out.writeShort(switchLength)
        return out.flip()
    }

    override fun serialize(def: ScriptDefinition): ByteArray {
        return save(def)
    }
}