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

import com.google.common.collect.LinkedListMultimap
import com.google.common.collect.Multimap
import com.javatar.definition.SerializableDefinition
import com.javatar.osrs.definitions.impl.LocationsDefinition
import com.javatar.osrs.definitions.impl.map.Location
import com.javatar.output.OutputStream

class LocationSaver : SerializableDefinition<LocationsDefinition> {
    fun save(locs: LocationsDefinition): ByteArray {
        val locById: Multimap<Int, Location> = LinkedListMultimap.create()
        val sortedLocs: List<Location> = ArrayList(locs.locations)
        sortedLocs.sortedWith(Comparator.comparingInt { it.id })
        for (loc in sortedLocs) {
            locById.put(loc.id, loc)
        }
        val out = OutputStream()
        var prevId = -1
        for (id in locById.keySet()) {
            val diffId = id - prevId
            prevId = id
            out.writeShortSmart(diffId)
            val locations = locById[id]
            var position = 0
            for (loc in locations) {
                val packedPosition = (loc.position.z shl 12
                        or (loc.position.x shl 6)
                        or loc.position.y)
                val diffPos = packedPosition - position
                position = packedPosition
                out.writeShortSmart(diffPos + 1)
                val packedAttributes = loc.type shl 2 or loc.orientation
                out.writeByte(packedAttributes)
            }
            out.writeShortSmart(0)
        }
        out.writeShortSmart(0)
        return out.flip()
    }

    override fun serialize(def: LocationsDefinition): ByteArray {
        return save(def)
    }
}