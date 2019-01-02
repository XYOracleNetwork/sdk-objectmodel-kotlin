package network.xyo.sdkobjectmodelkotlin

import network.xyo.sdkobjectmodelkotlin.exceptions.XyoObjectIteratorException
import network.xyo.sdkobjectmodelkotlin.objects.XyoIterableObject
import org.junit.Assert
import org.junit.Test

class XyoObjectIteratorTest  {

    @Test
    fun testObjectIteratorUntyped () {
        val iterator = object : XyoIterableObject() {
            override val allowedOffset: Int = 0
            override var item: ByteArray = byteArrayOf(0x20, 0x41, 0x09, 0x00, 0x44, 0x02, 0x14, 0x00, 0x42, 0x02, 0x37)
        }.iterator
        var index = 0

        while (iterator.hasNext()) {

            if (index == 0) {
                Assert.assertArrayEquals(iterator.next().bytesCopy, byteArrayOf(0x00, 0x44, 0x02, 0x14))
            }

            if (index == 1) {
                Assert.assertArrayEquals(iterator.next().bytesCopy, byteArrayOf(0x00, 0x42, 0x02, 0x37))
            }

            index++
        }
    }

    @Test
    fun testObjectIteratorTyped () {

        val iterator = object : XyoIterableObject() {
            override val allowedOffset: Int = 0
            override var item: ByteArray = byteArrayOf(0x30, 0x41, 0x07, 0x00, 0x44, 0x02, 0x13, 0x02, 0x37)
        }.iterator
        var index = 0

        while (iterator.hasNext()) {

            if (index == 0) {
                Assert.assertArrayEquals(iterator.next().bytesCopy, byteArrayOf(0x00, 0x44, 0x02, 0x13))
            }

            if (index == 1) {
                Assert.assertArrayEquals(iterator.next().bytesCopy, byteArrayOf(0x00, 0x44, 0x02, 0x37))
            }

            index++
        }
    }

    @Test
    fun testGetAtIndex () {
        val iterator = object : XyoIterableObject() {
            override val allowedOffset: Int = 0
            override var item: ByteArray = byteArrayOf(0x30, 0x41, 0x07, 0x00, 0x44, 0x02, 0x13, 0x02, 0x37)
        }

        Assert.assertArrayEquals(byteArrayOf(0x00, 0x44, 0x02, 0x13), iterator[0].bytesCopy)
        Assert.assertArrayEquals(byteArrayOf(0x00, 0x44, 0x02, 0x37), iterator[1].bytesCopy)
    }

    @Test
    fun testGetSize () {
        val iterator = object : XyoIterableObject() {
            override val allowedOffset: Int = 0
            override var item: ByteArray = byteArrayOf(0x30, 0x41, 0x07, 0x00, 0x44, 0x02, 0x13, 0x02, 0x37)
        }

        Assert.assertEquals(2, iterator.count)
    }

    @Test
    fun testWrongTypes () {
        try {
            val iterator = object : XyoIterableObject() {
                override val allowedOffset: Int = 0
                override var item: ByteArray = byteArrayOf(0x20, 0x41, 0x07, 0x00, 0x44, 0x02, 0x13, 0x02, 0x37)
            }.iterator

            for (item in iterator) { }

            throw Exception("Expected XyoObjectIteratorException to be thrown!")
        } catch (e : XyoObjectIteratorException) { }
    }
}