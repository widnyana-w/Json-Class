package wu.seal.jsontokotlin

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.winterbe.expekt.should
import org.junit.Test

import org.junit.Before
import wu.seal.jsontokotlin.model.classscodestruct.GenericListClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.classgenerator.DataClassGeneratorByJSONObject

class DataClassGeneratorByJSONObjectTest {

    val json ="""
{
    "glossary":{
        "title":"example glossary",
        "GlossDiv":{
            "title":"S",
            "GlossList":{
                "GlossEntry":{
                    "ID":"SGML",
                    "SortAs":"SGML",
                    "GlossTerm":"Standard Generalized Markup Language",
                    "Acronym":"SGML",
                    "Abbrev":"ISO 8879:1986",
                    "GlossDef":{
                        "para":"A meta-markup language, used to create markup languages such as DocBook.",
                        "GlossSeeAlso":[
                            "GML",
                            "XML"
                        ]
                    },
                    "GlossSee":"markup"
                }
            }
        }
    }
}
    """.trimIndent()
    @Before
    fun before() {
        TestConfig.setToTestInitState()
    }
    @Test
    fun generate() {
        val jsonObject = Gson().fromJson(json,JsonObject::class.java)
        val dataClass = DataClassGeneratorByJSONObject("Test", jsonObject).generate()
        dataClass.name.should.be.equal("Test")
        val p1 = dataClass.properties[0]
        p1.name.should.be.equal("glossary")
        p1.type.should.be.equal("Glossary")
        p1.typeObject.should.not.`null`
        val p1RefDataClass = p1.typeObject as DataClass
        p1RefDataClass.name.should.be.equal("Glossary")

        val p1p1 = p1RefDataClass.properties[0]
        p1p1.name.should.be.equal("title")
        p1p1.type.should.be.equal(KotlinClass.STRING.name)
        p1p1.originJsonValue.should.be.equal("example glossary")

        val p1p2 = p1RefDataClass.properties[1]
        p1p2.name.should.be.equal("GlossDiv")
        p1p2.type.should.be.equal("GlossDiv")
        p1p2.typeObject.should.not.`null`
        val p1p2RefDataClass = p1p2.typeObject as DataClass
        p1p2RefDataClass.name.should.be.equal("GlossDiv")
        p1p2RefDataClass.properties.size.should.be.equal(2)

        val p1p2p1 = p1p2RefDataClass.properties[0]
        p1p2p1.name.should.be.equal("title")
        p1p2p1.originJsonValue.should.be.equal("S")
        p1p2p1.type.should.be.equal(KotlinClass.STRING.name)
        p1p2p1.typeObject.should.equal(KotlinClass.STRING)

        val p1p2p2 = p1p2RefDataClass.properties[1]
        p1p2p2.name.should.be.equal("GlossList")
        p1p2p2.type.should.be.equal("GlossList")
        p1p2p2.typeObject.should.not.`null`
        val p1p2p2RefDataClass = p1p2p2.typeObject as DataClass
        p1p2p2RefDataClass.name.should.be.equal("GlossList")
        p1p2p2RefDataClass.properties.size.should.be.equal(1)

        val p1p2p2p1 = p1p2p2RefDataClass.properties[0]
        p1p2p2p1.typeObject.should.not.`null`
        p1p2p2p1.name.should.be.equal("GlossEntry")
        p1p2p2p1.type.should.be.equal("GlossEntry")
        p1p2p2p1.originJsonValue.should.be.empty
        val p1p2p2p1RefDataClass = p1p2p2p1.typeObject as DataClass
        p1p2p2p1RefDataClass.properties.size.should.be.equal(7)

        val glossEntryP1 = p1p2p2p1RefDataClass.properties[0]
        glossEntryP1.name.should.be.equal("ID")
        glossEntryP1.originJsonValue.should.be.equal("SGML")
        glossEntryP1.type.should.be.equal(KotlinClass.STRING.name)
        glossEntryP1.typeObject.should.equal(KotlinClass.STRING)

        val glossEntryP2 = p1p2p2p1RefDataClass.properties[1]
        glossEntryP2.name.should.be.equal("SortAs")
        glossEntryP2.originJsonValue.should.be.equal("SGML")
        glossEntryP2.type.should.be.equal(KotlinClass.STRING.name)
        glossEntryP2.typeObject.should.be.equal(KotlinClass.STRING)

        val glossEntryP3 = p1p2p2p1RefDataClass.properties[2]
        glossEntryP3.name.should.be.equal("GlossTerm")
        glossEntryP3.originJsonValue.should.be.equal("Standard Generalized Markup Language")
        glossEntryP3.type.should.be.equal(KotlinClass.STRING.name)
        glossEntryP3.typeObject.should.equal(KotlinClass.STRING)

        val glossEntryP4 = p1p2p2p1RefDataClass.properties[3]
        glossEntryP4.name.should.be.equal("Acronym")
        glossEntryP4.originJsonValue.should.be.equal("SGML")
        glossEntryP4.type.should.be.equal(KotlinClass.STRING.name)
        glossEntryP4.typeObject.should.be.equal(KotlinClass.STRING)

        val glossEntryP5 = p1p2p2p1RefDataClass.properties[4]
        glossEntryP5.name.should.be.equal("Abbrev")
        glossEntryP5.originJsonValue.should.be.equal("ISO 8879:1986")
        glossEntryP5.type.should.be.equal(KotlinClass.STRING.name)
        glossEntryP5.typeObject.should.be.equal(KotlinClass.STRING)

        val glossEntryP7 = p1p2p2p1RefDataClass.properties[6]
        glossEntryP7.name.should.be.equal("GlossSee")
        glossEntryP7.originJsonValue.should.be.equal("markup")
        glossEntryP7.type.should.be.equal(KotlinClass.STRING.name)
        glossEntryP7.typeObject.should.be.equal(KotlinClass.STRING)

        val glossEntryP6 = p1p2p2p1RefDataClass.properties[5]

        glossEntryP6.name.should.be.equal("GlossDef")
        glossEntryP6.type.should.be.equal("GlossDef")
        glossEntryP6.typeObject.should.be.not.`null`
        glossEntryP6.originJsonValue.should.be.empty

        val glossDefDataClass = glossEntryP6.typeObject as DataClass
        glossDefDataClass.name.should.be.equal("GlossDef")
        glossDefDataClass.properties.size.should.be.equal(2)

        val glossDefP1 = glossDefDataClass.properties[0]
        glossDefP1.name.should.be.equal("para")
        glossDefP1.originJsonValue.should.be.equal("A meta-markup language, used to create markup languages such as DocBook.")
        glossDefP1.type.should.be.equal(KotlinClass.STRING.name)
        glossDefP1.typeObject.should.equal(KotlinClass.STRING)

        val glossDefP2 = glossDefDataClass.properties[1]
        glossDefP2.name.should.be.equal("GlossSeeAlso")
        glossDefP2.type.should.be.equal(GenericListClass(KotlinClass.STRING).name)
        glossDefP2.originJsonValue.should.be.empty
        glossDefP2.typeObject.should.be.equal(GenericListClass(KotlinClass.STRING))
    }
}