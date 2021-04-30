/*
 * Copyright (c) 2016-2017, Adam <Adam@sigterm.info>
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
package com.javatar.output

import java.io.IOException
import java.io.OutputStream
import java.io.UnsupportedEncodingException
import java.nio.ByteBuffer

class OutputStream @JvmOverloads constructor(capacity: Int = 16) : OutputStream() {
    private var buffer: ByteBuffer
    val array: ByteArray
        get() {
            assert(buffer.hasArray())
            return buffer.array()
        }

    private fun ensureRemaining(remaining: Int) {
        while (remaining > buffer.remaining()) {
            val newCapacity = buffer.capacity() * 2
            val old = buffer
            old.flip()
            buffer = ByteBuffer.allocate(newCapacity)
            buffer.put(old)
        }
    }

    fun skip(length: Int) {
        var pos = buffer.position()
        pos += length
        buffer.position(pos)
    }

    var offset: Int
        get() = buffer.position()
        set(offset) {
            buffer.position(offset)
        }

    @JvmOverloads
    fun writeBytes(b: ByteArray, offset: Int = 0, length: Int = b.size) {
        ensureRemaining(length)
        buffer.put(b, offset, length)
    }

    fun writeByte(i: Int) {
        ensureRemaining(1)
        buffer.put(i.toByte())
    }

    fun writeBoolean(value: Boolean) {
        writeByte(if (value) 1 else 0)
    }

    fun writeBigSmart(value: Int) {
        if (value >= 32768) {
            ensureRemaining(4)
            writeInt(1 shl 31 or value)
        } else {
            ensureRemaining(2)
            writeShort(value)
        }
    }

    fun writeShort(i: Int) {
        ensureRemaining(2)
        buffer.putShort(i.toShort())
    }

    fun writeShortSmart(value: Int) {
        if (value < 128) {
            writeByte(value)
        } else {
            writeShort(0x8000 or value)
        }
    }

    fun write24BitInt(i: Int) {
        ensureRemaining(3)
        buffer.put((i ushr 16).toByte())
        buffer.put((i ushr 8).toByte())
        buffer.put((i and 0xFF).toByte())
    }

    fun writeInt(i: Int) {
        ensureRemaining(4)
        buffer.putInt(i)
    }

    fun writeVarInt(var1: Int) {
        if (var1 and -128 != 0) {
            if (var1 and -16384 != 0) {
                if (var1 and -2097152 != 0) {
                    if (var1 and -268435456 != 0) {
                        writeByte(var1 ushr 28 or 128)
                    }
                    writeByte(var1 ushr 21 or 128)
                }
                writeByte(var1 ushr 14 or 128)
            }
            writeByte(var1 ushr 7 or 128)
        }
        writeByte(var1 and 127)
    }

    fun writeLengthFromMark(var1: Int) {
        array[offset - var1 - 4] = (var1 shr 24).toByte()
        array[offset - var1 - 3] = (var1 shr 16).toByte()
        array[offset - var1 - 2] = (var1 shr 8).toByte()
        array[offset - var1 - 1] = var1.toByte()
    }

    fun writeString(str: String) {
        val b: ByteArray
        b = try {
            str.toByteArray(charset("ISO-8859-1"))
        } catch (ex: UnsupportedEncodingException) {
            throw RuntimeException(ex)
        }
        writeBytes(b)
        writeByte(0)
    }

    fun writeString2(str: String) {
        writeByte(0)
        val b = str.toByteArray()
        writeBytes(b)
        writeByte(0)
    }

    fun flip(): ByteArray {
        buffer.flip()
        val b = ByteArray(buffer.limit())
        buffer[b]
        return b
    }

    @Throws(IOException::class)
    override fun write(b: Int) {
        buffer.put(b.toByte())
    }

    init {
        buffer = ByteBuffer.allocate(capacity)
    }
}