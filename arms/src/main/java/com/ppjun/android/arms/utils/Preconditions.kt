package com.ppjun.android.arms.utils

/**
 * Created by ppjun on 3/7/18.
 */
class Preconditions {
    companion object {


        fun <T> checkNotNull(reference: T, errorMessageTemplate: String, vararg errorMessageArgs: Any): T {
            if (reference == null) {
                throw NullPointerException(format(errorMessageTemplate,errorMessageArgs))
            }

            return reference
        }

        fun checkState(expression:Boolean,errorMessage:Any){
               if(!expression){
                   throw IllegalStateException(errorMessage.toString())
               }

        }


        fun format(template: String, vararg args: Any): String {
            val builder = StringBuilder(template.length + 16 * args.size)
            var templateStart = 0
            var i = 0

            while (i < args.size) {
                val placeholderStart = template.indexOf("%s", templateStart)
                if (placeholderStart == -1) {
                    break
                }
                builder.append(template.substring(templateStart, placeholderStart))
                builder.append(args[i++])
                templateStart = placeholderStart + 2
            }
            builder.append(template.substring(templateStart))
            if (i < args.size) {
                builder.append("[")
                builder.append(args[i++])
                while (i < args.size) {
                    builder.append(",")
                    builder.append(args[i++])
                }
                builder.append("]")
            }
            return builder.toString()
        }
    }


}