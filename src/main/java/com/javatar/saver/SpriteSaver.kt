package com.javatar.saver

import com.javatar.definition.SerializableDefinition
import com.javatar.osrs.definitions.impl.SpriteGroupDefinition
import com.javatar.output.OutputStream

class SpriteSaver : SerializableDefinition<SpriteGroupDefinition> {
    fun save(group: SpriteGroupDefinition): ByteArray {
        var palette = IntArray(1)
        if (group.sprites.isNotEmpty()) {
            palette = group.sprites[0].palette
        }
        var length = 7 + 9 * group.sprites.size + palette.size * 3
        for (s in group) {
            length += s.getPixelIdx().size
        }
        val out = OutputStream(length)
        for (def in group) {
            out.writeByte(0x0)
            val pixels = def.getPixelIdx()
            for (pixel in pixels) {
                out.writeByte(pixel.toInt())
            }
        }
        for (value in palette) {
            out.write24BitInt(value)
        }
        if (group.sprites.size == 1) {
            out.writeShort(group.sprites[0].width)
            out.writeShort(group.sprites[0].height)
        } else {
            out.writeShort(group.width)
            out.writeShort(group.height)
        }
        out.writeByte(palette.size - 1)
        for (sprite in group) {
            out.writeShort(sprite.offsetX)
        }
        for (sprite in group) {
            out.writeShort(sprite.offsetY)
        }
        for (sprite in group) {
            out.writeShort(sprite.width)
        }
        for (sprite in group) {
            out.writeShort(sprite.height)
        }
        out.writeShort(group.sprites.size)
        return out.flip()
    }

    override fun serialize(def: SpriteGroupDefinition): ByteArray {
        return save(def)
    }
}