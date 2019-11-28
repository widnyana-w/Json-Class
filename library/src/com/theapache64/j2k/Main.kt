import wu.seal.jsontokotlin.*
import wu.seal.jsontokotlin.test.TestConfig

fun main() {
    TestConfig.setToTestInitState()

    val json1 = """{ "programmers": [
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


    val output = JsonToKotlinBuilder()
            .setPropertiesVar(false)
            .setPropertyTypeStrategy(PropertyTypeStrategy.AutoDeterMineNullableOrNot)
            .setDefaultValueStrategy(DefaultValueStrategy.AvoidNull)
            .setAnnotationLib(TargetJsonConverter.MoshiCodeGen)
            .setComment(true)
            .setOrderByAlphabetic(true)
            .setInnerClassModel(true)
            .setMapType(true)
            .setCreateAnnotationOnlyWhenNeeded(true)
            .setIndent(4)
            .setParentClassTemplate("android.os.Parcelable")
            .setKeepAnnotationOnClass(true)
            /*.setKeepAnnotationOnClassAndroidX(true)
            .setKeepAnnotationAndPropertyInSameLine(true)
            .setParcelableSupport(true)
            .setPropertyPrefix("MyPrefix")
            .setPropertySuffix("MySuffix")
            .setClassSuffix("MyClassSuffix")
            .setForcePrimitiveTypeNonNullable(true)
            .setForceInitDefaultValueWithOriginJsonValue(true)*/
            .build(json1, "GlossResponse")

    println("json1 ====>\n${output}")
}

class KotlinCodeMaker(private val className: String, private val inputJson: String) {

    fun makeKotlinData(): String {
        return KotlinDataClassCodeMaker(
                KotlinDataClassMaker(
                        className,
                        inputJson
                ).makeKotlinDataClass()
        ).makeKotlinDataClassCode()
    }
}
