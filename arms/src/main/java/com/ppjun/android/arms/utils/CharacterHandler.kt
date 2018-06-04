package com.ppjun.android.arms.utils

import android.text.TextUtils
import org.json.JSONArray
import org.json.JSONObject
import java.io.StringReader
import java.io.StringWriter
import javax.xml.transform.OutputKeys
import javax.xml.transform.Source
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

/**
 * Created by ppjun on 3/9/18.
 */
class CharacterHandler {

    private constructor() {
        throw IllegalStateException("you can't instanytiate me!")
    }

    companion object {


        /**
         * json 格式化
         *
         * @param json
         * @return
         */
        fun jsonFormat(json: String): String {
            if (json.isEmpty()) {
                return "empty/null json content"
            }
            var message = ""

            try {
                var jsonTrim = json.trim()
                if (jsonTrim.startsWith("{")) {
                    var jsonObject = JSONObject(jsonTrim)
                    message = jsonObject.toString(4)
                } else if (jsonTrim.startsWith("[")) {
                    var jsonArray = JSONArray(jsonTrim)
                    message = jsonArray.toString(4)

                } else {
                    message = json
                }
            } catch (e: Exception) {
                message = json
            }
            return message

        }

        /**
         * xml 格式化
         *
         * @param xml
         * @return
         */
        fun xmlFormat(xml: String): String {
            if (xml.isEmpty()) {
                return "empty/null xml content"
            }
            var message = ""
            try {
                var xmlInput = StreamSource(StringReader(xml))
                var xmlOutput = StreamResult(StringWriter())
                var transformer = TransformerFactory.newInstance().newTransformer()
                transformer.outputProperties.setProperty(OutputKeys.INDENT, "yes")
                transformer.outputProperties.setProperty("{http://xml.apache.org/xslt}indent-amount", "2")
                transformer.transform(xmlInput, xmlOutput)
                message = xmlOutput.writer.toString().replaceFirst(">", ">\n")
            } catch (e: TransformerException) {
                message = xml
            }

            return message
        }

    }
}