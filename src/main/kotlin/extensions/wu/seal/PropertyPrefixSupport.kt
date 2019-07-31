package extensions.wu.seal

import com.intellij.util.ui.JBDimension
import extensions.Extension
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.ui.horizontalLinearLayout
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import javax.swing.JCheckBox
import javax.swing.JPanel
import javax.swing.JTextField

object PropertyPrefixSupport : Extension() {

    private const val prefixKeyEnable = "wu.seal.property_prefix_enable"
    private const val prefixKey = "wu.seal.property_prefix"

    override fun createUI(): JPanel {
        val prefixJField = JTextField().apply {
            text = getConfig(prefixKey)

            addFocusListener(object : FocusListener {
                override fun focusGained(e: FocusEvent?) {
                }

                override fun focusLost(e: FocusEvent?) {
                    if (getConfig(prefixKeyEnable).toBoolean()) {
                        setConfig(prefixKey, text)
                    }
                }
            })

            isEnabled = getConfig(prefixKeyEnable).toBoolean()
        }

        val checkBox = JCheckBox("Prefix append before every property: ").apply {
            isSelected = getConfig(prefixKeyEnable).toBoolean()
            addActionListener {
                setConfig(prefixKeyEnable, isSelected.toString())
                prefixJField.isEnabled = isSelected
            }
        }

        return horizontalLinearLayout {
            checkBox()
            prefixJField()
        }.apply {
            maximumSize = JBDimension(600,40)
        }

    }


    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {
        return if (getConfig(prefixKeyEnable).toBoolean() && getConfig(prefixKey).isNotEmpty()) {
            val originProperties = kotlinDataClass.properties
            val newProperties = originProperties.map {
                val prefix = getConfig(prefixKey)
                val newName = prefix + it.name.first().toUpperCase() + it.name.substring(1)
                it.copy(name = newName)
            }
            kotlinDataClass.copy(properties = newProperties)
        } else {
            kotlinDataClass
        }

    }
}