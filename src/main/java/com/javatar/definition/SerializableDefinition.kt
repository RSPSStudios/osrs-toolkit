package com.javatar.definition

import com.javatar.osrs.definitions.Definition

fun interface SerializableDefinition<T : Definition> {
    fun serialize(def: T): ByteArray
}