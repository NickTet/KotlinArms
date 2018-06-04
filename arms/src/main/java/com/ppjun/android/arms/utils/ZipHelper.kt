package com.ppjun.android.arms.utils

import java.io.ByteArrayInputStream
import java.io.Closeable
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.zip.GZIPInputStream
import java.util.zip.Inflater

/**
 * Created by ppjun on 3/9/18.
 */
class ZipHelper {


    private constructor() {
        throw IllegalStateException("you can't instantiate me!")
    }


    companion object {


        fun decompressToStringForZlib(bytesToCompress: ByteArray) {
            decompressToStringForZlib(bytesToCompress, "UTF-8")
        }


        fun decompressToStringForZlib(bytesToCompress: ByteArray, charset: String): String {

            var bytesDecompressed = decompressForZlib(bytesToCompress)

            var returnValue = ""
            try {
                returnValue = String(
                        bytesDecompressed!!,
                        0,
                        bytesDecompressed.size,
                        Charset.forName(charset)

                )
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }
            return returnValue

        }

        private fun decompressForZlib(bytesToCompress: ByteArray): ByteArray? {

            var returnValues: ByteArray? = null

            var inflater = Inflater()

            var numberOfBytesToDecompress = bytesToCompress.size

            inflater.setInput(bytesToCompress,
                    0,
                    numberOfBytesToDecompress)
            var bufferSizeInBytes = numberOfBytesToDecompress
            var numberOfBytesDecompressedSoFar = 0
            var bytesDecompressedSoFar = arrayListOf<Byte>()

            try {
                while (inflater.needsInput().not()) {
                    var bytesDecompressedBuffer = ByteArray(bufferSizeInBytes)
                    var numberOfBytesDecompressedThisTime = inflater.inflate(bytesDecompressedBuffer)
                    numberOfBytesDecompressedSoFar += numberOfBytesDecompressedThisTime

                    var b = 0
                    while (b < numberOfBytesDecompressedThisTime) {

                        bytesDecompressedSoFar.add(bytesDecompressedBuffer[b])
                        b++
                    }
                }
                returnValues = ByteArray(bytesDecompressedSoFar.size)
                var b = 0
                while (b < returnValues.size) {
                    returnValues[b] = bytesDecompressedSoFar[b]
                    b++
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            inflater.end()
            return returnValues
        }


    fun decompressForGzip(compressed: ByteArray): String {
        return decompressForGzip(compressed, "UTF-8")
    }

    fun decompressForGzip(compressed: ByteArray, charset: String): String {

        val BUFFER_SIZE = compressed.size
        var gis: GZIPInputStream? = null
        var bis: ByteArrayInputStream? = null

        try {
            bis = ByteArrayInputStream(compressed)
            gis = GZIPInputStream(bis, BUFFER_SIZE)
            var data: ByteArray = ByteArray(BUFFER_SIZE)
            var string = StringBuilder()
            var bytesRead: Int = gis.read(data)
            while (bytesRead != -1) {
                string.append(String(data, 0, bytesRead, Charset.forName(charset)))
                bytesRead = gis.read(data)
            }
            return string.toString()

        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            closeIO(gis)
            closeIO(bis)
        }

        return ""

    }


    fun closeIO(closeable: Closeable?) {
        try {
            closeable?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    }
}