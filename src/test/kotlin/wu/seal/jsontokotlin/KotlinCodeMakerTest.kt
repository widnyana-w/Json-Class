package wu.seal.jsontokotlin

import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.test.TestConfig

/**
 *
 * Created by Seal.Wu on 2018/2/6.
 */
class KotlinCodeMakerTest {
    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun makeKotlinData() {
        val json1 = """{ "progr ammers": [
                { "isFirstName": "Brett", "lastName":"McLaughlin", "email": "aaaa" },
                { "firstName": "Jason", "lastName":"Hunter", "email": "bbbb" },
                { "firstName": "Elliotte", "lastName":"Harold", "email": "cccc" }
                ],
                "aut_hors": [
                { "firstName": "Isaac", "lastName": "Asimov", "genre": "science fiction" },
                { "firstName": "Tad", "lastName": "Williams", "genre": "fantasy" },
                { "firstName": "Frank", "lastName": "Peretti", "genre": "christian fiction" }
                ],
                "musicians": [
                { "firstName": "Eric", "lastName": "Clapton", "instrument": "guitar" },
                { "firstName": "Sergei", "lastName": "Rachmaninoff", "instrument": "piano" }
                ] } """

        val json2 = """ {"123menu": {
                "i d": [1,23,34],
                "value":[],
                "popup": {
                "m#@!$#%$#^%*^&(*)*(_)+{|}{:enu_item": [
                {"value": "New", "onclick": "CreateNewDoc()"},
                {"value": "Open", "onclick": "OpenDoc()"},
                {"value": "Close", "onclick": "CloseDoc()"}
                ]}
                }}"""

        val json3 = """{ "programmers": [
                { "isFirstName": "Brett", "lastName":"McLaughlin", "email": "aaaa" },
                { "firstName": "Jason", "lastName":"Hunter", "email": "bbbb" },
                { "firstName": "Elliotte", "lastName":"Harold", "email": "cccc" }
                ],
                "authors": [
                { "firstName": "Isaac", "lastName": "Asimov", "genre": "science fiction" },
                { "firstName": "Tad", "lastName": "Williams", "genre": "fantasy" },
                { "firstName": "Frank", "lastName": "Peretti", "genre": "christian fiction" }
                ],
                "musicians": [
                { "firstName": "Eric", "lastName": "Clapton", "instrument": "guitar" },
                { "firstName": "Sergei", "lastName": "Rachmaninoff", "instrument": "piano" }
                ] } """

        println("json1 ====>\n${KotlinCodeMaker("Class1", json1).makeKotlinData()}")
        println("json2 ====>\n${KotlinCodeMaker("Class2", json2).makeKotlinData()}")

        TestConfig.targetJsonConvertLib = TargetJsonConverter.Jackson
        TestConfig.isCommentOff = true
        TestConfig.isPropertiesVar = true
        TestConfig.propertyTypeStrategy = PropertyTypeStrategy.NotNullable

        println("===========================================Change to Jackson json lib support========================================= ")

        println("json1 ====>\n${KotlinCodeMaker("Class1", json1).makeKotlinData()}")
        println("json2 ====>\n${KotlinCodeMaker("Class2", json2).makeKotlinData()}")


        TestConfig.targetJsonConvertLib = TargetJsonConverter.FastJson
        TestConfig.isCommentOff = true
        TestConfig.isPropertiesVar = true
        TestConfig.propertyTypeStrategy = PropertyTypeStrategy.NotNullable

        println("===========================================Change to FastJson json lib support========================================= ")

        println("json1 ====>\n${KotlinCodeMaker("Class1", json1).makeKotlinData()}")
        println("json2 ====>\n${KotlinCodeMaker("Class2", json2).makeKotlinData()}")



        TestConfig.targetJsonConvertLib = TargetJsonConverter.Gson
        TestConfig.isCommentOff = false
        TestConfig.isPropertiesVar = false
        TestConfig.propertyTypeStrategy = PropertyTypeStrategy.Nullable

        println("===========================================Change to Gson json lib support========================================= ")

        println("json1 ====>\n${KotlinCodeMaker("Class1", json1).makeKotlinData()}")
        println("json2 ====>\n${KotlinCodeMaker("Class2", json2).makeKotlinData()}")


        TestConfig.isNestedClassModel = true

        println("===========================================Change to Gson json lib support And inner class model========================================= ")

        println("json1 ====>\n${KotlinCodeMaker("Class1", json1).makeKotlinData()}")
        println("json2 ====>\n${KotlinCodeMaker("Class2", json2).makeKotlinData()}")

        println("===========================================Change to None json lib support ========================================= ")

        TestConfig.isNestedClassModel = false
        TestConfig.targetJsonConvertLib = TargetJsonConverter.None
        println("json3 ====>\n${KotlinCodeMaker("Class3", json3).makeKotlinData()}")


        println("===========================================Change to None json lib support Without InitValue========================================= ")

        TestConfig.isNestedClassModel = false
        TestConfig.targetJsonConvertLib = TargetJsonConverter.None
        TestConfig.initWithDefaultValue = false
        TestConfig.propertyTypeStrategy = PropertyTypeStrategy.NotNullable
        println("json3 ====>\n${KotlinCodeMaker("Class3", json3).makeKotlinData()}")
    }


    @Test
    fun makeKotlinDataWithCustomAnnotation() {
        TestConfig.targetJsonConvertLib = TargetJsonConverter.Custom
        TestConfig.isNestedClassModel = false
        TestConfig.customPropertyAnnotationFormatString = "@Optional\n@SerialName(\"%s\")"
        TestConfig.customClassAnnotationFormatString = "@Serializable"
        TestConfig.customAnnotaionImportClassString =
                "import kotlinx.serialization.SerialName\nimport kotlinx.serialization.Serializable"
        val json = """{ "progr ammers": [
                { "isFirstName": "Brett", "lastName":"McLaughlin", "email": "aaaa" },
                { "firstName": "Jason", "lastName":"Hunter", "email": "bbbb" },
                { "firstName": "Elliotte", "lastName":"Harold", "email": "cccc" }
                ],
                "aut_hors": [
                { "firstName": "Isaac", "lastName": "Asimov", "genre": "science fiction" },
                { "firstName": "Tad", "lastName": "Williams", "genre": "fantasy" },
                { "firstName": "Frank", "lastName": "Peretti", "genre": "christian fiction" }
                ],
                "musicians": [
                { "firstName": "Eric", "lastName": "Clapton", "instrument": "guitar" },
                { "firstName": "Sergei", "lastName": "Rachmaninoff", "instrument": "piano" }
                ] } """

        val result = KotlinCodeMaker("TestData", json).makeKotlinData()
        println(result)
    }


    @Test
    fun makeAllConfigCode() {
        val json = """{ "progr ammers": [
                { "isFirstName": "Brett", "lastName":"McLaughlin", "email": "aaaa" },
                { "firstName": "Jason", "lastName":"Hunter", "email": "bbbb" },
                { "firstName": "Elliotte", "lastName":"Harold", "email": "cccc" }
                ],
                "aut_hors": [
                { "firstName": "Isaac", "lastName": "Asimov", "genre": "science fiction" },
                { "firstName": "Tad", "lastName": "Williams", "genre": "fantasy" },
                { "firstName": "Frank", "lastName": "Peretti", "genre": "christian fiction" }
                ],
                "musicians": [
                { "firstName": "Eric", "lastName": "Clapton", "instrument": "guitar" },
                { "firstName": "Sergei", "lastName": "Rachmaninoff", "instrument": "piano" }
                ] } """
        ConfigManagerTestHelper().testAllBoolConfigsWithAction {
            val code = KotlinCodeMaker("ClassName", json).makeKotlinData()
            println(code)
        }
    }

}