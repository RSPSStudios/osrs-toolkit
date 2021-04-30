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
import com.javatar.osrs.definitions.impl.MapDefinition
import com.javatar.output.OutputStream

class MapSaver : SerializableDefinition<MapDefinition> {
    fun save(map: MapDefinition): ByteArray {
        val tiles = map.tiles
        val out = OutputStream()
        for (z in 0 until MapDefinition.Z) {
            for (x in 0 until MapDefinition.X) {
                for (y in 0 until MapDefinition.Y) {
                    val tile = tiles[z][x][y]
                    if (tile.attrOpcode != 0) {
                        out.writeByte(tile.attrOpcode)
                        out.writeByte(tile.overlayId.toInt())
                    }
                    if (tile.settings.toInt() != 0) {
                        out.writeByte(tile.settings + 49)
                    }
                    if (tile.underlayId.toInt() != 0) {
                        out.writeByte(tile.underlayId + 81)
                    }
                    if (tile.height == null) {
                        out.writeByte(0)
                    } else {
                        out.writeByte(1)
                        out.writeByte(tile.height)
                    }
                }
            }
        }
        return out.flip()
    }

    override fun serialize(def: MapDefinition): ByteArray {
        return save(def)
    }
}