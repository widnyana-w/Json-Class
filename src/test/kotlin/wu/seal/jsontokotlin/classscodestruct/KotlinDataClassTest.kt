package wu.seal.jsontokotlin.classscodestruct

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.test.TestConfig

class KotlinDataClassTest {

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun getCode() {
        val normalProperty = Property(listOf(), "val", name = "name", type = "Type",  originJsonValue = "",originName = "",typeObject = KotlinClass.ANY)
        val withValueProperty = Property(listOf(), "val", name = "name", type = "Type", value = "Type()", comment = "comment", originJsonValue = "",originName = "",typeObject = KotlinClass.ANY)
        val properties = listOf(normalProperty, withValueProperty)
        val dataClass = KotlinDataClass( listOf(), "name", properties = properties)
        dataClass.getCode().should.be.equal(
            """data class name(
    val name: Type,
    val name: Type = Type() // comment
)"""
        )
    }
}