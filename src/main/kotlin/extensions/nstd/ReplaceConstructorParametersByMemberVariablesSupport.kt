package extensions.nstd

import extensions.Extension
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.ui.jCheckBox
import wu.seal.jsontokotlin.ui.jHorizontalLinearLayout
import wu.seal.jsontokotlin.ui.jLink
import javax.swing.JPanel

/**
 * Extension support replace constructor parameters by member variables
 *
 * default:
 *
 *     data class Foo(
 *         @SerializedName("a")
 *         val a: Int = 0 // 1
 *     )
 *
 *
 * after enable this:
 *
 *     data class Foo {
 *         @SerializedName("a")
 *         val a: Int = 0 // 1
 *     }
 *
 * Created by Nstd on 2020/6/29 17:45.
 */
object ReplaceConstructorParametersByMemberVariablesSupport : Extension() {

    const val configKey = "nstd.replace_constructor_parameters_by_member_variables"

    override fun createUI(): JPanel {

        return jHorizontalLinearLayout {
            jCheckBox(
                "",
                getConfig(configKey).toBoolean(),
                { isSelected -> setConfig(configKey, isSelected.toString()) }
            )
            jLink(
                "Replace constructor parameters by member variables",
                "https://github.com/wuseal/JsonToKotlinClass/blob/master/src/main/kotlin/extensions/nstd/ReplaceConstructorParametersByMemberVariablesSupport.kt"
            )
            fillSpace()
        }
    }

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        if (getConfig(configKey).toBoolean())
            if (kotlinClass is DataClass) {
                return kotlinClass.copy(codeBuilder = DataClassCodeBuilderForNoConstructorMemberFields(kotlinClass.codeBuilder))
            }
        return kotlinClass
    }
}