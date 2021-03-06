package extensions.wu.seal

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.test.TestConfig

class ForceInitDefaultValueWithOriginJsonValueSupportTest {
    val json = """{"a":1}"""

    val expectResult = """data class Test(
    val a: Int = 1 // 1
)"""
    var escapedCharacterJson = """{"key": "value with \"quote\""}"""

    val escapedCharacterExpectResult = """data class Test(
    val key: String = ""${'"'}value with "quote""${'"'}${'"'} // value with "quote"
)"""


    val configKey = "wu.seal.force_init_default_value_with_origin_json_value"


    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {
        ForceInitDefaultValueWithOriginJsonValueSupport.getTestHelper().setConfig(configKey, true.toString())
        val dataClass =
                json.generateKotlinDataClass("Test").applyInterceptor(ForceInitDefaultValueWithOriginJsonValueSupport)
        val resultCode = dataClass.getCode()
        resultCode.should.be.equal(expectResult)
    }

    @Test
    fun interceptWithEscapeCharacter(){
        ForceInitDefaultValueWithOriginJsonValueSupport.getTestHelper().setConfig(configKey, true.toString())
        val dataClass =
                escapedCharacterJson.generateKotlinDataClass("Test").applyInterceptor(ForceInitDefaultValueWithOriginJsonValueSupport)
        val resultCode = dataClass.getCode()
        resultCode.should.be.equal(escapedCharacterExpectResult)
    }
}